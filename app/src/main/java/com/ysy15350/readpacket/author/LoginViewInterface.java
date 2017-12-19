package com.ysy15350.readpacket.author;

import api.base.model.Response;

public interface LoginViewInterface {


    public void loginCallback(boolean isCache, Response response);

    public void activateCallback(boolean isCache, Response response);

}
