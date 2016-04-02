package com.yihukurama.cartoolst.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yihukurama.cartoolst.R;
import com.yihukurama.cartoolst.controler.AnimationUtils;

public class MainActivity extends Activity implements View.OnClickListener {
    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    ImageView image1;
    SlidingMenu menu;
    ImageButton liButton1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    private void initView(){
        image1 = (ImageView)findViewById(R.id.imagemokuai);
        image1.setOnClickListener(this);
        initSlidMenu();

    }

    private void initData(){

    }
    private void initSlidMenu(){
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.slidmenu_left);
        menu.setSecondaryMenu(R.layout.slidemenu_right);


        ImageButton button = (ImageButton)menu.findViewById(R.id.lbutton1);
        menu.addIgnoredView(button);
        button.setOnClickListener(this);


        image1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //当手指按下的时候
                    x1 = event.getX();
                    y1 = event.getY();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //当手指离开的时候
                    x2 = event.getX();
                    y2 = event.getY();
                    if (y1 - y2 > 5) {

                        Toast.makeText(MainActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
                        image1.setVisibility(View.VISIBLE);
                    } else if (y2 - y1 > 50) {
                        Toast.makeText(MainActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
                        image1.setVisibility(View.VISIBLE);
                        image1.setAnimation(AnimationUtils.moveToViewLocation());
                    } else if (x1 - x2 > 50) {
                        Toast.makeText(MainActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
                    } else if (x2 - x1 > 50) {
                        Toast.makeText(MainActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
    }



    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lbutton1:
                    Log.i("menu", "lbutton1");
                    break;
                case R.id.imagemokuai:
                    Log.i("menu", "CCC");
                    break;
                default:
                    break;
            }


        }

}
