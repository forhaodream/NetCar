package com.land.netcar.min.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by VULCAN on 2018/3/13.
 */

public class WxBean {


    /**data  :{"wxpay":{"appid":null,"partnerid":null,"prepayid":null,"package":"Sign=WXPay","noncestr":null,"timestamp":1521769403,"sign":"2636C8EDB22BA48F7E4C91019EECF0F5"}},"info":"\u652f\u4ed8\u6210\u529f","code":"0"}
     * data : {"wxpay":{"appid":"wxa39fb65b89dcc7c8","partnerid":"1498847982","prepayid":"wx20180212124356c42b3509000099871069","package":"Sign=WXPay","noncestr":"VoSPhwvWwiPVg7Bb","timestamp":1518410636,"sign":"394C42443839AC5B42631C19603D25D5"}}
     * info : 支付成功
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
         * wxpay : {"appid":"wxa39fb65b89dcc7c8","partnerid":"1498847982","prepayid":"wx20180212124356c42b3509000099871069","package":"Sign=WXPay","noncestr":"VoSPhwvWwiPVg7Bb","timestamp":1518410636,"sign":"394C42443839AC5B42631C19603D25D5"}
         */

        private WxpayBean wxpay;

        public WxpayBean getWxpay() {
            return wxpay;
        }

        public void setWxpay(WxpayBean wxpay) {
            this.wxpay = wxpay;
        }

        public static class WxpayBean {
            /**
             * appid : wxa39fb65b89dcc7c8
             * partnerid : 1498847982
             * prepayid : wx20180212124356c42b3509000099871069
             * package : Sign=WXPay
             * noncestr : VoSPhwvWwiPVg7Bb
             * timestamp : 1518410636
             * sign : 394C42443839AC5B42631C19603D25D5
             */

            private String appid;
            private String partnerid;
            private String prepayid;
            @SerializedName("package")
            private String packageX;
            private String noncestr;
            private int timestamp;
            private String sign;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public int getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(int timestamp) {
                this.timestamp = timestamp;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }
        }
    }
}
