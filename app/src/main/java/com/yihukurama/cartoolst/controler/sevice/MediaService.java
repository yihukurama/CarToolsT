package com.yihukurama.cartoolst.controler.sevice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.yihukurama.cartoolst.CartoolApp;
import com.yihukurama.cartoolst.R;
import com.yihukurama.cartoolst.controler.Utils;
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
        nowSeekBar = musicBean.getProgress();
        maxSeekBar = musicBean.getMax();
        sendBCThread = new Thread(new Runnable() {

                    @Override
                    public void run() {


                        while(nowSeekBar < maxSeekBar ){

                            if(mp.isPlaying()){
                                nowSeekBar = mp.getCurrentPosition();
                                musicBean.setProgress(nowSeekBar);
                                CartoolApp.musicBean = musicBean;
                                updateMusicProgress();
                            }else if(!mp.isPlaying() && CartoolApp.getMusicStatus().equals(ConstantValue.PLAY)){
                                mp.start();
                                nowSeekBar = 0;
                                musicBean.setProgress(0);
                                CartoolApp.musicBean = musicBean;
                            }

                            try {

                                Thread.sleep(1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                                break;
                            }

                        }
                    }
                });


        sendBCThread.start();
    }

    private void updateMusicProgress(){
        String maxTime = Utils.traInt2Time(musicBean.getMax());
        String proTime = Utils.traInt2Time(musicBean.getProgress());
        musicBean.setMaxTime(maxTime);
        musicBean.setCurrentTime(proTime);
        Intent intent = new Intent("com.yihukurama.updatemusicpro");
        intent.putExtra("music", musicBean);
        sendBroadcast(intent);
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
            //发送广播停止动画
            Intent intents = new Intent("com.yihukurama.stopanimation");
            sendBroadcast(intents);
        }else{
            CartoolApp.setMusicStatus(ConstantValue.PLAY);
            mp.start();
        }

        CartoolApp.musicBean = musicBean;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

 }


