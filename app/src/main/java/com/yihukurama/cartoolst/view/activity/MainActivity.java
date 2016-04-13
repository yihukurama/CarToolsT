package com.yihukurama.cartoolst.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yihukurama.cartoolst.R;
import com.yihukurama.cartoolst.controler.bluetooth.BluetoothCS;
import com.yihukurama.cartoolst.controler.sevice.MediaService;
import com.yihukurama.cartoolst.model.Command;
import com.yihukurama.cartoolst.model.UriSet;
import com.yihukurama.cartoolst.view.fragment.CallFragment;
import com.yihukurama.cartoolst.view.fragment.DaohanFragment;
import com.yihukurama.cartoolst.view.fragment.DiantaiFragment;
import com.yihukurama.cartoolst.view.fragment.MusicFragment;
import com.yihukurama.cartoolst.view.fragment.ShushiFragment;

public class MainActivity extends AppCompatActivity implements CallFragment.OnFragmentInteractionListener,
        MusicFragment.OnFragmentInteractionListener, ShushiFragment.OnFragmentInteractionListener,
        DaohanFragment.OnFragmentInteractionListener, DiantaiFragment.OnFragmentInteractionListener, View.OnClickListener {


    final static String TAG = MainActivity.class.getSimpleName();
    ImageView image1;
    SlidingMenu menu;
    TextView connectTV;
    Context context;
    BluetoothCS bcs;
    FragmentManager fm;
    FragmentTransaction transaction;
    private CallFragment callFragment;
    private MusicFragment musicFragment;
    private ShushiFragment shushiFragment;
    private DaohanFragment daohanFragment;
    private DiantaiFragment diantaiFragment;

    ImageButton menumusic;
    ImageButton menushushi;
    ImageButton menudaohang;
    ImageButton menudiantai;
    ImageButton menucall ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        prepare();
        initView();
        initData();

    }

    private void prepare() {
        context = this;
        setDefaultFragment();

    }

    private void setDefaultFragment() {
        fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        //overlook:俯视角；zoom：缩放
        MapStatus ms = new MapStatus.Builder().overlook(-20).zoom(18).build();
        //compassEnabled是否开启指南针；zoomControlsEnabled：是否按比例缩放；
        BaiduMapOptions bo = new BaiduMapOptions().mapStatus(ms).compassEnabled(false).zoomControlsEnabled(false);

        daohanFragment = new DaohanFragment();
        transaction.replace(R.id.showingfragment, daohanFragment);
        transaction.commit();

        musicFragment = new MusicFragment();
        shushiFragment = new ShushiFragment();
        callFragment = new CallFragment();
        diantaiFragment = new DiantaiFragment();
    }

    private void initView() {
        image1 = (ImageView)findViewById(R.id.imageleft);
        connectTV = (TextView)findViewById(R.id.connect);
        connectTV.setOnClickListener(this);
        setDefaultFragment();
        initSlidMenu();

    }

    private void initData() {
        //开启播放音乐的service
        Intent intentPlay = new Intent(context,MediaService.class);
        intentPlay.putExtra("cmd", "");
        startService(intentPlay);
        bcs = new BluetoothCS("C0:EE:FB:46:90:33",LinkDetectedHandler);

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
        menu.setOnClickListener(this);
        menumusic = (ImageButton) menu.findViewById(R.id.menumusic);
        menushushi = (ImageButton) menu.findViewById(R.id.menushushi);
        menudaohang = (ImageButton) menu.findViewById(R.id.rbutton1);
        menudiantai = (ImageButton) menu.findViewById(R.id.rbutton2);
        menucall = (ImageButton) menu.findViewById(R.id.rbutton4);
        menu.addIgnoredView(menushushi);
        menu.addIgnoredView(menumusic);
        menu.addIgnoredView(menudaohang);
        menu.addIgnoredView(menudiantai);
        menu.addIgnoredView(menucall);
        menumusic.setOnClickListener(this);
        menushushi.setOnClickListener(this);
        menudaohang.setOnClickListener(this);
        menudiantai.setOnClickListener(this);
        menucall.setOnClickListener(this);

        menumusic = (ImageButton) menu.getSecondaryMenu().findViewById(R.id.menumusic);
        menushushi = (ImageButton) menu.getSecondaryMenu().findViewById(R.id.menushushi);
        menudaohang = (ImageButton) menu.getSecondaryMenu().findViewById(R.id.rbutton1);
        menudiantai = (ImageButton) menu.getSecondaryMenu().findViewById(R.id.rbutton2);
        menucall = (ImageButton) menu.getSecondaryMenu().findViewById(R.id.rbutton4);
        menu.addIgnoredView(menushushi);
        menu.addIgnoredView(menumusic);
        menu.addIgnoredView(menudaohang);
        menu.addIgnoredView(menudiantai);
        menu.addIgnoredView(menucall);
        menumusic.setOnClickListener(this);
        menushushi.setOnClickListener(this);
        menudaohang.setOnClickListener(this);
        menudiantai.setOnClickListener(this);
        menucall.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menumusic:
                menu.toggle();
                transaction = fm.beginTransaction();
                if (musicFragment == null) {
                    musicFragment = new MusicFragment();
                }
                // 使用当前Fragment的布局替代id_content的控件
                transaction.replace(R.id.showingfragment, musicFragment);
                transaction.commit();
                break;
            case R.id.menushushi:
                menu.toggle();
                transaction = fm.beginTransaction();
                if (shushiFragment == null) {
                    shushiFragment = new ShushiFragment();
                }
                // 使用当前Fragment的布局替代id_content的控件
                transaction.replace(R.id.showingfragment, shushiFragment);
                transaction.commit();
                break;
            case R.id.rbutton1:
                menu.toggle();
                transaction = fm.beginTransaction();
                if (daohanFragment == null) {
                    //overlook:俯视角；zoom：缩放
                    MapStatus ms = new MapStatus.Builder().overlook(-20).zoom(18).build();
                    //compassEnabled是否开启指南针；zoomControlsEnabled：是否按比例缩放；
                    BaiduMapOptions bo = new BaiduMapOptions().mapStatus(ms).compassEnabled(false).zoomControlsEnabled(false);

                    daohanFragment = new DaohanFragment();

                }

                // 使用当前Fragment的布局替代id_content的控件
                transaction.replace(R.id.showingfragment, daohanFragment);
                transaction.commit();

                break;
            case R.id.rbutton2:
                menu.toggle();
                transaction = fm.beginTransaction();
                if (diantaiFragment == null) {
                    diantaiFragment = new DiantaiFragment();
                }
                // 使用当前Fragment的布局替代id_content的控件
                transaction.replace(R.id.showingfragment, diantaiFragment);
                transaction.commit();
                break;
            case R.id.rbutton4:
                menu.toggle();
                transaction = fm.beginTransaction();
                if (callFragment == null) {
                    callFragment = new CallFragment();
                }
                // 使用当前Fragment的布局替代id_content的控件
                transaction.replace(R.id.showingfragment, callFragment);
                transaction.commit();
                break;
            case R.id.connect://开启蓝牙服务端
                bcs.startServer();
                break;
            default:
                break;

        }


    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        String intent = uri.toString();
        switch (intent) {
            case UriSet.PLAYMUSIC:
                Intent intentPlay = new Intent(context,MediaService.class);
                intentPlay.putExtra("cmd", "play");
                startService(intentPlay);
                break;
            case UriSet.PAUSEMUSIC:
                Intent intentPause = new Intent(context,MediaService.class);
                intentPause.putExtra("cmd","pause");
                startService(intentPause);
                break;
            case UriSet.NEXTMUSIC:
                Intent intentNext = new Intent(context,MediaService.class);
                intentNext.putExtra("cmd","next");
                startService(intentNext);
                break;
            case UriSet.LASTMUSIC:
                Intent intentLast = new Intent(context,MediaService.class);
                intentLast.putExtra("cmd","last");
                startService(intentLast);
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MediaService.class));
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            System.exit(0);
        }

        return super.onKeyDown(keyCode, event);
    }


    private Handler LinkDetectedHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if(msg.what==1)//客户端
            {
                String mes = (String)msg.obj;
                Log.i(TAG, "client" + mes);
                connectTV.setText("手机信息：" + mes);
                switch (mes){
                    case Command.SKIPDUOMEITI:
                        transaction = fm.beginTransaction();
                        if (musicFragment == null) {
                            musicFragment = new MusicFragment();
                        }
                        // 使用当前Fragment的布局替代id_content的控件
                        transaction.replace(R.id.showingfragment, musicFragment);
                        transaction.commit();
                        break;
                    default:
                        break;
                }
            }
            else//服务器端
            {
                String remes = (String)msg.obj;
                Log.i(TAG, "server" + remes);
                connectTV.setText("平板信息："+remes);





            }
        }

    };

}
