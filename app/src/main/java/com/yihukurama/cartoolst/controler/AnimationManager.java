package com.yihukurama.cartoolst.controler;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class AnimationManager {
    private static final String TAG = AnimationManager.class.getSimpleName();

    /**
     * 旋转动画
     */
    public static ObjectAnimator rotateSelf(View view,long duration){
        ObjectAnimator operatingAnim = ObjectAnimator.ofFloat(view, "rotation", 0, 360);

        operatingAnim.setDuration(duration);
        operatingAnim.setRepeatCount(5);
        operatingAnim.setRepeatMode(ObjectAnimator.RESTART);
        return operatingAnim;

    }

    public static void transY(View view,int moveY){
        float curTranslationY = view.getTranslationY();
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", curTranslationY, moveY);
        animator.setDuration(250);
        animator.start();
    }
}

