package com.example.cuikang.poemmaster.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.cuikang.poemmaster.R;
import com.example.cuikang.poemmaster.UserManage.UserManage;

public class MeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    public MeFragment() {
        // Required empty public constructor
    }

    //创建并返回一个实例
    public static MeFragment newInstance(String param1) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //创建fragment对应的视图，并返回给调用者
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //页面是静态的，不需要调整
       /*
        View view=inflater.inflate(R.layout.fragment_me,null);
        Button btn_sign_out=view.findViewById(R.id.btn_sign_out);
        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClear(view);
            }
        });*/
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);

        Button btn_sign_out=getActivity().findViewById(R.id.btn_sign_out);
        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClear(view);
            }
        });
    }

    public void onClear(View view){
        System.out.println("Testing");
        UserManage.getInstance().clearUserInfo(getActivity());
        Toast t = Toast.makeText(getActivity(), "已取消！", Toast.LENGTH_SHORT);
        t.show();
    }

}
