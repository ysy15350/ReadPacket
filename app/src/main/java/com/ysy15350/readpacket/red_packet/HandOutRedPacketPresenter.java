package com.ysy15350.readpacket.red_packet;

import android.content.Context;

import api.AccountApi;
import api.RedPacketApi;
import api.UserApi;
import api.base.model.ApiCallBack;
import api.base.model.Response;
import api.impl.AccountApiImpl;
import api.impl.RedPacketApiImpl;
import api.impl.UserApiImpl;
import base.mvp.BasePresenter;

public class HandOutRedPacketPresenter extends BasePresenter<HandOutRedPacketViewInterface> {

    public HandOutRedPacketPresenter(Context context) {
        super(context);

    }

    RedPacketApi redPacketApi = new RedPacketApiImpl();

    UserApi userApi = new UserApiImpl();

    AccountApi accountApi=new AccountApiImpl();

    public void userInfo() {
        userApi.userInfo(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.user_infoCallback(isCache, response);
            }
        });
    }

    public void createRedPacket(int price, int luckNum) {
        redPacketApi.createRedPacket(price, luckNum, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.createRedPacketCallback(isCache, response);
            }
        });
    }

    public void getGrabChanceCount(){
        accountApi.getGrabChanceCount(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.getGrabChanceCountCallback(isCache,response);
            }
        });
    }


}
