package com.yihukurama.cartoolst.controler;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.yihukurama.cartoolst.R;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/6 0006.
 */
public class Utils {

    /**
     * 传入时间变为00：00格式的字符串
     *
     * @param progress
     * @return
     */
    public static String traInt2Time(int progress) {
        int min = progress / 1000 / 60;
        int secound = progress / 1000 % 60;
        String minTime;
        String secoundTime;

        minTime = (min < 10) ? ("0" + min) : ("" + min);
        secoundTime = (secound < 10) ? ("0" + secound) : ("" + secound);

        return minTime + " : " + secoundTime;
    }

    public static String getTimeHours() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String getTimeDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时
        String str = formatter.format(curDate);
        return str;
    }

    public static String getWeekDate() {
        final Calendar c = Calendar.getInstance();
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return "星期" + mWay;
    }


}

