package com.code.safechain.ui.wallet.bean;

/**
 * @Auther: hchen
 * @Date: 2020/8/1 0001
 * @Description:
 */
public class TransferRsBean {

    /**
     * error : 0
     * message : success
     * result : {"state":true}
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
         * state : true
         */

        private boolean state;

        public boolean isState() {
            return state;
        }

        public void setState(boolean state) {
            this.state = state;
        }
    }
}
