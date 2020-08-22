package com.code.safechain.ui.main.bean;

/**
 * @Auther: hchen
 * @Date: 2020/8/18 0018
 * @Description:
 */
public class UserBean {
    /**
     * error : 0
     * message : success
     * result : {"pk_user":11,"phone":"15110259107","state":1,"avatar_url":"","nickname":"","nation":86,"has_paywd":0,"app_id":0,"card_b":"http://13.251.156.240/upload/200817/a6FTXzwI7FYiPf40EEnqIev1EjIWftoz2z66RUrj.jpeg","card_z":"http://13.251.156.240/upload/200817/fF3ZjXFwDKK0sYLmnZHmoPukO6xZnroHJ1EOMUYA.jpeg","card_id":"413024198308241010","true_name":"哦哦","email":null,"ctime":"2020-07-10 14:06:03","utime":"2020-08-17 11:47:58","paywd":"2458","type":1}
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
         * pk_user : 11
         * phone : 15110259107
         * state : 1
         * avatar_url :
         * nickname :
         * nation : 86
         * has_paywd : 0
         * app_id : 0
         * card_b : http://13.251.156.240/upload/200817/a6FTXzwI7FYiPf40EEnqIev1EjIWftoz2z66RUrj.jpeg
         * card_z : http://13.251.156.240/upload/200817/fF3ZjXFwDKK0sYLmnZHmoPukO6xZnroHJ1EOMUYA.jpeg
         * card_id : 413024198308241010
         * true_name : 哦哦
         * email : null
         * ctime : 2020-07-10 14:06:03
         * utime : 2020-08-17 11:47:58
         * paywd : 2458
         * type : 1
         */

        private int pk_user;
        private int total;
        private String phone;
        private int state;
        private String avatar_url;
        private String nickname;
        private int nation;
        private int has_paywd;
        private int app_id;
        private String card_b;
        private String card_z;
        private String card_id;
        private String true_name;
        private String email;
        private String ctime;
        private String utime;
        private String paywd;
        private int type;

        public int getPk_user() {
            return pk_user;
        }

        public void setPk_user(int pk_user) {
            this.pk_user = pk_user;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getNation() {
            return nation;
        }

        public void setNation(int nation) {
            this.nation = nation;
        }

        public int getHas_paywd() {
            return has_paywd;
        }

        public void setHas_paywd(int has_paywd) {
            this.has_paywd = has_paywd;
        }

        public int getApp_id() {
            return app_id;
        }

        public void setApp_id(int app_id) {
            this.app_id = app_id;
        }

        public String getCard_b() {
            return card_b;
        }

        public void setCard_b(String card_b) {
            this.card_b = card_b;
        }

        public String getCard_z() {
            return card_z;
        }

        public void setCard_z(String card_z) {
            this.card_z = card_z;
        }

        public String getCard_id() {
            return card_id;
        }

        public void setCard_id(String card_id) {
            this.card_id = card_id;
        }

        public String getTrue_name() {
            return true_name;
        }

        public void setTrue_name(String true_name) {
            this.true_name = true_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getUtime() {
            return utime;
        }

        public void setUtime(String utime) {
            this.utime = utime;
        }

        public String getPaywd() {
            return paywd;
        }

        public void setPaywd(String paywd) {
            this.paywd = paywd;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
