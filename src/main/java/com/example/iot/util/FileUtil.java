package com.example.iot.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/16
 * @description:
 */
public class FileUtil {

    public static String readTxt(String address){
        File file = new File(address);
        String content = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            while((line=br.readLine())!=null){
                content += line + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }
}
