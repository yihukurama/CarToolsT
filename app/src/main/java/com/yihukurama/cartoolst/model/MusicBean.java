package com.yihukurama.cartoolst.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/6 0006.
 */
public class MusicBean implements Serializable {
    private String name = "";
    private String singer ="";
    private int cd = -1;
    private String maxTime ="00:00";
    private String currentTime ="00:00";
    private int max=100;
    private int progress=0;
    private int cdView;
    public MusicBean(){

    }

    public MusicBean(String name, String singer, int cd,int cdView) {
        this.name = name;
        this.singer = singer;
        this.cd = cd;
        this.cdView = cdView;
    }

    public int getCdView() {
        return cdView;
    }

    public void setCdView(int cdView) {
        this.cdView = cdView;
    }

    public int getCd() {
        return cd;
    }

    public void setCd(int cd) {
        this.cd = cd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(String maxTime) {
        this.maxTime = maxTime;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

}
