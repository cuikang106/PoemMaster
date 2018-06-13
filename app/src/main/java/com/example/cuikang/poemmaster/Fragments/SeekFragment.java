package com.example.cuikang.poemmaster.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cuikang.poemmaster.DatabaseUtil.AssetsDatabaseManager;
import com.example.cuikang.poemmaster.MainActivity;
import com.example.cuikang.poemmaster.R;
import com.example.cuikang.poemmaster.adapter.PoemAdapter;
import com.example.cuikang.poemmaster.bean.PoemBean;

import java.util.ArrayList;
import java.util.List;

public class SeekFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private List<PoemBean> poemList = new ArrayList<>();


    public SeekFragment() {
        // Required empty public constructor
    }

    //创建并返回一个实例
    public static SeekFragment newInstance(String param1) {
        SeekFragment fragment = new SeekFragment();
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
        return inflater.inflate(R.layout.fragment_seek, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPoems();
        PoemAdapter adapter = new PoemAdapter(getActivity().getApplicationContext(), R.layout.poem_item, poemList);
        ListView listView = getView().findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>parent,View view, int position, long id){
                MainActivity activity = (MainActivity) getActivity();
                activity.exchange=position;
                FragmentManager fragmentManager=activity.getSupportFragmentManager();
                activity.browseFragment=BrowseFragment.newInstance("浏览");
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.tb,activity.browseFragment);
                fragmentTransaction.commit();
            }
        });

    }

    public void initPoems(){
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        SQLiteDatabase db = mg.getDatabase("poem.db");
        Cursor cursor = db.query("poem_300", null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do{
                String author=cursor.getString(cursor.getColumnIndex("author"));
                String title=cursor.getString(cursor.getColumnIndex("title"));
                int sequence = cursor.getPosition();
                PoemBean poem = new PoemBean();
                poem.setTitle(title);
                poem.setAuthor(author);
                poem.setSequence(sequence+1);
                poemList.add(poem);
            } while(cursor.moveToNext());
        }
        cursor.close();
    }
}

