package com.code.safechain.ui.my.bean;

/**
 * @Auther: hchen
 * @Date: 2020/9/19 0019
 * @Description:
 */
public class CheckVerifiCodeRs {
    /**
     * error : 0
     * message : success
     * result : {"check_state":true}
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
         * check_state : true
         */

        private boolean check_state;

        public boolean isCheck_state() {
            return check_state;
        }

        public void setCheck_state(boolean check_state) {
            this.check_state = check_state;
        }
    }
}
