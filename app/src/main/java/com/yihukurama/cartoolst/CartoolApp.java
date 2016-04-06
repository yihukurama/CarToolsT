package com.yihukurama.cartoolst;

import android.app.Application;

import com.yihukurama.cartoolst.model.ConstantValue;


/**
 * Created by Administrator on 2016/4/3 0003.
 */
public class CartoolApp extends Application {

    static String musicStatus;
    static String mediaStatus;
    @Override
    public void onCreate() {
        super.onCreate();
        musicStatus = ConstantValue.STOP;
        mediaStatus = ConstantValue.STOP;
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
