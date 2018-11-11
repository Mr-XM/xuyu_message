package com.xuyu.tool;

import java.io.IOException;
import java.net.URL;


/**
 * 创建一个完整的URL
 */
public class MyURL {
    /**
     * 创建一个完整的URL
     *
     * @param url     域名和跳转界面
     * @param user_id 用户的user_id
     * @param token   时间戳
     * @return
     */
    public static String creatURL(String url, String user_id, String token) {
        String mURL = null; //完整的URL
        try {
            URL u = new URL(url + "?u=" + user_id + "&t=" + token + "&f=" + "0000");
            mURL = u.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mURL;
    }
}
