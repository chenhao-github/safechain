package com.code.safechain.ui.transaction.bean;

import java.util.List;

/**
 * @Auther: hchen
 * @Date: 2020/9/9 0009
 * @Description:
 */
public class OrderDetailRsBean {

    /**
     * error : 0
     * message : success
     * result : {"ctime":"2020-09-09 09:10:45","fk_store":37,"fk_user":11,"num":"20.000000000000000000","order_no":"200909426456408","pay_type":0,"pays":[{"bank_name":"","bank_no":"","img_url":"http://www.safe-chain.io/upload/200831/vphg6ZttQZZtJKMLUuRbS80JDsW9jDVKF9IIiI9C.jpeg","pay_type":1,"user_name":"111"},{"bank_name":"","bank_no":"","img_url":"http://www.safe-chain.io/upload/200909/hYl8kIdHlIU0gGEpWteXNFaJMfSAQTcQesTNv7V8.jpeg","pay_type":2,"user_name":"222"},{"bank_name":"中国银行,清河支行","bank_no":"333","img_url":"","pay_type":4,"user_name":"香山"}],"pk_order":66,"price":"0.03","remark":"","state":1,"store_info":{"ctime":"2020-09-09 09:10:24","fk_token":4,"fk_user":11,"max":10,"min":1,"num":"20.000000000000000000","pay_type":1,"pk_store":37,"price":"0.00","price_float":-2,"remark":"555","state":1,"type":2,"utime":"2020-09-09 09:10:24"},"total":"2.00","utime":"2020-09-09 09:10:45"}
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
        /**
         * ctime : 2020-09-09 09:10:45
         * fk_store : 37
         * fk_user : 11
         * num : 20.000000000000000000
         * order_no : 200909426456408
         * pay_type : 0
         * pays : [{"bank_name":"","bank_no":"","img_url":"http://www.safe-chain.io/upload/200831/vphg6ZttQZZtJKMLUuRbS80JDsW9jDVKF9IIiI9C.jpeg","pay_type":1,"user_name":"111"},{"bank_name":"","bank_no":"","img_url":"http://www.safe-chain.io/upload/200909/hYl8kIdHlIU0gGEpWteXNFaJMfSAQTcQesTNv7V8.jpeg","pay_type":2,"user_name":"222"},{"bank_name":"中国银行,清河支行","bank_no":"333","img_url":"","pay_type":4,"user_name":"香山"}]
         * pk_order : 66
         * price : 0.03
         * remark :
         * state : 1
         * store_info : {"ctime":"2020-09-09 09:10:24","fk_token":4,"fk_user":11,"max":10,"min":1,"num":"20.000000000000000000","pay_type":1,"pk_store":37,"price":"0.00","price_float":-2,"remark":"555","state":1,"type":2,"utime":"2020-09-09 09:10:24"}
         * total : 2.00
         * utime : 2020-09-09 09:10:45
         */

        private String ctime;
        private int fk_store;
        private int fk_user;
        private String num;
        private String order_no;
        private int pay_type;
        private int pk_order;
        private String price;
        private String remark;
        private int state;
        private StoreInfoBean store_info;
        private long timedown;
        private String total;
        private String utime;
        private String nickname;
        private List<PaysBean> pays;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public long getTimedown() {
            return timedown;
        }

        public void setTimedown(long timedown) {
            this.timedown = timedown;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public int getFk_store() {
            return fk_store;
        }

        public void setFk_store(int fk_store) {
            this.fk_store = fk_store;
        }

        public int getFk_user() {
            return fk_user;
        }

        public void setFk_user(int fk_user) {
            this.fk_user = fk_user;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

        public int getPk_order() {
            return pk_order;
        }

        public void setPk_order(int pk_order) {
            this.pk_order = pk_order;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public StoreInfoBean getStore_info() {
            return store_info;
        }

        public void setStore_info(StoreInfoBean store_info) {
            this.store_info = store_info;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getUtime() {
            return utime;
        }

        public void setUtime(String utime) {
            this.utime = utime;
        }

        public List<PaysBean> getPays() {
            return pays;
        }

        public void setPays(List<PaysBean> pays) {
            this.pays = pays;
        }

        public static class StoreInfoBean {
            /**
             * ctime : 2020-09-09 09:10:24
             * fk_token : 4
             * fk_user : 11
             * max : 10
             * min : 1
             * num : 20.000000000000000000
             * pay_type : 1
             * pk_store : 37
             * price : 0.00
             * price_float : -2
             * remark : 555
             * state : 1
             * type : 2
             * utime : 2020-09-09 09:10:24
             */

            private String ctime;
            private int fk_token;
            private int fk_user;
            private int max;
            private int min;
            private String num;
            private int pay_type;
            private int pk_store;
            private String price;
            private int price_float;
            private String remark;
            private String sailer_name;
            private int state;
            private int type;
            private String utime;

            public String getSailer_name() {
                return sailer_name;
            }

            public void setSailer_name(String sailer_name) {
                this.sailer_name = sailer_name;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public int getFk_token() {
                return fk_token;
            }

            public void setFk_token(int fk_token) {
                this.fk_token = fk_token;
            }

            public int getFk_user() {
                return fk_user;
            }

            public void setFk_user(int fk_user) {
                this.fk_user = fk_user;
            }

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public int getPay_type() {
                return pay_type;
            }

            public void setPay_type(int pay_type) {
                this.pay_type = pay_type;
            }

            public int getPk_store() {
                return pk_store;
            }

            public void setPk_store(int pk_store) {
                this.pk_store = pk_store;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getPrice_float() {
                return price_float;
            }

            public void setPrice_float(int price_float) {
                this.price_float = price_float;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUtime() {
                return utime;
            }

            public void setUtime(String utime) {
                this.utime = utime;
            }
        }

        public static class PaysBean {
            /**
             * bank_name :
             * bank_no :
             * img_url : http://www.safe-chain.io/upload/200831/vphg6ZttQZZtJKMLUuRbS80JDsW9jDVKF9IIiI9C.jpeg
             * pay_type : 1
             * user_name : 111
             */

            private String bank_name;
            private String bank_no;
            private String img_url;
            private int pay_type;
            private String user_name;
            private String pay_addr;

            public String getPay_addr() {
                return pay_addr;
            }

            public void setPay_addr(String pay_addr) {
                this.pay_addr = pay_addr;
            }

            public String getBank_name() {
                return bank_name;
            }

            public void setBank_name(String bank_name) {
                this.bank_name = bank_name;
            }

            public String getBank_no() {
                return bank_no;
            }

            public void setBank_no(String bank_no) {
                this.bank_no = bank_no;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }

            public int getPay_type() {
                return pay_type;
            }

            public void setPay_type(int pay_type) {
                this.pay_type = pay_type;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }
        }
    }
}
