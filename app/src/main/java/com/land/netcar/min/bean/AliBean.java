package com.land.netcar.min.bean;

/**
 * Created by VULCAN on 2018/3/13.
 */

public class AliBean {
    /**
     * data : {"pay":"alipay_sdk=alipay-sdk-php-20161101&app_id=2017120100293065&biz_content=%7B%22subject%22%3A%22%5Cu4e91%5Cu8f66%5Cu5145%5Cu503c%22%2C%22out_trade_no%22%3A%22CZ201803141049489251%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay¬ify_url=http%3A%2F%2Fzhinengcar.hxnzw.cn%2Fdata%2Fapi%2Fzzkali%2Fnotify_url.php&sign_type=RSA2×tamp=2018-03-14+10%3A49%3A49&version=1.0&sign=bUFEX0ck8ym8YjsytmYysh%2B7vx4DaR80tcITTHx105uAxZVGSAMW2Hhr7Tng%2BS6jvm%2FWfrrW2jh0%2BTFG3yS8WJuk4F3jRcxYzzzgR17Rb62mDjhmfn7EMj31Znr%2BzoiIrOs5Yv8W462DIEyIchv5Zj4nZkWgSdLBqhtqqNS32OOyKK34jpjxnzpJ090Qx%2FTLDTI64s%2FXEC6Mv50DI9LI82HV88Y4ahRsMhHH8KpIY10DnRBmB3XHpqTxd5kJjXFGoKszgTFPoZlKGnuIUjDVVnej9QEkMYnNJDlbQGZDSSGSQHzkwO0bqhKoFCK024D%2Bt8r410KsGRlDMW4IGqjRrQ%3D%3D"}
     * info : 获取成功
     * code : 0
     */

    private DataBean data;
    private String info;
    private String code;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * pay : alipay_sdk=alipay-sdk-php-20161101&app_id=2017120100293065&biz_content=%7B%22subject%22%3A%22%5Cu4e91%5Cu8f66%5Cu5145%5Cu503c%22%2C%22out_trade_no%22%3A%22CZ201803141049489251%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay¬ify_url=http%3A%2F%2Fzhinengcar.hxnzw.cn%2Fdata%2Fapi%2Fzzkali%2Fnotify_url.php&sign_type=RSA2×tamp=2018-03-14+10%3A49%3A49&version=1.0&sign=bUFEX0ck8ym8YjsytmYysh%2B7vx4DaR80tcITTHx105uAxZVGSAMW2Hhr7Tng%2BS6jvm%2FWfrrW2jh0%2BTFG3yS8WJuk4F3jRcxYzzzgR17Rb62mDjhmfn7EMj31Znr%2BzoiIrOs5Yv8W462DIEyIchv5Zj4nZkWgSdLBqhtqqNS32OOyKK34jpjxnzpJ090Qx%2FTLDTI64s%2FXEC6Mv50DI9LI82HV88Y4ahRsMhHH8KpIY10DnRBmB3XHpqTxd5kJjXFGoKszgTFPoZlKGnuIUjDVVnej9QEkMYnNJDlbQGZDSSGSQHzkwO0bqhKoFCK024D%2Bt8r410KsGRlDMW4IGqjRrQ%3D%3D
         */

        private String pay;

        public String getPay() {
            return pay;
        }

        public void setPay(String pay) {
            this.pay = pay;
        }
    }



}
