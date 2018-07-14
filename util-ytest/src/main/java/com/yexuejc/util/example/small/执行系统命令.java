package com.yexuejc.util.example.small;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Java 执行终端命令
 *
 * @author: maxf
 * @date: 2018/3/16 22:33
 */
public class 执行系统命令 {

    public static void main(String[] args) throws IOException, InterruptedException {
        String cmd = "cmd /c dir D:\\";
        final Process process = Runtime.getRuntime().exec(cmd);
        printMessage(process.getInputStream());
        printMessage(process.getErrorStream());
        int value = process.waitFor();
        System.out.println(value);
    }

    private static void printMessage(final InputStream input) {
        new Thread(new Runnable() {


            public void run() {
                Reader reader = new InputStreamReader(input, Charset.forName("GBK"));
                BufferedReader bf = new BufferedReader(reader);
                String line = null;
                try {
                    while ((line = bf.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }
}
