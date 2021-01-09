package com.kidd.amazon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/amazon")
public class AmazonController {


    @GetMapping("/testIp")
    @ResponseBody
    public Object testIp() {
        boolean result = true;
        return result;
    }
}
