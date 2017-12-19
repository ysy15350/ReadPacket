package com.ysy15350.readpacket.account;

import api.base.model.Response;

public interface RechargeViewInterface {

    public void rechargeCallback(boolean isCache, Response response);

    public void rechargeResultCallback(boolean isCache, Response response);
    
    public void showAliPayResult(String msg);

}
