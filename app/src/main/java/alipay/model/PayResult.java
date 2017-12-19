package alipay.model;

/**
 * Created by yangshiyou on 2017/11/26.
 */

public class PayResult {


//    {"alipay_trade_app_pay_response":{"code":"10000","msg":"Success","app_id":"2017121200619340","auth_app_id":"2017121200619340","charset":"utf-8","timestamp":"2017-12-12 20:33:53","total_amount":"0.01","trade_no":"2017121221001004730598001928","seller_id":"2088921052242290","out_trade_no":"121220334062900"},"sign":"KdA+5uLDfrs2SWvpXlXuVuxERTiA4UAhAPCjwjvKN/xcB40qYuK98eo21+qhy7e0HECjlA6YZt9e/3PdvONbKVrlLLr9GsHqILGplsbXjb9L7OiS4dj/uR2EgzOd4e16qH+Egjzi7+ThjnRyQSBIvYvLFjI1Y8oxqa2155qurjYK1iaubbQs4IBmt05SSrYZ5YXi9uHNwBmgcEQUQ2IS/tPllMhIcNjUUkggzP1CcmcwvxvRQnhqfn2HZZOws2Qi94Vfd3fsq2/lcyLaYCyrwAzhuMUuog+voKhuOCH5bC68BvPcrCwj5XzKV5flJL2FqlTgbPC02pLFVdxJjSh3sg==","sign_type":"RSA2"}


    /**
     * alipay_trade_app_pay_response : {"code":"40002","msg":"Invalid Arguments","sub_code":"isv.invalid-app-id","sub_msg":"无效的AppID参数"}
     */

    private AlipayTradeAppPayResponseBean alipay_trade_app_pay_response;

    private String sign;

    private String sign_type;

    public AlipayTradeAppPayResponseBean getAlipay_trade_app_pay_response() {
        return alipay_trade_app_pay_response;
    }

    public void setAlipay_trade_app_pay_response(AlipayTradeAppPayResponseBean alipay_trade_app_pay_response) {
        this.alipay_trade_app_pay_response = alipay_trade_app_pay_response;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public static class AlipayTradeAppPayResponseBean {
        /**
         * code : 40002
         * msg : Invalid Arguments
         * sub_code : isv.invalid-app-id
         * sub_msg : 无效的AppID参数
         */
        //成功json
        //                 "code": "10000",
        //                 "msg": "Success",
        //                 "app_id": "2017121200619340",
        //                 "auth_app_id": "2017121200619340",
        //                 "charset": "utf-8",
        //                 "timestamp": "2017-12-12 20:33:53",
        //                 "total_amount": "0.01",
        //                 "trade_no": "2017121221001004730598001928",
        //                 "seller_id": "2088921052242290",
        //                 "out_trade_no": "121220334062900"

        private String code;
        private String msg;
        private String sub_code;
        private String sub_msg;
        private String app_id;
        private String auth_app_id;
        private String charset;
        private String timestamp;
        private double total_amount;
        private String trade_no;
        private String seller_id;
        private String out_trade_no;


        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getSub_code() {
            return sub_code;
        }

        public void setSub_code(String sub_code) {
            this.sub_code = sub_code;
        }

        public String getSub_msg() {
            return sub_msg;
        }

        public void setSub_msg(String sub_msg) {
            this.sub_msg = sub_msg;
        }

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getAuth_app_id() {
            return auth_app_id;
        }

        public void setAuth_app_id(String auth_app_id) {
            this.auth_app_id = auth_app_id;
        }

        public String getCharset() {
            return charset;
        }

        public void setCharset(String charset) {
            this.charset = charset;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public double getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(double total_amount) {
            this.total_amount = total_amount;
        }

        public String getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
        }

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }
    }
}
