package com.yihukurama.cartoolst.controler.broadcast;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2016/4/11 0011.
 */
public class SendBroadCast {


    public final static String startAnimation = "com.yihukurama.sartanimation";
    public final static String resumeAnimation = "com.yihukurama.resumeanimation";
    public final static String stopAnimation = "com.yihukurama.stopanimation";
    public final static String resetSeekBar = "com.yihukurama.updatemusicpro";
    public final static String resetTime = "com.yihukurama.updatetime";
    public final static String secoundMenu = "com.yihukurama.secoundmenu";

    public static void sendBroadCast(Context context,String filter){
        Intent intents = new Intent(filter);
        context.sendBroadcast(intents);
    }
}
