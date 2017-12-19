package api;

import api.base.model.ApiCallBack;

/**
 * Created by yangshiyou on 2017/11/16.
 */

public interface RedPacketApi {


    public void createRedPacket(int price, int luckNum, ApiCallBack callBack);

    public void getRedPacketList(int page,int pageSize, ApiCallBack callBack);

    public void grabRedPacket(int redPacketId, ApiCallBack callBack);

}
