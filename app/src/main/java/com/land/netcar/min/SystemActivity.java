package com.land.netcar.min;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.land.netcar.LoginActivity;
import com.land.netcar.R;
import com.land.netcar.base.BaseActivity;
import com.land.netcar.login.loginsp.LoginSp;
import com.land.netcar.min.adapter.SystemAdapter;
import com.land.netcar.urls.CloseActivityClass;
import com.land.netcar.urls.UURL;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SystemActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.main_btn_t)
    Button main_btn_t;
//R.drawable.icon_to_update, "检测更新",

    private String[] titel = {"车辆位置", "绑定车辆", "账户充值", "意见反馈","关于我们"};
    private Integer[] image = {R.drawable.icon_position, R.drawable.icon_binding, R.drawable.icon_recharge,
            R.drawable.icon_feedback,  R.drawable.icon_about};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);
        CloseActivityClass.activityList.add(this);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        List<String> stringA = Arrays.asList(titel);
        List<Integer> stringB = Arrays.asList(image);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        main_btn_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginSp.loginchu(getApplicationContext());
                Message rMessage = new Message();
                rMessage.arg1 = 1;
                UURL.Handler.sendMessage(rMessage);
                Intent intent = new Intent(SystemActivity.this, LoginActivity.class);
                startActivity(intent);


            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        SystemAdapter adapter = new SystemAdapter(this, stringA, stringB);
        recyclerview.setAdapter(adapter);
    }
}
