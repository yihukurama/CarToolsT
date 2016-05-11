package com.yihukurama.cartoolst.controler.sevice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.yihukurama.cartoolst.CartoolApp;
import com.yihukurama.cartoolst.controler.Utils;
import com.yihukurama.cartoolst.controler.broadcast.SendBroadCast;
import com.yihukurama.cartoolst.model.ConstantValue;
import com.yihukurama.cartoolst.model.MusicBean;

public class MediaService extends Service {
    public MediaService() {
    }

    private MediaPlayer mp;
    int maxSeekBar;
    int nowSeekBar;
    Thread sendBCThread;
    Thread timeThread;
    MusicBean currentMusic;
    @Override
    public void onCreate() {
        super.onCreate();
        //未播放音乐
        if (CartoolApp.currentMusicIndex == -1){
            mp = MediaPlayer.create(this, CartoolApp.musicBeanList.get(0).getCd());
            CartoolApp.currentMusicIndex = 0;
            currentMusic = CartoolApp.musicBeanList.get(0);
            currentMusic.setMax(mp.getDuration());
            currentMusic.setProgress(0);
            Log.i("debug", "准备播放音乐");
            nowSeekBar = currentMusic.getProgress();
            maxSeekBar = currentMusic.getMax();

        }

        timeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){

                    Intent intent = new Intent(SendBroadCast.resetTime);
                    sendBroadcast(intent);
                    try {

                        Thread.sleep(30000);
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }


                }
            }
        });

        sendBCThread = new Thread(new Runnable() {

                    @Override
                    public void run() {


                        while(nowSeekBar < maxSeekBar){

                            if(CartoolApp.getMusicStatus() != ConstantValue.STOP && mp.isPlaying()){
                                nowSeekBar = mp.getCurrentPosition();
                                currentMusic.setProgress(nowSeekBar);
                                updateMusicProgress();
                            }else if(!mp.isPlaying() && CartoolApp.getMusicStatus().equals(ConstantValue.PLAY)){

                                startNextMusic();

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
        timeThread.start();
    }

    private void startLastMusic(){
        if(CartoolApp.currentMusicIndex > 0){
            CartoolApp.currentMusicIndex = CartoolApp.currentMusicIndex-1;
        }else{
            CartoolApp.currentMusicIndex = 3;
        }
        if(mp.isPlaying()){

            mp.stop();
        }
        mp = MediaPlayer.create(this, CartoolApp.musicBeanList.get(CartoolApp.currentMusicIndex).getCd());
        currentMusic = CartoolApp.musicBeanList.get(CartoolApp.currentMusicIndex);
        currentMusic.setMax(mp.getDuration());
        currentMusic.setProgress(0);
        Log.i("debug", "播放上一首音乐");
        nowSeekBar = currentMusic.getProgress();
        maxSeekBar = currentMusic.getMax();
        CartoolApp.setMusicStatus(ConstantValue.PLAY);
        //发送广播开始动画
        SendBroadCast.sendBroadCast(this, SendBroadCast.startAnimation);
        mp.start();
    }


    private void startNextMusic(){

        if(CartoolApp.currentMusicIndex < 3){
            CartoolApp.currentMusicIndex = CartoolApp.currentMusicIndex+1;
        }else{
            CartoolApp.currentMusicIndex = 0;
        }
        if(mp.isPlaying()){

            mp.stop();
        }
        mp = MediaPlayer.create(this, CartoolApp.musicBeanList.get(CartoolApp.currentMusicIndex).getCd());
        currentMusic = CartoolApp.musicBeanList.get(CartoolApp.currentMusicIndex);
        currentMusic.setMax(mp.getDuration());
        currentMusic.setProgress(0);
        Log.i("debug", "准备播放下一首音乐");
        nowSeekBar = currentMusic.getProgress();
        maxSeekBar = currentMusic.getMax();
        CartoolApp.setMusicStatus(ConstantValue.PLAY);
        //发送广播开始动画
        SendBroadCast.sendBroadCast(this, SendBroadCast.startAnimation);
        mp.start();
    }


    private void updateMusicProgress(){
        String maxTime = Utils.traInt2Time(currentMusic.getMax());
        String proTime = Utils.traInt2Time(currentMusic.getProgress());
        currentMusic.setMaxTime(maxTime);
        currentMusic.setCurrentTime(proTime);
        Intent intent = new Intent("com.yihukurama.updatemusicpro");
        intent.putExtra("music", currentMusic);
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
        String cmd = "";
        if(intent != null){
            cmd = intent.getStringExtra("cmd");
        }
        switch (cmd){
            case "next":
                    startNextMusic();
                break;
            case "last":
                    startLastMusic();
                break;
            case "play":
                if(CartoolApp.getMusicStatus().equals(ConstantValue.PAUSE)){
                    //发送广播恢复动画
                    SendBroadCast.sendBroadCast(this, SendBroadCast.resumeAnimation);
                }else{
                    //发送广播开始动画
                    SendBroadCast.sendBroadCast(this, SendBroadCast.startAnimation);
                }
                CartoolApp.setMusicStatus(ConstantValue.PLAY);
                mp.start();


                break;
            case "pause":
                CartoolApp.setMusicStatus(ConstantValue.PAUSE);
                mp.pause();
                //发送广播停止动画
                SendBroadCast.sendBroadCast(this, SendBroadCast.stopAnimation);
                break;
        }


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

 }


