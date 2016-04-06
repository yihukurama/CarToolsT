package com.yihukurama.cartoolst.controler.sevice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.yihukurama.cartoolst.CartoolApp;
import com.yihukurama.cartoolst.R;
import com.yihukurama.cartoolst.model.ConstantValue;
import com.yihukurama.cartoolst.model.MusicBean;

public class MediaService extends Service {
    public MediaService() {
    }

    private MediaPlayer mp;
    int maxSeekBar;
    int nowSeekBar;
    Thread sendBCThread;
    MusicBean musicBean;
    @Override
    public void onCreate() {
        super.onCreate();
        mp = MediaPlayer.create(this, R.raw.test);

        musicBean = new MusicBean();
        musicBean.setMax(mp.getDuration());
        musicBean.setProgress(0);

        Log.i("debug", "准备播放音乐");
        //设置音乐总长度
        maxSeekBar = mp.getDuration();
        //发送Action为com.yihukurama.updateseekbar的广播
        Intent intent = new Intent("com.yihukurama.setseekbarmax");
        intent.putExtra("max", maxSeekBar);
        sendBroadcast(intent);
        nowSeekBar = 0;
        sendBCThread = new Thread(new Runnable() {

                    @Override
                    public void run() {


                        while(nowSeekBar < maxSeekBar){



                            try {
                                nowSeekBar = mp.getCurrentPosition();
                                //发送Action为com.yihukurama.updateseekbar的广播
                                Intent intent = new Intent("com.yihukurama.updateseekbar");
                                intent.putExtra("progress", nowSeekBar);
                                sendBroadcast(intent);
                                Thread.sleep(1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                                break;
                            }

                        }
                    }
                });
        if (!sendBCThread.isAlive())
            sendBCThread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.release();
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获取当前播放位置

        //设置seekbar
        if (mp.isPlaying()){
            CartoolApp.setMusicStatus(ConstantValue.PAUSE);
            mp.pause();
        }else{
            CartoolApp.setMusicStatus(ConstantValue.PLAY);
            mp.start();
        }


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

 }


