package com.youzan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.api.*;
import com.youzan.open.sdk.gen.v3_0_0.model.*;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * 关于有赞库存的处理
 */
public class YouzanApi {
    /**
     * 判断库存是否为0
     * @param itemNo
     * @param itemId
     * @return
     */
    public static boolean isHasClass(String itemNo,Long itemId) {
        String accessToken = YouzanContext.getInstance().getAccessToken();
        YZClient client = new DefaultYZClient(new Token(accessToken));
        YouzanSkusCustomGetParams youzanSkusCustomGetParams = new YouzanSkusCustomGetParams();
        youzanSkusCustomGetParams.setItemId(itemId);
        youzanSkusCustomGetParams.setItemNo(itemNo);
        YouzanSkusCustomGet youzanSkusCustomGet = new YouzanSkusCustomGet();
        youzanSkusCustomGet.setAPIParams(youzanSkusCustomGetParams);
        YouzanSkusCustomGetResult result = client.invoke(youzanSkusCustomGet);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JSONObject jsonObj = new JSONObject(mapper.writeValueAsString(result));
            JSONArray array =jsonObj.getJSONArray("skus");
            boolean has=false;
            for(int i=0;i<array.length();i++) {
                int quantity=array.getJSONObject(i).getInt("quantity");
                if(quantity==0) {
                    has = true;
                    break;
                }else {
                    has = false;
                }
            }
            return has;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获得商品的itemId
     * @param itemNo
     * @return
     */
    public static String getItem_id(String itemNo) {
        String accessToken = YouzanContext.getInstance().getAccessToken();
        YZClient client = new DefaultYZClient(new Token(accessToken));
        YouzanItemsCustomGetParams youzanItemsCustomGetParams = new YouzanItemsCustomGetParams();
        youzanItemsCustomGetParams.setItemNo(itemNo);

        YouzanItemsCustomGet youzanItemsCustomGet = new YouzanItemsCustomGet();
        youzanItemsCustomGet.setAPIParams(youzanItemsCustomGetParams);
        YouzanItemsCustomGetResult result = client.invoke(youzanItemsCustomGet);
        try {
            ObjectMapper mapper = new ObjectMapper();
            JSONObject jsonObj = new JSONObject(mapper.writeValueAsString(result));
            JSONArray array =jsonObj.getJSONArray("items");
            if(!array.isNull(0)) {
                String item_id=array.getJSONObject(0).get("item_id").toString();
                return item_id;
            }else {
                return null;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


}
