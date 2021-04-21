package com.kidd.amazon.web;

import cn.hutool.core.io.FileUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class FileController {

    @GetMapping("/formatFishFile")
    public Object formatFishFile(@RequestParam(value = "filePath") String filePath) {

        List<String> splitLines = Arrays.asList("-----------------------------------");
        HashMap<String,Integer> sortedMap = new HashMap<String,Integer>(){{
            put("DateTime",1);
            put("Al",2);
            put("AmazonId",3);
            put("AmazomPwd",4);
            put("UserAgent",5);
            put("countryCode",6);
            put("FullName",7);
            put("PostalCode",8);
            put("StateOrRegion",9);
            put("BirthDay",10);
            put("AddressLine1",11);
            put("AddressLine2",12);
            put("AddressLine3",13);
            put("PhoneNumber",14);
            put("CardName",15);
            put("CardNo",16);
            put("CVV",17);
            put("Expire",18);
            put("3dSecret",19);
        }};
        //1、获取鱼文件
        File rawFish = FileUtil.file("/root/"+filePath);

        //2.相同的ip放在一起
        List<String> fishLines = FileUtil.readLines(rawFish,Charset.defaultCharset());
        Map<String,HashSet<String>> ipInfoMap = new HashMap<>();
         for(String fishLine : fishLines){
             if(!fishLine.contains("#")){
                 continue;
             }
             String[] ipInfoArr = fishLine.split("#");
             if(ipInfoArr.length!=2){
                 continue;
             }
             String ip = ipInfoArr[0];
             String cvvInfo = ipInfoArr[1];
             HashSet<String> infos = ipInfoMap.get(ip);
             if(CollectionUtils.isEmpty(infos)){
                 infos = new HashSet<>();
                 ipInfoMap.put(ip,infos);
             }
             infos.add(cvvInfo);
         }

        //3.生成新的鱼文件
        Set<String> keys = ipInfoMap.keySet();
         if(CollectionUtils.isEmpty(keys)){
             return "this is no fish!!!";
         }

         String formatPath = "/root/format"+filePath;
         if (FileUtil.exist(formatPath)){
             FileUtil.del(formatPath);
         }
         File formatFile = FileUtil.newFile(formatPath);
         for (String key : keys){
             HashSet<String> infos = ipInfoMap.get(key);
             if(CollectionUtils.isEmpty(infos) || infos.size()<=6){
                 continue;
             }
             List<String> sortedInfo = infos.stream().sorted((u1,u2)->{
                 String sortKey1 = getByInfo(u1);
                 String sortKey2 = getByInfo(u2);
                 Integer u1V = sortedMap.get(sortKey1);
                 Integer u2V = sortedMap.get(sortKey2);
                 if(u1V==null || u2V==null){
                     return 1;
                 }
                 if(u1V>u2V){
                     return 1;
                 }
                 else {
                     return -1;
                 }
             }).collect(Collectors.toList());
             List<String> ipInfo = Arrays.asList(" ipAddress: "+key);
             FileUtil.appendLines(ipInfo,formatFile,Charset.defaultCharset());
             FileUtil.appendLines(sortedInfo,formatFile,Charset.defaultCharset());
             FileUtil.appendLines(splitLines,formatFile,Charset.defaultCharset());
         }

        String headPath = "/root/head"+filePath;
        if (FileUtil.exist(headPath)){
            FileUtil.del(headPath);
        }
        File headFile = FileUtil.newFile(headPath);
        for (String key : keys){
            HashSet<String> infos = ipInfoMap.get(key);
            if(CollectionUtils.isEmpty(infos) || infos.size()<=6){
                continue;
            }
            for (String info:infos){
                if(info.contains("CardNo")){
                    String cardHead = info.substring(10,16)+",";
                    FileUtil.appendString(cardHead,headFile,Charset.defaultCharset());
                }
            }
        }
        return "ok";
    }

    private String getByInfo(String u1) {
        String key = "";
        List<String> keys = Arrays.asList("DateTime","Al","AmazonId","AmazomPwd","UserAgent","countryCode"
                ,"FullName","PostalCode","StateOrRegion","BirthDay","AddressLine1","AddressLine2"
                ,"AddressLine3","PhoneNumber","CardName","CardNo","CVV","Expire","3dSecret");
        for (String key1 :keys){
            if(u1.contains(key1)){
                key = key1;
                break;
            }
        }
        return key;
    }

    @GetMapping("/downLoadFishFile")
    public Object downLoadFishFile(@RequestParam String filePath) {
        //1、获取鱼文件
        return "";
    }

    @GetMapping("/extractEmail")
    public Object extractEmail(@RequestParam String filePath) {
        //1、获取鱼文件
        File rawEmail = FileUtil.file("/root/"+filePath);
        List<String> emails = FileUtil.readLines(rawEmail,Charset.defaultCharset());
        String headPath = "/root/formatEmail"+filePath;
        if (FileUtil.exist(headPath)){
            FileUtil.del(headPath);
        }
        File emailFile = FileUtil.newFile(headPath);
        List<String> emailList = new ArrayList();
        for (String emailLine:emails){
            String[] emailArr = emailLine.split(":");
            for(String email:emailArr){
                if (null!=email && email.contains("@")){
                    emailList.add(email.trim());
                }
            }
        }
        FileUtil.writeLines(emailList,emailFile,Charset.defaultCharset());
        return "ok";
    }
}
