package com.land.netcar.min.adapter;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.land.netcar.min.MainActivity;
import com.land.netcar.min.bean.PointOutBean;
import com.land.netcar.min.view.QitaActivity;
import com.land.netcar.urls.UURL;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by VULCAN on 2018/2/28.
 */

public class MainAdapter extends RecyclerView.Adapter {
    private final MainActivity mainActivity;
    private final List<String> stringA;
    private final List<Integer> stringB;
    private final List<String> stringC;
    private final Handler handlers;
    private LayoutInflater inflater;
    private int val = 0;
    private int po;
    private PointOutBean bean;


    public MainAdapter(MainActivity mainActivity, List<String> stringA, List<Integer> stringB, List<String> stringC, Handler handlers) {
        this.mainActivity = mainActivity;
        this.stringA = stringA;
        this.stringB = stringB;
        this.stringC = stringC;
        this.handlers = handlers;

        inflater = LayoutInflater.from(mainActivity);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MainBottomHolder(inflater.inflate(R.layout.activity_main_item, parent, false));


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MainBottomHolder) {
            if (position == 0) {

                ((MainBottomHolder) holder).tv.setText(stringA.get(position));
                Glide.with(mainActivity).load(stringB.get(position)).into(((MainBottomHolder) holder).iv);
                ((MainBottomHolder) holder).tv2.setVisibility(View.VISIBLE);
                ((MainBottomHolder) holder).tv2.setText(stringC.get(position) + "%");
                ((MainBottomHolder) holder).switch_on_off.setVisibility(View.GONE);
            }

            if (position == 1) {
                Log.d("adasdasdasdas", stringC.get(0) + "");
                ((MainBottomHolder) holder).tv.setText(stringA.get(position));
                Glide.with(mainActivity).load(stringB.get(position)).into(((MainBottomHolder) holder).iv);
                ((MainBottomHolder) holder).tv2.setVisibility(View.VISIBLE);
                ((MainBottomHolder) holder).tv2.setBackgroundResource(R.drawable.you);
                ((MainBottomHolder) holder).tv2.setTextSize(16);
                ((MainBottomHolder) holder).tv2.setHeight(15);
                ((MainBottomHolder) holder).tv2.setWidth(10);
                ((MainBottomHolder) holder).switch_on_off.setVisibility(View.GONE);
                ((MainBottomHolder) holder).tp_onc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mainActivity, LinesActivity.class);
                        intent.putExtra("num", "1");
                        mainActivity.startActivity(intent);
                    }
                });
            } else if (position == 2) {
                ((MainBottomHolder) holder).tv.setText(stringA.get(position));
                Glide.with(mainActivity).load(stringB.get(position)).into(((MainBottomHolder) holder).iv);
                ((MainBottomHolder) holder).tv2.setVisibility(View.VISIBLE);
                ((MainBottomHolder) holder).tv2.setBackgroundResource(R.drawable.you);
                ((MainBottomHolder) holder).tv2.setTextSize(16);
                ((MainBottomHolder) holder).tv2.setHeight(15);
                ((MainBottomHolder) holder).tv2.setWidth(10);
                ((MainBottomHolder) holder).switch_on_off.setVisibility(View.GONE);
                ((MainBottomHolder) holder).tp_onc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mainActivity, LinesActivity.class);
                        intent.putExtra("num", "2");
                        mainActivity.startActivity(intent);
                    }
                });
            } else if (position == 3) {
                ((MainBottomHolder) holder).tv.setText(stringA.get(position));
                Glide.with(mainActivity).load(stringB.get(position)).into(((MainBottomHolder) holder).iv);
                ((MainBottomHolder) holder).tv2.setVisibility(View.VISIBLE);
                ((MainBottomHolder) holder).tv2.setBackgroundResource(R.drawable.you);
                ((MainBottomHolder) holder).tv2.setTextSize(16);
                ((MainBottomHolder) holder).tv2.setHeight(15);
                ((MainBottomHolder) holder).tv2.setWidth(10);
                ((MainBottomHolder) holder).switch_on_off.setVisibility(View.GONE);
                ((MainBottomHolder) holder).tp_onc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mainActivity, ScrollingActivity.class);
                        mainActivity.startActivity(intent);
                    }
                });
            } else if (position == 4) {
                ((MainBottomHolder) holder).tv.setText(stringA.get(position));
                Glide.with(mainActivity).load(stringB.get(position)).into(((MainBottomHolder) holder).iv);
                ((MainBottomHolder) holder).tv2.setVisibility(View.VISIBLE);
                ((MainBottomHolder) holder).tv2.setBackgroundResource(R.drawable.you);
                ((MainBottomHolder) holder).tv2.setTextSize(16);
                ((MainBottomHolder) holder).tv2.setHeight(15);
                ((MainBottomHolder) holder).tv2.setWidth(10);
                ((MainBottomHolder) holder).switch_on_off.setVisibility(View.GONE);
                ((MainBottomHolder) holder).tp_onc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mainActivity, QitaActivity.class);
                        mainActivity.startActivityForResult(intent, 1);
                    }
                });

            } else if (position == 5) {
                ((MainBottomHolder) holder).tp_onc.setVisibility(View.GONE);
                ((MainBottomHolder) holder).visi.setVisibility(View.VISIBLE);
                ((MainBottomHolder) holder).kai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        IsKaiGuan(1);
                    }
                });
                ((MainBottomHolder) holder).guan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        IsKaiGuan(0);
                    }
                });
            }
        }
    }

    private void IsKaiGuan(int val) {
        mainActivity.Loading();
        OkGo.post(UURL.EQUIPCONTROL)
                .params("token", LoginSp.gettoken(mainActivity))
                .params("type", "6")
                .params("val", val + "")
                .params("deviceid", UURL.equipsearch)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("ssdweds", s);
                        bean = JSON.parseObject(s, PointOutBean.class);
                        Toast.makeText(mainActivity, bean.getInfo(), Toast.LENGTH_SHORT).show();
                        mainActivity.dismissDialog();
                        if (bean.getCode().equals("-101")) {

                        } else if (bean.getCode().equals("-102")) {
                            Intent intent = new Intent(mainActivity, LoginActivity.class);
                            mainActivity.startActivity(intent);
                        } else if (bean.getCode().equals("-103")) {
                            Intent intent = new Intent(mainActivity, BindActivity.class);
                            mainActivity.startActivity(intent);

                        } else if (bean.getCode().equals("-105")) {


                        } else {
//                            Message rMessage = new Message();
//                            rMessage.arg1 = 1;
//                            handlers.sendMessage(rMessage);
                        }

                    }
                });
    }

    private void getData(String trim) {
        mainActivity.Loading();
        Log.e("TAG", "" + trim);
        OkGo.post(UURL.TOOTHADDRESS)
                .params("token", LoginSp.gettoken(mainActivity))
                .params("deviceid", "" + UURL.equipsearch)
                .params("address", trim)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        PointOutBean pointOutBean = JSON.parseObject(s, PointOutBean.class);
                        Toast.makeText(mainActivity, "" + pointOutBean.getInfo(), Toast.LENGTH_SHORT).show();
                        mainActivity.dismissDialog();
                    }
                });
    }

    private void IsKaiGuan(int i, final Switch switch_on_off) {
        mainActivity.Loading();
        OkGo.post(UURL.EQUIPCONTROL)
                .params("token", LoginSp.gettoken(mainActivity))
                .params("type", po)
                .params("val", val)
                .params("deviceid", UURL.equipsearch)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        bean = JSON.parseObject(s, PointOutBean.class);
                        Toast.makeText(mainActivity, bean.getInfo(), Toast.LENGTH_SHORT).show();
                        mainActivity.dismissDialog();
                        if (bean.getCode().equals("-101")) {

                        } else if (bean.getCode().equals("-102")) {
                            Intent intent = new Intent(mainActivity, LoginActivity.class);
                            mainActivity.startActivity(intent);
                            mainActivity.finish();

                        } else if (bean.getCode().equals("-103")) {
                            Intent intent = new Intent(mainActivity, BindActivity.class);
                            mainActivity.startActivity(intent);

                        } else if (bean.getCode().equals("-105")) {
                            switch_on_off.setChecked(false);

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
        private ImageView iv;
        private TextView tv, tv2;

        private Switch switch_on_off;
        private RelativeLayout tp_onc;
        private LinearLayout visi, kai, guan;

        public MainBottomHolder(View view) {
            super(view);
            iv = view.findViewById(R.id.iv);
            tv = view.findViewById(R.id.tv);
            tv2 = view.findViewById(R.id.tv2);
            switch_on_off = view.findViewById(R.id.switch_on_off);
            tp_onc = view.findViewById(R.id.tp_onc);
            visi = view.findViewById(R.id.visi);
            kai = view.findViewById(R.id.kai);
            guan = view.findViewById(R.id.guan);
        }
    }
}
