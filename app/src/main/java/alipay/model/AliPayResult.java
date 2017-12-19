package alipay.model;

/**
 * Created by yangshiyou on 2017/9/29.
 */

public class AliPayResult {


//        "{\"alipay_trade_app_pay_response\":{\"code\":\"40002\",\"msg\":\"Invalid Arguments\",\"sub_code\":\"isv.invalid-signature\",\"sub_msg\":\"验签出错，建议检查签名字符串或签名私钥与应用公钥是否匹配，网关生成的验签字符串为：app_id\u003d2017061407489558\u0026biz_content\u003d{\\\"subject\\\":\\\"\\\\u5145\\\\u503c\\\\u8ba2\\\\u5355\\\",\\\"out_trade_no\\\":\\\"20170929379052\\\",\\\"total_amount\\\":\\\"0.01\\\",\\\"product_code\\\":\\\"qkb\\\"}\u0026body\u003d充值金额0.01\u0026charset\u003dutf-8\u0026method\u003dalipay.trade.app.pay\u0026notify_url\u003dhttp://www.mg0607.cn/App/public/notify_alipay\u0026sign_type\u003dRSA2\u0026timestamp\u003d2017-09-29 11:35:35\u0026version\u003d1.0\"}}",
//                "resultStatus": "4000"
//    }

//        {
//            "memo": "操作已经取消。",
//                "result": "",
//                "resultStatus": "6001"
//        }

//        {
//            "memo": "",
//                "result": "{\"alipay_trade_app_pay_response\":{\"code\":\"10000\",\"msg\":\"Success\",\"app_id\":\"2017061407489558\",\"auth_app_id\":\"2017061407489558\",\"charset\":\"utf-8\",\"timestamp\":\"2017-09-29 10:01:29\",\"total_amount\":\"0.01\",\"trade_no\":\"2017092921001004830273542225\",\"seller_id\":\"2088421590687653\",\"out_trade_no\":\"0929100109-1901\"},\"sign\":\"f1LaeF/gLUE6O7JWtnF+DDNDoBqt5KNBCvBY9LFCChXxcwVhOT2pYD923gu1+7NV85e3NlsPbo1DG1ybI2CspHbnl9G0IAhC3ri9eqknBC6Mw/cDNgJwe+RfA2KrD2nXCXkfNxKXk4uoKrUjOID3qZIXFunkR3gvdlj/Yz0qYJdLXaijOOQV3cMZEtoqVS80ywfCQwYUOPlLFu7ms+67jkOd00VlvlPPHoUCicn1qyRwcoV8rj3dIeiYpP8w7QCwv8J1uVn9UBp70b6SAoBiVe0XZ4jfTYv0st0QxXj4TQ7DdTsCCmAzDXXnjN3jeQIMXLkJ9SsWlJWXGYo3shVn6w\u003d\u003d\",\"sign_type\":\"RSA2\"}",
//                "resultStatus": "9000"
//        }

//        {
//            "memo": "",
//                "result": "{\"alipay_trade_app_pay_response\":{\"code\":\"40002\",\"msg\":\"Invalid Arguments\",\"sub_code\":\"isv.invalid-app-id\",\"sub_msg\":\"无效的AppID参数\"}}",
//                "resultStatus": "4000"
//        }

    private int resultStatus;

    private String memo;

    private Object result;


    public int getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(int resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
