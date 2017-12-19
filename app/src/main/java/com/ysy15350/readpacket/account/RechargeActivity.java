package com.ysy15350.readpacket.account;

import android.content.Intent;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.readpacket.R;

import org.xutils.view.annotation.ContentView;

import alipay.model.AliPayResult;
import alipay.model.PayResult;
import api.base.model.Response;
import api.base.model.ResponseHead;
import base.mvp.MVPBaseActivity;
import common.CommFun;
import common.CommFunAndroid;
import common.string.JsonConvertor;

/**
 * Created by yangshiyou on 2017/9/20.
 */

/**
 * 充值
 */
@ContentView(R.layout.activity_recharge)
public class RechargeActivity extends MVPBaseActivity<RechargeViewInterface, RechargePresenter> implements RechargeViewInterface {


    @Override
    protected RechargePresenter createPresenter() {
        // TODO Auto-generated method stub
        return new RechargePresenter(RechargeActivity.this);
    }

    private static final String TAG = "RechargeActivity";

    @Override
    public void initView() {

        super.initView();
        setFormHead("充值");


    }

    @Override
    protected void btn_okOnClick(View view) {
        super.btn_okOnClick(view);
        String et_priceText = mHolder.getViewText(R.id.et_price);
        if (CommFunAndroid.isNullOrEmpty(et_priceText)) {
            showMsg("请输入充值金额");

            return;
        }

        if (!CommFunAndroid.isNumber(et_priceText)) {
            showMsg("充值金额输入有误");
            return;
        }

        int price = CommFunAndroid.toInt32(et_priceText, 0);//充值金额

        showWaitDialog("服务器处理中，请稍后....");

        //mPresenter.alipayTest(this);

        mPresenter.recharge(price, 1);

    }

    @Override
    public void rechargeCallback(boolean isCache, Response response) {
        try {

            hideWaitDialog();


            if (response != null) {
                ResponseHead responseHead = response.getHead();
                if (responseHead != null) {
                    int status = responseHead.getResponse_status();
                    String msg = responseHead.getResponse_msg();
                    if (status == 100) {
                        ArrayMap<String, String> arrayMap = response.getData(new TypeToken<ArrayMap<String, String>>() {
                        }.getType());
                        if (arrayMap != null) {
                            String type = arrayMap.get("type");
                            if (CommFun.isEquals("1", type)) {
                                String params = arrayMap.get("params");
                                if (!CommFun.isNullOrEmpty(params)) {
                                    mPresenter.alipay(params, this);
                                }
                            }
                        }
                    } else
                        showMsg(msg);

                }
            } else {
                showMsg("系统错误");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showAliPayResult(String msg) {

//        "{\"alipay_trade_app_pay_response\":{\"code\":\"40002\",\"msg\":\"Invalid Arguments\",\"sub_code\":\"isv.invalid-signature\",\"sub_msg\":\"验签出错，建议检查签名字符串或签名私钥与应用公钥是否匹配，网关生成的验签字符串为：app_id\u003d2017061407489558\u0026biz_content\u003d{\\\"subject\\\":\\\"\\\\u5145\\\\u503c\\\\u8ba2\\\\u5355\\\",\\\"out_trade_no\\\":\\\"20170929379052\\\",\\\"total_amount\\\":\\\"0.01\\\",\\\"product_code\\\":\\\"qkb\\\"}\u0026body\u003d充值金额0.01\u0026charset\u003dutf-8\u0026method\u003dalipay.trade.app.pay\u0026notify_url\u003dhttp://www.mg0607.cn/App/public/notify_alipay\u0026sign_type\u003dRSA2\u0026timestamp\u003d2017-09-29 11:35:35\u0026version\u003d1.0\"}}",
//                "resultStatus": "4000"
//    }

//        {
//            "memo": "操作已经取消。",
//                "result": "",
//                "resultStatus": "6001"
//        }

//        {
//            "memo": "操作已经取消。",
//                "result": "",
//                "resultStatus": "6002"
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


        try {
            if (!CommFunAndroid.isNullOrEmpty(msg)) {

                Log.d(TAG, "showAliPayResult() called with: msg = [" + msg + "]");

                AliPayResult payResult = JsonConvertor.fromJson(msg, AliPayResult.class);

                if (payResult != null) {
                    int resultStatus = payResult.getResultStatus();
                    String memo = payResult.getMemo();
                    String resultJson = "";
                    if (payResult.getResult() != null)
                        resultJson = JsonConvertor.toJson(payResult.getResult());

                    switch (resultStatus) {
                        case 6001://操作取消
                            showMsg(memo);
                            break;
                        case 6002://操作取消
                            showMsg(memo);
                            break;
                        case 9000:
                            //充值成功
                            if (!CommFun.isNullOrEmpty(resultJson)) {
                                resultJson = resultJson.replace("\\", "");
                                resultJson = resultJson.substring(1, resultJson.length() - 1);


                                PayResult subResult = JsonConvertor.fromJson(resultJson, PayResult.class);
                                if (subResult != null) {

                                    String appPayResponseJson = JsonConvertor.toJson(subResult.getAlipay_trade_app_pay_response());
                                    String sign = subResult.getSign();
                                    String sign_type = subResult.getSign_type();
                                    mPresenter.rechargeResult(appPayResponseJson, sign, sign_type);

                                    showWaitDialog("服务器验证中...");
                                }
                            }

                            break;
                        case 4000:
                            if (!CommFun.isNullOrEmpty(resultJson)) {
                                resultJson = resultJson.replace("\\", "");
                                resultJson = resultJson.substring(1, resultJson.length() - 1);
                                PayResult subResult = JsonConvertor.fromJson(resultJson, PayResult.class);
                                if (subResult != null) {
                                    String sub_msg = subResult.getAlipay_trade_app_pay_response().getSub_msg();
                                    showMsg(sub_msg);
                                }
                            }
                            break;

                    }

                }
            } else {
                showMsg("未知支付错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rechargeResultCallback(boolean isCache, Response response) {
        try {

            hideWaitDialog();


            if (response != null) {
                ResponseHead responseHead = response.getHead();
                if (responseHead != null) {
                    int status = responseHead.getResponse_status();
                    String msg = responseHead.getResponse_msg();
                    if (status == 100) {
                        rechargeSuccess();
                    } else
                        showMsg(msg);

                }
            } else {
                showMsg("系统错误");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 充值成功
     */
    private void rechargeSuccess() {

        Intent intent = new Intent(this, AccountResultActivity.class);
        intent.putExtra("title", "充值成功");
        intent.putExtra("content", "");
        startActivity(intent);
        this.finish();
    }
}
