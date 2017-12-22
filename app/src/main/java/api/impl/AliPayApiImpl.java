package api.impl;

import api.AliPayApi;
import api.base.IServer;
import api.base.Request;
import api.base.model.ApiCallBack;
import common.CommFunAndroid;

/**
 * Created by yangshiyou on 2017/12/21.
 */

public class AliPayApiImpl implements AliPayApi {

    private String moduleName = "alipay/";


    @Override
    public void oauth_token(String auth_code, String userid, ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "oauth_token");

            server.setParam("auth_code", auth_code);
            server.setParam("userid", userid);
            server.setParam("platform", 1);
            server.setParam("deviceId", CommFunAndroid.getSharedPreferences("device_id"));
            //CommFunAndroid.setSharedPreferences("device_id", deviceId);


            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
