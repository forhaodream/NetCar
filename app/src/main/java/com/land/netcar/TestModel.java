package com.land.netcar;

/**
 * Created by CH
 * on 2018/10/31 17:26
 */
public class TestModel {

    /**
     * data : {"Info":35}
     * info : 查询成功
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
         * Info : 35
         */

        private int Info;

        public int getInfo() {
            return Info;
        }

        public void setInfo(int Info) {
            this.Info = Info;
        }
    }
}
