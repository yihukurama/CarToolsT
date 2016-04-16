package com.yihukurama.cartoolst;

import android.animation.ObjectAnimator;
import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;
import com.yihukurama.cartoolst.controler.sdk.baidu.Location.service.LocationService;
import com.yihukurama.cartoolst.controler.sdk.baidu.Location.service.WriteLog;
import com.yihukurama.cartoolst.model.ConstantValue;
import com.yihukurama.cartoolst.model.MusicBean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/4/3 0003.
 */
public class CartoolApp extends Application {
    public static int currentMusicIndex = -1;
    public static List<MusicBean> musicBeanList = new ArrayList<MusicBean>();
    static String musicStatus = ConstantValue.STOP;
    static String mediaStatus = ConstantValue.STOP;
    public static ObjectAnimator cdAnimation = null;
    public LocationService locationService;
    public Vibrator mVibrator;
    public static int cheneidushu = 24;
    public static String  chewaidushu = "26";
    public static int fengliang = 1;
    @Override
    public void onCreate() {
        super.onCreate();
        musicStatus = ConstantValue.STOP;
        mediaStatus = ConstantValue.STOP;

        initMusicList();

        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        WriteLog.getInstance().init(); // 初始化日志
        SDKInitializer.initialize(getApplicationContext());
    }

    private void initMusicList() {
        String singer[] ={
                "The Mamas & the Papas",
                "Moumoon",
                "Madonna",
                "Lilly Wood & The Prick,Robin Schulz"

        } ;
        String musicName[] = {
            "California Dreamin'", "Sunshine Girl",
                "Madonna",
                "Prayer In C (Robin Schulz Remix)"
        } ;
        int cd[] = {R.raw.cddreamin,R.raw.cdgirl,R.raw.cdlove,R.raw.cdremix};
        int cdView[] = {R.drawable.cddreamin,R.drawable.cdgirl,R.drawable.cdlove,R.drawable.cdremix};

        for (int i = 0; i<4;i++){
            MusicBean musicBean = new MusicBean(musicName[i],singer[i],cd[i],cdView[i]);
            musicBeanList.add(musicBean);
        }
    }


    public static String getMusicStatus() {
        return musicStatus;
    }

    public static void setMusicStatus(String musicStatus) {
        CartoolApp.musicStatus = musicStatus;
    }

    public static String getMediaStatus() {
        return mediaStatus;
    }

    public static void setMediaStatus(String mediaStatus) {
        CartoolApp.mediaStatus = mediaStatus;
    }


}
