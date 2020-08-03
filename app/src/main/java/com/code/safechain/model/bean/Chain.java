package com.code.safechain.model.bean;

import java.io.Serializable;

/**
 * @Auther: hchen
 * @Date: 2020/7/11 0011
 * @Description:
 */
public class Chain implements Serializable {
    private int img;
    private String name;
    private String money1;
    private String money2;
    private String address;
    boolean isCheck;

    public Chain(int img, String name, String money1, String money2, String address) {
        this.img = img;
        this.name = name;
        this.money1 = money1;
        this.money2 = money2;
        this.address = address;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney1() {
        return money1;
    }

    public void setMoney1(String money1) {
        this.money1 = money1;
    }

    public String getMoney2() {
        return money2;
    }

    public void setMoney2(String money2) {
        this.money2 = money2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public String toString() {
        return "Chain{" +
                "img=" + img +
                ", name='" + name + '\'' +
                ", money1='" + money1 + '\'' +
                ", money2='" + money2 + '\'' +
                ", address='" + address + '\'' +
                ", isCheck=" + isCheck +
                '}';
    }
}
