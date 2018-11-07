package com.land.netcar.min;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.land.netcar.R;
import com.land.netcar.base.BaseActivity;
import com.land.netcar.login.loginsp.LoginSp;
import com.land.netcar.min.adapter.BianAdapter;
import com.land.netcar.min.bean.BianBean;
import com.land.netcar.min.bean.PointOutBean;
import com.land.netcar.urls.CloseActivityClass;
import com.land.netcar.urls.UURL;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class BindActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swiperefres)
    SwipeRefreshLayout swiperefres;
    @BindView(R.id.bindcar)
    TextView bindcar;
    @BindView(R.id.onc)
    RelativeLayout onc;

    private BianAdapter adapter;
    private int page = 1;
    private List<BianBean.DataBean.InfoBean> info;
    private List<BianBean.DataBean.InfoBean> newInfoBean = new ArrayList<>();
    private int type = 1;
    long t1 = 0;//记录上一次单击的时间，初始值为0
    private int mOrientation = -1;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.arg1 == 1) {
                type = 1;
                getData(1);
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);
        ButterKnife.bind(this);
        CloseActivityClass.activityList.add(this);
        initView();
        getData(1);
    }

    private void getData(int page) {
        Loading();
        OkGo.post(UURL.MYEQUIP)
                .params("token", LoginSp.gettoken(this))
                .params("page", page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        BianBean bean = JSON.parseObject(s, BianBean.class);

                        if ("0".equals(bean.getCode())) {

                            info = bean.getData().getInfo();
                            Toast.makeText(BindActivity.this, bean.getInfo(), Toast.LENGTH_SHORT).show();


                            swiperefres.setVisibility(View.VISIBLE);
                            onc.setVisibility(View.GONE);

                            if (type == 1) {
                                newInfoBean.clear();
                                newInfoBean.addAll(info);
                                adapter.notifyDataSetChanged();
                            }
                            if (type == 2) {
                                newInfoBean.addAll(info);
                                adapter.notifyDataSetChanged();
                            }
                        } else if ("-103".equals(bean.getCode())) {
                            swiperefres.setVisibility(View.GONE);
                            onc.setVisibility(View.VISIBLE);
                            newInfoBean.clear();
                            adapter.notifyDataSetChanged();
                            Toast.makeText(BindActivity.this, bean.getInfo(), Toast.LENGTH_SHORT).show();
                        } else {
                            if (t1 == 0) {//第一次单击，初始化为本次单击的时间
                                t1 = (new Date()).getTime();
                            } else {
                                long curTime = (new Date()).getTime();//本地单击的时间
                                System.out.println("两次单击间隔时间：" + (curTime - t1));//计算本地和上次的时间差
                                if (curTime - t1 > 5 * 1000) {
                                    //间隔5秒允许点击，可以根据需要修改间隔时间
                                    t1 = curTime;//当前单击事件变为上次时间
                                    Toast.makeText(BindActivity.this, bean.getInfo(), Toast.LENGTH_SHORT).show();
                                }
                            }

//
                        }
                        dismissDialog();
                    }
                });
    }

    private void initView() {
        swiperefres.setOnRefreshListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        adapter = new BianAdapter(this, newInfoBean, handler);
        recyclerview.setAdapter(adapter);

        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (recyclerView.canScrollVertically(1) == false) {
                    type = 2;
                    page++;
                    getData(page);

                }
            }
        });
    }

    @Override
    public void onRefresh() {
        page = 1;
        type = 1;
        getData(page);

        swiperefres.setRefreshing(false);
    }

    @OnClick({R.id.back, R.id.bindcar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bindcar:

                final AlertDialog dialog = new AlertDialog.Builder(this).create();
//                dialog.setView(inflater.inflate(R.layout.alert_dialog, null));
                LayoutInflater flater = LayoutInflater.from(this);
                final View views = flater.inflate(R.layout.alert_dialog, null);
                dialog.setView(views);
                final EditText number = views.findViewById(R.id.number);
                final EditText name = views.findViewById(R.id.name_eit_bind);

                TextView confirm = views.findViewById(R.id.confirm);
                TextView cancel = views.findViewById(R.id.cancel);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Loading();
                        OkGo.post(UURL.REGEQUIP)
                                .params("token", LoginSp.gettoken(BindActivity.this))
                                .params("sn", number.getText().toString().trim())
                                .params("title", name.getText().toString().trim())
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(String s, Call call, Response response) {
                                        PointOutBean bean = JSON.parseObject(s, PointOutBean.class);
                                        showTextToastShort(BindActivity.this, bean.getInfo());
                                        Message rMessage = new Message();
                                        rMessage.arg1 = 1;
                                        handler.sendMessage(rMessage);
                                        dialog.dismiss();
                                        dismissDialog();
                                    }
                                });
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();

                break;
        }
    }


}
