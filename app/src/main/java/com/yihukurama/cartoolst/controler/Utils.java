package com.yihukurama.cartoolst.controler;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.yihukurama.cartoolst.R;

/**
 * Created by Administrator on 2016/4/6 0006.
 */
public class Utils {

    /**
     * 传入时间变为00：00格式的字符串
     * @param progress
     * @return
     */
    public static String traInt2Time(int progress){
        int min = progress/1000/60;
        int secound = progress/1000%60;
        String minTime;
        String secoundTime;

        minTime = (min<10)?("0"+min):(""+min);
        secoundTime = (secound<10)?("0"+secound):(""+secound);

        return minTime + " : " + secoundTime;
    }

}
