package com.land.netcar.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.land.netcar.min.MainActivity;
import com.land.netcar.min.view.DialogCallBackListener;
import com.land.netcar.min.view.LodingDialog;
import com.land.netcar.urls.CloseActivityClass;

import java.lang.reflect.Method;

/**
 * 项目基本页面，尽量所有的Activity都继承这个activity
 */
public class BaseActivity extends FragmentActivity implements DialogCallBackListener {
    //    private MyBroadcastReceiver broadcastReceiver;
    private boolean isForeground;
    private LodingDialog waitDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        判断是否有navigation bar

//        boolean isNavbar = checkDeviceHasNavigationBar(this);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            if (!isNavbar)
//                this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//        highApiEffects();

        checkDeviceHasNavigationBar(this);

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void highApiEffects() {
//        WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
        getWindow().getDecorView().setFitsSystemWindows(true);
//        //透明状态栏 @顶部
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS| localLayoutParams.flags);
        //透明导航栏 @底部    这一句不要加，目的是防止沉浸式状态栏和部分底部自带虚拟按键的手机（比如华为）发生冲突，注释掉就好了
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.GRAY);
//
//        }
    }


    @SuppressLint("NewApi")
    public boolean checkDeviceHasNavigationBar(Context context) {

        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(this)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            return true;
        }
        return false;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }

//                boolean checkPermissionResult = checkSelfPermissions();

                if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M)) {
                    // so far we do not use OnRequestPermissionsResultCallback
                }
            }
        }, 500);
    }


    /**
     * 4.4以上版本状态栏颜色改变
     */
    public void addStatusView(RelativeLayout statusView) {
        if (statusView == null)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int height = getStatusHeight();
            View view = new View(this);
            view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height));
            statusView.addView(view);
            statusView.setVisibility(View.VISIBLE);
        } else {
            statusView.setVisibility(View.GONE);
        }
    }

    public void setStatusResource(int resource) {
        //当前手机版本为4.4 , 4.4一下不支持改变
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
            View statusBarView = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusHeight());
            statusBarView.setBackgroundResource(resource);
            contentView.addView(statusBarView, lp);
        }
    }


    /**
     * 获取状态栏高度，单位px
     *
     * @return
     */
    private int getStatusHeight() {
        int statusBarHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusBarHeight = getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("getStatusHeight", "状态栏-方法2:" + statusBarHeight);
        return statusBarHeight;
    }


    @Override
    protected void onResume() {
        super.onResume();
        isForeground = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isForeground = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    /**
     * toast使用~short
     *
     * @param context
     * @param msg
     */
    public static Toast toast = null;

    public static void showTextToastShort(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * toast使用~long
     *
     * @param context
     * @param msg
     */
    public static void showTextToastLong(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 获取版本号
     */
    public static String getVersion(Context context) {
        String version = null;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            version = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }


    /**
     * 獲取手机序列号
     *
     * @return
     */
    public static String getSerialNumber() {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
            System.out.println(serial);
        } catch (Exception ignored) {

        }
        return serial;
    }


    public void Loading() {
        waitDialog = new LodingDialog(this, this, "");
        waitDialog.setRoundName("加载中....");

        waitDialog.show();
    }

    public void dismissDialog() {
        waitDialog.dismiss();
    }

    @Override
    public void ReceiveData(String flag) {

    }
}
