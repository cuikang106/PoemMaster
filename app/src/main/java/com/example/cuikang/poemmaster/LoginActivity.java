package com.example.cuikang.poemmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cuikang.baselibrary.activity.BaseActivity;
import com.example.cuikang.baselibrary.http.bean.CommonRequest;
import com.example.cuikang.baselibrary.http.bean.CommonResponse;
import com.example.cuikang.baselibrary.http.interf.ResponseHandler;
import com.example.cuikang.poemmaster.UserManage.UserManage;
import com.example.cuikang.poemmaster.url.ServerURL;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        final EditText edtName =  findViewById(R.id.edt_name);
        final EditText edtPassword =  findViewById(R.id.edt_password);

        Button btnLogin =  findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(edtName.getText().toString(), edtPassword.getText().toString());
            }
        });

        Button btnRegister =  findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(edtName.getText().toString(), edtPassword.getText().toString());
            }
        });
        findViewById(R.id.btn_visitor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                LoginActivity.this.finish();
            }
        });
    }

    private void login(final String name, final String password) {

        final CommonRequest request = new CommonRequest();
        request.addRequestParam("name", name);
        request.addRequestParam("password", password);

        //重写基类中的发送http报文方法，实现基类中的ResponseHandler的接口
        sendHttpPostRequest(ServerURL.LOGIN, request, new ResponseHandler() {
            //重写success和fail函数以实现接口，函数内容根据应用情况自定
            @Override
            public void success(CommonResponse response) {
                Toast.makeText(LoginActivity.this,"登陆成功！",Toast.LENGTH_LONG).show();
                UserManage.getInstance().saveUserInfo(LoginActivity.this,name,password);
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                LoginActivity.this.finish();
            }

            @Override
            public void fail(String failCode, String failMsg) {
                Toast.makeText(LoginActivity.this,failCode+":"+failMsg,Toast.LENGTH_LONG).show();
            }
        });
    }
    private void register(String name, String password) {

        final CommonRequest request = new CommonRequest();
        request.addRequestParam("name", name);
        request.addRequestParam("password", password);
        //实现基类中的ResponseHandler的接口
        sendHttpPostRequest(ServerURL.REGISTER, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                Toast.makeText(LoginActivity.this,"注册成功！",Toast.LENGTH_LONG).show();
            }

            @Override
            public void fail(String failCode, String failMsg) {

                Toast.makeText(LoginActivity.this,failCode+":"+failMsg,Toast.LENGTH_LONG).show();
            }
        });
    }
}
