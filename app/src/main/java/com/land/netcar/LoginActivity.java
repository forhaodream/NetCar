package com.land.netcar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.land.netcar.base.BaseActivity;
import com.land.netcar.ch.HomeActivity;
import com.land.netcar.login.RegisterActivity;
import com.land.netcar.login.bean.LoginBean;
import com.land.netcar.login.loginsp.LoginSp;
import com.land.netcar.urls.CloseActivityClass;
import com.land.netcar.urls.UURL;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.name_eliminate)
    LinearLayout nameEliminate;
    @BindView(R.id.pwd)
    EditText pwd;
    @BindView(R.id.pwd_eliminate)
    LinearLayout pwdEliminate;
    @BindView(R.id.login_landing)
    TextView loginLanding;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.forget)
    TextView forget;
    @BindView(R.id.touxianshenem)
    ImageView touxianshenem;
    private Intent intent;
    private LoginBean bean;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.arg1 == 1) {
                cu();
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        UURL.Handler = handler;
        if (LoginSp.gettoken(this).equals("")) {

        } else {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        rectRoundBitmap();
        initview();
    }

    private void rectRoundBitmap() {
        //得到资源文件的BitMap
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
        //创建RoundedBitmapDrawable对象
        RoundedBitmapDrawable roundImg = RoundedBitmapDrawableFactory.create(getResources(), image);
        //抗锯齿
        roundImg.setAntiAlias(true);
        //设置圆角半径
        roundImg.setCornerRadius(30);
        //设置显示图片
        touxianshenem.setImageDrawable(roundImg);
    }


    public void cu() {
        CloseActivityClass.exitClient(LoginActivity.this);
    }

    private void initview() {


        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    nameEliminate.setVisibility(View.GONE);
                } else {
                    nameEliminate.setVisibility(View.VISIBLE);
                }
            }
        });
        pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 1) {
                    pwdEliminate.setVisibility(View.GONE);
                } else {
                    pwdEliminate.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick({R.id.login_landing, R.id.register, R.id.forget, R.id.name_eliminate, R.id.pwd_eliminate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_landing:
                if (name.getText().toString().equals("")) {
                    showTextToastLong(this, "账号不能为空！");
                    return;
                }
                if (pwd.getText().toString().equals("")) {
                    showTextToastLong(this, "密码不能为空！");
                    return;
                }
                getData();
                break;
            case R.id.register:
                intent = new Intent(this, RegisterActivity.class);
                intent.putExtra("type", "1");
                startActivity(intent);

                break;
            case R.id.forget:
                Intent intent = new Intent(this, RegisterActivity.class);
                intent.putExtra("type", "2");
                startActivity(intent);

                break;
            case R.id.name_eliminate:
                name.setText(null);
                break;
            case R.id.pwd_eliminate:
                pwd.setText(null);
                break;
        }
    }

    private void getData() {
        Loading();
        OkGo.post(UURL.LOGIN)
                .params("username", name.getText().toString())
                .params("password", pwd.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        if (s.length() < 120) {
                            JSONObject jsonObject = JSON.parseObject(s);
                            //得到状态码
                            String code = jsonObject.getString("code");
                            String msg = jsonObject.getString("data");
                            String result = jsonObject.getString("info");
                            Toast.makeText(LoginActivity.this, "" + result, Toast.LENGTH_SHORT).show();
                            showTextToastLong(LoginActivity.this, result);
                            dismissDialog();
                        } else {
                            Gson gson = new Gson();
                            bean = gson.fromJson(s, LoginBean.class);
                            LoginSp.loginspc(LoginActivity.this, bean);
                            if (bean.getCode().equals("0")) {
                                intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                                showTextToastLong(LoginActivity.this, bean.getInfo());

                            }
                            dismissDialog();

                        }
                    }
                });
    }

}
