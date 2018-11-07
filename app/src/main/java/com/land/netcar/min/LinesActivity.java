package com.land.netcar.min;

import android.annotation.SuppressLint;
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
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.land.netcar.LoginActivity;
import com.land.netcar.R;
import com.land.netcar.base.BaseActivity;
import com.land.netcar.login.loginsp.LoginSp;
import com.land.netcar.min.adapter.LinesAdapter;
import com.land.netcar.min.bean.DSEBean;
import com.land.netcar.min.bean.LinesBean;
import com.land.netcar.urls.CloseActivityClass;
import com.land.netcar.urls.UURL;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class LinesActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    LinesBean bean;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swiperefres)
    SwipeRefreshLayout swiperefres;
    @BindView(R.id.add_lines)
    TextView addLines;
    @BindView(R.id.riasdh)
    RelativeLayout riasdh;
    private LinesAdapter adapter;
    private String num, gggg;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.arg1 == 1) {
                getData();
                adapter.notifyDataSetChanged();
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lines);
        ButterKnife.bind(this);
        CloseActivityClass.activityList.add(this);

        swiperefres.setOnRefreshListener(this);
        num = getIntent().getStringExtra("num");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        riasdh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(LinesActivity.this);

                normalDialog.setTitle(null);
                normalDialog.setMessage("是否清空指纹");
                normalDialog.setCancelable(false);
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Loading();
                                Log.e("TAGasdas", "" + num);
                                OkGo.post(UURL.FINGERPRINTDEL)
                                        .params("token", LoginSp.gettoken(LinesActivity.this))
                                        .params("deviceid", bean.getDeviceid())
                                        .params("ztype", num)
                                        .params("type", "1")
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(String s, Call call, Response response) {
                                                DSEBean bean = JSON.parseObject(s, DSEBean.class);
                                                showTextToastShort(LinesActivity.this, bean.getInfo());
//                                                Log.d("dsebean", s);
                                                dismissDialog();
                                                getData();
                                                if (bean.getCode().equals("-102")) {
                                                    Intent intent = new Intent(LinesActivity.this, LoginActivity.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });


                // 显示
                normalDialog.show();

            }
        });

        getData();

    }

    private void getData() {
        Loading();
        OkGo.post(UURL.MYZHIWEN)
                .params("token", LoginSp.gettoken(this))
                .params("type", num)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        bean = JSON.parseObject(s, LinesBean.class);
                        showTextToastShort(LinesActivity.this, bean.getInfo());
                        Log.d("111112", s);
                        Log.d("111112", LoginSp.gettoken(LinesActivity.this));
                        Log.d("111112", num);
                        if (bean.getCode().equals("-102")) {
                            LoginSp.loginchu(LinesActivity.this);
                            Intent intent = new Intent(LinesActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            dismissDialog();
                        } else {
                            adapter = new LinesAdapter(LinesActivity.this, bean, num, handler);
                            recyclerview.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            dismissDialog();


                        }

                    }
                });
    }


    @OnClick({R.id.back, R.id.add_lines})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();

                break;
            case R.id.add_lines:
                Intent intent = new Intent(this, ZWActivity.class);
                intent.putExtra("num", num);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRefresh() {
        getData();
        adapter.notifyDataSetChanged();
        swiperefres.setRefreshing(false);
    }
}
