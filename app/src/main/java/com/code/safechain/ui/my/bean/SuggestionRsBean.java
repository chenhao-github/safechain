package com.code.safechain.ui.my.bean;

import java.util.List;

/**
 * @Auther: hchen
 * @Date: 2020/8/23 0023
 * @Description:
 */
public class SuggestionRsBean {

    /**
     * error : 0
     * message : success
     * result : []
     */

    private int error;
    private String message;
    private List<?> result;

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
