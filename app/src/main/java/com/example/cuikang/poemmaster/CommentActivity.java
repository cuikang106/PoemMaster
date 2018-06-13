package com.example.cuikang.poemmaster;
import android.os.Bundle;

import com.example.cuikang.baselibrary.activity.BaseActivity;
import com.example.cuikang.baselibrary.http.bean.CommonRequest;
import com.example.cuikang.baselibrary.http.bean.CommonResponse;
import com.example.cuikang.baselibrary.http.interf.ResponseHandler;
import com.example.cuikang.poemmaster.UserManage.UserManage;
import com.example.cuikang.poemmaster.url.ServerURL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentActivity extends BaseActivity {

    private String commentText;
    private String poemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commentText=getIntent().getStringExtra("commentText");
        poemID=getIntent().getStringExtra("poemID");
        comment();
    }

    private void comment() {

        String name= UserManage.getInstance().getUserInfo(CommentActivity.this).getUserName();
        final CommonRequest request = new CommonRequest();
        request.addRequestParam("poemID",poemID);
        request.addRequestParam("name", name);
        request.addRequestParam("time", getTime());
        request.addRequestParam("commentText",commentText);
        //实现基类中的ResponseHandler的接口
        sendHttpPostRequest(ServerURL.COMMENT, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                finish();
            }

            @Override
            public void fail(String failCode, String failMsg) {
                finish();
            }
        });
    }

    private static String getTime(){
        Timestamp timestamp=new Timestamp(new Date().getTime());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = df.format(timestamp);
        return timeStr;
    }
}
