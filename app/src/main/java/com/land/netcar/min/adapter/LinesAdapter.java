package com.land.netcar.min.adapter;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.land.netcar.LoginActivity;
import com.land.netcar.R;
import com.land.netcar.login.loginsp.LoginSp;
import com.land.netcar.min.BindActivity;
import com.land.netcar.min.LinesActivity;
import com.land.netcar.min.bean.LinesBean;
import com.land.netcar.min.bean.PointOutBean;
import com.land.netcar.urls.UURL;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by VULCAN on 2018/3/8.
 */

public class LinesAdapter extends RecyclerView.Adapter {
    private final LinesActivity linesActivity;
    private final LinesBean bean;
    private final LayoutInflater inflater;
    private final String num;
    private final Handler handler;

    public LinesAdapter(LinesActivity linesActivity, LinesBean bean, String num, Handler handler) {
        this.linesActivity = linesActivity;
        this.bean = bean;
        this.num = num;
        this.handler = handler;
        inflater = LayoutInflater.from(linesActivity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainBottomHolder(inflater.inflate(R.layout.activity_linew_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MainBottomHolder) {
            ((MainBottomHolder) holder).tv_lines_name.setText(bean.getData().getInfo().get(position).getName());
            if (num.equals("1")) {
                ((MainBottomHolder) holder).zhuang.setText("开门");
            } else {
                ((MainBottomHolder) holder).zhuang.setText("点火");
            }

            ((MainBottomHolder) holder).tv_lines_dele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getData(position);
                }
            });
            ((MainBottomHolder) holder).tv_lines_bj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final AlertDialog dialog = new AlertDialog.Builder(linesActivity).create();
//                dialog.setView(inflater.inflate(R.layout.alert_dialog, null));
                    LayoutInflater flater = LayoutInflater.from(linesActivity);
                    final View views = flater.inflate(R.layout.alert_dialog_, null);
                    dialog.setView(views);
                    final EditText name = views.findViewById(R.id.name_eit_bind);//name_eit_bind

                    TextView confirm = views.findViewById(R.id.confirm);
                    TextView cancel = views.findViewById(R.id.cancel);
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(name.getText().toString().trim())) {
                                Toast.makeText(linesActivity, "修改名称不能为空", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            getBJ(position, name.getText().toString().trim());
                            dialog.dismiss();
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });


                    dialog.show();

                }
            });
        }
    }

    private void getData(final int position) {
        linesActivity.Loading();
        OkGo.post(UURL.FINGERPRINTDEL)
                .params("token", LoginSp.gettoken(linesActivity))
                .params("deviceid", bean.getDeviceid())
                .params("ztype", num)
                .params("type", "2")// 1
                .params("zid", bean.getData().getInfo().get(position).getId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        PointOutBean pointOutBean = JSON.parseObject(s, PointOutBean.class);
                        Toast.makeText(linesActivity, "" + pointOutBean.getInfo(), Toast.LENGTH_SHORT).show();
                        Log.d("11111111111", s);
                        Log.d("111112", bean.getData().getInfo().get(position).getId());
                        Log.d("111112", LoginSp.gettoken(linesActivity));
                        Log.d("111112", num);
                        Log.d("111112", bean.getDeviceid());
                        if (bean.getCode().equals("-102")) {
                            LoginSp.loginchu(linesActivity);
                            Intent intent = new Intent(linesActivity, LoginActivity.class);
                            linesActivity.startActivity(intent);
                            linesActivity.finish();
                            linesActivity.dismissDialog();
                        } else if (bean.getInfo().equals("-105") || bean.getInfo().equals("-103") || bean.getInfo().equals("-101")) {
                            linesActivity.dismissDialog();
                        } else {
                            Message rMessage = new Message();
                            rMessage.arg1 = 1;
                            handler.sendMessage(rMessage);
                        }
                        linesActivity.dismissDialog();

                    }
                });
    }

    private void getBJ(int position, String name) {
        linesActivity.Loading();
        OkGo.post(UURL.ZHIWENEDIT)
                .params("token", LoginSp.gettoken(linesActivity))
                .params("zid", bean.getData().getInfo().get(position).getId())
                .params("name", name)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        PointOutBean pointOutBean = JSON.parseObject(s, PointOutBean.class);
                        Toast.makeText(linesActivity, "" + pointOutBean.getInfo(), Toast.LENGTH_SHORT).show();
                        if (bean.getCode().equals("0")) {
                            Message rMessage = new Message();
                            rMessage.arg1 = 1;
                            handler.sendMessage(rMessage);
                        } else if (bean.getInfo().equals("-105") || bean.getInfo().equals("-103") || bean.getInfo().equals("-101")) {

                        } else if (bean.getInfo().equals("-102")) {
                            Intent intent = new Intent(linesActivity, LoginActivity.class);
                            linesActivity.startActivity(intent);
                            linesActivity.finish();
                        }
                        linesActivity.dismissDialog();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return bean.getData().getInfo().size();
    }

    private class MainBottomHolder extends RecyclerView.ViewHolder {
        private TextView tv_lines_name;
        private TextView zhuang;
        private TextView tv_lines_dele;
        private TextView tv_lines_bj;

        public MainBottomHolder(View inflate) {
            super(inflate);

            tv_lines_bj = inflate.findViewById(R.id.tv_lines_bj);
            tv_lines_dele = inflate.findViewById(R.id.tv_lines_dele);
            zhuang = inflate.findViewById(R.id.zhuang);
            tv_lines_name = inflate.findViewById(R.id.tv_lines_name);
        }
    }
}
