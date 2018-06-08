package com.example.cuikang.poemmaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;


public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    int lastSelectedPosition=0;                     //上次选中的位置，默认为第一个
    private String TAG=MainActivity.class.getSimpleName();//获得类的简写名称

    //需要导航的四个Fragment
    private BrowseFragment browseFragment;
    private ExamFragment examFragment;
    private SeekFragment seekFragment;
    private MeFragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    private void setDefaultFragment(){
        FragmentManager fragmentManager=getFragmentManager();//获取一个碎片管理实例
        //开启事务
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();//获取一个碎片切换实例
        browseFragment=BrowseFragment.newInstance("浏览");
        fragmentTransaction.replace(R.id.tb,browseFragment);//用默认的碎片去替换布局中的元素
        fragmentTransaction.commit();//提交切换
    }


    @Override
    public void onTabSelected(int position){
        //必须重写的方法，设置选中时的事务
        Log.d(TAG, "onTabSelected() called with: " + "position = [" + position + "]");
        FragmentManager fragmentManager = this.getFragmentManager();
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
    }

    //这个方法暂时不用，后续放到Me模块中
    /*
    public void onClear(View view){

        UserManage.getInstance().clearUserInfo(MainActivity.this);
        Toast t = Toast.makeText(this, "已取消！", Toast.LENGTH_SHORT);
        t.show();
    }
    */
}
