package com.code.safechain.ui.transaction.bean;

import java.util.List;

/**
 * @Auther: hchen
 * @Date: 2020/10/26 0026
 * @Description:
 */
public class WithDrawRsBean {

    /**
     * status : success
     * error : 0
     * message : success
     * result : []
     */

    private String status;
    private int error;
    private String message;
    private List<?> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public List<?> getResult() {
        return result;
    }

    public void setResult(List<?> result) {
        this.result = result;
    }
}
