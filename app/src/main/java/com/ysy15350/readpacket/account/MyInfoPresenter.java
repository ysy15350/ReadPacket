package com.ysy15350.readpacket.account;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.AuthTask;

import java.util.Map;

import alipay.AuthResult;
import alipay.model.Config;
import alipay.util.OrderInfoUtil2_0;
import api.AliPayApi;
import api.FileApi;
import api.UserApi;
import api.base.model.ApiCallBack;
import api.base.model.Response;
import api.impl.AliPayApiImpl;
import api.impl.FileApiImpl;
import api.impl.UserApiImpl;
import base.model.UserInfo;
import base.mvp.BasePresenter;
import common.message.MessageBox;
import common.string.JsonConvertor;

public class MyInfoPresenter extends BasePresenter<MyInfoViewInterface> {

    public MyInfoPresenter(Context context) {
        super(context);

    }

    private static final String TAG = "MyInfoPresenter";


    UserApi userApi = new UserApiImpl();
    FileApi fileApi = new FileApiImpl();
    AliPayApi aliPayApi = new AliPayApiImpl();

    public void userInfo() {
        userApi.userInfo(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.user_infoCallback(isCache, response);
            }
        });
    }

    public void saveUserInfo(UserInfo userInfo) {
        userApi.saveUserInfo(userInfo, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.saveUserInfoCallback(isCache, response);
            }
        });
    }

    public void imgUp(int type, String imgName, String imgData) {
        fileApi.imgUp(type, imgName, imgData, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.imgUpCallback(isCache, response);
            }
        });
    }


    //---------------------------支付宝--------------------------------------


    private static final int SDK_AUTH_FLAG = 2;

    /**
     * 支付宝账户授权业务
     */
    public void authV2() {


        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        boolean rsa2 = (Config.RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(Config.PID, Config.APPID, Config.TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? Config.RSA2_PRIVATE : Config.RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask((Activity) mContext);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_AUTH_FLAG: {

                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);

//                    {
//                        "alipayOpenId": "20881018570313238783042660913073",
//                            "authCode": "409b5e9998a841988d4ff34bfbdcPD73",
//                            "memo": "",
//                            "result": "success\u003dtrue\u0026result_code\u003d200\u0026app_id\u003d2017121200619340\u0026auth_code\u003d409b5e9998a841988d4ff34bfbdcPD73\u0026scope\u003dkuaijie\u0026alipay_open_id\u003d20881018570313238783042660913073\u0026user_id\u003d2088602140983735\u0026target_id\u003d1481112590@qq.com",
//                            "resultCode": "200",
//                            "resultStatus": "9000"
//                    }

                    Log.d(TAG, "authResult: [" + JsonConvertor.toJson(msg.obj) + "]");

                    Log.d(TAG, "authResult: [" + JsonConvertor.toJson(authResult) + "]");

                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
//                        Toast.makeText(mContext,
//                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
//                                .show();

                        MessageBox.show("支付宝授权成功，请等待服务器验证");

                        String auth_code = authResult.getAuthCode();
                        String userid = authResult.getUser_id();
                        oauth_token(auth_code, userid);

                    } else {
                        // 其他状态值则为授权失败
                        MessageBox.show("支付宝授权失败");
//                        Toast.makeText(mContext,
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

    public void oauth_token(String auth_code, String userid) {
        aliPayApi.oauth_token(auth_code, userid, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.oauth_tokenCallback(isCache, response);
            }
        });
    }


}
