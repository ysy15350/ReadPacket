package com.ysy15350.readpacket.account;

import api.base.model.Response;

public interface InviteListViewInterface {

    public void user_infoCallback(boolean isCache, Response response);

    public void getInviteList(boolean isCache, Response response);

}
