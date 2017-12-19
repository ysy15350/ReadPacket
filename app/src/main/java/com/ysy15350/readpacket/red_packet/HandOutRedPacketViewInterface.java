package com.ysy15350.readpacket.red_packet;

import api.base.model.Response;

public interface HandOutRedPacketViewInterface {

    public void user_infoCallback(boolean isCache, Response response);

    public void createRedPacketCallback(boolean isCache, Response response);

    public void getGrabChanceCountCallback(boolean isCache, Response response);

}
