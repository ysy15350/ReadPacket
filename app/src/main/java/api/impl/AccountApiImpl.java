package api.impl;

import api.AccountApi;
import api.base.IServer;
import api.base.Request;
import api.base.model.ApiCallBack;
import api.model.ShareInfo;

/**
 * Created by yangshiyou on 2017/11/26.
 */

public class AccountApiImpl implements AccountApi {

    private String moduleName = "account/";

    @Override
    public void recharge(int price, int type, ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "recharge");

            server.setParam("price", price);
            server.setParam("type", type);

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rechargeResult(String alipay_trade_app_pay_response, String sign, String sign_type, ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "rechargeResult");

            server.setParam("alipay_trade_app_pay_response", alipay_trade_app_pay_response);
            server.setParam("sign", sign);
            server.setParam("sign_type", sign_type);

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void withdraw(int price, int bankcardId, ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "withdraw");

            server.setParam("price", price);
            server.setParam("bankcardId", bankcardId);

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getGrabChanceCount(ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "getGrabChanceCount");

//            server.setParam("price", price);
//            server.setParam("type", type);

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMyPrizeInfo(ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "getMyPrizeInfo");

//            server.setParam("price", price);
//            server.setParam("type", type);

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sharePlatform(ShareInfo shareInfo, ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "sharePlatform");

            server.setParam("content", shareInfo.getContent());
            server.setParam("platform", shareInfo.getPlatform());
            server.setParam("url",shareInfo.getUrl());
            server.setParam("imageurl",shareInfo.getImageurl());
            server.setParam("status",shareInfo.getStatus());

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
