package com.youzan;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 通过该类获取有赞的access token
 */
public class YouzanContext {
    private static final Object LOCK = new Object();
    private static YouzanContext instance;
    private String accessToken;
    private long reqTime = 0;
    private long expiredTime = 0;

    public static YouzanContext getInstance() {
        if (null == instance) {
            synchronized (LOCK) {
                if (null == instance) {
                    instance = new YouzanContext();
                }
            }
        }
        return instance;
    }


    public String getAccessToken() {
        if (isExpired()) {
            fetchAccessToken();
        }
        return accessToken;
    }

    private boolean isExpired() {
        long curTime = System.currentTimeMillis();
        if (reqTime < 0 || curTime <= reqTime) {
            return true;
        }
        if (((curTime - reqTime) / 1000) > expiredTime) {
            return true;
        } else {
            return false;
        }
    }

    private void fetchAccessToken() {
        String url = "https://open.koudaitong.com/oauth/token";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair(IYouzanJsonKeys.CLIENT_ID, IYouzanInfo.CLIENT_ID));
            list.add(new BasicNameValuePair(IYouzanJsonKeys.CLIENT_SECRET, IYouzanInfo.CLIENT_SECRET));
            list.add(new BasicNameValuePair(IYouzanJsonKeys.GRANT_TYPE, "silent"));
            list.add(new BasicNameValuePair(IYouzanJsonKeys.KDT_ID, IYouzanInfo.KDT_ID));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            HttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity content = httpResponse.getEntity();
                String responseStr = EntityUtils.toString(content, "utf-8");
                JSONObject jsonobject = new JSONObject(responseStr);
                if (null != jsonobject) {
                    reqTime = System.currentTimeMillis();
                    accessToken = jsonobject.getString(IYouzanJsonKeys.ACCESS_TOKEN);
                    expiredTime = jsonobject.getLong(IYouzanJsonKeys.EXPIRES_IN);
                }
            }
        } catch (Exception ex) {
            accessToken = null;
            expiredTime = 0;
            reqTime = -1;
        }
    }
}