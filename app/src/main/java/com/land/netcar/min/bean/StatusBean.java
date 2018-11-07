package com.land.netcar.min.bean;

import java.util.List;

/**
 * Created by VULCAN on 2018/2/28.
 */

public class StatusBean {


    /**
     * data : {"Info":[{"id":"65","deviceid":"35674714","ekongtiao":"0","etianchuan":"0","ejiareqi":"0","echechuang":"0","ezhedie":"0","emensuo":"0","edianhuo":"0","edianping":"49.00","elt":{"edianya":0,"etaiya":0,"etaiwen":0,"etirestate":0},"ert":{"edianya":0,"etaiya":0,"etaiwen":0,"etirestate":0},"elb":{"edianya":0,"etaiya":0,"etaiwen":0,"etirestate":0},"erb":{"edianya":0,"etaiya":0,"etaiwen":0,"etirestate":0},"dian":49}]}
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
        private List<InfoBean> Info;

        public List<InfoBean> getInfo() {
            return Info;
        }

        public void setInfo(List<InfoBean> Info) {
            this.Info = Info;
        }

        public static class InfoBean {
            /**
             * id : 65
             * deviceid : 35674714
             * ekongtiao : 0
             * etianchuan : 0
             * ejiareqi : 0
             * echechuang : 0
             * ezhedie : 0
             * emensuo : 0
             * edianhuo : 0
             * edianping : 49.00
             * elt : {"edianya":0,"etaiya":0,"etaiwen":0,"etirestate":0}
             * ert : {"edianya":0,"etaiya":0,"etaiwen":0,"etirestate":0}
             * elb : {"edianya":0,"etaiya":0,"etaiwen":0,"etirestate":0}
             * erb : {"edianya":0,"etaiya":0,"etaiwen":0,"etirestate":0}
             * dian : 49
             */

            private String id;
            private String deviceid;
            private String ekongtiao;
            private String etianchuan;
            private String ejiareqi;
            private String echechuang;
            private String ezhedie;
            private String emensuo;
            private String edianhuo;
            private String edianping;
            private EltBean elt;
            private ErtBean ert;
            private ElbBean elb;
            private ErbBean erb;
            private int dian;

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

            public String getEkongtiao() {
                return ekongtiao;
            }

            public void setEkongtiao(String ekongtiao) {
                this.ekongtiao = ekongtiao;
            }

            public String getEtianchuan() {
                return etianchuan;
            }

            public void setEtianchuan(String etianchuan) {
                this.etianchuan = etianchuan;
            }

            public String getEjiareqi() {
                return ejiareqi;
            }

            public void setEjiareqi(String ejiareqi) {
                this.ejiareqi = ejiareqi;
            }

            public String getEchechuang() {
                return echechuang;
            }

            public void setEchechuang(String echechuang) {
                this.echechuang = echechuang;
            }

            public String getEzhedie() {
                return ezhedie;
            }

            public void setEzhedie(String ezhedie) {
                this.ezhedie = ezhedie;
            }

            public String getEmensuo() {
                return emensuo;
            }

            public void setEmensuo(String emensuo) {
                this.emensuo = emensuo;
            }

            public String getEdianhuo() {
                return edianhuo;
            }

            public void setEdianhuo(String edianhuo) {
                this.edianhuo = edianhuo;
            }

            public String getEdianping() {
                return edianping;
            }

            public void setEdianping(String edianping) {
                this.edianping = edianping;
            }

            public EltBean getElt() {
                return elt;
            }

            public void setElt(EltBean elt) {
                this.elt = elt;
            }

            public ErtBean getErt() {
                return ert;
            }

            public void setErt(ErtBean ert) {
                this.ert = ert;
            }

            public ElbBean getElb() {
                return elb;
            }

            public void setElb(ElbBean elb) {
                this.elb = elb;
            }

            public ErbBean getErb() {
                return erb;
            }

            public void setErb(ErbBean erb) {
                this.erb = erb;
            }

            public int getDian() {
                return dian;
            }

            public void setDian(int dian) {
                this.dian = dian;
            }

            public static class EltBean {
                /**
                 * edianya : 0
                 * etaiya : 0
                 * etaiwen : 0
                 * etirestate : 0
                 */

                private int edianya;
                private int etaiya;
                private int etaiwen;
                private int etirestate;

                public int getEdianya() {
                    return edianya;
                }

                public void setEdianya(int edianya) {
                    this.edianya = edianya;
                }

                public int getEtaiya() {
                    return etaiya;
                }

                public void setEtaiya(int etaiya) {
                    this.etaiya = etaiya;
                }

                public int getEtaiwen() {
                    return etaiwen;
                }

                public void setEtaiwen(int etaiwen) {
                    this.etaiwen = etaiwen;
                }

                public int getEtirestate() {
                    return etirestate;
                }

                public void setEtirestate(int etirestate) {
                    this.etirestate = etirestate;
                }
            }

            public static class ErtBean {
                /**
                 * edianya : 0
                 * etaiya : 0
                 * etaiwen : 0
                 * etirestate : 0
                 */

                private int edianya;
                private int etaiya;
                private int etaiwen;
                private int etirestate;

                public int getEdianya() {
                    return edianya;
                }

                public void setEdianya(int edianya) {
                    this.edianya = edianya;
                }

                public int getEtaiya() {
                    return etaiya;
                }

                public void setEtaiya(int etaiya) {
                    this.etaiya = etaiya;
                }

                public int getEtaiwen() {
                    return etaiwen;
                }

                public void setEtaiwen(int etaiwen) {
                    this.etaiwen = etaiwen;
                }

                public int getEtirestate() {
                    return etirestate;
                }

                public void setEtirestate(int etirestate) {
                    this.etirestate = etirestate;
                }
            }

            public static class ElbBean {
                /**
                 * edianya : 0
                 * etaiya : 0
                 * etaiwen : 0
                 * etirestate : 0
                 */

                private int edianya;
                private int etaiya;
                private int etaiwen;
                private int etirestate;

                public int getEdianya() {
                    return edianya;
                }

                public void setEdianya(int edianya) {
                    this.edianya = edianya;
                }

                public int getEtaiya() {
                    return etaiya;
                }

                public void setEtaiya(int etaiya) {
                    this.etaiya = etaiya;
                }

                public int getEtaiwen() {
                    return etaiwen;
                }

                public void setEtaiwen(int etaiwen) {
                    this.etaiwen = etaiwen;
                }

                public int getEtirestate() {
                    return etirestate;
                }

                public void setEtirestate(int etirestate) {
                    this.etirestate = etirestate;
                }
            }

            public static class ErbBean {
                /**
                 * edianya : 0
                 * etaiya : 0
                 * etaiwen : 0
                 * etirestate : 0
                 */

                private int edianya;
                private int etaiya;
                private int etaiwen;
                private int etirestate;

                public int getEdianya() {
                    return edianya;
                }

                public void setEdianya(int edianya) {
                    this.edianya = edianya;
                }

                public int getEtaiya() {
                    return etaiya;
                }

                public void setEtaiya(int etaiya) {
                    this.etaiya = etaiya;
                }

                public int getEtaiwen() {
                    return etaiwen;
                }

                public void setEtaiwen(int etaiwen) {
                    this.etaiwen = etaiwen;
                }

                public int getEtirestate() {
                    return etirestate;
                }

                public void setEtirestate(int etirestate) {
                    this.etirestate = etirestate;
                }
            }
        }
    }
}
