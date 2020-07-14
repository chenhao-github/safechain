package com.code.safechain.model.bean;

/**
 * @Auther: hchen
 * @Date: 2020/7/14 0014
 * @Description:
 */
public class ChainTransaction {
    private String price;
    private int type;
    private String time;

    public ChainTransaction(String price, int type, String time) {
        this.price = price;
        this.type = type;
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
