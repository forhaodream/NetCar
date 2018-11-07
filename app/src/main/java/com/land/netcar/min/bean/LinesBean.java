package com.land.netcar.min.bean;

import java.util.List;

/**
 * Created by VULCAN on 2018/3/8.
 */

public class LinesBean {


    /**
     * data : {"Info":[{"id":"216","name":"右手拇指","regtime":"2018-09-21 02:44:52"}]}
     * info : 获取成功
     * code : 0
     * deviceid : 35674714
     */

    private DataBean data;
    private String info;
    private String code;
    private String deviceid;

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

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
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
             * id : 216
             * name : 右手拇指
             * regtime : 2018-09-21 02:44:52
             */

            private String id;
            private String name;
            private String regtime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRegtime() {
                return regtime;
            }

            public void setRegtime(String regtime) {
                this.regtime = regtime;
            }
        }
    }
}
