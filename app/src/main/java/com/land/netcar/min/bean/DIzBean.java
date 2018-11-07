package com.land.netcar.min.bean;

import java.util.List;

/**
 * Created by VULCAN on 2018/3/14.
 */

public class DIzBean {

    /**
     * data : {"Info":[{"id":"25","deviceid":"23246502","elng":18886736,"elat":1614811216}]}
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
        private List<InfoBean> Info;

        public List<InfoBean> getInfo() {
            return Info;
        }

        public void setInfo(List<InfoBean> Info) {
            this.Info = Info;
        }

        public static class InfoBean {
            /**
             * id : 25
             * deviceid : 23246502
             * elng : 18886736
             * elat : 1614811216
             */

            private String id;
            private String deviceid;
            private int elng;
            private int elat;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDeviceid() {
                return deviceid;
            }

            public void setDeviceid(String deviceid) {
                this.deviceid = deviceid;
            }

            public int getElng() {
                return elng;
            }

            public void setElng(int elng) {
                this.elng = elng;
            }

            public int getElat() {
                return elat;
            }

            public void setElat(int elat) {
                this.elat = elat;
            }
        }
    }
}
