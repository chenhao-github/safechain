package com.code.safechain.ui.transaction.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hchen
 * @Date: 2020/8/9 0009
 * @Description:
 */
public class OrderRsBean {

    /**
     * error : 0
     * message : success
     * result : [{"pk_order":11,"fk_user":11,"order_no":"200807133982634","fk_store":16,"pay_type":2,"state":1,"num":"2.000000000000000000","price":"0.03","total":"0.03","remark":"","ctime":"2020-08-07 15:16:38","utime":"2020-08-07 15:16:38"},{"pk_order":10,"fk_user":11,"order_no":"200807132413051","fk_store":16,"pay_type":2,"state":1,"num":"2.000000000000000000","price":"0.03","total":"0.03","remark":"","ctime":"2020-08-07 15:14:01","utime":"2020-08-07 15:14:01"},{"pk_order":9,"fk_user":11,"order_no":"200807121045591","fk_store":16,"pay_type":2,"state":1,"num":"2.000000000000000000","price":"0.03","total":"0.03","remark":"","ctime":"2020-08-07 14:55:04","utime":"2020-08-07 14:55:04"},{"pk_order":8,"fk_user":11,"order_no":"200805203375849","fk_store":15,"pay_type":4,"state":1,"num":"2.000000000000000000","price":"0.03","total":"12.00","remark":"","ctime":"2020-08-05 09:38:57","utime":"2020-08-05 09:38:57"},{"pk_order":7,"fk_user":11,"order_no":"200805203281209","fk_store":16,"pay_type":2,"state":1,"num":"2.000000000000000000","price":"0.03","total":"12.00","remark":"","ctime":"2020-08-05 09:38:48","utime":"2020-08-05 09:38:48"},{"pk_order":6,"fk_user":11,"order_no":"200805201404130","fk_store":16,"pay_type":1,"state":1,"num":"2.000000000000000000","price":"0.03","total":"16.00","remark":"","ctime":"2020-08-05 09:35:40","utime":"2020-08-05 09:35:40"},{"pk_order":5,"fk_user":11,"order_no":"200805201207184","fk_store":16,"pay_type":1,"state":1,"num":"2.000000000000000000","price":"0.03","total":"12.00","remark":"","ctime":"2020-08-05 09:35:20","utime":"2020-08-05 09:35:20"},{"pk_order":4,"fk_user":11,"order_no":"200805185658317","fk_store":16,"pay_type":1,"state":1,"num":"2.000000000000000000","price":"0.03","total":"12.00","remark":"","ctime":"2020-08-05 09:09:25","utime":"2020-08-05 09:09:25"},{"pk_order":3,"fk_user":11,"order_no":"200731791689193","fk_store":8,"pay_type":1,"state":1,"num":"2.000000000000000000","price":"0.03","total":"12.00","remark":"","ctime":"2020-07-31 07:06:08","utime":"2020-07-31 07:06:08"},{"pk_order":2,"fk_user":11,"order_no":"200730725695777","fk_store":6,"pay_type":1,"state":1,"num":"93.000000000000000000","price":"0.03","total":"12.00","remark":"","ctime":"2020-07-30 01:29:29","utime":"2020-07-30 01:29:29"},{"pk_order":1,"fk_user":11,"order_no":"200730722687169","fk_store":6,"pay_type":1,"state":1,"num":"93.000000000000000000","price":"0.03","total":"10.00","remark":"","ctime":"2020-07-30 01:24:28","utime":"2020-07-30 01:24:28"}]
     */

    private int error;
    private String message;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        /**
         * pk_order : 11
         * fk_user : 11
         * order_no : 200807133982634
         * fk_store : 16
         * pay_type : 2
         * state : 1
         * num : 2.000000000000000000
         * price : 0.03
         * total : 0.03
         * remark :
         * ctime : 2020-08-07 15:16:38
         * utime : 2020-08-07 15:16:38
         */

        private int pk_order;
        private int fk_user;
        private String order_no;
        private int fk_store;
        private int pay_type;
        private int state;
        private String num;
        private String price;
        private String total;
        private String remark;
        private String ctime;
        private String utime;

        public int getPk_order() {
            return pk_order;
        }

        public void setPk_order(int pk_order) {
            this.pk_order = pk_order;
        }

        public int getFk_user() {
            return fk_user;
        }

        public void setFk_user(int fk_user) {
            this.fk_user = fk_user;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public int getFk_store() {
            return fk_store;
        }

        public void setFk_store(int fk_store) {
            this.fk_store = fk_store;
        }

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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
