package com.ysy15350.readpacket.account;

import api.base.model.Response;

public interface MyInfoViewInterface {

    public void user_infoCallback(boolean isCache, Response response);

    public void saveUserInfoCallback(boolean isCache, Response response);

    public void imgUpCallback(boolean isCache, Response response);

    public void oauth_tokenCallback(boolean isCache, Response response);

}
