package com.example.cuikang.poemmaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText edt_username;
    private EditText edt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //进行必要的初始化工作
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    //initViews函数，获取输入框的View元素，并为按钮设置监听器
    private void initViews() {
        edt_username =  findViewById(R.id.edt_username);
        edt_password =  findViewById(R.id.edt_password);
        findViewById(R.id.btn_login).setOnClickListener(mOnClickListener);
    }

    //创建一个OnClickListener实例，并重写它的onClick方法
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {


        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.btn_login://登录
                    String userName = edt_username.getText().toString();
                    String userPwd = edt_password.getText().toString();
                    //把输入框的内容存入UserInfo中
                    if(userName.equals("admin")){
                        if(userPwd.equals("admin")){
                            showToast("登录成功");
                            UserManage.getInstance().saveUserInfo(LoginActivity.this, userName, userPwd);
                            //跳转到主页
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            //finish();
                            onDestroy();
                            //break;
                        }else
                            showToast("密码错误");
                    }else
                        showToast("账户不存在");

            }

        }
    };

    //自定义Toast样式
    private void showToast(String s) {
        Toast t = Toast.makeText(this,
                s,
                Toast.LENGTH_SHORT);

        // 字体
        ViewGroup group = (ViewGroup) t.getView();
        TextView messageTextView = (TextView) group.getChildAt(0);
        messageTextView.setTextSize(18);
        // 居中
        t.setGravity(Gravity.CENTER, 0, 0);
        // 显示
        t.show();
    }
}
