package com.land.netcar.login.loginsp;

import android.content.Context;
import android.content.SharedPreferences;


import com.land.netcar.login.bean.LoginBean;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by VULCAN on 2017/12/20.
 */

public class LoginSp {
    public static String uid;
    public static String token;
    public static String username;
    public static String cnname;
    public static String avatar;
    public static String sex;
    public static String birthday;
    public static String tag;
    public static String cid;

    public static SharedPreferences preferences;


    public static void loginspc(Context context, LoginBean loginBean) {

        preferences = context.getSharedPreferences("login", MODE_PRIVATE);

        //获取editor对象
        SharedPreferences.Editor editor = preferences.edit();
        //存储数据时选用对应类型的方法
        editor.putString("uid", loginBean.getData().getInfo().get(0).getUid());
        editor.putString("token", loginBean.getData().getInfo().get(0).getToken());
        editor.putString("username", loginBean.getData().getInfo().get(0).getUsername());
        editor.putString("amount", loginBean.getData().getInfo().get(0).getAmount());
        editor.putString("sex", loginBean.getData().getInfo().get(0).getSex());
        editor.putString("birth", loginBean.getData().getInfo().get(0).getBirth());
        editor.putString("nickname", loginBean.getData().getInfo().get(0).getNickname());
        editor.putString("mobile", loginBean.getData().getInfo().get(0).getMobile());
        editor.putString("address", loginBean.getData().getInfo().get(0).getAddress());
        editor.putString("intro", loginBean.getData().getInfo().get(0).getIntro());
        editor.putString("qq", loginBean.getData().getInfo().get(0).getQq());
        editor.putString("weixin", loginBean.getData().getInfo().get(0).getWeixin());


        //提交保存数据
        editor.commit();

    }

    public static void loginchu(Context context) {
        setuid("", context);
        settoken("", context);
        setusername("", context);
        setamoint("", context);
        setnickname("", context);
        setsex("", context);
        setbirth("", context);
        setaddress("", context);
        setintro("", context);
        setmobile("", context);
        setqq("", context);
        setweixin("", context);
    }

    public static void setuid(String uid, Context context) {
        preferences = context.getSharedPreferences("login", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("uid", uid);
        editor.commit();
    }

    public static String getuid(Context context) {
        preferences = context.getSharedPreferences("login", MODE_PRIVATE);
        uid = preferences.getString("uid", "");
        return uid;
    }

    public static void settoken(String uid, Context context) {
        preferences = context.getSharedPreferences("login", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", uid);
        editor.commit();
    }

    public static String gettoken(Context context) {
        preferences = context.getSharedPreferences("login", MODE_PRIVATE);
        token = preferences.getString("token", "");
        return token;
    }

    public static void setusername(String uid, Context context) {
        preferences = context.getSharedPreferences("login", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", uid);
        editor.commit();
    }

    public static String getusername(Context context) {
        preferences = context.getSharedPreferences("login", MODE_PRIVATE);
        username = preferences.getString("username", "");
        return username;
    }

    public static void setamoint(String uid, Context context) {
        preferences = context.getSharedPreferences("login", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("amount", uid);
        editor.commit();
    }

    public static String getamoint(Context context) {
        preferences = context.getSharedPreferences("login", MODE_PRIVATE);
        username = preferences.getString("amount", "");
        return username;
    }

    public static void setnickname(String uid, Context context) {
        preferences = context.getSharedPreferences("login", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nickname", uid);
        editor.commit();
    }

    public static String getnickname(Context context) {
        preferences = context.getSharedPreferences("login", MODE_PRIVATE);
        username = preferences.getString("nickname", "");
        return username;
    }

    public static void setsex(String uid, Context context) {
        preferences = context.getSharedPreferences("login", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("sex", uid);
        editor.commit();
    }

    public static String getsex(Context context) {
        preferences = context.getSharedPreferences("login", MODE_PRIVATE);
        username = preferences.getString("sex", "");
        return username;
    }

    public static void setbirth(String uid, Context context) {
        preferences = context.getSharedPreferences("login", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("birth", uid);
        editor.commit();
    }

    public static String getbirth(Context context) {
        preferences = context.getSharedPreferences("login", MODE_PRIVATE);
        username = preferences.getString("birth", "");
        return username;
    }

    public static void setaddress(String uid, Context context) {
        preferences = context.getSharedPreferences("login", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("address", uid);
        editor.commit();
    }

    public static String getaddress(Context context) {
        preferences = context.getSharedPreferences("login", MODE_PRIVATE);
        username = preferences.getString("address", "");
        return username;
    }

    public static void setintro(String uid, Context context) {
        preferences = context.getSharedPreferences("login", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("intro", uid);
        editor.commit();
    }

    public static String getintro(Context context) {
        preferences = context.getSharedPreferences("login", MODE_PRIVATE);
        username = preferences.getString("intro", "");
        return username;
    }

    public static void setmobile(String uid, Context context) {
        preferences = context.getSharedPreferences("login", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("mobile", uid);
        editor.commit();
    }

    public static String getmobile(Context context) {
        preferences = context.getSharedPreferences("login", MODE_PRIVATE);
        username = preferences.getString("mobile", "");
        return username;
    }

    public static void setqq(String uid, Context context) {
        preferences = context.getSharedPreferences("login", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("qq", uid);
        editor.commit();
    }

    public static String getqq(Context context) {
        preferences = context.getSharedPreferences("login", MODE_PRIVATE);
        username = preferences.getString("qq", "");
        return username;
    }

    public static void setweixin(String uid, Context context) {
        preferences = context.getSharedPreferences("login", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("weixin", uid);
        editor.commit();
    }

    public static String getweixin(Context context) {
        preferences = context.getSharedPreferences("login", MODE_PRIVATE);
        username = preferences.getString("weixin", "");
        return username;
    }

}
