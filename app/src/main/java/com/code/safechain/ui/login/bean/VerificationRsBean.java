package com.code.safechain.ui.login.bean;

/**
 * @Auther: hchen
 * @Date: 2020/7/29 0029
 * @Description:
 */
public class VerificationRsBean {
    private String error;
    private String message;

    public VerificationRsBean(String error, String message) {
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "VerificationRsBean{" +
                "error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
