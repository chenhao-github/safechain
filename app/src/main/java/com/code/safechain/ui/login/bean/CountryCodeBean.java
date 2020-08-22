package com.code.safechain.ui.login.bean;

import java.util.List;

/**
 * @Auther: hchen
 * @Date: 2020/8/13 0013
 * @Description:
 */
public class CountryCodeBean {

    /**
     * error : 0
     * message : success
     * result : [{"cname":"安哥拉","ename":"Angola","tel_code":244}]
     */

    private int error;
    private String message;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * cname : 安哥拉
         * ename : Angola
         * tel_code : 244
         */

        private String cname;
        private String ename;
        private int tel_code;

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getEname() {
            return ename;
        }

        public void setEname(String ename) {
            this.ename = ename;
        }

        public int getTel_code() {
            return tel_code;
        }

        public void setTel_code(int tel_code) {
            this.tel_code = tel_code;
        }
    }
}
