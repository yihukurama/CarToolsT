package com.yihukurama.cartoolst.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yihukurama.cartoolst.CartoolApp;
import com.yihukurama.cartoolst.R;
import com.yihukurama.cartoolst.view.activity.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShushiFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShushiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShushiFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    final String TAG = ShushiFragment.class.getSimpleName();
    Button kongtiaojia;
    Button kongtaiojian;
    Button fengliangjia;
    Button fengliangjian;
    Button fengxiangqian;
    Button fengxiangxia;
    TextView fengli;
    public TextView kongtiao;
    View v;
    ImageView fengxiang;
    MainActivity activity;
    int mode = 0;//前下，前，下
    public ShushiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShushiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShushiFragment newInstance(String param1, String param2) {
        ShushiFragment fragment = new ShushiFragment();
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
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_shushi, container, false);
        initView(v);
        initData();
        return v;
    }

    private void initView(View v) {
        kongtiaojia = (Button)v.findViewById(R.id.kongtiaojia);
        kongtaiojian = (Button)v.findViewById(R.id.kongtiaojian);
        fengliangjia = (Button)v.findViewById(R.id.fengliangjia);
        fengliangjian = (Button)v.findViewById(R.id.fengliangjian);
        fengxiangqian = (Button)v.findViewById(R.id.fengxiangqian);
        fengxiangxia = (Button)v.findViewById(R.id.fengxiangxia);
        fengli=(TextView)v.findViewById(R.id.fengli);
        kongtiao = (TextView)v.findViewById(R.id.kongtiao);
        fengxiang = (ImageView)v.findViewById(R.id.imageView2);
        kongtiaojia.setOnClickListener(this);
        kongtaiojian.setOnClickListener(this);
        fengliangjia.setOnClickListener(this);
        fengliangjian.setOnClickListener(this);
        fengxiangqian.setOnClickListener(this);
        fengxiangxia.setOnClickListener(this);
    }

    private void initData() {
        activity = (MainActivity)getActivity();


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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.kongtiaojia:
                if (CartoolApp.cheneidushu<30){
                    CartoolApp.cheneidushu++;
                    kongtiao.setText(CartoolApp.cheneidushu+"");
                    ((MainActivity)getActivity()).cheneiwendu.setText(CartoolApp.cheneidushu+"");
                }
                break;
            case R.id.kongtiaojian:
                if (CartoolApp.cheneidushu>16){
                    CartoolApp.cheneidushu--;
                    kongtiao.setText(CartoolApp.cheneidushu+"");
                    ((MainActivity)getActivity()).cheneiwendu.setText(CartoolApp.cheneidushu+"");
                }

                break;
            case R.id.fengliangjia:
                if (CartoolApp.fengliang<5){
                    CartoolApp.fengliang++;
                    fengli.setText(CartoolApp.fengliang+"");
                }
                break;
            case R.id.fengliangjian:
                if (CartoolApp.fengliang>1){
                    CartoolApp.fengliang--;
                    fengli.setText(CartoolApp.fengliang+"");
                }
                break;
            case R.id.fengxiangqian:
                if(mode == 0){//前下
                    fengxiang.setImageResource(R.drawable.fengxiangxia);
                    mode = 2;
                }else if(mode == 1){//前
                    fengxiang.setImageResource(R.drawable.fengxiangnone);
                    mode = -1;
                }else if(mode == 2){//下
                    fengxiang.setImageResource(R.drawable.fengxiangall);
                    mode = 0;
                }else if(mode == -1){//无
                    fengxiang.setImageResource(R.drawable.fengxiangyou);
                    mode = 1;
                }
                break;
            case R.id.fengxiangxia:
                if(mode == 0){//前下
                    fengxiang.setImageResource(R.drawable.fengxiangyou);
                    mode = 1;
                }else if(mode == 1){//前
                    fengxiang.setImageResource(R.drawable.fengxiangall);
                    mode = 0;
                }else if(mode == 2){//下
                    fengxiang.setImageResource(R.drawable.fengxiangnone);
                    mode = -1;
                }else if(mode == -1){//无
                    fengxiang.setImageResource(R.drawable.fengxiangxia);
                    mode = 2;
                }

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



}
