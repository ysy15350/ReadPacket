package com.ysy15350.readpacket.author;

import api.base.model.Response;

public interface LoginViewInterface {


    public void loginCallback(boolean isCache, Response response);

    public void activateCallback(boolean isCache, Response response);

    /**
     * 支付宝授权结果
     *
     * @param resultStatus 9000:成功
     * @param resultCode 200成功
     * @param msg
     */
    public void AuthResult(String resultStatus, String resultCode, String msg);

    public void oauth_tokenCallback(boolean isCache, Response response);

}
