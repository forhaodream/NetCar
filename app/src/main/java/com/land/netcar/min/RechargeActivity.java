package com.land.netcar.min;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.land.netcar.R;
import com.land.netcar.base.BaseActivity;
import com.land.netcar.login.loginsp.LoginSp;
import com.land.netcar.min.bean.AliBean;
import com.land.netcar.min.bean.WxBean;
import com.land.netcar.pay.AuthResult;
import com.land.netcar.pay.PayResult;
import com.land.netcar.urls.CloseActivityClass;
import com.land.netcar.urls.UURL;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class RechargeActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.edit_shuru_money)
    EditText editShuruMoney;
    @BindView(R.id.iv_pay_ali)
    ImageView ivPayAli;
    @BindView(R.id.tv_car_type)
    TextView tvCarType;
    @BindView(R.id.cb_choose_type_ali)
    ImageView cbChooseTypeAli;
    @BindView(R.id.onc_rl_pay_ali)
    RelativeLayout oncRlPayAli;
    @BindView(R.id.iv_pay_we)
    ImageView ivPayWe;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.cb_choose_type_we)
    ImageView cbChooseTypeWe;
    @BindView(R.id.onc_rl_pay_we)
    RelativeLayout oncRlPayWe;
    @BindView(R.id.btn_sure_pay)
    Button btnSurePay;
    private int payfs = 1;
    public static String RSA2_PRIVATE = "";
    public static final int SDK_PAY_FLAG = 1;
    public static final int SDK_AUTH_FLAG = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
        CloseActivityClass.activityList.add(this);
    }

    @OnClick({R.id.onc_rl_pay_ali, R.id.onc_rl_pay_we, R.id.btn_sure_pay, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.onc_rl_pay_ali:
                payfs = 1;
                cbChooseTypeAli.setImageDrawable(getResources().getDrawable(R.drawable.hongxinyuan));
                cbChooseTypeWe.setImageDrawable(getResources().getDrawable(R.drawable.kongixianyuan));
                break;
            case R.id.onc_rl_pay_we:
                payfs = 2;
                cbChooseTypeAli.setImageDrawable(getResources().getDrawable(R.drawable.kongixianyuan));
                cbChooseTypeWe.setImageDrawable(getResources().getDrawable(R.drawable.hongxinyuan));
                break;
            case R.id.btn_sure_pay:
                if (TextUtils.isEmpty(editShuruMoney.getText().toString().trim())) {
                    showTextToastShort(this, "请输入要充值的金额");
                    return;
                }

                getData();
                break;
            case R.id.back:
                finish();

                break;

        }
    }

    private void getData() {
        Log.e("TAGssd", "" + LoginSp.gettoken(this));
        Loading();
        OkGo.post(UURL.CHONGZHI)
                .params("token", LoginSp.gettoken(this))
                .params("price", editShuruMoney.getText().toString().trim())
                .params("paymode", payfs)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Log.e("TAGssd", "" + e.getMessage());
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("TAGssd", "" + s);
                        Gson gson = new Gson();
                        dismissDialog();
                        if (payfs == 1) {
                            AliBean payBean = gson.fromJson(s, AliBean.class);
                            RSA2_PRIVATE = payBean.getData().getPay();
                            appPay(RSA2_PRIVATE);
                        } else if (payfs == 2) {
                            WxBean bean = gson.fromJson(s, WxBean.class);

                            new WxPayActivity(RechargeActivity.this, bean);
                        }
                    }
                });
    }


    private void appPay(final String RSA_PRIVATE) {
        /**
         * 完整的符合支付宝参数规范的订单信息
         //         */
//        boolean rsa2 = (RSA_PRIVATE.length() > 0);
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID,rsa2);
//        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//        String sign = OrderInfoUtil2_0.getSign(params, RSA_PRIVATE, rsa2);
//        final String orderInfo = orderParam + "&" + sign;
        // 必须异步调用 新版本的直接把值传过来就ok
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(RechargeActivity.this);
                Map<String, String> result = alipay.payV2(RSA_PRIVATE, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")

                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
//                        AppManager.getInstance().finishActivity(PayActivity.class);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();
                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(RechargeActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(RechargeActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
}
