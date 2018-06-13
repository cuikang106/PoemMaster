package com.example.cuikang.poemmaster;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.cuikang.poemmaster.DatabaseUtil.AssetsDatabaseManager;
import com.example.cuikang.poemmaster.Fragments.BrowseFragment;
import com.example.cuikang.poemmaster.Fragments.ExamFragment;
import com.example.cuikang.poemmaster.Fragments.MeFragment;
import com.example.cuikang.poemmaster.Fragments.SeekFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.cuikang.poemmaster.bean.PoemBean;


public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    int lastSelectedPosition=0;                     //上次选中的位置，默认为第一个
    private String TAG=MainActivity.class.getSimpleName();//获得类的简写名称
    public int exchange=-1;  //传递数据的媒介，在查找导航栏中点击一首诗，会跳到浏览中相应的诗
    public List<PoemBean> poemList = new ArrayList<>();

    //需要导航的四个Fragment
    public BrowseFragment browseFragment;
    public ExamFragment examFragment;
    public SeekFragment seekFragment;
    public MeFragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readPoem();

        BottomNavigationBar bottomNavigationBar;//创建一个导航栏实例
        //获取布局中的元素
        bottomNavigationBar=findViewById(R.id.bottom_navigation_bar);//获取布局中的元素

        //设置风格
        //这里bottomNavigationBar设置语法比较特殊！
        bottomNavigationBar
                .setTabSelectedListener(this)
                .setMode(BottomNavigationBar.MODE_FIXED)//Item显示模式
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)//选中样式
                .setActiveColor("#FF107FFD") //选中颜色
                .setInActiveColor("#e9e6e6") //未选中颜色
                .setBarBackgroundColor("#1ccbae");//导航栏背景色

        //添加导航按钮
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_browse,"浏览"))
                .addItem(new BottomNavigationItem(R.drawable.ic_exam,"答题"))
                .addItem(new BottomNavigationItem(R.drawable.ic_seek,"查找"))
                .addItem(new BottomNavigationItem(R.drawable.ic_me,"我"))
                .setFirstSelectedPosition(lastSelectedPosition )
                .initialise(); //initialise 一定要放在 所有设置的最后一项

        setDefaultFragment();
    }

    //设置默认导航栏
    public void setDefaultFragment(){
        FragmentManager fragmentManager=getSupportFragmentManager();//获取一个碎片管理实例
        //开启事务
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();//获取一个碎片切换实例
        browseFragment= BrowseFragment.newInstance("浏览");
        fragmentTransaction.replace(R.id.tb,browseFragment);//用默认的碎片去替换布局中的元素
        fragmentTransaction.commit();//提交切换
    }


    @Override
    public void onTabSelected(int position){
        //必须重写的方法，设置选中时的事务
//        Log.d(TAG, "onTabSelected() called with: " + "position = [" + position + "]");
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        //开启事务
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (position) {
            case 0:
                if (browseFragment == null) {
                    browseFragment = BrowseFragment.newInstance("浏览");
                }
                fragmentTransaction.replace(R.id.tb, browseFragment);
                break;
            case 1:
                if (examFragment == null) {
                    examFragment = ExamFragment.newInstance("答题");
                }
                fragmentTransaction.replace(R.id.tb, examFragment);
                break;
            case 2:
                if (seekFragment == null) {
                    seekFragment = SeekFragment.newInstance("查找");
                }
                fragmentTransaction.replace(R.id.tb, seekFragment);
                break;
            case 3:
                if(meFragment == null) {
                    meFragment = MeFragment.newInstance("我");
                }
                fragmentTransaction.replace(R.id.tb,meFragment);
            default:
                break;
        }

        fragmentTransaction.commit();// 事务提交
    }


    @Override
    public void onTabUnselected(int Position){
        //必须重写的方法，设置未选中时的事务
    }

    @Override
    public void onTabReselected(int Position){
        //必须重写的方法，设置释放时的事务
        onTabSelected(Position);
    }

    public void readPoem(){
        SQLiteDatabase db;
        // 初始化，只需要调用一次
        AssetsDatabaseManager.initManager(getApplicationContext());
        // 获取管理对象，因为数据库需要通过管理对象才能够获取
        AssetsDatabaseManager mg = AssetsDatabaseManager.getManager();
        // 通过管理对象获取数据库
        db = mg.getDatabase("poem.db");
        Cursor cursor = db.query("poem_300", null, null, null, null, null, null);
        if (cursor.moveToFirst())
        {
            do {
                PoemBean poem = new PoemBean();
                poem.setSequence(cursor.getPosition()+1);
                poem.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                poem.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
                poem.setParagraphs(Arrays.asList(cursor.getString(cursor.getColumnIndex("paragraphs")).split(",")));
                poem.setStrains(Arrays.asList(cursor.getString(cursor.getColumnIndex("strains")).split(",")));
//                Log.d(TAG, poem.getParagraphs().get(1));
                poemList.add(poem);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }


}
