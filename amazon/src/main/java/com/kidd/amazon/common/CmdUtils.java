package com.kidd.amazon.common;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class CmdUtils {

    public String queryRdns(String ip) {
        try {

            String[] cmd = new String[]{"nslookup", ip, "8.8.8.8"};

            Process ps = Runtime.getRuntime().exec(cmd);

            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));

            StringBuffer sb = new StringBuffer();

            String line;

            while ((line = br.readLine()) != null) {

                sb.append(line).append("\n");

            }

            String result = sb.toString();

            System.out.println(result);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("query rdns error:" + e.getMessage());
            return "";
        }
    }
}
