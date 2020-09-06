package com.code.safechain.ui.transaction.bean;

import java.util.List;

/**
 * @Auther: hchen
 * @Date: 2020/9/1 0001
 * @Description: 1.17. 确认付款/收款/更新支付方式  返回值对象
 */
public class UpdateOrderRsBean {

    /**
     * error : 0
     * message : success
     * result : []
     */

    private int error;
    private String message;
//    private List<?> result;

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

//    public List<?> getResult() {
//        return result;
//    }
//
//    public void setResult(List<?> result) {
//        this.result = result;
//    }
}
