package com.yihukurama.cartoolst.controler;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;

import com.yihukurama.cartoolst.R;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class AnimationManager {
    private static final String TAG = AnimationManager.class.getSimpleName();

    /**
     * 从控件所在位置移动到控件的底部
     *
     * @return
     */
    public static TranslateAnimation moveToViewBottom() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        mHiddenAction.setDuration(500);
        return mHiddenAction;
    }

    /**
     * 从控件的底部移动到控件所在位置
     *
     * @return
     */
    public static TranslateAnimation moveToViewLocation() {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(500);
        return mHiddenAction;
    }

    /**
     * 水平移动动画
     */
    public static TranslateAnimation moveHorizon(float x1,float x2,View view,long duration,long delay) {
            float moveL = x2 - x1;
            /*
                    Animation还有几个方法
                    setFillAfter(boolean fillAfter)
                    如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
                    setFillBefore(boolean fillBefore)
                    如果fillBefore的值为真的话，动画结束后，控件停留在动画开始的状态
                    setStartOffset(long startOffset)
                    设置动画控件执行动画之前等待的时间
                    setRepeatCount(int repeatCount)
                    设置动画重复执行的次数
             */
        TranslateAnimation translateAnimation = new TranslateAnimation(
                //X轴初始位置
                Animation.RELATIVE_TO_SELF, 0.0f,
                //X轴移动的结束位置
                Animation.RELATIVE_TO_SELF,0.5f,
                //y轴开始位置
                Animation.RELATIVE_TO_SELF,0.0f,
                //y轴移动后的结束位置
                Animation.RELATIVE_TO_SELF,1.5f);

        //3秒完成动画
        translateAnimation.setDuration(duration);

        return translateAnimation;
    }

    public static void slideview(final float p1, final float p2,final View view,long durationMillis,long delayMillis) {
        TranslateAnimation animation = new TranslateAnimation(p1, p2, 0, 0);
        animation.setInterpolator(new OvershootInterpolator());
        animation.setDuration(durationMillis);
        animation.setStartOffset(delayMillis);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int left = view.getLeft()+(int)(p2-p1);
                int top = view.getTop();
                int width = view.getWidth();
                int height = view.getHeight();
                view.clearAnimation();
                view.layout(left, top, left+width, top+height);

            }
        });
        view.startAnimation(animation);
    }

    /**
     * 垂直移动动画
     */
    public static TranslateAnimation moveVertical(int y1,int y2,View view,long duration) {
        float moveY = y2 - y1;
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        AnimationSet animationSet = new AnimationSet(true);
        view.setVisibility(View.VISIBLE);
        TranslateAnimation mShowAction = new TranslateAnimation(
                x,
                x,
                y+view.getHeight(),
                y+view.getHeight()+moveY);
        mShowAction.setDuration(duration);
        animationSet.setFillAfter(true);
        animationSet.addAnimation(mShowAction);
        view.startAnimation(animationSet);
        return mShowAction;
    }


    /**
     * 向上滑动自身高度
     */
    public static TranslateAnimation moveSelfHeigh(View view,long duration){
        AnimationSet animationSet = new AnimationSet(true);
        view.setVisibility(View.VISIBLE);
        TranslateAnimation mShowAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f);
        mShowAction.setDuration(duration);
        animationSet.setFillAfter(true);
        animationSet.addAnimation(mShowAction);
        view.startAnimation(animationSet);
        return mShowAction;

    }

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

    public static void transX(){

    }
}

