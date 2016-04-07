package com.yihukurama.cartoolst;

import android.animation.ObjectAnimator;
import android.app.Application;

import com.yihukurama.cartoolst.model.ConstantValue;
import com.yihukurama.cartoolst.model.MusicBean;


/**
 * Created by Administrator on 2016/4/3 0003.
 */
public class CartoolApp extends Application {
    public static MusicBean musicBean = new MusicBean();
    static String musicStatus = ConstantValue.STOP;
    static String mediaStatus = ConstantValue.STOP;
    public static ObjectAnimator cdAnimation = null;
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
