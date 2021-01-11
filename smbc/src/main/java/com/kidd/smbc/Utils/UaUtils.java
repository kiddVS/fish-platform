package com.kidd.smbc.Utils;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UaUtils {
    public Boolean isMobile(String ua){
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
        Boolean isMobile = false;
        for(String mobileUa : mobileUaList){
            if(ua.toLowerCase().contains(mobileUa)){
                isMobile = true;
            }
        }
        return  isMobile;
    }
}
