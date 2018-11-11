package com.xuyu.main;


import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import java.util.Calendar;
import java.util.Date;

import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;


import com.xuyu.message.Messagesend;
import com.xuyu.message.RemindMessage;
import com.xuyu.tool.TimeUtils;

/**
 *生成发送消息的界面
 * @author zhangyinghao
 */
public class MessageSendFrame extends JFrame implements ActionListener{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 定时发送时间间隔
     */
    public static final int TIMER_SEND_TIME_INTERVAL=7 * 24 * 60 * 60 * 1000;

    /**
     * 窗口
     */
    public JFrame jf;

    /**
     * 立即发送按钮
     */
    public JButton mBtnRightSend;

    /**
     * 启动定时发送按钮
     */
    public JButton mBtnOpenTimerSend;

    /**
     * 关闭定时发送按钮
     */
    public JButton mBtnCloseTimerSend;

    /**
     * 清除记录按钮
     */
    public JButton mBtnClear;

    /**
     *定时任务状态提示
     */
    public JLabel mLblSendStatusTips;

    /**
     * 发送记录提示
     */
    public JLabel mLblSendTips;

    /**
     * 当前定时任务状态 未/已启动
     */
    public JLabel mLblSendStatus;

    /**
     * 请设置时间提示   请设置时间：
     */
    public JLabel mLblPleaseSetTimeTips;

    /**
     * 冒号 ：
     */
    public JLabel mLblColon;

    /**
     * 输入时间例子    例如：08:00  为08:00分发送
     */
    public JLabel mLblInputTimeExampeTips;

    /**
     * 输出发送记录文本域
     */
    public static JTextArea mTxaOutRecord;

    /**
     * 小时输入框
     */
    public JTextField mTxtHour;

    /**
     * 分钟输入框
     */
    public JTextField mTxtMinute;

    /**
     * 星期下拉列表
     */
    public JComboBox mCboWeek;

    /**
     * 定时发送消息定时器
     */
    Timer mtimerSend;

    public MessageSendFrame() {
        jf = new JFrame("任务发送器");

        mBtnRightSend = new JButton("立即发送");
        mBtnOpenTimerSend = new JButton("开启定时任务");
        mBtnCloseTimerSend = new JButton("取消定时任务");
        mBtnClear = new JButton("清除记录");

        mLblSendStatusTips = new JLabel("定时任务状态：");
        mLblSendTips = new JLabel("发送记录：");
        mLblSendStatus = new JLabel("未启动");
        mLblSendStatus.setForeground(Color.red);
        mLblPleaseSetTimeTips = new JLabel("请设置时间：");
        mLblColon = new JLabel(":");
        mLblInputTimeExampeTips = new JLabel("例如：08:00  为08:00分发送");
        mLblInputTimeExampeTips.setForeground(Color.blue);

        mTxaOutRecord = new JTextArea();
        JScrollPane sp = new JScrollPane(mTxaOutRecord);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mTxaOutRecord.setEditable(false);
        mTxaOutRecord.append("\t时间" + "\t\t" + "发送方式" + "\n");

        mTxtHour = new JTextField();
        mTxtMinute = new JTextField();


        final String labels[] = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };
        final DefaultComboBoxModel model = new DefaultComboBoxModel(labels);
        mCboWeek = new JComboBox(model);

        jf.setBounds(0, 0, 750, 600);
        jf.setLayout(null);
        jf.setResizable(false);
        Container c = jf.getContentPane();

        mLblSendStatusTips.setBounds(30, 30, 100, 30);
        mLblSendStatus.setBounds(130, 30, 100, 30);
        mBtnOpenTimerSend.setBounds(600, 100, 100, 35);
        mBtnCloseTimerSend.setBounds(600, 150, 100, 35);
        mBtnRightSend.setBounds(600, 200, 100, 35);
        mBtnClear.setBounds(600, 300, 100, 35);
        mLblSendTips.setBounds(35, 220, 100, 30);
        //jt_out.setBounds(35, 250,500,300);
        sp.setBounds(35, 250, 500, 300);
        mCboWeek.setBounds(35, 130, 100, 35);
        mLblPleaseSetTimeTips.setBounds(35, 100, 100, 30);
        mTxtHour.setBounds(145, 130, 30, 30);
        mLblColon.setBounds(178, 130, 6, 30);
        mTxtMinute.setBounds(185, 130, 30, 30);
        mLblInputTimeExampeTips.setBounds(240, 130, 270, 30);

        mCboWeek.addActionListener(this);
        mBtnRightSend.addActionListener(this);
        mBtnOpenTimerSend.addActionListener(this);
        mBtnCloseTimerSend.addActionListener(this);
        mBtnClear.addActionListener(this);


        c.add(mLblSendStatusTips);
        c.add(mLblSendStatus);
        c.add(mBtnOpenTimerSend);
        c.add(mBtnCloseTimerSend);
        c.add(mBtnRightSend);
        c.add(mLblSendTips);
        c.add(mBtnClear);
        c.add(mCboWeek);
        c.add(mLblPleaseSetTimeTips);
        c.add(mTxtHour);
        c.add(mTxtMinute);
        c.add(mLblColon);
        c.add(mLblInputTimeExampeTips);
        c.add(sp);



        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * 判断输入的字符是否是数字
     * @param str 传入的字符串
     * @return boolean类型
     */
    public boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");//匹配模式
        Matcher isNumberOrNot = pattern.matcher(str);
        if (!isNumberOrNot.matches()) {
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource().equals(mBtnOpenTimerSend)) {
            if (mTxtHour.getText().equals("") || mTxtMinute.getText().equals("")) {
                JOptionPane.showMessageDialog(jf, "请输入时，分");
                return;
            }
            if(!isNumber(mTxtHour.getText()) && !isNumber(mTxtMinute.getText())){
                JOptionPane.showMessageDialog(jf, "请输入正确的时间");
                return;
            }

            int hour;  //保存小时
            int minute; //保存分钟

            hour = Integer.valueOf(mTxtHour.getText());
            minute = Integer.valueOf(mTxtMinute.getText());

            if ((hour > 23 || hour < 0) || (minute < 0 || minute > 59)){
                JOptionPane.showMessageDialog(jf, "请输入正确的时间");
                return;
            }

            mLblSendStatus.setText("已启动");
            mLblSendStatus.setForeground(Color.blue);

            mTxtHour.setEditable(false);
            mTxtMinute.setEditable(false);
            mCboWeek.setEnabled(false);

            Date time = Messagesend.timerSendTimeJudge((String) mCboWeek.getSelectedItem(), hour, minute);

            if (mtimerSend == null) {
                mtimerSend = new Timer();
                mtimerSend.schedule(new RemindTask(), time, TIMER_SEND_TIME_INTERVAL);
            }

        }
        if (e.getSource().equals(mBtnCloseTimerSend)) {
            mLblSendStatus.setText("未启动");
            mLblSendStatus.setForeground(Color.red);
            mTxtHour.setEditable(true);
            mTxtMinute.setEditable(true);
            mCboWeek.setEnabled(true);
            if (mtimerSend != null) {

                mtimerSend.cancel();
                mtimerSend.purge();
                mtimerSend =null;

            }

        }
        if (e.getSource().equals(mBtnRightSend)) {
            String text="\t" + TimeUtils.getNowTime() + "\t" + "立即发送" + "\n";
            Messagesend.rightSend();
            addSendRecord(text);
        }
        if (e.getSource().equals(mBtnClear)) {
            mTxaOutRecord.setText("");
            mTxaOutRecord.append("\t时间" + "\t\t" + "发送方式" + "\n");
        }
    }

    /**
     * 在JTextArea文本域添加定时发送记录
     * @param text 发送记录文本，存储发送的时间和方式
     */
    public static void addSendRecord(String text){
        mTxaOutRecord.append(text);
        mTxaOutRecord.setCaretPosition(mTxaOutRecord.getText().length());
    }

    public static void main(String[] arg)
    {
        int period=24*60*60*1000;
        Timer remindMessageTimer = new Timer(true);
        //设置执行时间
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //定制每天的20:00:00执行
        calendar.set(year, month, day, 20, 00, 00);
        java.util.Date date = calendar.getTime();
        remindMessageTimer.schedule(new RemindMessage(), date,period);

        MessageSendFrame a = new MessageSendFrame();
    }

}
