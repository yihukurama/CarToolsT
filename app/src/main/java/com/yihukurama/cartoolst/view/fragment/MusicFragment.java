package com.yihukurama.cartoolst.view.fragment;

import android.app.Activity;
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
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yihukurama.cartoolst.CartoolApp;
import com.yihukurama.cartoolst.R;
import com.yihukurama.cartoolst.controler.AnimationManager;
import com.yihukurama.cartoolst.controler.sevice.MediaService;
import com.yihukurama.cartoolst.model.ConstantValue;
import com.yihukurama.cartoolst.model.MusicBean;
import com.yihukurama.cartoolst.model.UriSet;


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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Activity activity;
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

    ImageButton playBtn;
    ImageButton pauseBtn;
    SeekBar seekBar;
    TextView textView;
    ImageView cdView;
    Animation cdAnimation;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        activity = getActivity();
        regesitBC();
//        seekBar.setProgress(CartoolApp.musicBean.getProgress());
//        seekBar.setMax(CartoolApp.musicBean.getMax());
//        textView.setText(CartoolApp.musicBean.getCurrentTime() + "/" + CartoolApp.musicBean.getMaxTime());
//        if(CartoolApp.getMusicStatus().equals(ConstantValue.PLAY)){
//            playBtn.setVisibility(View.GONE);
//            pauseBtn.setVisibility(View.VISIBLE);
//        }
    }

    private void initView(View view){
        view.findViewById(R.id.cd).setOnClickListener(this);
        playBtn = (ImageButton)view.findViewById(R.id.play);
        playBtn.setOnClickListener(this);
        pauseBtn = (ImageButton)view.findViewById(R.id.pause);
        pauseBtn.setOnClickListener(this);
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
        activity.unregisterReceiver(msgReceiver);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.cd:
                Uri uri = Uri.parse(UriSet.TRA2CALLF);
                mListener.onFragmentInteraction(uri);
                break;
            case R.id.play:
                playBtn.setVisibility(View.GONE);
                pauseBtn.setVisibility(View.VISIBLE);
                intent = new Intent(activity, MediaService.class);
                activity.startService(intent);
                break;
            case R.id.pause:
                playBtn.setVisibility(View.VISIBLE);
                pauseBtn.setVisibility(View.GONE);
                intent = new Intent(activity, MediaService.class);
                activity.startService(intent);

                break;
        }
    }

    MsgReceiver msgReceiver;
    private void regesitBC(){
        //动态注册广播接收器
        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.yihukurama.updatemusicpro");
        intentFilter.addAction("com.yihukurama.stopanimation");
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
                    case "com.yihukurama.updatemusicpro":
                        //拿到进度，更新UI
                        Log.i("debug", "收到广播");
                        MusicBean musicBean = (MusicBean)intent.getSerializableExtra("music");
                        seekBar.setProgress(musicBean.getProgress());
                        seekBar.setMax(musicBean.getMax());
                        textView.setText(musicBean.getCurrentTime() + "/" + musicBean.getMaxTime());

                        if (CartoolApp.cdAnimation == null){
                            CartoolApp.cdAnimation = AnimationManager.rotateSelf(cdView,40000);
                            CartoolApp.cdAnimation.start();
                        }else if (CartoolApp.cdAnimation.isPaused()){
                            CartoolApp.cdAnimation = AnimationManager.rotateSelf(cdView,40000);
                            CartoolApp.cdAnimation.resume();
                        }else if(!CartoolApp.cdAnimation.isStarted()){
                            CartoolApp.cdAnimation = AnimationManager.rotateSelf(cdView,40000);
                            CartoolApp.cdAnimation.resume();
                        }
                        break;
                    case "com.yihukurama.stopanimation":
                        CartoolApp.cdAnimation.pause();
                        break;

                }



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
}
