package com.xuyu.message;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.xuyu.tool.TimeUtils;
import com.xuyu.tool.SqlHelper;
import com.youzan.YouzanApi;

/**
 * 提示消息类，读取有课老师名单，给第二天要上课的老师发送提示消息
 *
 * @author zhangyinghao
 */
public class RemindMessage extends TimerTask {

    /**
     * 每天晚上发送一条信息提醒第二天有课要上的老师
     */
    public void sendRemind() {
        TimeUtils timeutils = new TimeUtils();
        String AccessToken = WxContext.getInstance().getAccessToken();
        String text = "明天有工作等待您的完成，请不要忘记哦！";
        if (timeutils.getWeekOfDate1(new Date()).equals("Monday")) {
            List<Teacher> list = SqlHelper.getUserId("Tuesday");
            for (int i = 0; i < list.size(); i++) {
                String itemId = YouzanApi.getItem_id(list.get(i).getUserId() + ":" + list.get(i).getName());
                String itemNo[] = list.get(i).getItemNo().split(",");
                for (int j = 0; j < itemNo.length; j++) {
                    if (YouzanApi.isHasClass(itemNo[j], Long.parseLong(itemId))) {
                        WxApi.sendTxtMsg1(AccessToken, list.get(i).getUserId(), text);
                        break;
                    }
                }
            }
        } else if (timeutils.getWeekOfDate1(new Date()).equals("Tuesday")) {
            List<Teacher> list = SqlHelper.getUserId("Wednesday");
            for (int i = 0; i < list.size(); i++) {
                String itemId = YouzanApi.getItem_id(list.get(i).getUserId() + ":" + list.get(i).getName());
                String itemNo[] = list.get(i).getItemNo().split(",");
                for (int j = 0; j < itemNo.length; j++) {
                    if (YouzanApi.isHasClass(itemNo[j], Long.parseLong(itemId))) {
                        WxApi.sendTxtMsg1(AccessToken, list.get(i).getUserId(), text);
                        break;
                    }
                }
            }
        } else if (timeutils.getWeekOfDate1(new Date()).equals("Wednesday")) {
            List<Teacher> list = SqlHelper.getUserId("Thursday");
            for (int i = 0; i < list.size(); i++) {
                String itemId = YouzanApi.getItem_id(list.get(i).getUserId() + ":" + list.get(i).getName());
                String itemNo[] = list.get(i).getItemNo().split(",");
                for (int j = 0; j < itemNo.length; j++) {
                    if (YouzanApi.isHasClass(itemNo[j], Long.parseLong(itemId))) {
                        WxApi.sendTxtMsg1(AccessToken, list.get(i).getUserId(), text);
                        break;
                    }
                }
            }
        } else if (timeutils.getWeekOfDate1(new Date()).equals("Thursday")) {
            List<Teacher> list = SqlHelper.getUserId("Friday");
            for (int i = 0; i < list.size(); i++) {
                String itemId = YouzanApi.getItem_id(list.get(i).getUserId() + ":" + list.get(i).getName());
                String itemNo[] = list.get(i).getItemNo().split(",");
                for (int j = 0; j < itemNo.length; j++) {
                    if (YouzanApi.isHasClass(itemNo[j], Long.parseLong(itemId))) {
                        WxApi.sendTxtMsg1(AccessToken, list.get(i).getUserId(), text);
                        break;
                    }
                }
            }
        } else if (timeutils.getWeekOfDate1(new Date()).equals("Friday")) {
            List<Teacher> list = SqlHelper.getUserId("Saturday");
            for (int i = 0; i < list.size(); i++) {
                String itemId = YouzanApi.getItem_id(list.get(i).getUserId() + ":" + list.get(i).getName());
                String itemNo[] = list.get(i).getItemNo().split(",");
                for (int j = 0; j < itemNo.length; j++) {
                    if (YouzanApi.isHasClass(itemNo[j], Long.parseLong(itemId))) {
                        WxApi.sendTxtMsg1(AccessToken, list.get(i).getUserId(), text);
                        break;
                    }
                }
            }
        } else if (timeutils.getWeekOfDate1(new Date()).equals("Saturday")) {
            List<Teacher> list = SqlHelper.getUserId("Sunday");
            for (int i = 0; i < list.size(); i++) {
                String itemId = YouzanApi.getItem_id(list.get(i).getUserId() + ":" + list.get(i).getName());
                String itemNo[] = list.get(i).getItemNo().split(",");
                for (int j = 0; j < itemNo.length; j++) {
                    if (YouzanApi.isHasClass(itemNo[j], Long.parseLong(itemId))) {
                        WxApi.sendTxtMsg1(AccessToken, list.get(i).getUserId(), text);
                        break;
                    }
                }
            }
        } else {
            List<Teacher> list = SqlHelper.getUserId("Monday");
            for (int i = 0; i < list.size(); i++) {
                String itemId = YouzanApi.getItem_id(list.get(i).getUserId() + ":" + list.get(i).getName());
                String itemNo[] = list.get(i).getItemNo().split(",");
                for (int j = 0; j < itemNo.length; j++) {
                    if (YouzanApi.isHasClass(itemNo[j], Long.parseLong(itemId))) {
                        WxApi.sendTxtMsg1(AccessToken, list.get(i).getUserId(), text);
                        break;
                    }
                }
            }
        }


    }

    private static boolean isRunning = false;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        if (!isRunning) {
            isRunning = true;
            sendRemind();
            isRunning = false;
        }
    }

}
