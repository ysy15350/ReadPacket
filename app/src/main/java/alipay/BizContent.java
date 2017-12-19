package alipay;

import common.string.JsonConvertor;

/**
 * Created by yangshiyou on 2017/9/29.
 */

public class BizContent {

//    keyValues.put("biz_content",
// "{\"timeout_express\":\"30m\",
// \"product_code\":\"QUICK_MSECURITY_PAY\",
// \"total_amount\":\"0.01\",
// \"subject\":\"1\",
// \"body\":\"我是测试数据\",
// \"out_trade_no\":\"" + getOutTradeNo() + "\"}");

    private String timeout_express;

    private String product_code;

    private float total_amount;

    private int subject;

    private String body;

    private String out_trade_no;

    public String getTimeout_express() {
        return timeout_express;
    }

    public void setTimeout_express(String timeout_express) {
        this.timeout_express = timeout_express;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getBizContentJson(String orderNum, float price, String des) {


        this.setOut_trade_no(orderNum);
        this.setTotal_amount(price);
        this.setBody(des);


        this.setTimeout_express("30m");
        this.setProduct_code("QUICK_MSECURITY_PAY");
        this.setSubject(1);

        return JsonConvertor.toJson(this);
    }
}
