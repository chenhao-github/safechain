package com.code.safechain.ui.transaction.bean;

/**
 * @Auther: hchen
 * @Date: 2020/8/7 0007
 * @Description:
 */
public class TransactionBuyRsBean {

    /**
     * error : 0
     * message : success
     * result : {"order_id":9,"order_no":"200807121045591"}
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
         * order_id : 9
         * order_no : 200807121045591
         */

        private int order_id;
        private String order_no;

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }
    }
}
