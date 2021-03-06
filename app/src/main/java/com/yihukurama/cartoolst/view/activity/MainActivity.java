package com.yihukurama.cartoolst.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yihukurama.cartoolst.CartoolApp;
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
    boolean isShowSecoundMenu = false;
    LinearLayout secll;
    FrameLayout fl;
    ImageButton menumusic;
    ImageButton menushushi;
    ImageButton menudaohang;
    ImageButton menudiantai;
    ImageButton menucall ;
    boolean isShowDaohan = false;
    TextView time1;
    TextView time2;
    TextView time3;
    public TextView cheneiwendu;
    public String currentFragment = "shushi";
    final String secoundMenu1[] ={"空调","车锁","时间","单位","语言"};
    final String secoundMenu2[] ={"音乐","视频","时间","电视","浏览器"};
    RelativeLayout background1;
    TextView value;
    TextView valueName;
    Button close;
    Button quedin;
    RelativeLayout shipinDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
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
        secll = (LinearLayout)findViewById(R.id.secoundmenu);
        r = (RelativeLayout)findViewById(R.id.main);
        fl = (FrameLayout)findViewById(R.id.showingfragment);
        time1 = (TextView)findViewById(R.id.hourstime);
        time2 = (TextView)findViewById(R.id.datetime);
        time3 = (TextView)findViewById(R.id.weektime);
        image1 = (ImageView)findViewById(R.id.imageleft);
        connectTV = (TextView)findViewById(R.id.connect);
        cheneiwendu = (TextView)findViewById(R.id.cheneiwendu);
        background1 = (RelativeLayout)findViewById(R.id.background1);
        value = (TextView)findViewById(R.id.value);
        valueName = (TextView)findViewById(R.id.valuename);
        close = (Button)findViewById(R.id.close);
        close.setOnClickListener(this);
        quedin = (Button)findViewById(R.id.quedin);
        quedin.setOnClickListener(this);
        shipinDialog = (RelativeLayout)findViewById(R.id.shipindialog);
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
        findViewById(R.id.yinyue).setOnClickListener(null);
        findViewById(R.id.shipin).setOnClickListener(null);
        findViewById(R.id.dianshi).setOnClickListener(null);
        findViewById(R.id.youxi).setOnClickListener(null);
        findViewById(R.id.liulanqi).setOnClickListener(null);
        setSecMenu();
        //开启播放音乐的service
        Intent intentPlay = new Intent(context,MediaService.class);
        intentPlay.putExtra("cmd", "");
        startService(intentPlay);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
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
                        endGesture();
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

//        bcs.startServer();
        connectTV.setBackground(CartoolApp.getInstace().getResources().getDrawable(R.drawable.dengdai));
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
//            case R.id.connect://开启蓝牙服务端
//
//                try{
//                    bcs.shutdownServer();
//                }catch (Exception e){
//
//                }
//                bcs.startServer();
//                break;
            case R.id.close:
                shipinDialog.setVisibility(View.GONE);
                break;
            case R.id.quedin:
                MediaManager.playDefault(context, ConstantValue.DAOHANMEDIA6);
                shipinDialog.setVisibility(View.GONE);
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
        currentFragment = "duomeiti";
        setSecMenu();
        isShowDaohan = false;
    }
    private void transDiantai(){
        hideDuomeiTiMenu();
        transaction = fm.beginTransaction();
        if (diantaiFragment == null) {
            diantaiFragment = new DiantaiFragment();
        }
        // 使用当前Fragment的布局替代id_content的控件
        transaction.replace(R.id.showingfragment, diantaiFragment);
        transaction.commit();
        currentFragment = "diantai";
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
        currentFragment = "shushi";
        setSecMenu();
        isShowDaohan = false;
    }

    private void transDaohan(){
        hideDuomeiTiMenu();
        transaction = fm.beginTransaction();
        if (daohanFragment == null) {
            daohanFragment = new DaohanFragment();
        }

        // 使用当前Fragment的布局替代id_content的控件
        transaction.replace(R.id.showingfragment, daohanFragment);
        transaction.commit();
        currentFragment = "daohan";
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

    private void setSecMenu(){
        String currentMenup[] = null;
        if(currentFragment.equals("duomeiti")){
            currentMenup = secoundMenu2;
            ((TextView)findViewById(R.id.yinyue)).setText(currentMenup[0]);
            ((TextView)findViewById(R.id.shipin)).setText(currentMenup[1]);
            ((TextView)findViewById(R.id.dianshi)).setText(currentMenup[2]);
            ((TextView)findViewById(R.id.youxi)).setText(currentMenup[3]);
            ((TextView)findViewById(R.id.liulanqi)).setText(currentMenup[4]);
        }else if(currentFragment.equals("shushi")){
            currentMenup = secoundMenu1;
            ((TextView)findViewById(R.id.yinyue)).setText(currentMenup[0]);
            ((TextView)findViewById(R.id.shipin)).setText(currentMenup[1]);
            ((TextView)findViewById(R.id.dianshi)).setText(currentMenup[2]);
            ((TextView)findViewById(R.id.youxi)).setText(currentMenup[3]);
            ((TextView)findViewById(R.id.liulanqi)).setText(currentMenup[4]);
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
//                connectTV.setText("手机信息：" + mes);
                String sub[] = mes.split(":");
                int precent= 0;
                if(sub.length>1){
                    precent =Integer.parseInt(sub[sub.length-1]);
                    mes = sub[0];
                }

                switch (mes){
                    case Command.YINLIANG:
                        onVolumeSlide(precent);
                        endGesture();
                        break;

                    case Command.WENDUJIA:
                        CartoolApp.cheneidushu = precent;
                        valueName.setText("温度");
                        background1.setVisibility(View.VISIBLE);
                        value.setText(sub[1]);
                        cheneiwendu.setText(sub[1]+" ");
                        if(shushiFragment!=null) shushiFragment.kongtiao.setText(sub[1]);
                        endGesture();
                        break;
                    case Command.WENDUJIAN:
                        CartoolApp.cheneidushu = precent;
                        valueName.setText("温度");
                        background1.setVisibility(View.VISIBLE);
                        value.setText(sub[1]);
                        cheneiwendu.setText(sub[1]+" ");
                        if(shushiFragment!=null) shushiFragment.kongtiao.setText(sub[1]);
                        endGesture();
                        break;
                    case Command.SAVEADDRESS:
                        shipinDialog.setVisibility(View.VISIBLE);
                        break;

                    case Command.EXIT:
                        try{
                            bcs.shutdownServer();
                        }catch (Exception e){

                        }
                        break;
                    case Command.ENDGESTURE:
                        endGesture();
                        break;
                    default:
                        break;
                }
            }
            else//服务器端
            {
                String remes = (String)msg.obj;
                Log.i(TAG, "server" + remes);
                if(remes.contains("正在连接平板")){
                    connectTV.setBackground(CartoolApp.getInstace().getResources().getDrawable(R.drawable.lianjiezhong));
                }else if(remes.contains("已连接")){
                    connectTV.setBackground(CartoolApp.getInstace().getResources().getDrawable(R.drawable.yilianjie));

                    bcs.sendMessageHandle(Command.DANGQIANWENDU + CartoolApp.cheneidushu);
                }

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

    /** 定时隐藏 */
    private Handler mDismissHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            background1.setVisibility(View.GONE);
        }
    };

    /** 手势结束 */
    private void endGesture() {
        mVolume = -1;

        // 隐藏
        mDismissHandler.removeMessages(0);
        mDismissHandler.sendEmptyMessageDelayed(0, 1000);
        Log.i(TAG,"结束手势");
    }

    /**
     * 滑动改变声音大小
     *
     * @param percent
     */
    /** 最大声音 */
    private int mMaxVolume;
    /** 当前声音 */
    private int mVolume = -1;
    private AudioManager mAudioManager;
    private void onVolumeSlide(int percent) {
        valueName.setText("温度");
        background1.setVisibility(View.VISIBLE);
        int index = (int)(percent/100f*15);
        // 变更声音
        valueName.setText("音量");
        value.setText(percent+"");
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
        Log.i(TAG,"调节音量");
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

            if(Math.abs(moveX)<Math.abs(moveY) && Math.abs(moveY)>50){//上下滑动
                if(moveY<0){
                    Log.i(TAG, "上滑" + mode);

                    if(mode == 1){
                        showShortToast("oneup");

                    }else if(mode == 2){
                        if(currentFragment.equals("shushi")||currentFragment.equals("duomeiti")){
                            showDuomeiTiMenu();
                        }
                    }else{
                        showShortToast("threeup");
                    }
                }else{
                    Log.i(TAG,"下滑"+mode);



                    if(mode == 1){

                    }else if(mode == 2){

                        if(menu.isMenuShowing() || menu.isSecondaryMenuShowing()){
                            menu.toggle();
                        }
                        if(isShowSecoundMenu){
                            hideDuomeiTiMenu();
                        }else if (!isShowDaohan){
                            transDaohan();
                        }
                    }else{
                    }
                }

            }else if ( Math.abs(moveX)>50){//左右滑动
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


    private void showDuomeiTiMenu(){
        if (!isShowSecoundMenu){
            isShowSecoundMenu = true;
            findViewById(R.id.secoundmenu).setVisibility(View.VISIBLE);
            AnimationManager.transY(fl, -180);
        }
    }

    private void hideDuomeiTiMenu(){
        if (isShowSecoundMenu){
            isShowSecoundMenu = false;
            AnimationManager.transY(fl, 0);
            findViewById(R.id.secoundmenu).setVisibility(View.GONE);

        }
    }

}
