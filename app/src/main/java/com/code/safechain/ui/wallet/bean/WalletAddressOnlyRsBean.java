package com.code.safechain.ui.wallet.bean;

/**
 * @Auther: hchen
 * @Date: 2020/8/19 0019
 * @Description:
 */
public class WalletAddressOnlyRsBean {

    /**
     * error : 0
     * message : success
     * result : {"token_addr":"0x15972fe63053d6924faf9fb035efcc527462b372"}
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
         * token_addr : 0x15972fe63053d6924faf9fb035efcc527462b372
         */

        private String token_addr;

        public String getToken_addr() {
            return token_addr;
        }

        public void setToken_addr(String token_addr) {
            this.token_addr = token_addr;
        }
    }
}
