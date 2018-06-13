package com.example.cuikang.poemmaster.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cuikang.poemmaster.AboutActivity;
import com.example.cuikang.poemmaster.DonateActivity;
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
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);
        AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>listView,View v,int position,long id){
                if(position==0) {
                    Intent intentDonate = new Intent(getActivity(), DonateActivity.class);
                    startActivity(intentDonate);
                }
                if(position==1){
                    onClear();
                }
                if(position==2){
                    Intent intentAbout = new Intent(getActivity(), AboutActivity.class);
                    startActivity(intentAbout);
                }
            }
        };
        ListView listview=getView().findViewById(R.id.list_options);
        listview.setOnItemClickListener(itemClickListener);
    }

    public void onClear(){
        System.out.println("Testing");
        UserManage.getInstance().clearUserInfo(getActivity());
        Toast t = Toast.makeText(getActivity(), "已注销！", Toast.LENGTH_SHORT);
        t.show();
    }


}
