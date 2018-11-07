package com.land;

import java.util.List;

/**
 * Created by CH
 * on 2018/11/6 16:15
 */
public class TestBean2 {

    /**
     * data : {"Info":[]}
     * info : {"elt":{"edianya":0,"etaiya":0,"etaiwen":0,"etirestate":0},"ert":{"edianya":0,"etaiya":0,"etaiwen":0,"etirestate":0},"elb":{"edianya":0,"etaiya":0,"etaiwen":0,"etirestate":0},"erb":{"edianya":0,"etaiya":0,"etaiwen":0,"etirestate":0}}
     * code : 0
     */

    private DataBean data;
    private InfoBean info;
    private String code;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
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

    public static class InfoBean {
        /**
         * elt : {"edianya":0,"etaiya":0,"etaiwen":0,"etirestate":0}
         * ert : {"edianya":0,"etaiya":0,"etaiwen":0,"etirestate":0}
         * elb : {"edianya":0,"etaiya":0,"etaiwen":0,"etirestate":0}
         * erb : {"edianya":0,"etaiya":0,"etaiwen":0,"etirestate":0}
         */

        private EltBean elt;
        private ErtBean ert;
        private ElbBean elb;
        private ErbBean erb;

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
