package com.land.netcar.min.bean;

import java.util.List;

/**
 * Created by VULCAN on 2018/3/7.
 */

public class BianBean {


    /**
     * data : {"Info":[{"id":"5","deviceid":"19883046","title":"0008","ismo":"0"},{"id":"4","deviceid":"19914594","title":"我的设备","ismo":"0"}],"page":1,"page_count":1}
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
         * Info : [{"id":"5","deviceid":"19883046","title":"0008","ismo":"0"},{"id":"4","deviceid":"19914594","title":"我的设备","ismo":"0"}]
         * page : 1
         * page_count : 1
         */

        private int page;
        private int page_count;
        private List<InfoBean> Info;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPage_count() {
            return page_count;
        }

        public void setPage_count(int page_count) {
            this.page_count = page_count;
        }

        public List<InfoBean> getInfo() {
            return Info;
        }

        public void setInfo(List<InfoBean> Info) {
            this.Info = Info;
        }

        public static class InfoBean {
            /**
             * id : 5
             * deviceid : 19883046
             * title : 0008
             * ismo : 0
             */

            private String id;
            private String deviceid;
            private String title;
            private String ismo;

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getIsmo() {
                return ismo;
            }

            public void setIsmo(String ismo) {
                this.ismo = ismo;
            }
        }
    }
}
