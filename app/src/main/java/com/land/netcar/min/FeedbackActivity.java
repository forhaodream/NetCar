package com.land.netcar.min;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.land.netcar.R;
import com.land.netcar.base.BaseActivity;
import com.land.netcar.login.loginsp.LoginSp;
import com.land.netcar.min.bean.PointOutBean;
import com.land.netcar.urls.CloseActivityClass;
import com.land.netcar.urls.UURL;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.eit_feedback)
    EditText eitFeedback;
    @BindView(R.id.queding)
    Button queding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        CloseActivityClass.activityList.add(this);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.back, R.id.queding})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.queding:
                Loading();
                OkGo.post(UURL.FEEDBACK)
                        .params("content", eitFeedback.getText().toString())
                        .params("token", LoginSp.gettoken(this))
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                PointOutBean bean = JSON.parseObject(s, PointOutBean.class);
                                showTextToastShort(FeedbackActivity.this, bean.getInfo());
                                dismissDialog();
                            }
                        });
                break;
        }
    }
}
