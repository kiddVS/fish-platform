package com.kidd.opdb.controller;

import cn.hutool.core.io.FileUtil;
import com.kidd.opdb.mapper.UrlScanPoMapper;
import com.kidd.opdb.model.UrlScanPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/extract")
public class ExtractMailController {

    @Autowired
    private UrlScanPoMapper urlScanPoMapper;

    @GetMapping("/mail")
    @ResponseBody
    public Object extractEmail(@RequestParam(required = false) String filePath) throws MalformedURLException {
        /*
         * 查看当前项目根目录下的所有子项
         */
        List<String> injectableUrls = FileUtil.readLines("/root/injection.txt",Charset.defaultCharset());
        File dir = new File("C:\\Users\\Administrator\\AppData\\Local\\sqlmap\\output");
        //首先判断是否为一个目录
        if (dir.isDirectory()) {
            //获取当前目录下的所有子项
            List<File> subs=Arrays.asList( dir.listFiles());
            for(String injectableUrl:injectableUrls){
                File logFile = extractEmailFromFile(subs,injectableUrl);
            }
        }
        return "success";
    }

    @GetMapping("/mailFromDir")
    @ResponseBody
    public Object mailFromDir(@RequestParam(required = false) String filePath) throws MalformedURLException {
        extractEmailFromDir("/root/maildir");
        return "success";
    }

    private File extractEmailFromFile(List<File> subs, String injectableUrl) throws MalformedURLException {
        List<String> emails= new ArrayList<>();
        File sub = subs.stream().filter(u->injectableUrl.contains( u.getName())).findFirst().orElse(null);
        List<File> logFiles=Arrays.asList( sub.listFiles());
        //String reg = "[\\w!#$%&amp;'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&amp;'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        String reg = "[\\w!#$%&amp;*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&amp;*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        //String reg = "/w+([-+.']/w+)*@/w+([-.]/w+)*/./w+([-.]/w+)*";
        //String reg = "[^/s]*@[^/s]*";
        Pattern pt = Pattern.compile(reg);
        for (File logFile : logFiles){
            if(logFile.isDirectory() || !logFile.getName().contains("log")){
                continue;
            }
            List<String> logFileStrs = FileUtil.readLines(logFile,Charset.defaultCharset());
            for (String logFileStr:logFileStrs){
                Matcher m = pt.matcher(logFileStr);
                while(m.find())
                {
                    emails.add(m.group());
                }
            }
        }
        URL url = new URL(injectableUrl);
        emails= emails.stream().distinct().sorted().collect(Collectors.toList());
        String host = url.getHost();
        String fileName = String.format("%s#%s",host,emails.size());
        if(StringUtils.isEmpty(url.getHost()) && emails.size()>=0){
            urlScanPoMapper.updateByDomain(url.getHost(),emails.size());
        }
        String path = String.format("/root/extractEmail/%s",fileName);
        if(FileUtil.exist(path)){
            FileUtil.del(path);
        }
        FileUtil.newFile(path);
        FileUtil.writeLines(emails,path,Charset.defaultCharset());
        return FileUtil.file(path);
    }

    private void extractEmailFromDir(String filePath) throws MalformedURLException {
        File dir = new File(filePath);
        List<File> subs=Arrays.asList( dir.listFiles());
        String reg = "[\\w!#$%&amp;*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&amp;*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        //String reg = "/w+([-+.']/w+)*@/w+([-.]/w+)*/./w+([-.]/w+)*";
        //String reg = "[^/s]*@[^/s]*";
        Pattern pt = Pattern.compile(reg);
        for (File file:subs){
            if(file.isFile()){
                List<String> emails= new ArrayList<>();
                List<String> logFileStrs = FileUtil.readLines(file,Charset.defaultCharset());
                for (String logFileStr:logFileStrs){
                    Matcher m = pt.matcher(logFileStr);
                    while(m.find())
                    {
                        emails.add(m.group());
                    }

                }
                emails= emails.stream().distinct().collect(Collectors.toList());
                String path = String.format("/root/extractEmaildir/%s",file.getName()+"#"+emails.size());
                if(FileUtil.exist(path)){
                    FileUtil.del(path);
                }
                FileUtil.newFile(path);
                FileUtil.writeLines(emails,path,Charset.defaultCharset());
            }
        }
    }
}
