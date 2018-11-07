package com.land.netcar.min.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.land.netcar.LoginActivity;
import com.land.netcar.R;
import com.land.netcar.base.BaseActivity;
import com.land.netcar.login.loginsp.LoginSp;
import com.land.netcar.min.BindActivity;
import com.land.netcar.min.adapter.QitaAdapter;
import com.land.netcar.min.bean.PointOutBean;
import com.land.netcar.urls.UURL;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class QitaActivity extends BaseActivity {

    private RecyclerView recyclerview;
    private ImageView back,kai,guan;
    private QitaAdapter adapter;
    private String[] titel = {"加热器", "折叠镜", "汽车空调", "车窗控制", "天窗控制"};//, "车门锁" //, R.drawable.chemen,
    private Integer[] image = {
            R.drawable.jiareqi, R.drawable.zhdiejing, R.drawable.kongtiao,
            R.drawable.cchechang, R.drawable.tanchang
    };
    private PointOutBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qita);
        List<String> stringA = Arrays.asList(titel);
        back = findViewById(R.id.back);
        kai = findViewById(R.id.kai);
        guan = findViewById(R.id.guan);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        List<Integer> stringB = Arrays.asList(image);
        recyclerview = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        adapter = new QitaAdapter(this, stringA, stringB);
        recyclerview.setAdapter(adapter);
        kai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IsKaiGuan(1);
            }
        });
        guan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
IsKaiGuan(0);
            }
        });
    }
    private void IsKaiGuan(int val) {
       Loading();
        OkGo.post(UURL.EQUIPCONTROL)
                .params("token", LoginSp.gettoken(this))
                .params("type", "6")
                .params("val", val+"")
                .params("deviceid", UURL.equipsearch)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("ssdweds",s);
                        bean = JSON.parseObject(s, PointOutBean.class);
                        Toast.makeText(QitaActivity.this, bean.getInfo(), Toast.LENGTH_SHORT).show();
                       dismissDialog();
                        if (bean.getCode().equals("-101")) {

                        } else if (bean.getCode().equals("-102")) {
                            Intent intent = new Intent(QitaActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                        } else if (bean.getCode().equals("-103")) {
                            Intent intent = new Intent(QitaActivity.this, BindActivity.class);
                            startActivity(intent);

                        } else if (bean.getCode().equals("-105")) {


                        } else {
//                            Message rMessage = new Message();
//                            rMessage.arg1 = 1;
//                            handlers.sendMessage(rMessage);
                        }

                    }
                });
    }

}
