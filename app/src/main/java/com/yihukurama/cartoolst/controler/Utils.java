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


    public static void initBaiduMapWidget(Context context,MapView mapView,View.OnClickListener listener){
        int buttonL = 1460;
        Point point = new Point();

        //右下角图层按钮
        point.set(buttonL, 314);
        MapViewLayoutParams mlp = new MapViewLayoutParams.Builder().layoutMode(MapViewLayoutParams.ELayoutMode.absoluteMode).point(point).width(80).height(80).build();
        ImageButton imagebutton = new ImageButton(context);
        imagebutton.setBackground(context.getResources().getDrawable(R.drawable.tucengselector));
        mapView.addView(imagebutton, mlp);
        mapView.refreshDrawableState();

        //右下角我的位置按钮
        Point point2 = new Point();
        point2.set(buttonL, 405);
        MapViewLayoutParams mlp2 = new MapViewLayoutParams.Builder().layoutMode(MapViewLayoutParams.ELayoutMode.absoluteMode).point(point2).width(80).height(80).build();
        ImageButton imagebutton2 = new ImageButton(context);
        imagebutton2.setBackground(context.getResources().getDrawable(R.drawable.wodeweizhiselector));
        mapView.addView(imagebutton2, mlp2);
        mapView.refreshDrawableState();

        //右下角垂直加号按钮
        Point point3 = new Point();
        point3.set(buttonL, 496);
        MapViewLayoutParams mlp3 = new MapViewLayoutParams.Builder().layoutMode(MapViewLayoutParams.ELayoutMode.absoluteMode).point(point3).width(80).height(80).build();
        ImageButton imagebutton3 = new ImageButton(context);
        imagebutton3.setBackground(context.getResources().getDrawable(R.drawable.cjiaselector));
        mapView.addView(imagebutton3, mlp3);
        mapView.refreshDrawableState();

        //右下角垂直减号按钮
        Point point4 = new Point();
        point4.set(buttonL, 576);
        MapViewLayoutParams mlp4 = new MapViewLayoutParams.Builder().layoutMode(MapViewLayoutParams.ELayoutMode.absoluteMode).point(point4).width(80).height(80).build();
        ImageButton imagebutton4 = new ImageButton(context);
        imagebutton4.setBackground(context.getResources().getDrawable(R.drawable.cjianselector));
        mapView.addView(imagebutton4, mlp4);

        mapView.refreshDrawableState();
    }
}
