package com.xuyu.message;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 用于获取微信接口调用的基本信息.
 */
public class WxContext {
    private static final String corpId = "wx69f61a8797ba4e15";
    private static final String corpSecret = "eL74AF92K5K9l-E74n4mrBT-b6zwLKO1SQySecN8gR78t71b6ZbQb_jrBVpNyW8d";
    //private static final String corpId = "ww7df8fc6945395e96";
    //private static final String corpSecret = "tWAUF8gopPPW8VuDvLb9c9wekUucfd_cb0ADX43Lx4w";
    private volatile long createTime = -1;
    private volatile long expiredTime = 7200;
    private volatile String accessToken = null;
    private static volatile WxContext instance;
    private static final Object LOCK = new Object();

    /**
     * 获取单例
     */
    public static WxContext getInstance() {
        if (null == instance) {
            synchronized (LOCK) {
                if (null == instance) {
                    instance = new WxContext();
                }
            }
        }
        return instance;
    }

    /**
     * 对外开放的 获取微信接口访问凭证
     */
    public String getAccessToken() {
        if (isExpired()) {
            updateAccessTokenAndExpiredTime();
        }
        return accessToken;
    }

    /**
     * 是否过期
     */
    private boolean isExpired() {
        synchronized (LOCK) {
            long curTime = System.currentTimeMillis();
            if (createTime < 0 || curTime <= createTime) {
                return true;
            }
            if (((curTime - createTime) / 1000) > expiredTime) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 保存accessToken
     *
     * @param accessToken 访问凭证
     * @param expiredTime 生效时间段
     */
    private void saveAccessTokenAndExpiredTime(String accessToken, long expiredTime) {
        synchronized (LOCK) {
            this.accessToken = accessToken;
            this.expiredTime = expiredTime;
            this.createTime = System.currentTimeMillis();
        }
    }

    /**
     * 更新AccessToken和生效时间
     */
    private void updateAccessTokenAndExpiredTime() {
        StringBuilder result = new StringBuilder();
        StringBuilder url = new StringBuilder("https://qyapi.weixin.qq.com/cgi-bin/gettoken?");
        url.append(IWxInfo.CORPID + "=" + corpId);
        url.append("&" + IWxInfo.CORPSECRET + "=" + corpSecret);
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url.toString());
            HttpResponse response = client.execute(request);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            JSONObject jsonObject = new JSONObject(result.toString());
            if (jsonObject.has(IWxInfo.ACCESS_TOKEN)) {
                String accessToken = jsonObject.optString(IWxInfo.ACCESS_TOKEN);
                long expiredTime = jsonObject.optInt(IWxInfo.EXPIRES_IN);
                saveAccessTokenAndExpiredTime(accessToken, expiredTime);

            } else {
                accessToken = null;
                expiredTime = -1;
            }
        } catch (Exception ex) {
            accessToken = null;
            expiredTime = -1;
        }
    }
}

