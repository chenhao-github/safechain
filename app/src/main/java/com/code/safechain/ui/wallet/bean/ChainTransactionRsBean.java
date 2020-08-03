package com.code.safechain.ui.wallet.bean;

import java.util.List;

/**
 * @Auther: hchen
 * @Date: 2020/7/14 0014
 * @Description:
 */
public class ChainTransactionRsBean {

    /**
     * error : 0
     * message : success
     * result : {"total":"100000.000000000000000000","data":[{"amount":"7.000000000000000000","ctime":"2020-07-19 01:38:44","type":2,"status":2}]}
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
         * total : 100000.000000000000000000
         * data : [{"amount":"7.000000000000000000","ctime":"2020-07-19 01:38:44","type":2,"status":2}]
         */

        private String total;
        private List<DataBean> data;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * amount : 7.000000000000000000
             * ctime : 2020-07-19 01:38:44
             * type : 2
             * status : 2
             */

            private String amount;
            private String ctime;
            private int type;
            private int status;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getCtime() {
                return ctime;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
