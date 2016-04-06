package com.yihukurama.cartoolst.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yihukurama.cartoolst.R;
import com.yihukurama.cartoolst.controler.sevice.MediaService;
import com.yihukurama.cartoolst.model.UriSet;
import com.yihukurama.cartoolst.view.fragment.CallFragment;
import com.yihukurama.cartoolst.view.fragment.MusicFragment;
import com.yihukurama.cartoolst.view.fragment.ShushiFragment;

public class MainActivity extends AppCompatActivity implements CallFragment.OnFragmentInteractionListener,
        MusicFragment.OnFragmentInteractionListener,ShushiFragment.OnFragmentInteractionListener,View.OnClickListener {
    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    ImageView image1;
    SlidingMenu menu;
    ImageButton liButton1;

    FragmentManager fm;
    FragmentTransaction transaction;
    private CallFragment callFragment;
    private MusicFragment musicFragment;
    private ShushiFragment shushiFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepare();
        initView();
        initData();

    }

    private void prepare() {
        setDefaultFragment();

    }

    private void setDefaultFragment() {
        fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        shushiFragment = new ShushiFragment();
        transaction.replace(R.id.showingfragment, shushiFragment);
        transaction.commit();
    }
    private void initView(){
        setDefaultFragment();
        initSlidMenu();

    }

    private void initData(){

    }
    private void initSlidMenu() {
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.slidmenu_left);
        menu.setSecondaryMenu(R.layout.slidemenu_right);

    }




    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                default:
                    break;
            }


        }

    @Override
    public void onFragmentInteraction(Uri uri) {
        String intent = uri.toString();
        switch (intent){
            case UriSet.TRA2MUSICF:
                transaction = fm.beginTransaction();
                if (musicFragment == null)
                {
                    musicFragment = new MusicFragment();
                }
                // 使用当前Fragment的布局替代id_content的控件
                transaction.replace(R.id.showingfragment, musicFragment);
                transaction.commit();
                break;
            case UriSet.TRA2CALLF:
                transaction = fm.beginTransaction();
                if (callFragment == null)
                {
                    callFragment = new CallFragment();
                }
                // 使用当前Fragment的布局替代id_content的控件
                transaction.replace(R.id.showingfragment, callFragment);
                transaction.commit();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MediaService.class));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            System.exit(0);
        }

        return super.onKeyDown(keyCode, event);
    }
}
