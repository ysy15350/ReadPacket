package com.ysy15350.readpacket.author;

import api.base.model.Response;

public interface BindMobileViewInterface {

    public void getDynCodeCallback(boolean isCache, Response response);

    /**
     * 修改手机号
     *
     * @param isCache
     * @param response
     */
    public void updateMobileCallback(boolean isCache, Response response);

}
