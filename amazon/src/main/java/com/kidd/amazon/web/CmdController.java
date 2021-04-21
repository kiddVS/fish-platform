package com.kidd.amazon.web;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RuntimeUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class CmdController {



    @GetMapping("/searchLeads")
    public Object searchLeads(@RequestParam String filePath) {
//        //1、获取鱼文件
//       String res =  RuntimeUtil.execForStr("ipconfig");
////       String[] cmds =  new String[]{"nslookup", ip, "8.8.8.8"};
//        Process process = RuntimeUtil.exec(cmds);
//        while (process.isAlive()){
//
//        }
        return "";
    }

}
