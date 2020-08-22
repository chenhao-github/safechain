package com.code.safechain.ui.wallet.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: hchen
 * @Date: 2020/8/1 0001
 * @Description:
 */
public class WalletHomeRsBean {

    /**
     * error : 0
     * message : success
     * result : {"data":[{"addr":"0x18d5d9536dbc9d65a66aa65afc19ddfde743ae7","logo_url":"http://13.251.156.240/upload/200724/VxBrJOQJXz51YHrgvY4ccSpdEVneWTveGl2cNBRT.png","num":"985.000000000000000000","sum":27.933854068562,"symbol":"SEC","token_id":4},{"logo_url":"http://13.251.156.240/upload/200723/9j3GxhQwd1Rq2889V7CHfHksv9CLiutIF0ipYDAN.png","num":0,"sum":0,"symbol":"USDT","token_id":149},{"logo_url":"http://13.251.156.240/upload/200723/YGeXQsFIOBFpPT4VisE18GvxYCBX1FfztYHlXrow.png","num":0,"sum":0,"symbol":"ETH","token_id":156},{"addr":"","logo_url":"http://13.251.156.240/upload/200723/YGeXQsFIOBFpPT4VisE18GvxYCBX1FfztYHlXrow.png","num":"888.000000000000000000","sum":6.943278216000001E7,"symbol":"BTC","token_id":160},{"logo_url":"http://13.251.156.240/upload/200723/YGeXQsFIOBFpPT4VisE18GvxYCBX1FfztYHlXrow.png","num":0,"sum":0,"symbol":"EID","token_id":271},{"logo_url":"http://13.251.156.240/upload/200723/9j3GxhQwd1Rq2889V7CHfHksv9CLiutIF0ipYDAN.png","num":0,"sum":0,"symbol":"USDT(ERC20)","token_id":1344}],"total":6.943281009385408E7}
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
         * data : [{"addr":"0x18d5d9536dbc9d65a66aa65afc19ddfde743ae7","logo_url":"http://13.251.156.240/upload/200724/VxBrJOQJXz51YHrgvY4ccSpdEVneWTveGl2cNBRT.png","num":"985.000000000000000000","sum":27.933854068562,"symbol":"SEC","token_id":4},{"logo_url":"http://13.251.156.240/upload/200723/9j3GxhQwd1Rq2889V7CHfHksv9CLiutIF0ipYDAN.png","num":0,"sum":0,"symbol":"USDT","token_id":149},{"logo_url":"http://13.251.156.240/upload/200723/YGeXQsFIOBFpPT4VisE18GvxYCBX1FfztYHlXrow.png","num":0,"sum":0,"symbol":"ETH","token_id":156},{"addr":"","logo_url":"http://13.251.156.240/upload/200723/YGeXQsFIOBFpPT4VisE18GvxYCBX1FfztYHlXrow.png","num":"888.000000000000000000","sum":6.943278216000001E7,"symbol":"BTC","token_id":160},{"logo_url":"http://13.251.156.240/upload/200723/YGeXQsFIOBFpPT4VisE18GvxYCBX1FfztYHlXrow.png","num":0,"sum":0,"symbol":"EID","token_id":271},{"logo_url":"http://13.251.156.240/upload/200723/9j3GxhQwd1Rq2889V7CHfHksv9CLiutIF0ipYDAN.png","num":0,"sum":0,"symbol":"USDT(ERC20)","token_id":1344}]
         * total : 6.943281009385408E7
         */

        private double total;
        private List<DataBean> data;

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean implements Serializable {
            /**
             * addr : 0x18d5d9536dbc9d65a66aa65afc19ddfde743ae7
             * logo_url : http://13.251.156.240/upload/200724/VxBrJOQJXz51YHrgvY4ccSpdEVneWTveGl2cNBRT.png
             * num : 985.000000000000000000
             * sum : 27.933854068562
             * symbol : SEC
             * token_id : 4
             */

            private String addr;
            private String logo_url;
            private String num;
            private double sum;
            private String symbol;
            private int token_id;
            private String price_cny;

            public String getPrice_cny() {
                return price_cny;
            }

            public void setPrice_cny(String price_cny) {
                this.price_cny = price_cny;
            }

            public String getPrice_usd() {
                return price_usd;
            }

            public void setPrice_usd(String price_usd) {
                this.price_usd = price_usd;
            }

            private String price_usd;

            public String getAddr() {
                return addr;
            }

            public void setAddr(String addr) {
                this.addr = addr;
            }

            public String getLogo_url() {
                return logo_url;
            }

            public void setLogo_url(String logo_url) {
                this.logo_url = logo_url;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public double getSum() {
                return sum;
            }

            public void setSum(double sum) {
                this.sum = sum;
            }

            public String getSymbol() {
                return symbol;
            }

            public void setSymbol(String symbol) {
                this.symbol = symbol;
            }

            public int getToken_id() {
                return token_id;
            }

            public void setToken_id(int token_id) {
                this.token_id = token_id;
            }
        }
    }
}
