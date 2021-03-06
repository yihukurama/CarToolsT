package com.yihukurama.cartoolst;

import android.animation.ObjectAnimator;
import android.app.Application;
import android.app.Service;
import android.graphics.Typeface;
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
    private static CartoolApp _instance;
    public static int currentMusicIndex = -1;
    public static List<MusicBean> musicBeanList = new ArrayList<MusicBean>();
    static String musicStatus = ConstantValue.STOP;
    static String mediaStatus = ConstantValue.STOP;
    public static ObjectAnimator cdAnimation = null;
    public LocationService locationService;
    public Vibrator mVibrator;
    public static int cheneidushu = 25;
    public static String  chewaidushu = "26";
    public static int fengliang = 1;
    private Typeface typefaceB;
    private Typeface typefaceC;
    private Typeface typefaceM;
    private Typeface typefaceR;

    public static  CartoolApp getInstace() {
        return _instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = (CartoolApp) getApplicationContext();
        musicStatus = ConstantValue.STOP;
        mediaStatus = ConstantValue.STOP;
        typefaceB = Typeface.createFromAsset(getAssets(), "fonts/BebasNeueBold.otf");
        typefaceC = Typeface.createFromAsset(getAssets(), "fonts/CenturyGothic.TTF");
        typefaceM = Typeface.createFromAsset(getAssets(), "fonts/FZLanTingHei-M-GBKRegular.ttf");
        typefaceR = Typeface.createFromAsset(getAssets(), "fonts/FZLanTingHei-R-GBKRegular.TTF");
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


    public Typeface getTypefaceR() {
        return typefaceR;
    }

    public void setTypefaceR(Typeface typefaceR) {
        this.typefaceR = typefaceR;
    }

    public Typeface getTypefaceB() {
        return typefaceB;
    }

    public void setTypefaceB(Typeface typefaceB) {
        this.typefaceB = typefaceB;
    }

    public Typeface getTypefaceC() {
        return typefaceC;
    }

    public void setTypefaceC(Typeface typefaceC) {
        this.typefaceC = typefaceC;
    }

    public Typeface getTypefaceM() {
        return typefaceM;
    }

    public void setTypefaceM(Typeface typefaceM) {
        this.typefaceM = typefaceM;
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
