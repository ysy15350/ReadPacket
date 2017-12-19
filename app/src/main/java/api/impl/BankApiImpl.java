package api.impl;

import api.BankApi;
import api.base.IServer;
import api.base.Request;
import api.base.model.ApiCallBack;
import api.model.BankCardInfo;

/**
 * Created by yangshiyou on 2017/11/26.
 */

public class BankApiImpl implements BankApi {

    private String moduleName = "bank/";


    @Override
    public void getBankInfoList(ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "getBankInfoList");

//            server.setParam("page", page);
//            server.setParam("pageSize", pageSize);

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getBankCardInfoList(ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "getBankCardInfoList");

//            server.setParam("page", page);
//            server.setParam("pageSize", pageSize);

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBankCardInfo(BankCardInfo bankCardInfo, ApiCallBack callBack) {
        try {

            if (bankCardInfo == null)
                return;

            IServer server = new Request();

            server.setMethodName(moduleName + "addBankCardInfo");

            server.setParam("fullname", bankCardInfo.getFullname());
            server.setParam("phone", bankCardInfo.getPhone());
            server.setParam("cardnum", bankCardInfo.getCardnum());
            server.setParam("bankid", bankCardInfo.getBankid());
            server.setParam("banksubname", bankCardInfo.getBanksubname());

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
