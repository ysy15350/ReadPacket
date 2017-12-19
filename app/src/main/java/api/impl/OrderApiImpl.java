package api.impl;

import api.OrderApi;
import api.base.IServer;
import api.base.Request;
import api.base.model.ApiCallBack;

/**
 * Created by yangshiyou on 2017/11/28.
 */

public class OrderApiImpl implements OrderApi {

    private String moduleName = "order/";

    @Override
    public void orderList(int page, int pageSize, ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "orderList");

            server.setParam("page", page);
            server.setParam("pageSize", pageSize);

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
