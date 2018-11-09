package com.xuyu.message;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * 调用微信发送消息接口和获取成员信息
 */

public class WxApi {

    /**
     * 发送信息，返回值可作为发送成功的判断
     *
     * @param accessToken 有效期内的accessToken
     * @param User        用户的user_id
     * @param txtContent  发送的信息内容
     * @return 发送消息失败的的userId
     */
    public static boolean sendTxtMsg1(String accessToken, String User, String txtContent) {
        if (null == User || null == accessToken) {
            return false;
        }
        StringBuilder url = new StringBuilder("https://qyapi.weixin.qq.com/cgi-bin/message/send?");
        url.append(IWxInfo.ACCESS_TOKEN + "=" + accessToken);
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url.toString());
            JSONObject root = new JSONObject();
            root.put(IWxInfo.TOUSER, User);
            root.put(IWxInfo.MSGTYPE, "text");
            root.put(IWxInfo.AGENTID, "1000002");
            JSONObject content = new JSONObject();
            content.put(IWxInfo.CONTENT, txtContent);
            root.put(IWxInfo.TEXT, content);
            root.put(IWxInfo.SAFE, 0);

            StringEntity stringEntity = new StringEntity(root.toString(), "utf-8");
            httpPost.setEntity(stringEntity);

            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(httpPost);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            JSONObject jsonObject = new JSONObject(result.toString());


            String netResult = jsonObject.getString(IWxInfo.INVALIDUSER);


            if (netResult.equals("")) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 获取用户user_id、name、mobile
     *
     * @param accessToken
     * @return
     */
    public static List<Teacher> getUerId(String accessToken) {
        List<Teacher> list = new ArrayList<Teacher>();
        StringBuilder result = new StringBuilder();
        StringBuilder url = new StringBuilder("https://qyapi.weixin.qq.com/cgi-bin/user/list?");
        url.append(IWxInfo.ACCESS_TOKEN + "=" + accessToken);
        url.append("&" + "department_id" + "=" + "4");
        url.append("&" + "fetch_child" + "=" + "0");
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
            if (jsonObject.has("userlist")) {
                JSONArray array = jsonObject.getJSONArray("userlist");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject ob = (JSONObject) array.get(i);//得到json对象
                    Teacher teacher = new Teacher(ob.getString("userid"), ob.getString("name"), ob.getString("mobile"));
                    list.add(teacher);

                }
            } else {
                System.out.print("该组成员为空");
            }
        } catch (Exception ex) {

        }
        return list;
    }

    /*public static void main(String[] arg) {
        getUerId(WxContext.getInstance().getAccessToken());
    }*/
}
