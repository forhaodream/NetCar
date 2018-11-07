package com.land.netcar.min.adapter;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.land.netcar.LoginActivity;
import com.land.netcar.R;
import com.land.netcar.login.loginsp.LoginSp;
import com.land.netcar.min.BindActivity;
import com.land.netcar.min.BluActivity;
import com.land.netcar.min.MainActivity;
import com.land.netcar.min.bean.PointOutBean;
import com.land.netcar.min.bean.StatusBean;
import com.land.netcar.urls.UURL;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by VULCAN on 2018/2/28.
 */

public class HeaderAdapter extends RecyclerView.Adapter {
    private final MainActivity activity;
    private StatusBean beans;
    private final Handler handlers;
    private LayoutInflater inflater;
    private boolean isHuo = false;
    private int val = 0;
    private BluetoothSocket socket;

    public HeaderAdapter(MainActivity activity, StatusBean bean, Handler handlers) {
        this.activity = activity;
        this.beans = bean;
        this.handlers = handlers;
        inflater = LayoutInflater.from(activity);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainHolder(inflater.inflate(R.layout.header_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MainHolder) {
            if (beans.getData().getInfo().get(0).getEdianhuo().equals("1")) {
                //on
                ((MainHolder) holder).ivbtn.setBackgroundResource(R.drawable.icon_start_button_on);
                isHuo = true;
                val = 1;
            } else {
                //off
                isHuo = false;
                val = 0;
                ((MainHolder) holder).ivbtn.setBackgroundResource(R.mipmap.icon_start_button);

            }


            ((MainHolder) holder).ivbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (isHuo == true) {
                        //on
                        isHuo = false;
                        val = 0;
//                        ((MainHolder) holder).ivbtn.setBackgroundResource(R.drawable.icon_start_button_on);
                    } else {
                        //off
//                        ((MainHolder) holder).ivbtn.setBackgroundResource(R.mipmap.icon_start_button);
//                        isHuo = true;
                        val = 1;

                    }

                    IsKaiGuan(7, ((MainHolder) holder).ivbtn, beans.getData().getInfo().get(0).getEdianhuo());

                }
            });


            //1
            ((MainHolder) holder).left_top_wendu.setText("胎温：" + beans.getData().getInfo().get(0).getElt().getEtaiwen());
            ((MainHolder) holder).left_top_yali.setText("胎压：" + beans.getData().getInfo().get(0).getElt().getEtaiya());
            if (beans.getData().getInfo().get(0).getElt().getEtirestate() == 0) {
                //判断感叹号
                ((MainHolder) holder).left_top_gantan.setVisibility(View.GONE);
            } else {
                ((MainHolder) holder).left_top_gantan.setVisibility(View.VISIBLE);
            }

            //2
            ((MainHolder) holder).riht_top_wendu.setText("胎温：" + beans.getData().getInfo().get(0).getErt().getEtaiwen());
            ((MainHolder) holder).riht_top_yali.setText("胎压：" + beans.getData().getInfo().get(0).getErt().getEtaiya());
            if (beans.getData().getInfo().get(0).getErt().getEtirestate() == 0) {
                //判断感叹号

                ((MainHolder) holder).right_top_gantan.setVisibility(View.GONE);
            } else {
                ((MainHolder) holder).right_top_gantan.setVisibility(View.VISIBLE);

            }


            //3
            ((MainHolder) holder).left_bottom_wendu.setText("胎温：" + beans.getData().getInfo().get(0).getElb().getEtaiwen());
            ((MainHolder) holder).left_bottom_yali.setText("胎压：" + beans.getData().getInfo().get(0).getElb().getEtaiya());
            if (beans.getData().getInfo().get(0).getElb().getEtirestate() == 0) {
                //判断感叹号

                ((MainHolder) holder).left_bottom_gantan.setVisibility(View.GONE);
            } else {
                ((MainHolder) holder).left_bottom_gantan.setVisibility(View.VISIBLE);

            }


            //4
            ((MainHolder) holder).riht_bottom_wendu.setText("胎温：" + beans.getData().getInfo().get(0).getErb().getEtaiwen());
            ((MainHolder) holder).riht_bottom_yali.setText("胎压：" + beans.getData().getInfo().get(0).getErb().getEtaiya());
            if (beans.getData().getInfo().get(0).getErb().getEtirestate() == 0) {
                //判断感叹号

                ((MainHolder) holder).riht_bottom_gantan.setVisibility(View.GONE);
            } else {
                ((MainHolder) holder).riht_bottom_gantan.setVisibility(View.VISIBLE);

            }

        }
    }

    private void IsKaiGuan(int i, final ImageButton ivbtn, String s) {

        activity.Loading();
        OkGo.post(UURL.EQUIPCONTROL)
                .params("token", LoginSp.gettoken(activity))
                .params("type", i)
//                .params("val", s)
                .params("deviceid", UURL.equipsearch)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        PointOutBean bean = JSON.parseObject(s, PointOutBean.class);
                        if (bean.getCode().equals("0")) {
                            if (bean.getColor().equals("black")) {
                                ivbtn.setBackgroundResource(R.mipmap.icon_start_button);
                                Toast.makeText(activity, bean.getInfo(), Toast.LENGTH_SHORT).show();
                            } else {
                                ivbtn.setBackgroundResource(R.drawable.icon_start_button_on);
                                Toast.makeText(activity, bean.getInfo(), Toast.LENGTH_SHORT).show();
                            }

//                            if (bean.getVal() == 0) {
//                                isHuo = false;
//                                val = 1;
//                                ivbtn.setBackgroundResource(R.drawable.icon_start_button_on);
//                            } else if (bean.getVal() == 1) {
//                                isHuo = true;
//                                val = 0;
//                                ivbtn.setBackgroundResource(R.mipmap.icon_start_button);
//                            }

                            Message rMessage = new Message();
                            rMessage.arg1 = 1;
                            handlers.sendMessage(rMessage);
                        } else if (bean.getCode().equals("-101")) {

                        } else if (bean.getCode().equals("-102")) {
                            Intent intent = new Intent(activity, LoginActivity.class);
                            activity.startActivity(intent);
                            activity.finish();

                        } else if (bean.getCode().equals("-103")) {
                            Intent intent = new Intent(activity, BindActivity.class);
                            activity.startActivity(intent);
                        } else if (bean.getCode().equals("-105")) {
                            if (bean.getColor().equals("black")) {
                                ivbtn.setBackgroundResource(R.mipmap.icon_start_button);
                                Toast.makeText(activity, bean.getInfo(), Toast.LENGTH_SHORT).show();
                            } else {
                                ivbtn.setBackgroundResource(R.drawable.icon_start_button_on);
                                Toast.makeText(activity, bean.getInfo(), Toast.LENGTH_SHORT).show();
                            }
//                            if (bean.getVal() == 1) {
//                                isHuo = true;
//                                val = 1;
//                                ivbtn.setBackgroundResource(R.mipmap.icon_start_button);
//
//                            } else if (bean.getVal() == 0) {
//                                isHuo = false;
//                                val = 0;
//                                ivbtn.setBackgroundResource(R.drawable.icon_start_button_on);
//
//                            }
                        }
                        activity.dismissDialog();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    private class MainHolder extends RecyclerView.ViewHolder {
        private ImageButton ivbtn;
        private ImageView left_top_gantan;
        private TextView left_top_wendu, left_top_yali;
        private ImageView right_top_gantan;
        private TextView riht_top_wendu, riht_top_yali;
        private ImageView left_bottom_gantan;
        private TextView left_bottom_wendu, left_bottom_yali;
        private ImageView riht_bottom_gantan;
        private TextView riht_bottom_wendu, riht_bottom_yali;

        public MainHolder(View view) {
            super(view);

            ivbtn = view.findViewById(R.id.ivbtn);

            left_top_gantan = view.findViewById(R.id.left_top_gantan);
            left_top_wendu = view.findViewById(R.id.left_top_wendu);
            left_top_yali = view.findViewById(R.id.left_top_yali);

            right_top_gantan = view.findViewById(R.id.right_top_gantan);
            riht_top_wendu = view.findViewById(R.id.riht_top_wendu);
            riht_top_yali = view.findViewById(R.id.riht_top_yali);

            left_bottom_gantan = view.findViewById(R.id.left_bottom_gantan);
            left_bottom_wendu = view.findViewById(R.id.left_bottom_wendu);
            left_bottom_yali = view.findViewById(R.id.left_bottom_yali);

            riht_bottom_gantan = view.findViewById(R.id.riht_bottom_gantan);
            riht_bottom_wendu = view.findViewById(R.id.riht_bottom_wendu);
            riht_bottom_yali = view.findViewById(R.id.riht_bottom_yali);
        }
    }

    //发送数据
    private void sendData() {
        // 蓝牙服务端发送简单的一个字符串：hello,world!给连接的客户
        String s = "helloWorld";
        byte[] buffer = s.getBytes();
        try {
            OutputStream os = socket.getOutputStream();
            Log.d("JJJ=====", "服务器端数据发送完毕!" + s);
//            os.write(i);
            os.write(buffer);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}