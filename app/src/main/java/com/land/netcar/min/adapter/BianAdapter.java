package com.land.netcar.min.adapter;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.land.netcar.R;
import com.land.netcar.login.loginsp.LoginSp;
import com.land.netcar.min.BindActivity;
import com.land.netcar.min.bean.BianBean;
import com.land.netcar.min.bean.PointOutBean;
import com.land.netcar.urls.UURL;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by VULCAN on 2018/3/1.
 */

public class BianAdapter extends RecyclerView.Adapter {
    private final BindActivity bindActivity;
    private final List<BianBean.DataBean.InfoBean> newInfoBean;
    private final Handler handler;
    private LayoutInflater inflater;

    public BianAdapter(BindActivity bindActivity, List<BianBean.DataBean.InfoBean> newInfoBean, Handler handler) {
        this.bindActivity = bindActivity;
        this.newInfoBean = newInfoBean;
        this.handler = handler;
        inflater = LayoutInflater.from(bindActivity);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BianHolder(inflater.inflate(R.layout.bian_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof BianHolder) {
            ((BianHolder) holder).name_car.setText(newInfoBean.get(position).getTitle());
            Log.e("TAGdw", "" + newInfoBean.get(position).getIsmo());
            if (newInfoBean.get(position).getIsmo().equals("0")) {
                ((BianHolder) holder).set_up.setTextColor(Color.GRAY);
            } else if (newInfoBean.get(position).getIsmo().equals("1")) {
                ((BianHolder) holder).set_up.setTextColor(Color.RED);

            }
            ((BianHolder) holder).set_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bindActivity.Loading();
                    OkGo.post(UURL.MYEQUIPMO)
                            .params("token", LoginSp.gettoken(bindActivity))
                            .params("deviceid", newInfoBean.get(position).getDeviceid())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    PointOutBean bean = JSON.parseObject(s, PointOutBean.class);
                                    Toast.makeText(bindActivity, "" + bean.getInfo(), Toast.LENGTH_SHORT).show();
                                    Message rMessage = new Message();
                                    rMessage.arg1 = 1;
                                    handler.sendMessage(rMessage);
                                    bindActivity.dismissDialog();
                                }
                            });
                }
            });
            ((BianHolder) holder).cancel_bian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bindActivity.Loading();
                    OkGo.post(UURL.DELEQUIP)
                            .params("token", LoginSp.gettoken(bindActivity))
                            .params("deviceid", newInfoBean.get(position).getDeviceid())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    PointOutBean bean = JSON.parseObject(s, PointOutBean.class);
                                    Toast.makeText(bindActivity, "" + bean.getInfo(), Toast.LENGTH_SHORT).show();
                                    Message rMessage = new Message();
                                    rMessage.arg1 = 1;
                                    handler.sendMessage(rMessage);
                                    bindActivity.dismissDialog();
                                }
                            });
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return newInfoBean.size();
    }

    private class BianHolder extends RecyclerView.ViewHolder {
        private TextView name_car, set_up, cancel_bian;

        public BianHolder(View inflate) {
            super(inflate);

            set_up = inflate.findViewById(R.id.set_up);
            name_car = inflate.findViewById(R.id.name_car);
            cancel_bian = inflate.findViewById(R.id.cancel_bian);
        }
    }
}
