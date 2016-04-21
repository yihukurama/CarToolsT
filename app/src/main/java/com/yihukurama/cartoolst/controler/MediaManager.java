package com.yihukurama.cartoolst.controler;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.yihukurama.cartoolst.CartoolApp;

import java.io.File;

/**
 * Created by Administrator on 2016/4/16 0016.
 */
public class MediaManager {


    public static void playVideo(Context context,String path,final VideoView videoView){
        videoView.setVisibility(View.VISIBLE);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+ File.separator+path);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //播放结束后的动作
                videoView.setVisibility(View.GONE);
            }
        });
        videoView.setMediaController(new MediaController(context));
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.requestFocus();
    }

    public static void playDefault(Context context,String path){
        if (CartoolApp.cdAnimation != null && CartoolApp.cdAnimation.isRunning())
        {
            CartoolApp.cdAnimation.pause();
        }
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+File.separator+path);
        //调用系统自带的播放器
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "video/mp4");
        context.startActivity(intent);
    }

}
