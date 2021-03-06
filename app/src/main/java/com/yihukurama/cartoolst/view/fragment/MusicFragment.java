package com.yihukurama.cartoolst.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yihukurama.cartoolst.CartoolApp;
import com.yihukurama.cartoolst.R;
import com.yihukurama.cartoolst.controler.AnimationManager;
import com.yihukurama.cartoolst.controler.broadcast.SendBroadCast;
import com.yihukurama.cartoolst.model.ConstantValue;
import com.yihukurama.cartoolst.model.MusicBean;
import com.yihukurama.cartoolst.model.UriSet;
import com.yihukurama.cartoolst.view.activity.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MusicFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MusicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    final String TAG = MusicFragment.class.getSimpleName();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private MainActivity activity;
    public MusicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MusicFragment newInstance(String param1, String param2) {
        MusicFragment fragment = new MusicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    TextView musicName;
    TextView singer;
    ImageButton playBtn;
    ImageButton pauseBtn;
    ImageButton nextBtn;
    ImageButton lastBtn;
    SeekBar seekBar;
    TextView textView;
    ImageView cdView;

    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initView(view);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_music, container, false);
        return view;
    }


    private void initData() {
        activity = (MainActivity)getActivity();
        regesitBC();

        resetUI();




    }
    @Override
    public void onResume() {
        super.onResume();
        if (CartoolApp.cdAnimation != null && CartoolApp.cdAnimation.isRunning())
        {
            CartoolApp.cdAnimation.resume();
        }
    }

    private void initView(View view){
        playBtn = (ImageButton)view.findViewById(R.id.play);
        playBtn.setOnClickListener(this);
        pauseBtn = (ImageButton)view.findViewById(R.id.pause);
        pauseBtn.setOnClickListener(this);
        nextBtn = (ImageButton)view.findViewById(R.id.next);
        nextBtn.setOnClickListener(this);
        lastBtn = (ImageButton)view.findViewById(R.id.last);
        lastBtn.setOnClickListener(this);
        musicName = (TextView)view.findViewById(R.id.musicname);
        singer = (TextView)view.findViewById(R.id.singer);
        seekBar = (SeekBar)view.findViewById(R.id.seekBar);
        textView = (TextView)view.findViewById(R.id.time);
        cdView = (ImageView)view.findViewById(R.id.cd);

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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.unregisterReceiver(msgReceiver);
    }

    @Override
    public void onClick(View v) {
        Uri uri;
        switch (v.getId()){
            case R.id.play:
                playBtn.setVisibility(View.GONE);
                pauseBtn.setVisibility(View.VISIBLE);
                uri = Uri.parse(UriSet.PLAYMUSIC);
                mListener.onFragmentInteraction(uri);
                break;
            case R.id.pause:
                playBtn.setVisibility(View.VISIBLE);
                pauseBtn.setVisibility(View.GONE);
                uri = Uri.parse(UriSet.PAUSEMUSIC);
                mListener.onFragmentInteraction(uri);
                break;
            case R.id.next:
                playBtn.setVisibility(View.GONE);
                pauseBtn.setVisibility(View.VISIBLE);
                uri = Uri.parse(UriSet.NEXTMUSIC);
                mListener.onFragmentInteraction(uri);
                break;
            case R.id.last:
                playBtn.setVisibility(View.GONE);
                pauseBtn.setVisibility(View.VISIBLE);
                uri = Uri.parse(UriSet.LASTMUSIC);
                mListener.onFragmentInteraction(uri);
                break;
        }
    }

    MsgReceiver msgReceiver;
    private void regesitBC(){
        //动态注册广播接收器
        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SendBroadCast.stopAnimation);
        intentFilter.addAction(SendBroadCast.resetSeekBar);
        intentFilter.addAction(SendBroadCast.resumeAnimation);
        intentFilter.addAction(SendBroadCast.startAnimation);
        activity.registerReceiver(msgReceiver, intentFilter);

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
                    case SendBroadCast.resetSeekBar:
                        //拿到进度，更新UI
                        Log.i("debug", "收到广播");
                        MusicBean musicBean = (MusicBean)intent.getSerializableExtra("music");
                        seekBar.setProgress(musicBean.getProgress());
                        seekBar.setMax(musicBean.getMax());
                        textView.setText(musicBean.getCurrentTime() + "/" + musicBean.getMaxTime());

                        break;
                    case SendBroadCast.stopAnimation:
                        Log.i("debug", "暂停动画");
                        CartoolApp.cdAnimation.pause();
                        break;
                    case SendBroadCast.startAnimation:
                        Log.i("debug", "开始执行动画");
                        if (CartoolApp.cdAnimation.isRunning()){
                            CartoolApp.cdAnimation.end();
                        }
                        CartoolApp.cdAnimation = AnimationManager.rotateSelf(cdView, 40000);
                        CartoolApp.cdAnimation.start();
                        //更新cd，歌曲名和作者
                        refreshUI();

                        break;
                    case SendBroadCast.resumeAnimation:

                            if(CartoolApp.cdAnimation.isStarted()){
                                Log.i("debug", "恢复动画");
                                CartoolApp.cdAnimation.resume();
                            }


                        break;

                }



            }

        }

    private void resetUI(){
        MusicBean musicBean = CartoolApp.musicBeanList.get(CartoolApp.currentMusicIndex);
        seekBar.setProgress(musicBean.getProgress());
        seekBar.setMax(musicBean.getMax());
        textView.setText(musicBean.getCurrentTime() + "/" + musicBean.getMaxTime());
        if(CartoolApp.getMusicStatus().equals(ConstantValue.PLAY)){
            playBtn.setVisibility(View.GONE);
            pauseBtn.setVisibility(View.VISIBLE);
        }
        if (CartoolApp.cdAnimation == null){
            CartoolApp.cdAnimation = AnimationManager.rotateSelf(cdView, 40000);
            Log.i("debug", "初始化动画");
        }

        if (CartoolApp.getMusicStatus().equals(ConstantValue.PLAY)){
            Log.i("debug", "重新开始动画");
            CartoolApp.cdAnimation = AnimationManager.rotateSelf(cdView, 40000);
            CartoolApp.cdAnimation.start();
        }else if(CartoolApp.getMusicStatus().equals(ConstantValue.PAUSE)){
            Log.i("debug", "结束动画");
            CartoolApp.cdAnimation.end();
            CartoolApp.setMusicStatus(ConstantValue.STOP);
        }
        refreshUI();
    }
    private void refreshUI() {

        MusicBean musicBean = CartoolApp.musicBeanList.get(CartoolApp.currentMusicIndex);
        cdView.setImageResource(musicBean.getCdView());
        musicName.setText(musicBean.getName());
        singer.setText(musicBean.getSinger());

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

}
