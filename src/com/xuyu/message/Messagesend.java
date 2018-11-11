package com.xuyu.message;

import java.util.*;


import com.xuyu.tool.TimeUtils;
import com.xuyu.tool.SqlHelper;
import com.xuyu.tool.MyURL;
import com.xuyu.tool.MD5;


/**
 * 消息发送，立即发送，定时发送
 */
public class Messagesend {

    /**
     * 在DEBUG变成true输出关键数据
     */
    public static final boolean DEBUG = false;

    /**
     * 老师设置上课时间的链接头部
     */
    public static final String URL = "http://xml2244.free.idcfengye.com/xuyu_time/teacher_time.jsp";


    /**
     * 立即发送信息函数
     */
    public static void rightSend() {

        SqlHelper sqlhelper = new SqlHelper();
        List<Teacher> list = WxApi.getUerId(WxContext.getInstance().getAccessToken());
        String link; //完整链接
        String userId; //用户的user_id
        String token;  //时间戳
        String banner = sqlhelper.getBanner();  //接收获得的文案
        String text; //发送消息的文本
        String AccessToken = WxContext.getInstance().getAccessToken();
        if (DEBUG == true) {
            System.out.println(AccessToken);
        }
        for (int i = 0; i < list.size(); i++) {

            userId = list.get(i).getUserId();
            token = String.valueOf(System.currentTimeMillis());
            link = MyURL.creatURL(URL, MD5.getMD5(userId), token);
            text = banner + "<a href = '" + link + "'>设置上课时间，请点击此处</a>";
            if (DEBUG == true) {
                System.out.println(link);
            }
            //boolean result = WxApi.sendTxtMsg1(AccessToken, list.get(i).getUserId(), text);
            boolean result = WxApi.sendTxtMsg1(AccessToken, "zhangyinghao", text);
        }
    }

    /**
     * 定时发送信息函数
     */
    public static void timerSend() {
        List<Teacher> list = WxApi.getUerId(WxContext.getInstance().getAccessToken());

        SqlHelper sqlhelper = new SqlHelper();
        MyURL greaturl = new MyURL();
        String link;    //完整链接
        String userId; //用户的user_id
        String token; //时间戳
        String banner = sqlhelper.getBanner();
        String text; //发送消息的文本
        String sendfalsePeopleInformation = ""; //发送失败人数的信息 姓名和联系电话
        String AccessToken = WxContext.getInstance().getAccessToken();
        if (DEBUG == true) {
            System.out.println(AccessToken);
        }
        int fal = 0;//统计发送失败人数
        for (int i = 0; i < list.size(); i++) {

            userId = list.get(i).getUserId();
            token = String.valueOf(System.currentTimeMillis());
            link = greaturl.creatURL(URL, MD5.getMD5(userId), token);
            if (DEBUG == true) {
                System.out.println(link);
            }
            text = banner + "<a href = '" + link + "'>设置上课时间，请点击此处</a>";
            //boolean result = WxApi.sendTxtMsg1(AccessToken, list.get(i).getUserId(), text);
            boolean result = WxApi.sendTxtMsg1(AccessToken, "zhangyinghao", text);
            if (result == false) {
                fal++;
                sendfalsePeopleInformation = sendfalsePeopleInformation + list.get(i).getName() + "\t" + list.get(i).getMobile() + "\n";
            }
        }

        int j = list.size() - fal;
        text = "定时任务已结束，总计发送人数为：" + list.size() + "，接受成功人数为：" + j + "，接受失败人数为：" + fal + " !";
        text = text + "\n" + "发送失败老师姓名" + "\t" + "联系方式" + "\n" + sendfalsePeopleInformation;
        //boolean result = WxApi.sendTxtMsg1(AccessToken, "xuyujiajiao_001", text);
        boolean result = WxApi.sendTxtMsg1(AccessToken, "zhangyinghao", text);
    }

    /**
     * 根据星期几获取对应的i值，这里获取的i可做比较日期大小
     *
     * @param week 星期几
     * @return int i值
     */
    public static int week(String week) {
        int i = -1;
        if (week.equals("星期一")) {
            i = 0;
            return i;
        } else if (week.equals("星期二")) {
            i = 1;
            return i;
        } else if (week.equals("星期三")) {
            i = 2;
            return i;
        } else if (week.equals("星期四")) {
            i = 3;
            return i;
        } else if (week.equals("星期五")) {
            i = 4;
            return i;
        } else if (week.equals("星期六")) {
            i = 5;
            return i;
        } else if (week.equals("星期日")) {
            i = 6;
            return i;
        }
        return i;
    }


    /**
     * 判断界面类里面文本框输入的时间是否在当前时间的之前，如果之前则在定时发送的日期加7天
     *
     * @param setWeek   星期几
     * @param setHour   小时
     * @param setMinute 分钟
     * @return Date类型 时间
     */
    public static Date timerSendTimeJudge(String setWeek, int setHour, int setMinute) {
        TimeUtils t = new TimeUtils();
        int newI = week(t.getWeekOfDate(new Date()));  //当前星期对应的i值
        int setI = week(setWeek);   //输入星期对应的i值
        int month; //月份
        int day; //日期
        int newHour; //当前小时
        int newMinute; //当前分钟
        String time;
        String nowTime = TimeUtils.getNowTime(); //当前时间


        newHour = Integer.parseInt((String) nowTime.subSequence(12, 14));
        newMinute = Integer.parseInt((String) nowTime.subSequence(15, 17));

        //当前星期几在设置时间星期之前
        if (newI < setI) {
            time = t.getNextweekday(new Date(), setI);
            month = Integer.parseInt((String) time.subSequence(5, 7));
            day = Integer.parseInt((String) time.subSequence(8, 10));
            return TimeUtils.setTime(month, day, setHour, setMinute);
        }
        //同一天，当前小时小于设置小时
        if (newI == setI && newHour < setHour) {
            time = t.getNextweekday(new Date(), setI);
            month = Integer.parseInt((String) time.subSequence(5, 7));
            day = Integer.parseInt((String) time.subSequence(8, 10));
            return TimeUtils.setTime(month, day, setHour, setMinute);
        }
        //同一天，同一小时，当前分钟小于设置分钟
        if (newI == setI && newHour == setHour && newMinute < setMinute) {
            time = t.getNextweekday(new Date(), setI);
            month = Integer.parseInt((String) time.subSequence(5, 7));
            day = Integer.parseInt((String) time.subSequence(8, 10));
            return TimeUtils.setTime(month, day, setHour, setMinute);
        }
        //当前时间大于设置时间
        setI = setI + 7;
        time = t.getNextweekday(new Date(), setI);
        month = Integer.parseInt((String) time.subSequence(5, 7));
        day = Integer.parseInt((String) time.subSequence(8, 10));
        return TimeUtils.setTime(month, day, setHour, setMinute);

    }

}
