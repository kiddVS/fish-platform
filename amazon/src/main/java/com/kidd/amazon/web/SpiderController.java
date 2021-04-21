package com.kidd.amazon.web;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileAppender;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.BigIntegerNode;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class SpiderController {
    @RequestMapping("/crawlAmazonTester")
    @ResponseBody
    public Object crawlAmazonTester() throws InterruptedException {

        String testerIndexBaseUrl = "https://www.amazon.com/hz/leaderboard/top-reviewers/ref=cm_cr_tr_link_1?page=";
        List<String> testAmzIdList = new ArrayList<>();
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 10802));
        int startIndex = 142;
        int endIndex = 1000;
        //循环所有页码
        for (int index = startIndex; index <= endIndex; index++) {
            System.out.println("testerIndexPage:" + index);
            String testerIndexUrl = testerIndexBaseUrl + index;
            String indexPageRes = "";
            try {
                indexPageRes = HttpRequest.get(testerIndexUrl)
                        .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36")//头信息，多个头信息多次调用此方法即可
                        .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                        .header("accept-encoding", "gzip, deflate, br")
                        .header("accept-language", "zh-CN,zh;q=0.9,ja;q=0.8,en-US;q=0.7,en;q=0.6")
                        .header("cookie", "session-id=131-2897947-4426900; session-id-time=2082787201l; i18n-prefs=USD; ubid-main=131-3352437-3838505; lc-main=en_US; session-token=OJ5wz6uu3AKnoJcdpYuuthpQGXPqdvYhrvB31EbHoeKaubpeG4PaiYHWWpo5UtZaJc17hXQgR1AUaMOaH4wy4SHfBy4PBneKnPQW4bo7lOpsQKM0WGWNGi+gVk4NaK6poBq4ZzJzL48FU8taWbPHFyAChSUbFG5kZKZlVRqbSzF29Tm6uQCelqWzWIKICP59; csm-hit=tb:s-070FZK7PS1WZ7NFRS21V|1618496645535&t:1618496645713&adb:adblk_yes")
                        .setProxy(proxy)
                        .timeout(40000)//超时，毫秒
                        .execute()
                        .body();
            } catch (Exception e) {
                Thread.sleep(5000L);
                continue;
            }
            indexPageRes =  indexPageRes.replaceAll("\\r\\n|\\n|\\r", "<BR/>");
            //1、对于每一页，拿到所有的测评人
            String REGEX = "<a name=\"(amzn1\\.account.*?)\"></a>(.*?)<b>(.*?)</b>";
            Pattern p = Pattern.compile(REGEX);
            Matcher m = p.matcher(indexPageRes);
            while (m.find()) {
                Boolean hasLinks = false;
                List<String> socialLinks = new ArrayList<>();
                String testerAmzId = m.group(1);
                String testerAmzName = m.group(3);
                System.out.println("testerAmzId:" + testerAmzId);
                testAmzIdList.add(testerAmzId);
                String path = "/root/amzTester.txt";
                if (!FileUtil.exist(path)) {
                    FileUtil.newFile(path);
                }

                //2、对于所有的测评人，获取他们的社交主页
                FileAppender fileAppender = new FileAppender(FileUtil.file(path), 1000, true);
                fileAppender.append(testerAmzId);
                fileAppender.flush();
                String personalPageResult = "";
                String personalPageUrl = "https://www.amazon.com/profilewidget/bio/"+testerAmzId+"?view=visitor";
                try {
                    personalPageResult = HttpRequest.get(personalPageUrl)
                            .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36")//头信息，多个头信息多次调用此方法即可
                            .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                            .header("accept-encoding", "gzip, deflate, br")
                            .header("accept-language", "zh-CN,zh;q=0.9,ja;q=0.8,en-US;q=0.7,en;q=0.6")
                            .header("cookie", "session-id=131-2897947-4426900; session-id-time=2082787201l; i18n-prefs=USD; ubid-main=131-3352437-3838505; lc-main=en_US; session-token=OJ5wz6uu3AKnoJcdpYuuthpQGXPqdvYhrvB31EbHoeKaubpeG4PaiYHWWpo5UtZaJc17hXQgR1AUaMOaH4wy4SHfBy4PBneKnPQW4bo7lOpsQKM0WGWNGi+gVk4NaK6poBq4ZzJzL48FU8taWbPHFyAChSUbFG5kZKZlVRqbSzF29Tm6uQCelqWzWIKICP59; csm-hit=tb:s-070FZK7PS1WZ7NFRS21V|1618496645535&t:1618496645713&adb:adblk_yes")
                            .timeout(40000)//超时，毫秒
                            .setProxy(proxy)
                            .execute()
                            .body();
                } catch (Exception e) {
                    Thread.sleep(5000L);
                    continue;
                }
                JSONObject personJson = JSON.parseObject(personalPageResult);
                if(personJson.containsKey("social")){
                    String facebook = "N/A";
                    String twitter = "N/A";
                    String pinterest = "N/A";
                    String instagram = "N/A";
                    String youtube = "N/A";
                    JSONObject socialJSON =personJson.getJSONObject("social");
                    if(socialJSON.containsKey("socialLinks")){
                        JSONArray socialLinksArr = socialJSON.getJSONArray("socialLinks");
                        if(null != socialLinksArr){
                            for (int i = 0;i< socialLinksArr.size();i++){
                                JSONObject socialLinkObj = socialLinksArr.getJSONObject(i);
                                if(null != socialLinkObj.get("url")){
                                    hasLinks = true;
                                    String socialLink = socialLinkObj.get("type") + ":" + socialLinkObj.get("url");
                                    socialLinks.add(socialLink);
                                    switch (socialLinkObj.get("type").toString()){
                                        case "facebook":
                                            facebook =  socialLinkObj.get("url").toString();
                                            break;
                                        case "twitter":
                                            twitter =  socialLinkObj.get("url").toString();
                                            break;
                                        case "pinterest":
                                            pinterest =  socialLinkObj.get("url").toString();
                                            break;
                                        case "instagram":
                                            instagram =  socialLinkObj.get("url").toString();
                                            break;
                                        case "youtube":
                                            youtube =  socialLinkObj.get("url").toString();
                                            break;
                                            default:
                                                break;
                                    }
                                }
                            }

                        }
                    }
                    String rankStr = "rank:";
                    if(personJson.containsKey("topReviewerInfo")){
                       JSONObject topReviewerInfo =  personJson.getJSONObject("topReviewerInfo");
                       if(topReviewerInfo.containsKey("rank")){
                          int rank = topReviewerInfo.getIntValue("rank");
                          rankStr = rank+"";
                       }
                    }
                    if(hasLinks && !CollectionUtils.isEmpty(socialLinks)) {
                        String personPage = "https://www.amazon.com/gp/profile/" + testerAmzId;
                        System.out.println("hit！！！");
                        CsvWriter writer = CsvUtil.getWriter("/root/amzTesterInfos.csv", CharsetUtil.CHARSET_UTF_8,true);
                        writer.write(
                                new String[] {rankStr, testerAmzName, facebook, twitter, pinterest, instagram, youtube, personPage}
                        );
                    }
                }
                Thread.sleep(1000L);
            }
        }
        return null;
    }

}
