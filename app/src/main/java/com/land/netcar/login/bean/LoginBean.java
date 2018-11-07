package com.land.netcar.login.bean;

import java.util.List;

/**
 * Created by VULCAN on 2018/2/27.
 */

public class LoginBean {

    /**
     * data : {"Info":[{"uid":"5","token":"9535cd24756b3a408202eb695ff96f93","username":"13166753735","amount":"0.00","nickname":"","sex":"","birth":"-1","address":"","intro":"","mobile":"13166753735","qq":"","weixin":""}]}
     * info : 登录成功
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
        private List<InfoBean> Info;

        public List<InfoBean> getInfo() {
            return Info;
        }

        public void setInfo(List<InfoBean> Info) {
            this.Info = Info;
        }

        public static class InfoBean {
            /**
             * uid : 5
             * token : 9535cd24756b3a408202eb695ff96f93
             * username : 13166753735
             * amount : 0.00
             * nickname :
             * sex :
             * birth : -1
             * address :
             * intro :
             * mobile : 13166753735
             * qq :
             * weixin :
             */

            private String uid;
            private String token;
            private String username;
            private String amount;
            private String nickname;
            private String sex;
            private String birth;
            private String address;
            private String intro;
            private String mobile;
            private String qq;
            private String weixin;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getBirth() {
                return birth;
            }

            public void setBirth(String birth) {
                this.birth = birth;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getWeixin() {
                return weixin;
            }

            public void setWeixin(String weixin) {
                this.weixin = weixin;
            }
        }
    }
}
