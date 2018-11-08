package com.land.netcar.ch;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.liquorslib.view.LTextView;
import com.example.liquorslib.view.ToolbarView;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.land.OkHttpClientManager;
import com.land.netcar.LoginActivity;
import com.land.netcar.R;
import com.land.netcar.jp.JPushActivity;
import com.land.netcar.login.loginsp.LoginSp;
import com.land.netcar.min.BindActivity;
import com.land.netcar.min.BluActivity;
import com.land.netcar.min.LinesActivity;
import com.land.netcar.min.SystemActivity;
import com.land.netcar.min.adapter.ScrollingActivity;
import com.land.netcar.min.bean.PointOutBean;
import com.land.netcar.min.bean.StatusBean;
import com.land.netcar.min.view.DialogCallBackListener;
import com.land.netcar.min.view.LodingDialog;
import com.land.netcar.min.view.QitaActivity;
import com.land.netcar.urls.CloseActivityClass;
import com.land.netcar.urls.UURL;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.squareup.okhttp.Request;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 智能云车项目,由于前期员工代码过于混乱,车辆设置界面只能重构
 * create by ch
 * on 2018/11/6 13:53
 */
public class HomeActivity extends Activity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, DialogCallBackListener {

    /**
     * 胎温：11
     */
    private TextView mLeftTopWendu;
    /**
     * 胎压：11
     */
    private TextView mLeftTopYali;
    private ImageView mLeftTopGantan;
    private LodingDialog waitDialog;
    private TextView mRihtTopWendu;
    private PointOutBean bean;
    private TextView mRihtTopYali;
    private ImageView mRightTopGantan;
    private ImageView mIvbtn;
    private ImageView mLeftBottomGantan;
    private ImageView mRihtBottomGantan;
    private LinearLayout mTop;
    private TextView mLeftBottomWendu;
    private TextView mLeftBottomYali;
    private TextView mRihtBottomWendu;
    private TextView mRihtBottomYali;
    private LTextView m电量LT;
    private LTextView m开门LT;
    private LTextView m点火LT;
    private LTextView m添加蓝牙LT;
    private LTextView m其他设置LT;
    private ImageView m开门img;
    private ImageView m关门img;
    private StatusBean mStatusBean;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private JPushActivity mJPush;
    private ToolbarView mToolbarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        CloseActivityClass.activityList.add(this);
        mJPush = new JPushActivity(this);
        mJPush.initJPush();//初始化极光推送
        mJPush.registerMessageReceiver();//注册信息接收器
        mJPush.setTag(LoginSp.getuid(this));//为设备设置标签
        mJPush.setAlias(LoginSp.getuid(this));//为设备设置别名
        UURL.MYMAC = getBtAddressByReflection();
        initView();
        getIndex();
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

    private void initView() {
        mToolbarView = findViewById(R.id.车辆设置tb);
        mToolbarView.setOnViewClickListener(new ToolbarView.OnRightClickListener() {
            @Override
            public void onClick() {
                startActivity(new Intent(HomeActivity.this, SystemActivity.class));
            }
        });
        mSwipeRefreshLayout = findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mLeftTopWendu = (TextView) findViewById(R.id.left_top_wendu);
        mLeftTopWendu.setOnClickListener(this);
        mLeftTopYali = (TextView) findViewById(R.id.left_top_yali);
        mLeftTopYali.setOnClickListener(this);
        mLeftTopGantan = (ImageView) findViewById(R.id.left_top_gantan);
        mLeftTopGantan.setOnClickListener(this);
        mRihtTopWendu = (TextView) findViewById(R.id.riht_top_wendu);
        mRihtTopWendu.setOnClickListener(this);
        mRihtTopYali = (TextView) findViewById(R.id.riht_top_yali);
        mRihtTopYali.setOnClickListener(this);
        mRightTopGantan = (ImageView) findViewById(R.id.right_top_gantan);
        mRightTopGantan.setOnClickListener(this);
        mTop = (LinearLayout) findViewById(R.id.top);
        mTop.setOnClickListener(this);
        mLeftBottomGantan = (ImageView) findViewById(R.id.left_bottom_gantan);
        mLeftBottomGantan.setOnClickListener(this);
        mLeftBottomWendu = (TextView) findViewById(R.id.left_bottom_wendu);
        mLeftBottomWendu.setOnClickListener(this);
        mLeftBottomYali = (TextView) findViewById(R.id.left_bottom_yali);
        mLeftBottomYali.setOnClickListener(this);
        mRihtBottomGantan = (ImageView) findViewById(R.id.riht_bottom_gantan);
        mRihtBottomGantan.setOnClickListener(this);
        mRihtBottomWendu = (TextView) findViewById(R.id.riht_bottom_wendu);
        mRihtBottomWendu.setOnClickListener(this);
        mRihtBottomYali = (TextView) findViewById(R.id.riht_bottom_yali);
        mRihtBottomYali.setOnClickListener(this);
        mIvbtn = (ImageView) findViewById(R.id.ivbtn);
        mIvbtn.setOnClickListener(this);
        m电量LT = (LTextView) findViewById(R.id.电量LT);
        m电量LT.setOnClickListener(this);
        m开门LT = (LTextView) findViewById(R.id.开门LT);
        m开门LT.setOnClickListener(this);
        m点火LT = (LTextView) findViewById(R.id.点火LT);
        m点火LT.setOnClickListener(this);
        m添加蓝牙LT = (LTextView) findViewById(R.id.添加蓝牙LT);
        m添加蓝牙LT.setOnClickListener(this);
        m其他设置LT = (LTextView) findViewById(R.id.其他设置LT);
        m其他设置LT.setOnClickListener(this);
        m开门img = (ImageView) findViewById(R.id.开门img);
        m开门img.setOnClickListener(this);
        m关门img = (ImageView) findViewById(R.id.关门img);
        m关门img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivbtn:
                isKaiGuan(7);
                break;
            case R.id.开门LT:
                Intent intent = new Intent(HomeActivity.this, LinesActivity.class);
                intent.putExtra("num", "1");
                startActivity(intent);
                break;
            case R.id.点火LT:
                Intent intent1 = new Intent(HomeActivity.this, LinesActivity.class);
                intent1.putExtra("num", "2");
                startActivity(intent1);
                break;
            case R.id.添加蓝牙LT:
                startActivity(new Intent(HomeActivity.this, ScrollingActivity.class));
                break;
            case R.id.其他设置LT:
                startActivity(new Intent(HomeActivity.this, QitaActivity.class));
                break;
            case R.id.开门img:
                IsKaiGuan(1);
                break;
            case R.id.关门img:
                IsKaiGuan(0);
                break;
        }
    }

    private String deviceid;

    private void getIndex() {
        Loading();
        OkHttpClientManager.postAsyn(UURL.INDEX, new OkHttpClientManager.ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        Log.d("aaaa", LoginSp.gettoken(HomeActivity.this));
                        Log.d("aaaa", response);
                        Gson gson = new Gson();
                        JsonReader reader = new JsonReader(new StringReader(response));
                        StatusBean mStatusBean = gson.fromJson(reader, StatusBean.class);
                        if (mStatusBean.getCode().equals("0")) {
                            Toast.makeText(HomeActivity.this, mStatusBean.getInfo(), Toast.LENGTH_SHORT).show();
                            //左上
                            mLeftTopWendu.setText("胎温: " + String.valueOf(mStatusBean.getData().getInfo().get(0).getElt().getEtaiwen()));
                            mLeftTopYali.setText("胎压: " + String.valueOf(mStatusBean.getData().getInfo().get(0).getElt().getEtaiya()));
                            // 右上
                            mRihtTopWendu.setText("胎温: " + String.valueOf(mStatusBean.getData().getInfo().get(0).getErt().getEtaiwen()));
                            mRihtTopYali.setText("胎压: " + String.valueOf(mStatusBean.getData().getInfo().get(0).getErt().getEtaiya()));
                            //左下
                            mLeftBottomWendu.setText("胎温: " + String.valueOf(mStatusBean.getData().getInfo().get(0).getElb().getEtaiwen()));
                            mLeftBottomYali.setText("胎压: " + String.valueOf(mStatusBean.getData().getInfo().get(0).getElb().getEtaiya()));
                            //右下
                            mRihtBottomWendu.setText("胎温: " + String.valueOf(mStatusBean.getData().getInfo().get(0).getErb().getEtaiwen()));
                            mRihtBottomYali.setText("胎压: " + String.valueOf(mStatusBean.getData().getInfo().get(0).getErb().getEtaiya()));
                            // 电量
                            m电量LT.setValue(mStatusBean.getData().getInfo().get(0).getEdianping() + "%");
                            deviceid = mStatusBean.getData().getInfo().get(0).getDeviceid();
                            UURL.equipsearch = deviceid;
                            dismissDialog();
                        } else if (mStatusBean.getCode().equals("-102")) {
                            dismissDialog();
                            LoginSp.loginchu(HomeActivity.this);
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            return;
                        } else if (mStatusBean.getCode().equals("-103")) {
                            Toast.makeText(HomeActivity.this, mStatusBean.getInfo(), Toast.LENGTH_SHORT).show();
                            dismissDialog();
                            return;
                        } else if (mStatusBean.getCode().equals("-104")) {
                            Toast.makeText(HomeActivity.this, mStatusBean.getInfo(), Toast.LENGTH_SHORT).show();
                            dismissDialog();
                            return;
                        } else {
                            Toast.makeText(HomeActivity.this, mStatusBean.getInfo(), Toast.LENGTH_SHORT).show();
                            dismissDialog();
                        }


                    }
                }
                , new OkHttpClientManager.Param("token", LoginSp.gettoken(this)));
    }

    @Override
    public void onRefresh() {
        reFresh();
    }

    private void reFresh() {
        Loading();
        String url = UURL.EQUIPSEARCH + "&token=" + LoginSp.gettoken(this) + "&deviceid=" + deviceid;//+ UURL.equipsearch
        Log.d("aaaa1", url);
        OkHttpClientManager.getAsyn(url, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(String response) {
                dismissDialog();
                mSwipeRefreshLayout.setRefreshing(false);
                Gson gson = new Gson();
                JsonReader reader = new JsonReader(new StringReader(response));
                StatusBean mStatusBean = gson.fromJson(reader, StatusBean.class);
                if (mStatusBean.getCode().equals("0")) {
                    Toast.makeText(HomeActivity.this, mStatusBean.getInfo(), Toast.LENGTH_SHORT).show();
                    //左上
                    mLeftTopWendu.setText("胎温: " + String.valueOf(mStatusBean.getData().getInfo().get(0).getElt().getEtaiwen()));
                    mLeftTopYali.setText("胎压: " + String.valueOf(mStatusBean.getData().getInfo().get(0).getElt().getEtaiya()));
                    // 右上
                    mRihtTopWendu.setText("胎温: " + String.valueOf(mStatusBean.getData().getInfo().get(0).getErt().getEtaiwen()));
                    mRihtTopYali.setText("胎压: " + String.valueOf(mStatusBean.getData().getInfo().get(0).getErt().getEtaiya()));
                    //左下
                    mLeftBottomWendu.setText("胎温: " + String.valueOf(mStatusBean.getData().getInfo().get(0).getElb().getEtaiwen()));
                    mLeftBottomYali.setText("胎压: " + String.valueOf(mStatusBean.getData().getInfo().get(0).getElb().getEtaiya()));
                    //右下
                    mRihtBottomWendu.setText("胎温: " + String.valueOf(mStatusBean.getData().getInfo().get(0).getErb().getEtaiwen()));
                    mRihtBottomYali.setText("胎压: " + String.valueOf(mStatusBean.getData().getInfo().get(0).getErb().getEtaiya()));
                    // 电量
                    m电量LT.setValue(mStatusBean.getData().getInfo().get(0).getDian() + "%");
                } else if (mStatusBean.getCode().equals("-102")) {
                    LoginSp.loginchu(HomeActivity.this);
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                } else if (mStatusBean.getCode().equals("-103")) {
                    Toast.makeText(HomeActivity.this, mStatusBean.getInfo(), Toast.LENGTH_SHORT).show();
                    return;
                } else if (mStatusBean.getCode().equals("-104")) {
                    Toast.makeText(HomeActivity.this, mStatusBean.getInfo(), Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(HomeActivity.this, mStatusBean.getInfo(), Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    private void isKaiGuan(int i) {
        Loading();
        OkGo.post(UURL.EQUIPCONTROL)
                .params("token", LoginSp.gettoken(HomeActivity.this))
                .params("type", i)
                .params("deviceid", deviceid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        dismissDialog();
                        Log.d("38901283912", s);
                        Log.d("38901283912", deviceid);
                        Log.d("38901283912", LoginSp.gettoken(HomeActivity.this));
                        PointOutBean bean = JSON.parseObject(s, PointOutBean.class);
                        if (bean.getCode().equals("0")) {
                            if (bean.getColor().equals("black")) {
                                mIvbtn.setBackgroundResource(R.mipmap.icon_start_button);
                                Toast.makeText(HomeActivity.this, bean.getInfo(), Toast.LENGTH_SHORT).show();
                            } else {
                                mIvbtn.setBackgroundResource(R.drawable.icon_start_button_on);
                                Toast.makeText(HomeActivity.this, bean.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                            Message rMessage = new Message();
                            rMessage.arg1 = 1;
                        } else if (bean.getCode().equals("-101")) {

                        } else if (bean.getCode().equals("-102")) {
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            HomeActivity.this.startActivity(intent);
                            HomeActivity.this.finish();

                        } else if (bean.getCode().equals("-103")) {
                            Intent intent = new Intent(HomeActivity.this, BindActivity.class);
                            startActivity(intent);
                        } else if (bean.getCode().equals("-105")) {
                            if (bean.getColor().equals("black")) {
                                mIvbtn.setBackgroundResource(R.mipmap.icon_start_button);
                                Toast.makeText(HomeActivity.this, bean.getInfo(), Toast.LENGTH_SHORT).show();
                            } else {
                                mIvbtn.setBackgroundResource(R.drawable.icon_start_button_on);
                                Toast.makeText(HomeActivity.this, bean.getInfo(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void IsKaiGuan(final int val) {
        Loading();
        OkGo.post(UURL.EQUIPCONTROL)
                .params("token", LoginSp.gettoken(HomeActivity.this))
                .params("type", "6")
                .params("val", val + "")
                .params("deviceid", deviceid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        dismissDialog();
                        Log.d("38901283912", s);
                        Log.d("38901283912", String.valueOf(val));
                        Log.d("38901283912", deviceid);
                        Log.d("38901283912", LoginSp.gettoken(HomeActivity.this));
                        bean = JSON.parseObject(s, PointOutBean.class);
                        Toast.makeText(HomeActivity.this, bean.getInfo(), Toast.LENGTH_SHORT).show();
                        if (bean.getCode().equals("-101")) {

                        } else if (bean.getCode().equals("-102")) {
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else if (bean.getCode().equals("-103")) {
                            Intent intent = new Intent(HomeActivity.this, BindActivity.class);
                            startActivity(intent);

                        } else if (bean.getCode().equals("-105")) {


                        } else {

                        }

                    }
                });
    }

    public void Loading() {
        waitDialog = new LodingDialog(HomeActivity.this, this, "");
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
