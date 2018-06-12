package com.example.cuikang.poemmaster.UserManage;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;  //一个处理字符串的工具类

/**
 * Created by njau_ on 2018/6/6.
 */

public class UserManage {
    private static UserManage instance;  //单例类

    private UserManage() {
    }

    //外界获取一个UserManage实例
    public static UserManage getInstance() {
        if (instance == null) {
            instance = new UserManage();
        }
        return instance;
    }

    //保存用于自动登录的用户信息,一下方法都是有上下文的，必须用UserManage实例调用
    public void saveUserInfo(Context context, String username, String password) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();// 创建一个编辑器
        editor.putString("USER_NAME", username);
        editor.putString("PASSWORD", password);
        editor.apply();
    }

    //获取用户信息,返回一个UserInfo的实例
    public UserInfo getUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(sp.getString("USER_NAME", ""));
        userInfo.setPassword(sp.getString("PASSWORD", ""));
        return userInfo;
    }
    //判断是否有数据
    public boolean hasUserInfo(Context context) {
        UserInfo userInfo = getUserInfo(context);
        if (userInfo != null)
            return ((!TextUtils.isEmpty(userInfo.getUserName())) && (!TextUtils.isEmpty(userInfo.getPassword())));   //有数据
        else
            return false;
    }

    //清除已有信息
    public void clearUserInfo(Context context){
        SharedPreferences sp=context.getSharedPreferences("userInfo",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.clear();
        editor.apply();
    }
}

