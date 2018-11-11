package com.xuyu.main;

import com.xuyu.message.Messagesend;

import java.util.TimerTask;

import com.xuyu.tool.TimeUtils;

/**
 * 定时发送消息类
 */
public class RemindTask extends TimerTask {

    @Override
    public void run() {
        String text = "\t" + TimeUtils.getNowTime() + "\t" + "定时发送" + "\n";//保存一条发送记录
        Messagesend.timerSend();
        MessageSendFrame.addSendRecord(text);
    }
}
