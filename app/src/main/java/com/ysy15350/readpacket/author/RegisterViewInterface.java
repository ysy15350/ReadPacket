package com.ysy15350.readpacket.author;

import api.base.model.Response;

public interface RegisterViewInterface {


    public void getDynCodeCallback(boolean isCache, Response response);

    public void registerCallback(boolean isCache, Response response);

}
