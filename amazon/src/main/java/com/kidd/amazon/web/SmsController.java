package com.kidd.amazon.web;

import cn.hutool.core.io.FileUtil;
import com.kidd.amazon.task.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@Slf4j
public class SmsController {

    @Autowired
    private AsyncTask asyncTask;

    @GetMapping("/sendSms")
    @ResponseBody
    public Object sendSms() throws InterruptedException {
        File rawLeads = FileUtil.file("/root/smsLeads.txt");
        List<String> smsList = FileUtil.readLines(rawLeads, Charset.defaultCharset());
        for(String sms:smsList){
            Thread.sleep(1000L);
            asyncTask.asyncSendMsg(sms,"【Arnazon】プライム会費のお支払い方法に問題があります。詳細はこち:https://bit.ly/3eiyl9y","PHcomm");
        }
        return "success";
    }

}
