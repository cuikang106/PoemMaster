package com.example.cuikang.poemmaster;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;

public class SplashActivity extends Activity {

    private static final int GO_HOME=0;
    private static final int GO_LOGIN=1;

    //Hander类是消息处理器类，可以在特定时间运行命令
    private Handler mHandler = new Handler() {
        //获取信息，根据信息判断启动哪个应用
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME://去主页
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case GO_LOGIN://去登录页
                    Intent intent2 = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //启动时进行自动登录判断，SharePrefences中有数据，则跳转到主页，没数据则跳转到登录页
        if (UserManage.getInstance().hasUserInfo(this))
        {
            //在2000ms后发送数据
            mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
        } else {
            //与上一句相同
            mHandler.sendEmptyMessageAtTime(GO_LOGIN, 2000);
        }
    }

}
