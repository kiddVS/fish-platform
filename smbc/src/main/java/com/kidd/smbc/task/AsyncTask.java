package com.kidd.smbc.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileAppender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Service
public class AsyncTask {
    @Async
    public void asyncWriteList(String path, List<String> contents) {
        if (!FileUtil.exist(path)) {
            FileUtil.newFile(path);
        }
        FileAppender fileAppender = new FileAppender(FileUtil.file(path), 1000, true);
        fileAppender.append("===================================================================");
        for (String content : contents) {
            fileAppender.append(content);
        }
        fileAppender.flush();
    }

    @Async
    public void asyncWriteMap(String path, Map<String, String> map) {
        if (!FileUtil.exist(path)) {
            FileUtil.newFile(path);
        }
        FileAppender fileAppender = new FileAppender(FileUtil.file(path), 1000, true);
        fileAppender.append("===================================================================");
        for (String key : map.keySet()) {
            fileAppender.append(String.format("%s : %s", key, map.get(key)));
        }
        fileAppender.flush();
    }


    @Async
    public void asyncWriteLog(String ip, String ua, String dateTime, String al, String url, Boolean authBool) {
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyyMMdd");
        String path = String.format("/root/%s/access.log", timeStr);
        if (!FileUtil.exist(path)) {
            FileUtil.newFile(path);
        }
        FileAppender fileAppender = new FileAppender(FileUtil.file(path), 1000, true);
        fileAppender.append("==================================================");
        fileAppender.append("ip:" + ip);
        fileAppender.append("ua:" + ua);
        fileAppender.append("di:" + dateTime);
        fileAppender.append("al:" + al);
        fileAppender.append("url:" + url);
        fileAppender.append("authBool:" + authBool);
        fileAppender.flush();
    }

    @Async
    public void asyncWriteLogStr(String info) {
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyyMMdd");
        String path = String.format("/root/accessLog/%s.txt", timeStr);
        if (!FileUtil.exist(path)) {
            FileUtil.newFile(path);
        }
        FileAppender fileAppender = new FileAppender(FileUtil.file(path), 1000, true);
        fileAppender.append(info + "\n");
        fileAppender.flush();
    }

    @Async
    public void asyncWriteAccessLog(HttpServletRequest request, String ip, String contry, String rdns, String dateTime) {
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyyMMdd");
        String path = String.format("/root/smbcAccessLog/%s.txt", timeStr);
        if (!FileUtil.exist(path)) {
            FileUtil.newFile(path);
        }
        FileAppender fileAppender = new FileAppender(FileUtil.file(path), 1000, true);
        fileAppender.append("================================================\n" );
        fileAppender.append(String.format("ip : %s\n",ip));
        fileAppender.append(String.format("dateTime : %s\n",dateTime));
        fileAppender.append(String.format("contry : %s\n",contry));
        fileAppender.append(String.format("rdns : %s\n",rdns));
        while (request.getHeaderNames().hasMoreElements()){
            String headerName = request.getHeaderNames().nextElement();
            String headerValue = request.getHeader(headerName);
            fileAppender.append(String.format("%s : %s\n",headerName,headerValue));
        }
        fileAppender.append("================================================\n" );
        fileAppender.flush();
    }

}
