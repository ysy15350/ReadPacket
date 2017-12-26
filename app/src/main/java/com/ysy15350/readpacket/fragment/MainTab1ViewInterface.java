package com.ysy15350.readpacket.fragment;

import api.base.model.Response;

public interface MainTab1ViewInterface {

    public void getRedPacketListCallback(boolean isCache, Response response);

    //public void grabRedPacketCallback(boolean isCache, Response response);

    public void getSystemConfigCallback(boolean isCache, Response response);

    public void noticeInfoCallback(boolean isCache, Response response);

}
