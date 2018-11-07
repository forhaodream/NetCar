package com.land;

import java.util.List;

/**
 * Created by CH
 * on 2018/11/6 15:48
 */
public class TestBean {

    /**
     * data : {"Info":[]}
     * info : 111
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
        private List<?> Info;

        public List<?> getInfo() {
            return Info;
        }

        public void setInfo(List<?> Info) {
            this.Info = Info;
        }
    }
}
