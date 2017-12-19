package com.ysy15350.readpacket.account;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

import alipay.AuthResult;
import alipay.PayResult;
import alipay.util.OrderInfoUtil2_0;
import api.AccountApi;
import api.base.model.ApiCallBack;
import api.base.model.Response;
import api.impl.AccountApiImpl;
import base.mvp.BasePresenter;
import common.string.JsonConvertor;

public class RechargePresenter extends BasePresenter<RechargeViewInterface> {

    public RechargePresenter(Context context) {
        super(context);

    }

    private static final String TAG = "RechargePresenter";

    AccountApi accountApi = new AccountApiImpl();

    /**
     * 充值下单，后台生成签名
     *
     * @param price
     * @param type  1:支付宝；2：微信
     */
    public void recharge(int price, int type) {
        accountApi.recharge(price, type, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.rechargeCallback(isCache, response);
            }
        });
    }

    /**
     * 充值结果
     * @param alipay_trade_app_pay_response
     * @param sign
     * @param sign_type
     */
    public void rechargeResult(String alipay_trade_app_pay_response, String sign, String sign_type) {
        accountApi.rechargeResult(alipay_trade_app_pay_response, sign, sign_type, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.rechargeResultCallback(isCache, response);
            }
        });
    }

    public void alipayTest(Activity activity) {
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        alipay(orderInfo, activity);

    }

    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "ddd";
    public static final String RSA_PRIVATE = "";
    public static final String RSA2_PRIVATE = "ddd";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    public void alipay(final String orderInfo, final Activity context) {

        Log.d(TAG, "alipay() called with: authInfo = [" + orderInfo + "], context = [" + context + "]");

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(context);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();

                    Log.d(TAG, "支付结果: " + JsonConvertor.toJson(payResult));

                    mView.showAliPayResult(JsonConvertor.toJson(payResult));

                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
//                        Toast.makeText(PayDemoActivity.this,
//                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
//                                .show();
                    } else {
                        // 其他状态值则为授权失败
//                        Toast.makeText(PayDemoActivity.this,
//                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


}
