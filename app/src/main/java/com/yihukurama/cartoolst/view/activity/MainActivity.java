package com.yihukurama.cartoolst.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yihukurama.cartoolst.R;
import com.yihukurama.cartoolst.controler.AnimationUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity implements View.OnClickListener {
    ListView list1,list2;
    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    ImageView image1;
    ImageView image2;
    SlidingMenu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        initData();

    }

    private void initView(){
        image1 = (ImageView)findViewById(R.id.imageView);
        image2 = (ImageView)findViewById(R.id.imageView2);
        initSlidMenu();

    }

    private void initData(){

    }
    private void initSlidMenu(){
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT_RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.slidmenu_left);
        setMenuList();
        menu.setSecondaryMenu(R.layout.slidemenu_right);
        setSecondMenuList();


        image1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    //当手指按下的时候
                    x1 = event.getX();
                    y1 = event.getY();
                }
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    //当手指离开的时候
                    x2 = event.getX();
                    y2 = event.getY();
                    if(y1 - y2 > 5) {

                        Toast.makeText(MainActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
                        image1.setVisibility(View.VISIBLE);
                        image2.setVisibility(View.VISIBLE);
                        AnimationUtils.moveVertical(0,image2.getHeight(),image1, 2000);
                    } else if(y2 - y1 > 50) {
                        Toast.makeText(MainActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
                        image2.setVisibility(View.GONE);
                        image1.setVisibility(View.VISIBLE);
                        image2.setAnimation(AnimationUtils.moveToViewBottom());
                        image1.setAnimation(AnimationUtils.moveToViewLocation());
                    } else if(x1 - x2 > 50) {
                        Toast.makeText(MainActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
                    } else if(x2 - x1 > 50) {
                        Toast.makeText(MainActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
                    }
                }
                return true ;
            }
        });
    }



    private void setSecondMenuList() {
        list1 = (ListView) findViewById(R.id.listview_menu_right);
        //生成动态数组，加入数据
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<6;i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", R.drawable.aa);//图像资源的ID
            map.put("ItemTitle", "导航");
            listItem.add(map);
        }
        //生成适配器的Item和动态数组对应的元素
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源
                R.layout.left_right_item,//ListItem的XML实现
                //动态数组与ImageItem对应的子项
                new String[] {"ItemImage","ItemTitle"},
                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[] {R.id.ItemImage,R.id.ItemTitle}
        );

        //添加并且显示
        list1.setAdapter(listItemAdapter);
    }

    private void setMenuList() {
        list2 = (ListView) findViewById(R.id.listview_menu_left);
        //生成动态数组，加入数据
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<6;i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", R.drawable.aa);//图像资源的ID
            map.put("ItemTitle", "导航");
            listItem.add(map);
        }
        //生成适配器的Item和动态数组对应的元素
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源
                R.layout.left_right_item,//ListItem的XML实现
                //动态数组与ImageItem对应的子项
                new String[] {"ItemImage","ItemTitle"},
                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[] {R.id.ItemImage,R.id.ItemTitle}
        );

        //添加并且显示
        list2.setAdapter(listItemAdapter);
    }


    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                default:
                    break;
            }


        }

}
