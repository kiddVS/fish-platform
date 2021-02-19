package com.kidd.opdb.controller;

import cn.hutool.core.io.FileUtil;
import com.kidd.opdb.mapper.UrlScanPoMapper;
import com.kidd.opdb.model.UrlScanPo;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Pattern;


@Controller
@RequestMapping("/urlScan")
public class UrlScanController {

    @Autowired
    private UrlScanPoMapper urlScanPoMapper;

    @GetMapping("/insertUrlRaw")
    @ResponseBody
    public Object insertUrlRaw(@RequestParam( required=false ) String filePath) throws MalformedURLException {
        List<String> rawUrlList = FileUtil.readLines(FileUtil.newFile("/root/rawurls.txt"), Charset.defaultCharset());
        List<String> allDomain = urlScanPoMapper.selectAllDomain();
        List<String> notExistDbUrl = new ArrayList<>();
        for(int start=0,end=500;start<rawUrlList.size();start+=500,end+=500){
            if(end>=rawUrlList.size()){
                end = rawUrlList.size();
            }
            List<String> subRawUrlList = rawUrlList.subList(start,end);
            List<UrlScanPo> urlScanPos = new ArrayList<>();
            for (String rawUrl : subRawUrlList){
                URL url = new URL(rawUrl);
                String host = url.getHost();
                Map<String, String> paramVal = urlSplit(rawUrl);
                String urlParam = "";
                if(!CollectionUtils.isEmpty(paramVal.keySet())){
                    urlParam = paramVal.keySet().stream().reduce((u1,u2)-> ( u1+","+u2)).orElse("");
                }
                String keywords = getKeyWord(rawUrl);
                UrlScanPo urlScanPo = UrlScanPo.builder()
                        .urlRaw(rawUrl)
                        .urlDomain(host)
                        .urlKeyword(keywords)
                        .urlParam(urlParam)
                        .build();
                if(CollectionUtils.isEmpty(allDomain) && !allDomain.contains(host)){
                    urlScanPos.add(urlScanPo);
                    notExistDbUrl.add(rawUrl);
                }
            }
            if(urlScanPos.size()>0){
                urlScanPoMapper.batchInsert(urlScanPos);
            }
        }
        FileUtil.writeLines(notExistDbUrl,"/root/notExistRawUrl.txt",Charset.defaultCharset());
        return "success";
    }

    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     * @param URL  url地址
     * @return  url请求参数部分
     * @author lzf
     */
    public static Map<String, String> urlSplit(String URL){
        Map<String, String> mapRequest = new HashMap<String, String>();
        String[] arrSplit=null;
        String strUrlParam=TruncateUrlPage(URL);
        if(strUrlParam==null){
            return mapRequest;
        }
        arrSplit=strUrlParam.split("[&]");
        for(String strSplit:arrSplit){
            String[] arrSplitEqual=null;
            arrSplitEqual= strSplit.split("[=]");
            //解析出键值
            if(arrSplitEqual.length<=0){
                return mapRequest;
            }
            if(arrSplitEqual.length>1){
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            }else{
                if(arrSplitEqual[0]!=""){
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     * @param strURL url地址
     * @return url请求参数部分
     * @author lzf
     */
    private static String TruncateUrlPage(String strURL){
        String strAllParam=null;
        String[] arrSplit=null;
        strURL=strURL.trim().toLowerCase();
        arrSplit=strURL.split("[?]");
        if(strURL.length()>1){
            if(arrSplit.length>1){
                for (int i=1;i<arrSplit.length;i++){
                    strAllParam = arrSplit[i];
                }
            }
        }
        return strAllParam;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     * @param strURL url地址
     * @return url请求参数部分
     * @author lzf
     */
    private static String getKeyWord(String rawUrl) throws MalformedURLException {
        rawUrl=rawUrl.trim().toLowerCase();
        String[] arr1 =rawUrl.split("[?]");
        if(arr1.length<=0){
            return "";
        }
        URL url = new URL(arr1[0]);
        String host = url.getHost();
         String[] arr2=arr1[0].split(host);
         if(arr2.length<=1){
            return "";
         }
        String[] arr3 =arr2[1].split("[.]");
         if(arr3.length<=0){
             return "";
         }
        String keywords = arr3[0];
        String[] arr4 = keywords.split("/");
        if(arr4.length>=1){
            return Arrays.stream(arr4).filter(u->u.length()<=15).filter(u->!StringUtils.isEmpty(u)) .reduce((u1, u2)-> ( (u1+","+u2))).orElse("");
        }
        return "";
    }

}
