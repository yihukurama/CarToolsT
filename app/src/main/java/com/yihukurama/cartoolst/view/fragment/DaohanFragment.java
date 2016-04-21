package com.yihukurama.cartoolst.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.yihukurama.cartoolst.CartoolApp;
import com.yihukurama.cartoolst.R;
import com.yihukurama.cartoolst.controler.MediaManager;
import com.yihukurama.cartoolst.controler.Utils;
import com.yihukurama.cartoolst.controler.sdk.baidu.Location.service.LocationService;
import com.yihukurama.cartoolst.model.ConstantValue;
import com.yihukurama.cartoolst.view.activity.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DaohanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DaohanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DaohanFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = DaohanFragment.class.getSimpleName();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    MapView mapView;
    BaiduMap mBaiduMap;
    EditText mudidiView;
    float bilichi = 16;
    Button bianqianBtn;
    Button cjiaBtn;
    Button cjianBtn;
    Button dinweiBtn;
    View view;
    private MainActivity context;

    public DaohanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DaohanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DaohanFragment newInstance(String param1, String param2) {
        DaohanFragment fragment = new DaohanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_daohan, container, false);

        return view;
    }

    @Override
    public void onStart() {
        prepare();
        initView(view);
        initData();
        super.onStart();
    }

    private void prepare() {
        context = (MainActivity)getActivity();
    }

    private void initData() {

        //两边侧滑栏忽略滑动时间
        context.menu.addIgnoredView(mapView);
        // -----------location config ------------
        locationService = ((CartoolApp) this.context.getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mLocListener);
        //注册监听
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());

        mBaiduMap = mapView.getMap();
        MapStatus ms = new MapStatus.Builder().overlook(-20).zoom(bilichi).build();

        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
        locationService.start();
    }

    private void initView(View view) {
        mapView = (MapView)view.findViewById(R.id.bmapView);
        mapView.showZoomControls(false);
        mudidiView = (EditText)view.findViewById(R.id.dizhi);
        cjiaBtn = (Button)view.findViewById(R.id.cjia);
        cjiaBtn.setOnClickListener(this);
        cjianBtn = (Button)view.findViewById(R.id.cjian);
        cjianBtn.setOnClickListener(this);
        bianqianBtn = (Button)view.findViewById(R.id.bianqian1);
        bianqianBtn.setOnClickListener(this);
        dinweiBtn = (Button)view.findViewById(R.id.dinwei);
        dinweiBtn.setOnClickListener(this);

        mudidiView.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				/*判断是否是“GO”键*/
                Log.i(TAG,actionId+"输入法搜索id");
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {

                    MediaManager.playDefault(context, ConstantValue.DAOHANMEDIA);
                    return true;
                }
                return false;
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        locationService.unregisterListener(mLocListener); //注销掉监听
        locationService.stop(); //停止定位服务
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bianqian1:
                //播放视频
                MediaManager.playDefault(context, ConstantValue.DAOHANMEDIA);
                break;
            case R.id.cjia:
                //放大地图
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
                break;
            case R.id.cjian:
                //缩小地图
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomOut());
                break;
            case R.id.dinwei:
                //定位
                Log.i(TAG,"开始定位");
                locationService.start();
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /********************百度地图*******************************/
    private LocationService locationService;

    /*****
     * @see copy funtion to you project
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDLocationListener mLocListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nerror code : ");
                sb.append(location.getLocType());
                sb.append("\nlatitude : ");
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");
                sb.append(location.getLongitude());
                sb.append("\nradius : ");
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");
                sb.append(location.getCityCode());
                sb.append("\ncity : ");
                sb.append(location.getCity());
                sb.append("\nDistrict : ");
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");
                sb.append(location.getStreet());
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\nDescribe: ");
                sb.append(location.getLocationDescribe());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());
                sb.append("\nPoi: ");
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 单位：米
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    sb.append("\noperationers : ");
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                getLocation(location);
                Log.i(TAG,sb.toString());
            }
        }

    };


    //定位
    private void getLocation(BDLocation location){
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);


        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);

        MapStatus ms = new MapStatus.Builder().overlook(-20).zoom(bilichi).targetScreen(new Point(1200, 400)).build();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(ms));


        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.weizhi);
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, mCurrentMarker);
        mBaiduMap.setMyLocationConfigeration(config);

        // 当不需要定位图层时关闭定位图层
//        mBaiduMap.setMyLocationEnabled(false);
        locationService.stop(); //停止定位服务
    }
}
