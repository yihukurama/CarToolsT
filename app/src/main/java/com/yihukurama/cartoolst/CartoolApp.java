package com.yihukurama.cartoolst;

import android.animation.ObjectAnimator;
import android.app.Application;

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
    @Override
    public void onCreate() {
        super.onCreate();
        musicStatus = ConstantValue.STOP;
        mediaStatus = ConstantValue.STOP;

        initMusicList();
    }

    private void initMusicList() {
        String musicName[] ={
                "The Mamas & the Papas - California Dreamin'",
                "Moumoon - Sunshine Girl",
                "Madonna - Living For Love",
                "Lilly Wood & The Prick,Robin Schulz - Prayer In C (Robin Schulz Remix)"

        } ;
        String singer[] = {
            "The Mamas & the Papas", "Sunshine Girl",
                "Madonna",
                "Lilly Wood & The Prick,Robin Schulz"
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
