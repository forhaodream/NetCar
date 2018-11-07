package com.land.netcar.min;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.land.netcar.LoginActivity;
import com.land.netcar.R;
import com.land.netcar.base.BaseActivity;
import com.land.netcar.login.loginsp.LoginSp;
import com.land.netcar.min.bean.PointOutBean;
import com.land.netcar.urls.UURL;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class ZWActivity extends BaseActivity {

    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.titel)
    TextView titel;
    @BindView(R.id.iv_onc_zw)
    ImageView ivOncZw;
    @BindView(R.id.eit_zw_beizhu)
    EditText eit_zw_beizhu;
    private String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zw);
        ButterKnife.bind(this);

        num = getIntent().getStringExtra("num");
        if (num.equals("1")) {
            titel.setText("添加开门指纹");
        } else {
            titel.setText("添加点火指纹");
        }

    }

    @OnClick({R.id.back, R.id.iv_onc_zw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.iv_onc_zw:
                Loading();
//                OkGo.post(UURL.FINGERPRINTENTRY)
//                        .params("token", LoginSp.gettoken(this))
//                        .params("deviceid", UURL.equipsearch)
//                        .params("type", num)
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onSuccess(String s, Call call, Response response) {
//                                PointOutBean bean = JSON.parseObject(s, PointOutBean.class);
//                                showTextToastShort(ZWActivity.this, bean.getInfo());
//                                if (bean.getCode().equals("-102")) {
//                                    LoginSp.loginchu(ZWActivity.this);
//                                    Intent intent = new Intent(ZWActivity.this, LoginActivity.class);
//                                    startActivity(intent);
//                                    finish();
//
//                                } else if (bean.getCode().equals("-101") || bean.getCode().equals("-103")) {
//                                    ivOncZw.setBackgroundResource(R.drawable.ooo);
//                                } else if (bean.getCode().equals("0")) {
//                                    ivOncZw.setBackgroundResource(R.drawable.oooo);
                                    getNextTwo();
//                                    getNext();
//                                } else if (bean.getCode().equals("-105")) {
//                                    //gg了
//                                    ivOncZw.setBackgroundResource(R.drawable.ooo);
//                                }
//
//                            }
//                        });

                break;
        }
    }

    private void getNext() {
        OkGo.post(UURL.STEP2)
                .params("token", LoginSp.gettoken(this))
                .params("deviceid", UURL.equipsearch)
                .params("type", num)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        PointOutBean bean = JSON.parseObject(s, PointOutBean.class);
                        showTextToastShort(ZWActivity.this, bean.getInfo());
                        if (bean.getCode().equals("-102")) {
                            LoginSp.loginchu(ZWActivity.this);
                            Intent intent = new Intent(ZWActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                        } else if (bean.getCode().equals("-101") || bean.getCode().equals("-103") || bean.getCode().equals("-105")) {

                            ivOncZw.setBackgroundResource(R.drawable.ooo);

                        } else if (bean.getCode().equals("0")) {
                            getNextTwo();
                            ivOncZw.setBackgroundResource(R.drawable.ooooo);
                        }

                    }
                });

    }

    private void getNextTwo() {
//        OkGo.post(UURL.STEP3)
        OkGo.post(UURL.FINGERPRINTENTRY)
                .params("token", LoginSp.gettoken(this))
                .params("deviceid", UURL.equipsearch)
                .params("type", num)
                .params("name", eit_zw_beizhu.getText().toString().trim())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        PointOutBean bean = JSON.parseObject(s, PointOutBean.class);
                        showTextToastShort(ZWActivity.this, bean.getInfo());
                        if (bean.getCode().equals("-102")) {
                            LoginSp.loginchu(ZWActivity.this);
                            Intent intent = new Intent(ZWActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                        } else if (bean.getCode().equals("-101") || bean.getCode().equals("-103")) {

                            ivOncZw.setBackgroundResource(R.drawable.ooo);

                        } else if (bean.getCode().equals("0")) {


                            Intent intent = new Intent(ZWActivity.this, LinesActivity.class);

                            intent.putExtra("num", num);
                            startActivity(intent);
                            finish();
                        }
                        dismissDialog();
                    }
                });

    }

    private void showNormalDialog(String name, final int i) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(ZWActivity.this);

        normalDialog.setTitle(null);
        normalDialog.setMessage(name + ",请点击“确定”执行下一步");
        normalDialog.setCancelable(false);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        if (i == 1) {
                            getNext();
                        }

                        if (i == 2) {
                            getNextTwo();
                        }

                    }
                });

        // 显示
        normalDialog.show();
    }

    private void showDialog(String name) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(ZWActivity.this);

        normalDialog.setTitle(null);
        normalDialog.setMessage(name + ",请点击“确定”重新录入指纹");
        normalDialog.setCancelable(false);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        dismissDialog();

                    }
                });

        // 显示
        normalDialog.show();
    }

    private void show(String name) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(ZWActivity.this);

        normalDialog.setTitle(null);
        normalDialog.setMessage("指纹录入成功,请点击“确定”返回指纹列表");
        normalDialog.setCancelable(false);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do

                        Intent intent = new Intent(ZWActivity.this, LinesActivity.class);
                        startActivity(intent);
                        finish();
                        dismissDialog();

                    }
                });

        // 显示
        normalDialog.show();
    }
}
