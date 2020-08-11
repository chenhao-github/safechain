package com.code.safechain.ui.transaction.bean;

/**
 * @Auther: hchen
 * @Date: 2020/8/8 0008
 * @Description:
 */
public class PayTypeRsBean {

    /**
     * error : 0
     * message : success
     * result : {"lastid":2}
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
         * lastid : 2
         */

        private int lastid;

        public int getLastid() {
            return lastid;
        }

        public void setLastid(int lastid) {
            this.lastid = lastid;
        }
    }
}
