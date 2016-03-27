package com.yihukurama.cartoolst.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yihukurama.cartoolst.R;
import com.yihukurama.cartoolst.view.activity.widget.SelectPicPopupWindow;

public class MainActivity extends Activity implements View.OnClickListener {
    SelectPicPopupWindow menuWindow;
    ImageView imageView;
    private GestureDetector mGestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        initData();

    }

    private void initView(){
        imageView = (ImageView)findViewById(R.id.imageView);
        mGestureDetector = new GestureDetector(this, new MyOnGestureListener());
        imageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                // 一定要返回true，不然获取不到完整的事件
                return true;
            }});
        menuWindow = new SelectPicPopupWindow(MainActivity.this,this);
        initSlidMenu();

    }

    private void initData(){

    }
    private void initSlidMenu(){
        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.toggle();
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
//        menu.setShadowDrawable(R.drawable.octocat);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.slidmenu_layout_left);
        menu.setSecondaryMenu(R.layout.slidmenu_layout_left);
    }


    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    break;
                case R.id.btn_pick_photo:
                    break;
                default:
                    break;
            }


        }

    private String getActionName(int action) {
        String name = "";
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                name = "ACTION_DOWN";
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                name = "ACTION_MOVE";
                break;
            }
            case MotionEvent.ACTION_UP: {
                name = "ACTION_UP";
                break;
            }
            default:
                break;
        }
        return name;
    }
    class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.i(getClass().getName(), "onSingleTapUp-----" + getActionName(e.getAction()));
            menuWindow.showAtLocation(MainActivity.this.findViewById(R.id.mainview), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.i(getClass().getName(), "onLongPress-----" + getActionName(e.getAction()));

        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.i(getClass().getName(),
                    "onScroll-----" + getActionName(e2.getAction()) + ",(" + e1.getX() + "," + e1.getY() + ") ,("
                            + e2.getX() + "," + e2.getY() + ")");
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.i(getClass().getName(),
                    "onFling-----" + getActionName(e2.getAction()) + ",(" + e1.getX() + "," + e1.getY() + ") ,("
                            + e2.getX() + "," + e2.getY() + ")");
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.i(getClass().getName(), "onShowPress-----" + getActionName(e.getAction()));
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.i(getClass().getName(), "onDown-----" + getActionName(e.getAction()));
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.i(getClass().getName(), "onDoubleTap-----" + getActionName(e.getAction()));
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.i(getClass().getName(), "onDoubleTapEvent-----" + getActionName(e.getAction()));
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.i(getClass().getName(), "onSingleTapConfirmed-----" + getActionName(e.getAction()));
            return false;
        }
    }

}
