package com.code.safechain.ui.login.bean;

/**
 * @Auther: hchen
 * @Date: 2020/7/30 0030
 * @Description:
 */
public class RegistRsBean {

    /**
     * error : 0
     * message : success
     * result : {"token":"Y6KQszFQgnq5zBrWtNiIrw=="}
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
         * token : Y6KQszFQgnq5zBrWtNiIrw==
         */

        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
