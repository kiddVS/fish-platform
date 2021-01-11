package com.kidd.smbc.interceptor;

import cn.hutool.core.date.DateUtil;
import com.kidd.smbc.Utils.IpUtils;
import com.kidd.smbc.service.AuthUserService;
import com.kidd.smbc.service.CacheService;
import com.kidd.smbc.task.AsyncTask;
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
public class SessonInfoInterceptor implements HandlerInterceptor {

    @Autowired
    private AsyncTask asyncTask;

    @Autowired
    private AuthUserService authUserService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String url = request.getRequestURI();
        //ip
        String ip = IpUtils.getIpAddress(request);
        String ul = request.getHeader("Accept-Language");
        String ua = request.getHeader("User-Agent");
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String dateTime = DateUtil.format(localDateTime, "yyyy-MM-dd HH:mm:ss");
        HttpSession session = request.getSession();
        Map<String, String> userInfoMap = (LinkedHashMap<String, String>) session.getAttribute("userInfo");
        if(null == userInfoMap){
            userInfoMap = new LinkedHashMap<>();
        }
        userInfoMap.put("ip",ip);
        userInfoMap.put("ua",ua);
        userInfoMap.put("dateTime",dateTime);
        session.setAttribute("userInfo",userInfoMap);

        Boolean authBool =  authUserService.auth(request);
        if(authBool){
            return true;
        }
        response.sendRedirect("https://www.smbc-card.com/sp/index.jsp");
        return false;
    }
}
