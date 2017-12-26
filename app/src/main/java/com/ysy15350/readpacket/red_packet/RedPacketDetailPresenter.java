package com.ysy15350.readpacket.red_packet;

import android.content.Context;

import api.RedPacketApi;
import api.base.model.ApiCallBack;
import api.base.model.Response;
import api.impl.RedPacketApiImpl;
import base.mvp.BasePresenter;

public class RedPacketDetailPresenter extends BasePresenter<RedPacketDetailViewInterface> {

    public RedPacketDetailPresenter(Context context) {
        super(context);

    }

    RedPacketApi redPacketApi = new RedPacketApiImpl();

    public void grabRedPacket(int redPacketId) {

        redPacketApi.grabRedPacket(redPacketId, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);

                mView.grabRedPacketCallback(isCache, response);
            }
        });
    }

}



