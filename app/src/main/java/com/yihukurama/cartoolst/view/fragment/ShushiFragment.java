package com.yihukurama.cartoolst.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yihukurama.cartoolst.R;


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

    Button kongtiaojia;
    Button kongtaiojian;
    Button fengliangjia;
    Button fengliangjian;
    Button fengxiangqian;
    Button fengxiangxia;
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
        View v = inflater.inflate(R.layout.fragment_shushi, container, false);
        initView(v);
        initData();
        return v;
    }

    private void initView(View v) {
        kongtiaojia = (Button)v.findViewById(R.id.kongtiaojia);
        kongtaiojian = (Button)v.findViewById(R.id.kongtiaojian);
        fengliangjia = (Button)v.findViewById(R.id.fenliangjia);
        fengliangjian = (Button)v.findViewById(R.id.fengliangjian);
        fengxiangqian = (Button)v.findViewById(R.id.fengxiangqian);
        fengxiangxia = (Button)v.findViewById(R.id.fengxiangxia);

        kongtiaojia.setOnClickListener(this);
        kongtaiojian.setOnClickListener(this);
        fengliangjia.setOnClickListener(this);
        fengliangjian.setOnClickListener(this);
        fengxiangqian.setOnClickListener(this);
        fengxiangxia.setOnClickListener(this);
    }

    private void initData() {

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
    public void onClick(View v) {

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
