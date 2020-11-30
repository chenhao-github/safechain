package com.code.safechain.ui.transaction.bean;

import java.util.List;

/**
 * @Auther: hchen
 * @Date: 2020/8/31 0031
 * @Description:
 */
public class GetPayTypeRsBean {

    /**
     * error : 0
     * message : success
     * result : {"pays":[{"pk_pay_type":35,"fk_user":11,"type":1,"user_name":"111","img_url":"http://www.safe-chain.io/upload/200830/s8Xs9Iyjh1RaNqwdS1UNkjVDavRt1zbTsPAOaL2C.jpeg","bank_id":0,"bank_name":"","bank_no":"","state":1,"ctime":"2020-08-30 15:18:49","utime":"2020-08-30 15:18:49"},{"pk_pay_type":36,"fk_user":11,"type":2,"user_name":"222","img_url":"http://www.safe-chain.io/upload/200830/lxlI0TO96MoNnmd0VLafEOq2a5TEbpnNdyyEgC6o.jpeg","bank_id":0,"bank_name":"","bank_no":"","state":1,"ctime":"2020-08-30 15:18:49","utime":"2020-08-30 15:18:49"},{"pk_pay_type":37,"fk_user":11,"type":4,"user_name":"333","img_url":"","bank_id":0,"bank_name":"中国银行,333","bank_no":"333","state":1,"ctime":"2020-08-30 15:18:49","utime":"2020-08-30 15:18:49"}]}
     */

    private int error;
    private String message;
    private ResultBean result;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<PaysBean> pays;

        public List<PaysBean> getPays() {
            return pays;
        }

        public void setPays(List<PaysBean> pays) {
            this.pays = pays;
        }

        public static class PaysBean {
            /**
             * pk_pay_type : 35
             * fk_user : 11
             * type : 1
             * user_name : 111
             * img_url : http://www.safe-chain.io/upload/200830/s8Xs9Iyjh1RaNqwdS1UNkjVDavRt1zbTsPAOaL2C.jpeg
             * bank_id : 0
             * bank_name :
             * bank_no :
             * state : 1
             * ctime : 2020-08-30 15:18:49
             * utime : 2020-08-30 15:18:49
             */

            private int pk_pay_type;
            private int fk_user;
            private int type;
            private String user_name;
            private String img_url;
            private int bank_id;
            private String bank_name;
            private String pay_addr;
            private String bank_no;
            private int state;
            private String ctime;
            private String utime;

            public int getPk_pay_type() {
                return pk_pay_type;
            }

            public void setPk_pay_type(int pk_pay_type) {
                this.pk_pay_type = pk_pay_type;
            }

            public int getFk_user() {
                return fk_user;
            }

            public void setFk_user(int fk_user) {
                this.fk_user = fk_user;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public int getBank_id() {
                return bank_id;
            }

            public void setBank_id(int bank_id) {
                this.bank_id = bank_id;
            }

            public String getBank_name() {
                return bank_name;
            }

            public void setBank_name(String bank_name) {
                this.bank_name = bank_name;
            }

            public String getPay_addr() { return pay_addr; }

            public void setPay_addr(String pay_addr) { this.pay_addr = pay_addr; }

            public String getBank_no() {
                return bank_no;
            }

            public void setBank_no(String bank_no) {
                this.bank_no = bank_no;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public String getUtime() {
                return utime;
            }

            public void setUtime(String utime) {
                this.utime = utime;
            }
        }
    }
}
