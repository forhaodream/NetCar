package com.land.netcar.urls;


import java.util.List;

/**
 * Created by VULCAN on 2018/2/27.
 */

public class UURL {
    public static String URL = "http://www.syznyc.cn/api/index.php?a=";

    //登陆
    public static String LOGIN = URL + "login";
    //注册
    public static String REG = URL + "reg";
    //验证码
    public static String SENDSMSCODE = URL + "sendsmscode";
    //找回
    public static String BACKPWD = URL + "backpwd";
    //首页
    public static String INDEX = URL + "index";
    //绑定设备
    public static String REGEQUIP = URL + "regequip";
    //设备列表
    public static String MYEQUIP = URL + "myequip";
    //默认
    public static String MYEQUIPMO = URL + "myequipmo";
    //取消绑定设备
    public static String DELEQUIP = URL + "delequip";
    //电瓶电量
    public static String DCSEARCH = URL + "dcsearch";
    //轮胎状态
    public static String TIRESEARCH = URL + "tiresearch";
    //车内电器状态
    public static String EQUIPSEARCH = URL + "equipsearch";
    //车内设备控制
    public static String EQUIPCONTROL = URL + "equipcontrol";
    //指纹列表
    public static String MYZHIWEN = URL + "myzhiwen";
    //指纹删除
    public static String FINGERPRINTDEL = URL + "fingerprintdel";
    //指纹add
    public static String FINGERPRINTENTRY = URL + "fingerprintentry";
    //指纹add2
    public static String STEP2 = URL + "step2";
    //指纹add2
    public static String STEP3 = URL + "step3";
    //指纹bj
    public static String ZHIWENEDIT = URL + "zhiwenedit";
    //充值
    public static String CHONGZHI = URL + "chongzhi";
    //zuobia
    public static String EQUIPGPS = URL + "equipgps";
    //lanya
    public static String TOOTH = URL + "tooth";
    //que
    public static String TOOTHADDRESS = URL + "toothaddress";
    //意见反馈
    public static String FEEDBACK = URL + "feedback";
    //关于
    public static String SHOW = "http://zhinengcar.hxnzw.cn/api/show.php?cid=";

    public static android.os.Handler Handler;
    public static String equipsearch;
    public static java.lang.String APP_ID;
    public static String jiaqia_id;
    public static String jiaqia;
    public static String MYMAC;
    public static List<String> SthingC;
}
