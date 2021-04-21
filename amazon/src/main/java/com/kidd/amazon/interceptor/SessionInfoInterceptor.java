package com.kidd.amazon.interceptor;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.kidd.amazon.common.IpUtils;
import com.kidd.amazon.service.AuthUserService;
import com.kidd.amazon.task.AsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class SessionInfoInterceptor implements HandlerInterceptor {

    @Autowired
    private AsyncTask asyncTask;

    @Autowired
    private AuthUserService authUserService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        System.out.println( request.getRequestURL().toString()+request.getQueryString());
        System.out.println(request.getRequestURI());
        //ip
        String ip = IpUtils.getIpAddress(request);
        String ul = request.getHeader("Accept-Language");
        String ua = request.getHeader("User-Agent");
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String dateTime = DateUtil.format(localDateTime, "yyyy-MM-dd HH:mm:ss");
        asyncTask.asyncWriteAccessLog(request,ip,null,null,dateTime);
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap = (LinkedHashMap<String, String>) session.getAttribute("userInfo");
        if(null == userInfoMap){
            userInfoMap = new LinkedHashMap<>();
        }
        userInfoMap.put("UserAgent", ua);
        userInfoMap.put("DateTime", dateTime);
        userInfoMap.put("Ip", IpUtils.getIpAddress(request));
        userInfoMap.put("Al", ul);
        session.setAttribute("userInfo",userInfoMap);
        Boolean authBool =  authUserService.auth(request);
        if(authBool){
            return true;
        }
        response.sendRedirect("https://www.amazon.co.jp/gp/css/homepage.html/ref=nav_youraccount_ya");
        return false;
    }
}
