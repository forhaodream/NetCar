package com.land.netcar.min;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.land.TestBean;
import com.land.netcar.LoginActivity;
import com.land.netcar.R;
import com.land.netcar.TestModel;
import com.land.netcar.base.BaseActivity;
import com.land.netcar.jp.JPushActivity;
import com.land.netcar.login.loginsp.LoginSp;
import com.land.netcar.min.adapter.HeaderAdapter;
import com.land.netcar.min.adapter.MainAdapter;
import com.land.netcar.min.adapter.WapHeaderAndFooterAdapter;
import com.land.netcar.min.bean.PointOutBean;
import com.land.netcar.min.bean.StatusBean;
import com.land.netcar.urls.CloseActivityClass;
import com.land.netcar.urls.UURL;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.main_system)
    ImageView mainSystem;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swiperefres)
    SwipeRefreshLayout swiperefres;
    private MainAdapter adapter;
    private WapHeaderAndFooterAdapter headerAndFooterAdapter;
    private String[] titel = {"电瓶电量", "指纹管理（开门）", "指纹管理（点火）", "添加蓝牙", "其他设置", ""};// "模块电压",  //R.drawable.assaddd,
    private Integer[] image = {R.drawable.dianliang,
            R.drawable.kaiwen, R.drawable.dianhuo, R.drawable.lanya, R.drawable.other, R.drawable.assaddd};
    private String[] zt;
    List<String> stringC = new ArrayList<>();
    List<String> stringD = new ArrayList<>();
    private StatusBean bean;
    private JPushActivity mJPush;


    @SuppressLint("HandlerLeak")
    Handler handlers = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            Loading();
//        getData();
//        getEquipsearch();
//            getTiresearch();
            adapter.notifyDataSetChanged();
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();
        CloseActivityClass.activityList.add(this);
        ButterKnife.bind(this);

        mJPush = new JPushActivity(this);

        mJPush.initJPush();//初始化极光推送

        mJPush.registerMessageReceiver();//注册信息接收器

        mJPush.setTag(LoginSp.getuid(this));//为设备设置标签

        mJPush.setAlias(LoginSp.getuid(this));//为设备设置别名

        iniview();
        getData();
        UURL.MYMAC = getBtAddressByReflection();

    }


    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    List<String> stringA;
    List<Integer> stringB;

    private void iniview() {

        stringA = Arrays.asList(titel);

        stringB = Arrays.asList(image);
        swiperefres.setOnRefreshListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        adapter = new MainAdapter(this, stringA, stringB, stringC, handlers);
        headerAndFooterAdapter = new WapHeaderAndFooterAdapter(adapter);
        mainSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SystemActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getData();
    }

    private void getData() {
        Log.d("aaaddaaa", LoginSp.gettoken(this));
        Loading();
        OkGo.post(UURL.INDEX)
                .params("token", LoginSp.gettoken(this))//LoginSp.gettoken(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        bean = gson.fromJson(s, StatusBean.class);
                        Log.e("TAGsd", "" + s);
                        showTextToastShort(MainActivity.this, bean.getInfo());
                        if (bean.getCode().equals("-102")) {
                            LoginSp.loginchu(MainActivity.this);
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            dismissDialog();
                            return;
                        } else if (bean.getCode().equals("-103")) {
//                            Intent intent = new Intent(MainActivity.this, BindActivity.class);
//                            startActivityForResult(intent,1);
//                            dismissDialog();
                            showNormalDialog(bean.getInfo());
                            return;
                        } else if (bean.getCode().equals("-104")) {
                            showNormalDialog(bean.getInfo());
                            return;
                        } else {
                            Log.d("aaaaaaaaaa1", bean.getData().getInfo().get(0).getEdianping() + "");
                            String ssd = bean.getData().getInfo().get(0).getElt().getEdianya() + "/" +
                                    bean.getData().getInfo().get(0).getErt().getEdianya() + "/" +
                                    bean.getData().getInfo().get(0).getElb().getEdianya() + "/" +
                                    bean.getData().getInfo().get(0).getErb().getEdianya();

                            stringC.add(bean.getData().getInfo().get(0).getEdianping() + "");
                            stringC.add(ssd);
                            stringC.add(bean.getData().getInfo().get(0).getEjiareqi());
                            stringC.add(bean.getData().getInfo().get(0).getEzhedie());
                            stringC.add(bean.getData().getInfo().get(0).getEkongtiao());
                            stringC.add(bean.getData().getInfo().get(0).getEchechuang());
                            stringC.add(bean.getData().getInfo().get(0).getEtianchuan());
                            stringC.add(bean.getData().getInfo().get(0).getEmensuo());

                            UURL.SthingC = stringC;
                            UURL.equipsearch = bean.getData().getInfo().get(0).getDeviceid();
                            Log.e("TAGasd", "" + UURL.equipsearch);

                            header = LayoutInflater.from(MainActivity.this).inflate(R.layout.header_view, recyclerview, false);
                            r = header.findViewById(R.id.mrecyclerviews);
                            LinearLayoutManager layout = new LinearLayoutManager(MainActivity.this);
                            r.setLayoutManager(layout);
                            HeaderAdapter adapters = new HeaderAdapter(MainActivity.this, bean, handlers);
                            r.setAdapter(adapters);
                            headerAndFooterAdapter.addHeader(header);
                            recyclerview.setAdapter(headerAndFooterAdapter);
                            dismissDialog();
                            swiperefres.setRefreshing(false);
                        }

                    }
                });
    }

    View header;
    RecyclerView r;

    //电器状态刷新
    private void getEquipsearch() {

        String url = UURL.EQUIPSEARCH + "&token=" + LoginSp.gettoken(this) + "&deviceid=" + UURL.equipsearch;
        Log.d("dadsa", url);
        OkGo.get(url)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        swiperefres.setRefreshing(false);
                        Log.e("TAGsd22", "" + LoginSp.gettoken(MainActivity.this));
                        Log.e("TAGsd22", "" + UURL.equipsearch);
                        TestBean aaaaaaa = gson.fromJson(s, TestBean.class);
                   final StatusBean     bean = gson.fromJson(s, StatusBean.class);
                        Log.e("TAGsd", "" + s);
                        Log.d("aassdadas", String.valueOf(bean.getCode()));
                        if (bean.getCode().equals("0")) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("aaddaad23", bean.getData().getInfo().get(0).getDian() + "");
                                    String ssd = bean.getData().getInfo().get(0).getElt().getEdianya() + "/" +
                                            bean.getData().getInfo().get(0).getErt().getEdianya() + "/" +
                                            bean.getData().getInfo().get(0).getElb().getEdianya() + "/" +
                                            bean.getData().getInfo().get(0).getErb().getEdianya();
                                    List<String> stringC = new ArrayList<>();
                                    stringC.add(String.valueOf(bean.getData().getInfo().get(0).getDian()));//bean.getData().getInfo().get(0).getEdianping()
                                    stringC.add(ssd);
                                    stringC.add(bean.getData().getInfo().get(0).getEjiareqi());
                                    stringC.add(bean.getData().getInfo().get(0).getEzhedie());
                                    stringC.add(bean.getData().getInfo().get(0).getEkongtiao());
                                    stringC.add(bean.getData().getInfo().get(0).getEchechuang());
                                    stringC.add(bean.getData().getInfo().get(0).getEtianchuan());
                                    stringC.add(bean.getData().getInfo().get(0).getEmensuo());
                                    header = LayoutInflater.from(MainActivity.this).inflate(R.layout.header_view, recyclerview, false);
                                    r = header.findViewById(R.id.mrecyclerviews);
                                    LinearLayoutManager layout = new LinearLayoutManager(MainActivity.this);
                                    r.setLayoutManager(layout);
                                    HeaderAdapter adapters = new HeaderAdapter(MainActivity.this, bean, handlers);
                                    r.setAdapter(adapters);
                                    headerAndFooterAdapter.addHeader(header);
                                    recyclerview.setAdapter(headerAndFooterAdapter);
                                    adapter = new MainAdapter(MainActivity.this, stringA, stringB, stringC, handlers);
                                    headerAndFooterAdapter = new WapHeaderAndFooterAdapter(adapter);
                                }
                            });

//                            LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
//                            recyclerview.setLayoutManager(layoutManager);
//                            adapter = new MainAdapter(MainActivity.this, stringA, stringB, stringC, handlers);
//
//                            UURL.equipsearch = bean.getData().getInfo().get(0).getDeviceid();
//                            UURL.SthingC = stringC;
//
//                            final View header = LayoutInflater.from(MainActivity.this).inflate(R.layout.header_view, recyclerview, false);
//                            RecyclerView r = header.findViewById(R.id.mrecyclerviews);
//                            LinearLayoutManager layout = new LinearLayoutManager(MainActivity.this);
//                            r.setLayoutManager(layout);
//                            HeaderAdapter adapters = new HeaderAdapter(MainActivity.this, bean, handlers);
//                            adapters.notifyDataSetChanged();
//                            r.setAdapter(adapters);
//                            headerAndFooterAdapter.addHeader(header);
//                            headerAndFooterAdapter.notifyDataSetChanged();
//                            recyclerview.setAdapter(headerAndFooterAdapter);
                            dismissDialog();
                        } else if (bean.getCode().equals("-105")) {
                            showTextToastShort(MainActivity.this, bean.getInfo());
                        } else if (bean.getCode().equals("-102")) {
                            LoginSp.loginchu(MainActivity.this);
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            dismissDialog();
                        } else if (bean.getCode().equals("-103")) {
                            showNormalDialog(bean.getInfo());
                        } else if (bean.getCode().equals("-104")) {
                            showNormalDialog(bean.getInfo());
                        } else {
                            showNormalDialog(bean.getInfo());
                        }


                    }

                });
    }

    //四轮状态刷新
    private void getTiresearch() {

        OkGo.post(UURL.TIRESEARCH)
                .params("token", LoginSp.gettoken(this))
                .params("deviceid", UURL.equipsearch)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        PointOutBean model = JSON.parseObject(s, PointOutBean.class);
//                        showTextToastShort(MainActivity.this, bean.getInfo());
                        if (model.getCode().equals("-101")) {

                        } else if (model.getCode().equals("-102")) {
                            LoginSp.loginchu(MainActivity.this);
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            dismissDialog();
                        } else if (model.getCode().equals("-103")) {
                            Intent intent = new Intent(MainActivity.this, BindActivity.class);
                            startActivity(intent);
                            dismissDialog();
                        } else {
                            getDcsearch();
                        }
                    }
                });
    }

    TestModel testModel;

    //电量刷新
    private void getDcsearch() {
        //Log.d("aaaddaaa", LoginSp.gettoken(this));
        //Log.d("aaaddaaa", UURL.equipsearch);
        OkGo.post(UURL.DCSEARCH)
                .params("token", LoginSp.gettoken(this))
                .params("deviceid", UURL.equipsearch)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        testModel = JSON.parseObject(s, TestModel.class);
                        showTextToastShort(MainActivity.this, testModel.getInfo());//testModel.getInfo()
                        Log.d("onSuccess", s);
                        Log.d("aaaddaaa`````", testModel.getCode() + "");
                        if (testModel.getCode().equals("-101")) {

                        } else if (testModel.getCode().equals("-102")) {
                            LoginSp.loginchu(MainActivity.this);
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            dismissDialog();
                        } else if (testModel.getCode().equals("-103")) {
                            Intent intent = new Intent(MainActivity.this, BindActivity.class);
                            startActivity(intent);
                            dismissDialog();
                        } else {
                            getEquipsearch();
                            getData();
                        }
                        dismissDialog();
                    }

                });
    }

    @Override
    public void onRefresh() {
        //Loading();
//        getData();
//        getEquipsearch();
//        getTiresearch();
//        getDcsearch();
        getEquipsearch();


    }

    private boolean flag = true;
    private static final int WHAT_RESET_BACK = 1;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Log.e("TAG", "handleMessage");
            switch (msg.what) {
                case WHAT_RESET_BACK:
                    flag = true;//复原
                    break;
            }
        }
    };

    private void showNormalDialog(String name) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);

        normalDialog.setTitle(null);
        normalDialog.setMessage(name + ",请点击“确定”前往设置");
        normalDialog.setCancelable(false);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        Intent intent = new Intent(MainActivity.this, BindActivity.class);
                        startActivityForResult(intent, 1);
                        dismissDialog();
                    }
                });

        // 显示
        normalDialog.show();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && flag) {
            showTextToastShort(this, "再点击一次，退出当前应用");
            flag = false;
            //发送延迟消息
            handler.sendEmptyMessageDelayed(WHAT_RESET_BACK, 2000);
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    //为了避免出现内存的泄漏，需要在onDestroy()中，移除所有未被执行的消息
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //方式二：移除所有的未被执行的消息
        handler.removeCallbacksAndMessages(null);
    }

    public static String getBtAddressByReflection() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Field field = null;
        try {
            field = BluetoothAdapter.class.getDeclaredField("mService");
            field.setAccessible(true);
            Object bluetoothManagerService = field.get(bluetoothAdapter);
            if (bluetoothManagerService == null) {
                return null;
            }
            Method method = bluetoothManagerService.getClass().getMethod("getAddress");
            if (method != null) {
                Object obj = method.invoke(bluetoothManagerService);
                if (obj != null) {
                    return obj.toString();
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
