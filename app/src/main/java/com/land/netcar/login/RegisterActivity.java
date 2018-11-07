package com.land.netcar.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.land.netcar.R;
import com.land.netcar.base.BaseActivity;
import com.land.netcar.login.bean.RetrieveBean;
import com.land.netcar.min.AboutActivity;
import com.land.netcar.urls.UURL;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


public class RegisterActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.smscode)
    EditText smscode;
    @BindView(R.id.code)
    TextView code;
    @BindView(R.id.pwd)
    EditText pwd;
    @BindView(R.id.checbok)
    CheckBox checbok;
    @BindView(R.id.onc_text)
    TextView oncText;
    @BindView(R.id.login_ok)
    Button loginOk;
    @BindView(R.id.titel_name)
    TextView titel_name;
    private int mSecCount;
    private RetrieveBean bean;
    private String intent;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        intent = getIntent().getStringExtra("type");
        type = Integer.parseInt(intent.toString());
        initView();
    }

    private void initView() {

        if (type == 1) {
            titel_name.setText("注册");
            loginOk.setText("注册");

            checbok.setVisibility(View.VISIBLE);
            oncText.setVisibility(View.VISIBLE);
        }
        if (type == 2) {
            titel_name.setText("找回密码");
            loginOk.setText("确定");
            checbok.setVisibility(View.GONE);
            oncText.setVisibility(View.GONE);
        }

        oncText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(RegisterActivity.this, AboutActivity.class);
                intents.putExtra("ssd", "2");
                startActivity(intents);
            }
        });
    }

    @OnClick({R.id.back, R.id.code, R.id.login_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.code:
                if (phone.getText().toString().equals("")) {
                    showTextToastShort(this, "手机号不能为空！");
                    return;
                }

                getSmsCode();

                break;
            case R.id.login_ok:
                if (phone.getText().toString().equals("")) {
                    showTextToastShort(this, "手机号不能为空！");
                    return;
                }
                if (smscode.getText().toString().equals("")) {
                    showTextToastShort(this, "验证码不能为空！");
                    return;
                }
                if (phone.getText().toString().equals("")) {
                    showTextToastShort(this, "密码不能为空！");
                    return;
                }
                if (type == 1) {
                    getRegister();
                }
                if (type == 2) {
                    getBackpwd();
                }

                break;
        }
    }

    private void getBackpwd() {
        OkGo.post(UURL.BACKPWD)
                .params("mobile", phone.getText().toString())
                .params("smscode", code.getText().toString())
                .params("password", pwd.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        bean = gson.fromJson(s, RetrieveBean.class);
                        showTextToastShort(RegisterActivity.this, bean.getInfo());
                        if (bean.getCode().equals("0")) {
                            finish();
                        }

                    }
                });
    }

    private void getSmsCode() {
        OkGo.post(UURL.SENDSMSCODE)
                .params("mobile", phone.getText().toString())
                .params("smstype", intent)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        bean = gson.fromJson(s, RetrieveBean.class);
                        if (bean.getCode().equals("0")) {
                            code.setBackgroundResource(R.drawable.kuang_daojishi);
                            setCodeTimeDown();

                        }
                        showTextToastShort(RegisterActivity.this, bean.getInfo());
                    }
                });
    }

    private void getRegister() {
        OkGo.post(UURL.REG)
                .params("mobile", phone.getText().toString())
                .params("smscode", code.getText().toString())
                .params("password", pwd.getText().toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        bean = gson.fromJson(s, RetrieveBean.class);
                        showTextToastShort(RegisterActivity.this, bean.getInfo());
                        if (bean.getCode().equals("0")) {
                            finish();
                        }
                    }
                });
    }


    private void setCodeTimeDown() {
        {
            code.setEnabled(false);
            final Timer timer = new Timer();
            mSecCount = 60;
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSecCount--;
                            code.setText(mSecCount + " s");
                            if (mSecCount <= 0) {
                                timer.cancel();
                                code.setText("重新发送");
                                code.setBackgroundResource(R.drawable.kuang);
                                code.setEnabled(true);
                            }
                        }
                    });
                }
            };
            timer.schedule(timerTask, 1000, 1000);
        }
    }
}
