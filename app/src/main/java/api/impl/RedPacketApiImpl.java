package api.impl;

import api.RedPacketApi;
import api.base.IServer;
import api.base.Request;
import api.base.model.ApiCallBack;
import base.data.BaseData;

/**
 * Created by yangshiyou on 2017/11/22.
 */

public class RedPacketApiImpl implements RedPacketApi {

    private String moduleName = "red_packet/";


    @Override
    public void createRedPacket(int price, int luckNum, ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "createRedPacket");

            server.setParam("uid", BaseData.getInstance().getUid());
            server.setParam("price", price);
            server.setParam("luckNum", luckNum);

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getRedPacketList(int page, int pageSize, ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "getRedPacketList");

            server.setParam("uid", BaseData.getInstance().getUid());
            server.setParam("page", page);
            server.setParam("pageSize", pageSize);

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void grabRedPacket(int redPacketId, ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "grabRedPacket");

            server.setParam("redPacketId", redPacketId);

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
