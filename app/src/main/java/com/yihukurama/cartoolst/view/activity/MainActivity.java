package com.yihukurama.cartoolst.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapStatus;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yihukurama.cartoolst.R;
import com.yihukurama.cartoolst.controler.AnimationManager;
import com.yihukurama.cartoolst.controler.MediaManager;
import com.yihukurama.cartoolst.controler.Utils;
import com.yihukurama.cartoolst.controler.bluetooth.BluetoothCS;
import com.yihukurama.cartoolst.controler.broadcast.SendBroadCast;
import com.yihukurama.cartoolst.controler.sevice.MediaService;
import com.yihukurama.cartoolst.model.Command;
import com.yihukurama.cartoolst.model.ConstantValue;
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
    public SlidingMenu menu;
    TextView connectTV;
    Context context;
    BluetoothCS bcs;
    FragmentManager fm;
    RelativeLayout r;
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
    boolean isShowDaohan = true;
    TextView time1;
    TextView time2;
    TextView time3;
    public TextView cheneiwendu;
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
        musicFragment = new MusicFragment();
        shushiFragment = new ShushiFragment();
        callFragment = new CallFragment();
        diantaiFragment = new DiantaiFragment();
        daohanFragment = new DaohanFragment();

        fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.showingfragment, shushiFragment);
        transaction.commit();


    }

    private void initView() {
        r = (RelativeLayout)findViewById(R.id.main);
        time1 = (TextView)findViewById(R.id.hourstime);
        time2 = (TextView)findViewById(R.id.datetime);
        time3 = (TextView)findViewById(R.id.weektime);
        image1 = (ImageView)findViewById(R.id.imageleft);
        connectTV = (TextView)findViewById(R.id.connect);
        cheneiwendu = (TextView)findViewById(R.id.cheneiwendu);
        connectTV.setOnClickListener(this);
        setDefaultFragment();
        initSlidMenu();

    }


    double moveY;
    double moveX;
    double bX,bY,eX,eY;
    int mode;
    GestureDetector gestureDetector;
    private void initData() {
        //开启播放音乐的service
        Intent intentPlay = new Intent(context,MediaService.class);
        intentPlay.putExtra("cmd", "");
        startService(intentPlay);
        bcs = new BluetoothCS("C0:EE:FB:46:90:33",LinkDetectedHandler);
        time2.setText(Utils.getTimeDate());
        time3.setText(Utils.getWeekDate());
        regesitBC();

        menu.addIgnoredView(r);
        gestureDetector = new GestureDetector(this,new MyGesture());
        r.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        mode = 1;
                        bX = event.getX();
                        bY = event.getY();
                        Log.i(TAG, "单点down");
                        break;
                    case MotionEvent.ACTION_UP:
                        eX = event.getX();
                        eY = event.getY();
                        Log.i(TAG, "单点up");
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        mode += 1;
                        Log.i(TAG, "多点" + mode);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 手指滑动事件
                        if (mode == 1) {
                            // 是一个手指拖动

                        } else if (mode == 2) {
                            // 两个手指滑动

                        } else if (mode == 3) {

                        }
                        break;
                }
                return gestureDetector.onTouchEvent(event);
            }
        });
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

        RelativeLayout lm = (RelativeLayout)menu.getSecondaryMenu().findViewById(R.id.left_menu);
        menu.addIgnoredView(lm);
        menumusic = (ImageButton) menu.findViewById(R.id.menumusic);
        menushushi = (ImageButton) menu.findViewById(R.id.menushushi);
        menudaohang = (ImageButton) menu.findViewById(R.id.rbutton1);
        menudiantai = (ImageButton) menu.findViewById(R.id.rbutton2);
        menucall = (ImageButton) menu.findViewById(R.id.rbutton4);
        menumusic.setOnClickListener(this);
        menushushi.setOnClickListener(this);
        menudaohang.setOnClickListener(this);
        menudiantai.setOnClickListener(this);
        menucall.setOnClickListener(this);

        RelativeLayout rm = (RelativeLayout)menu.getSecondaryMenu().findViewById(R.id.right_menu);
        menu.addIgnoredView(rm);
        menumusic = (ImageButton) menu.getSecondaryMenu().findViewById(R.id.menumusic);
        menushushi = (ImageButton) menu.getSecondaryMenu().findViewById(R.id.menushushi);
        menudaohang = (ImageButton) menu.getSecondaryMenu().findViewById(R.id.rbutton1);
        menudiantai = (ImageButton) menu.getSecondaryMenu().findViewById(R.id.rbutton2);
        menucall = (ImageButton) menu.getSecondaryMenu().findViewById(R.id.rbutton4);
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
                transDuomeiti();
                break;
            case R.id.menushushi:
                menu.toggle();
                transShushi();
                break;
            case R.id.rbutton1:
                menu.toggle();
                transDaohan();
                break;
            case R.id.rbutton2:
                menu.toggle();
                transDiantai();
                break;
            case R.id.rbutton4:
                menu.toggle();
                MediaManager.playDefault(context, ConstantValue.TONGXUNMEDIA);
                break;
            case R.id.connect://开启蓝牙服务端

                try{
                    bcs.shutdownServer();
                }catch (Exception e){

                }
                bcs.startServer();
                break;
            default:
                break;

        }
    }

    private void transDuomeiti(){
        transaction = fm.beginTransaction();
        if (musicFragment == null) {
            musicFragment = new MusicFragment();
        }
        // 使用当前Fragment的布局替代id_content的控件
        transaction.replace(R.id.showingfragment, musicFragment);
        transaction.commit();
        isShowDaohan = false;
    }
    private void transDiantai(){
        transaction = fm.beginTransaction();
        if (diantaiFragment == null) {
            diantaiFragment = new DiantaiFragment();
        }
        // 使用当前Fragment的布局替代id_content的控件
        transaction.replace(R.id.showingfragment, diantaiFragment);
        transaction.commit();
        isShowDaohan = false;
    }
    private void transShushi(){
        transaction = fm.beginTransaction();
        if (shushiFragment == null) {
            shushiFragment = new ShushiFragment();
        }
        // 使用当前Fragment的布局替代id_content的控件
        transaction.replace(R.id.showingfragment, shushiFragment);
        transaction.commit();
        isShowDaohan = false;
    }
    private void transTongxun(){
        transaction = fm.beginTransaction();
        if (callFragment == null) {
            callFragment = new CallFragment();
        }
        // 使用当前Fragment的布局替代id_content的控件
        transaction.replace(R.id.showingfragment, callFragment);
        transaction.commit();
        isShowDaohan = false;
    }
    private void transDaohan(){
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
        isShowDaohan = true;
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
        unregisterReceiver(msgReceiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            stopService(new Intent(this, MediaService.class));
            System.exit(0);
        }

        return super.onKeyDown(keyCode, event);
    }


    private Handler LinkDetectedHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if(MediaManager.isplaying) return;
            if(msg.what==1)//客户端
            {
                String mes = (String)msg.obj;
                Log.i(TAG, "client" + mes);
                connectTV.setText("手机信息：" + mes);
                switch (mes){
                    case Command.SKIPDUOMEITI:
                        if(menu.isMenuShowing() || menu.isSecondaryMenuShowing()){
                            menu.toggle();
                        }
                        transDuomeiti();
                        break;
                    case Command.SKIPDAOHAN:
                        if(menu.isMenuShowing() || menu.isSecondaryMenuShowing()){
                        menu.toggle();
                         }
                        transDaohan();
                        break;
                    case Command.SKIPDIANTAI:
                        if(menu.isMenuShowing() || menu.isSecondaryMenuShowing()){
                        menu.toggle();
                         }
                        transDiantai();
                        break;
                    case Command.SKIPSHUSHI:
                        if(menu.isMenuShowing() || menu.isSecondaryMenuShowing()){
                            menu.toggle();
                         }
                        transShushi();
                        break;
                    case Command.SKIPTONGXUN:
                        if(menu.isMenuShowing() || menu.isSecondaryMenuShowing()){
                            menu.toggle();
                        }
                        transTongxun();
                        break;
                    case Command.TWOLEFT:
                        if(menu.isMenuShowing() && !menu.isSecondaryMenuShowing()){
                            menu.toggle();
                        }else if(!menu.isSecondaryMenuShowing()){
                            menu.showSecondaryMenu();
                        }

                        break;
                    case Command.TWORIGHT:
                        if(menu.isSecondaryMenuShowing()){
                            menu.toggle();
                        }else if(!menu.isMenuShowing()){
                            menu.showMenu();
                        }
                        break;
                    case Command.TWOUP:
                        if(menu.isMenuShowing() || menu.isSecondaryMenuShowing()){
                            menu.toggle();
                        }
                        break;
                    case Command.TWODOWN:
                        if(menu.isMenuShowing() || menu.isSecondaryMenuShowing()){
                            menu.toggle();
                        }
                        if (isShowDaohan){

                        }else{
                            transDaohan();
                        }
                        break;
                    case Command.EXIT:
                        bcs.shutdownServer();
                        bcs.shutdownServer();
                        bcs.startServer();
                        break;
                    default:
                        break;
                }
            }
            else//服务器端
            {
                String remes = (String)msg.obj;
                Log.i(TAG, "server" + remes);
                connectTV.setText(remes);

            }
        }

    };


    MsgReceiver msgReceiver;
    private void regesitBC(){
        //动态注册广播接收器
        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SendBroadCast.resetTime);
        registerReceiver(msgReceiver, intentFilter);

    }

    /**
     * 广播接收器
     * @author len
     *
     */
    public class MsgReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch ( intent.getAction() ){
                case SendBroadCast.resetTime:
                    time1.setText(Utils.getTimeHours());


                 break;

            }



        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaManager.isplaying = false;

    }

    public void showShortToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    class MyGesture implements GestureDetector.OnGestureListener{

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.i(TAG, "mode" + mode);

            moveX = eX - bX;
            moveY = eY - bY;

            if(Math.abs(moveX)<Math.abs(moveY) && Math.abs(moveY)>120){//上下滑动
                if(moveY<0){
                    Log.i(TAG, "上滑" + mode);
                    if(mode == 1){
                        showShortToast("oneup");
                        AnimationManager.moveVertical(180,0,shushiFragment.getView(),1000);
                    }else if(mode == 2){
                        showShortToast("twoup");
                    }else{
                        showShortToast("threeup");
                    }
                }else{
                    Log.i(TAG,"下滑"+mode);
                    if(mode == 1){

                    }else if(mode == 2){
                    }else{
                    }
                }

            }else{//左右滑动
                if(moveX<0){
                    Log.i(TAG, "左滑" + mode);
                    if(menu.isMenuShowing() && !menu.isSecondaryMenuShowing()){
                        menu.toggle();
                    }else if(!menu.isSecondaryMenuShowing()){
                        menu.showSecondaryMenu();
                    }
                }else{
                    Log.i(TAG, "右滑"+mode);
                    if(menu.isSecondaryMenuShowing()){
                        menu.toggle();
                    }else if(!menu.isMenuShowing()){
                        menu.showMenu();
                    }
                }
            }



            mode = 0;
            Log.i(TAG,"清除");
            return false;
        }
    }
}
