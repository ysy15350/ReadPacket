package api.impl;

import api.PublicApi;
import api.base.IServer;
import api.base.Request;
import api.base.model.ApiCallBack;

/**
 * Created by yangshiyou on 2017/12/25.
 */

public class PublicApiImpl implements PublicApi {

    private String moduleName = "public/";

    @Override
    public void getSystemConfig(ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "getSystemConfig");

//            server.setParam("phone", mobile);
//            server.setParam("type", type);

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void noticeInfo(ApiCallBack callBack) {

        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "noticeInfo");

//            server.setParam("phone", mobile);
//            server.setParam("type", type);

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
