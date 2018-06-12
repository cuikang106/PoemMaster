package com.example.cuikang.baselibrary.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.example.cuikang.baselibrary.http.Constant;
import com.example.cuikang.baselibrary.http.HttpPostTask;
import com.example.cuikang.baselibrary.http.bean.CommonRequest;
import com.example.cuikang.baselibrary.http.interf.ResponseHandler;
import com.example.cuikang.baselibrary.util.DialogUtil;
import com.example.cuikang.baselibrary.util.LogUtil;


public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
    }

    //发送Http请求
    protected void sendHttpPostRequest(String url, CommonRequest request, ResponseHandler responseHandler) {
        new HttpPostTask(request, mHandler, responseHandler).execute(url);

    }


    protected Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == Constant.HANDLER_HTTP_SEND_FAIL) {
                LogUtil.logErr(msg.obj.toString());

                DialogUtil.showHintDialog(BaseActivity.this, "请求发送失败，请重试", true);
            } else if (msg.what == Constant.HANDLER_HTTP_RECEIVE_FAIL) {
                LogUtil.logErr(msg.obj.toString());

                DialogUtil.showHintDialog(BaseActivity.this, "请求接受失败，请重试", true);
            }
        }
    };
}
