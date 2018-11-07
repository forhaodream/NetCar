package com.land.netcar.min.bean;

import java.util.List;

/**
 * Created by VULCAN on 2018/3/2.
 */

public class PointOutBean {


    /**
     * data : {"Info":[]}
     * info : 操作失败
     * code : -105
     * color : black
     */

    private DataBean data;
    private String info;
    private String code;
    private String color;

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static class DataBean {
        private List<?> Info;

        public List<?> getInfo() {
            return Info;
        }

        public void setInfo(List<?> Info) {
            this.Info = Info;
        }
    }
}
