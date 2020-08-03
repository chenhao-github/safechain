package com.code.safechain.model.bean;

/**
 * @Auther: hchen
 * @Date: 2020/7/25 0025
 * @Description:
 */
public class OrderBean {
    private String chainName;
    private String  number;
    private String unitPrice;
    private String orderNo;
    private String status;
    private String volume;

    public OrderBean(String chainName, String number, String unitPrice, String orderNo, String status, String volume) {
        this.chainName = chainName;
        this.number = number;
        this.unitPrice = unitPrice;
        this.orderNo = orderNo;
        this.status = status;
        this.volume = volume;
    }

    public String getChainName() {
        return chainName;
    }

    public void setChainName(String chainName) {
        this.chainName = chainName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}
