package com.code.safechain.ui.wallet.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hchen
 * @Date: 2020/8/1 0001
 * @Description:
 */
public class WalletAddressRsBean {

    /**
     * error : 0
     * message : success
     * result : {"data":[{"addr":"123123","fk_token":4,"fk_user":11,"name":"ETC","pk_user_addr":3,"remark":"ETC"},{"addr":"0X1233","fk_token":149,"fk_user":11,"name":"USDT","pk_user_addr":4,"remark":"USDT"},{"addr":"12312","fk_token":156,"fk_user":11,"name":"ETH","pk_user_addr":5,"remark":"ETH"}]}
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
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean implements Serializable {
            /**
             * addr : 123123
             * fk_token : 4
             * fk_user : 11
             * name : ETC
             * pk_user_addr : 3
             * remark : ETC
             */

            private String addr;
            private int fk_token;
            private int fk_user;
            private String name;
            private int pk_user_addr;
            private String remark;
            boolean isCheck;

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }

            public String getAddr() {
                return addr;
            }

            public void setAddr(String addr) {
                this.addr = addr;
            }

            public int getFk_token() {
                return fk_token;
            }

            public void setFk_token(int fk_token) {
                this.fk_token = fk_token;
            }

            public int getFk_user() {
                return fk_user;
            }

            public void setFk_user(int fk_user) {
                this.fk_user = fk_user;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPk_user_addr() {
                return pk_user_addr;
            }

            public void setPk_user_addr(int pk_user_addr) {
                this.pk_user_addr = pk_user_addr;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
        }
    }
}
