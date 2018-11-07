package com.land.netcar.min;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.land.netcar.min.bean.WxBean;
import com.land.netcar.urls.UURL;
import com.land.simcpux.Constants;
import com.land.simcpux.MD5;
import com.land.simcpux.Util;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;



public class WxPayActivity {
    private static final String TAG = "MicroMsg.SDKSample.PayActivity";
    private WxBean bean;
    private String good_id;
    private String good_price;
    PayReq req;
    IWXAPI msgApi;
    Map<String, String> resultunifiedorder;
    StringBuffer sb;
    Context context;

    public WxPayActivity(Context context) {
        this.context = context;
        msgApi = WXAPIFactory.createWXAPI(context, null);
        req = new PayReq();
        sb = new StringBuffer();
        msgApi.registerApp(Constants.APP_ID);
        // 预付订单
        // GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
        // getPrepayId.execute();
        // genPayReq();
        GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
        getPrepayId.execute();
    }

    //7a81780c8220054e11d4ec746d92a14a
    public WxPayActivity(Context context, WxBean bean) {
        this.context = context;
        this.bean = bean;
        msgApi = WXAPIFactory.createWXAPI(context, null);
        req = new PayReq();
        sb = new StringBuffer();
        msgApi.registerApp(Constants.APP_ID);
//        genPayReqs(bean);
        GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
        getPrepayId.execute();
    }

    public WxPayActivity(Context context, String good_id, String good_price) {
        this.good_id = good_id;
        this.good_price = good_price;
        this.context = context;
        msgApi = WXAPIFactory.createWXAPI(context, null);
        req = new PayReq();
        sb = new StringBuffer();
        msgApi.registerApp(Constants.APP_ID);
        // 预付订单
        // GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
        // getPrepayId.execute();
        // genPayReq();
//		genPayReqs(bean);
        GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
        getPrepayId.execute();
    }

    /**
     * 生成签名
     */

    @SuppressLint("DefaultLocale")
    private String genPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);


        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion", packageSign);
        return packageSign;
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);
        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion", appSign);
        return appSign;
    }

    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");
            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");

        Log.e("orion", sb.toString());
        return sb.toString();
    }

    private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            //dialog = ProgressDialog.show(PayActivity.this, getString(R.string.app_tip), getString(R.string.getting_prepayid));
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            if (dialog != null) {
                dialog.dismiss();
            }
            sb.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
            //show.setText(sb.toString());

            resultunifiedorder = result;
            if (resultunifiedorder != null) {
                genPayReqs(bean);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Map<String, String> doInBackground(Void... params) {
            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
            String entity = genProductArgs();
            Log.e("orion", entity);

            byte[] buf = Util.httpPost(url, bean.getData().getWxpay().getPartnerid());

            String content = new String(buf);
            Log.e("orion", content);
            Map<String, String> xml = decodeXml(content);

            return xml;
        }
    }


    public Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            //实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("orion", e.toString());
        }
        return null;

    }


    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }


    private String genOutTradNo() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }


    //
    private String genProductArgs() {
        String body = "超级棒考研";
        StringBuffer xml = new StringBuffer();

        try {
            String nonceStr = genNonceStr();
            xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid", bean.getData().getWxpay().getAppid()));
            packageParams.add(new BasicNameValuePair("body", body));
            packageParams.add(new BasicNameValuePair("mch_id", bean.getData().getWxpay().getPartnerid()));
            packageParams.add(new BasicNameValuePair("nonce_str", bean.getData().getWxpay().getNoncestr()));
            packageParams.add(new BasicNameValuePair("notify_url", "http://cjibang.com/data/api/zzkwx/example/notify.php"));
            packageParams.add(new BasicNameValuePair("out_trade_no", UURL.jiaqia_id));
            packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));
            packageParams.add(new BasicNameValuePair("total_fee", UURL.jiaqia + ""));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));
            String sign = genPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));

            String xmlstring = toXml(packageParams);
            return new String(xmlstring.toString().getBytes(), "ISO8859-1");
            //return xmlstring;

        } catch (Exception e) {
            return null;
        }


    }

    private void genPayReqs(WxBean bean) {
        Log.e("TAGddd", "" + bean.getData().getWxpay().getSign());
        req.appId = bean.getData().getWxpay().getAppid();
        req.partnerId = bean.getData().getWxpay().getPartnerid();
        req.prepayId = bean.getData().getWxpay().getPrepayid();
        req.packageValue = "Sign=WXPay";
        req.nonceStr = bean.getData().getWxpay().getNoncestr();
        req.timeStamp = String.valueOf(bean.getData().getWxpay().getTimestamp());
        //req.timeStamp=PayActivity.posttime;
//        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
//        signParams.add(new BasicNameValuePair("appid", req.appId));
//        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
//        signParams.add(new BasicNameValuePair("package", req.packageValue));
//        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
//        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
//        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

//        req.sign = genAppSign(signParams);
        req.sign = bean.getData().getWxpay().getSign();

        sb.append("sign\n" + req.sign + "\n\n");


        sendPayReq();


    }

    private void genPayReq() {
        req.appId = Constants.APP_ID;
        req.partnerId = Constants.MCH_ID;
        req.prepayId = resultunifiedorder.get("prepay_id");
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());
        //req.timeStamp=PayActivity.posttime;
        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);

        sb.append("sign\n" + req.sign + "\n\n");

        //show.setText(sb.toString());

        Log.e("orion", signParams.toString());
        sendPayReq();

    }

    private void sendPayReq() {
        Log.e("TAGddd", "" + bean.getData().getWxpay().getAppid());
        msgApi.registerApp(bean.getData().getWxpay().getAppid());
        msgApi.sendReq(req);
    }

}