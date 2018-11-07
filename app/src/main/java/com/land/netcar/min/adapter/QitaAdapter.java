package com.land.netcar.min.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.land.netcar.LoginActivity;
import com.land.netcar.R;
import com.land.netcar.login.loginsp.LoginSp;
import com.land.netcar.min.BindActivity;
import com.land.netcar.min.LinesActivity;
import com.land.netcar.min.bean.PointOutBean;
import com.land.netcar.min.view.QitaActivity;
import com.land.netcar.urls.UURL;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by VULCAN on 2018/5/14.
 */

public class QitaAdapter extends RecyclerView.Adapter {
    private final List<Integer> stringB;
    private final List<String> stringA;
    private final QitaActivity activity;
    private final LayoutInflater inflater;
    private int po;
    private PointOutBean bean;
    private int val = 0;

    public QitaAdapter(QitaActivity activity, List<String> stringA, List<Integer> stringB) {
        this.activity = activity;
        this.stringA = stringA;
        this.stringB = stringB;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MainBottomHolder(inflater.inflate(R.layout.activity_main_item, parent, false));


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MainBottomHolder) {
            ((MainBottomHolder) holder).tv.setText(stringA.get(position));
            Glide.with(activity).load(stringB.get(position)).into(((MainBottomHolder) holder).iv);
//            if (UURL.SthingC.get(position).equals("0")) {
//                ((MainBottomHolder) holder).switch_on_off.setChecked(false);
//            } else if (UURL.SthingC.get(position).equals("1")) {
//                ((MainBottomHolder) holder).switch_on_off.setChecked(true);
//            }
            // 添加监听
            ((MainBottomHolder) holder).switch_on_off.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
//                            开启
                        val = 1;
                    } else {
//                            关闭
                        val = 0;
                    }
                    if (position == 0) {
                        po = 3;
                    }

                    if (position == 1) {
                        po = 5;
                    }
                    if (position == 2) {
                        po = 1;
                    }
                    if (position == 3) {
                        po = 4;
                    }
                    if (position == 4) {
                        po = 2;
                    }
                    if (position == 5) {
                        po = 6;
                    }


                    IsKaiGuan(po, ((MainBottomHolder) holder).switch_on_off);


                }
            });


        }


    }

    private void getData(String trim) {
        activity.Loading();
        Log.e("TAG", "" + trim);
        OkGo.post(UURL.TOOTHADDRESS)
                .params("token", LoginSp.gettoken(activity))
                .params("deviceid", "" + UURL.equipsearch)
                .params("address", trim)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        PointOutBean pointOutBean = JSON.parseObject(s, PointOutBean.class);
                        Toast.makeText(activity, "" + pointOutBean.getInfo(), Toast.LENGTH_SHORT).show();
                        activity.dismissDialog();
                    }
                });
    }

    private void IsKaiGuan(int i, final Switch switch_on_off) {
        activity.Loading();
        Log.e("wwwww", "" + val);
        OkGo.post(UURL.EQUIPCONTROL)
                .params("token", LoginSp.gettoken(activity))
                .params("type", po)
                .params("val", val)
                .params("deviceid", UURL.equipsearch)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        bean = JSON.parseObject(s, PointOutBean.class);
                        Toast.makeText(activity, bean.getInfo(), Toast.LENGTH_SHORT).show();
                        activity.dismissDialog();
                        if (bean.getCode().equals("0")) {
                            if (val == 0) {
                                val = 1;
                            } else {
                                val = 0;
                            }

                        }
                        if (bean.getCode().equals("-101")) {

                        } else if (bean.getCode().equals("-102")) {
                            Intent intent = new Intent(activity, LoginActivity.class);
                            activity.startActivity(intent);
                            activity.finish();

                        } else if (bean.getCode().equals("-103")) {
                            Intent intent = new Intent(activity, BindActivity.class);
                            activity.startActivity(intent);

                        } else if (bean.getCode().equals("-105")) {
                            switch_on_off.setChecked(false);
                            if (val == 0) {
                                val = 0;
                            } else {
                                val = 1;
                            }
                        } else {
//                            Message rMessage = new Message();
//                            rMessage.arg1 = 1;
//                            handlers.sendMessage(rMessage);
                        }

                    }
                });
    }

    @Override
    public int getItemCount() {
        return stringA.size();
    }

    private class MainBottomHolder extends RecyclerView.ViewHolder {
        private ImageView iv, kai, guan;
        private TextView tv, tv2;

        private Switch switch_on_off;
        private RelativeLayout tp_onc;


        public MainBottomHolder(View view) {
            super(view);
            iv = view.findViewById(R.id.iv);
            tv = view.findViewById(R.id.tv);
            tv2 = view.findViewById(R.id.tv2);
            switch_on_off = view.findViewById(R.id.switch_on_off);
            tp_onc = view.findViewById(R.id.tp_onc);


        }
    }
}

