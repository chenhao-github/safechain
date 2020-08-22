package com.code.safechain.ui.wallet.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @Auther: hchen
 * @Date: 2020/8/16 0016
 * @Description:
 */
public class ChainInfoRsBean {

    /**
     * error : 0
     * message : success
     * result : {"abstract":"XXXX","contract_addr":"XXXX","created_at":"2018-02-13 00:35:43","decimal":8,"en_abstract":"XXXX","en_name":"XXXX","fee_type":1,"gas_limit":90000,"homepage":"XXXX","id":4,"in_wallet":1,"jp_abstract":"XXXX","jp_name":"","logo_url":"http://13.251.156.240/upload/200719/SEC.png","mytoken_id":0,"name":"safechain","ord":1,"packet_cover":"XXXX","price":"0.007222091538032100","price_cny":"0.028359242709200000","price_usd":"0.007222091538032100","protocol":1,"symbol":"SEC","tc_abstract":"XXXX","tc_name":"","token_percent":0,"updated_at":"2020-04-03 11:10:03","wallet_max":"999999999.000000000000000000","wallet_min":"0.000100000000000000"}
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
         * abstract : XXXX
         * contract_addr : XXXX
         * created_at : 2018-02-13 00:35:43
         * decimal : 8
         * en_abstract : XXXX
         * en_name : XXXX
         * fee_type : 1
         * gas_limit : 90000
         * homepage : XXXX
         * id : 4
         * in_wallet : 1
         * jp_abstract : XXXX
         * jp_name :
         * logo_url : http://13.251.156.240/upload/200719/SEC.png
         * mytoken_id : 0
         * name : safechain
         * ord : 1
         * packet_cover : XXXX
         * price : 0.007222091538032100
         * price_cny : 0.028359242709200000
         * price_usd : 0.007222091538032100
         * protocol : 1
         * symbol : SEC
         * tc_abstract : XXXX
         * tc_name :
         * token_percent : 0
         * updated_at : 2020-04-03 11:10:03
         * wallet_max : 999999999.000000000000000000
         * wallet_min : 0.000100000000000000
         */

        @SerializedName("abstract")
        private String abstractX;
        private String contract_addr;
        private String created_at;
        private int decimal;
        private String en_abstract;
        private String en_name;
        private int fee_type;
        private int gas_limit;
        private String homepage;
        private int id;
        private int in_wallet;
        private String jp_abstract;
        private String jp_name;
        private String logo_url;
        private int mytoken_id;
        private String name;
        private int ord;
        private String packet_cover;
        private String price;
        private String price_cny;
        private String price_usd;
        private int protocol;
        private String symbol;
        private String tc_abstract;
        private String tc_name;
        private int token_percent;
        private String updated_at;
        private String wallet_max;
        private String wallet_min;

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public String getContract_addr() {
            return contract_addr;
        }

        public void setContract_addr(String contract_addr) {
            this.contract_addr = contract_addr;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int getDecimal() {
            return decimal;
        }

        public void setDecimal(int decimal) {
            this.decimal = decimal;
        }

        public String getEn_abstract() {
            return en_abstract;
        }

        public void setEn_abstract(String en_abstract) {
            this.en_abstract = en_abstract;
        }

        public String getEn_name() {
            return en_name;
        }

        public void setEn_name(String en_name) {
            this.en_name = en_name;
        }

        public int getFee_type() {
            return fee_type;
        }

        public void setFee_type(int fee_type) {
            this.fee_type = fee_type;
        }

        public int getGas_limit() {
            return gas_limit;
        }

        public void setGas_limit(int gas_limit) {
            this.gas_limit = gas_limit;
        }

        public String getHomepage() {
            return homepage;
        }

        public void setHomepage(String homepage) {
            this.homepage = homepage;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIn_wallet() {
            return in_wallet;
        }

        public void setIn_wallet(int in_wallet) {
            this.in_wallet = in_wallet;
        }

        public String getJp_abstract() {
            return jp_abstract;
        }

        public void setJp_abstract(String jp_abstract) {
            this.jp_abstract = jp_abstract;
        }

        public String getJp_name() {
            return jp_name;
        }

        public void setJp_name(String jp_name) {
            this.jp_name = jp_name;
        }

        public String getLogo_url() {
            return logo_url;
        }

        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public int getMytoken_id() {
            return mytoken_id;
        }

        public void setMytoken_id(int mytoken_id) {
            this.mytoken_id = mytoken_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOrd() {
            return ord;
        }

        public void setOrd(int ord) {
            this.ord = ord;
        }

        public String getPacket_cover() {
            return packet_cover;
        }

        public void setPacket_cover(String packet_cover) {
            this.packet_cover = packet_cover;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

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

        public int getProtocol() {
            return protocol;
        }

        public void setProtocol(int protocol) {
            this.protocol = protocol;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getTc_abstract() {
            return tc_abstract;
        }

        public void setTc_abstract(String tc_abstract) {
            this.tc_abstract = tc_abstract;
        }

        public String getTc_name() {
            return tc_name;
        }

        public void setTc_name(String tc_name) {
            this.tc_name = tc_name;
        }

        public int getToken_percent() {
            return token_percent;
        }

        public void setToken_percent(int token_percent) {
            this.token_percent = token_percent;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getWallet_max() {
            return wallet_max;
        }

        public void setWallet_max(String wallet_max) {
            this.wallet_max = wallet_max;
        }

        public String getWallet_min() {
            return wallet_min;
        }

        public void setWallet_min(String wallet_min) {
            this.wallet_min = wallet_min;
        }
    }
}
