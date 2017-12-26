package com.ysy15350.readpacket.fragment;

import android.content.Context;

import api.PublicApi;
import api.RedPacketApi;
import api.base.model.ApiCallBack;
import api.base.model.Response;
import api.impl.PublicApiImpl;
import api.impl.RedPacketApiImpl;
import base.mvp.BasePresenter;


public class MainTab1Presenter extends BasePresenter<MainTab1ViewInterface> {

    public MainTab1Presenter() {
    }

    public MainTab1Presenter(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    PublicApi publicApi = new PublicApiImpl();
    RedPacketApi redPacketApi = new RedPacketApiImpl();

    public void getRedPacketList(int page, int pageSize) {
        redPacketApi.getRedPacketList(page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.getRedPacketListCallback(isCache, response);
            }
        });
    }


//    public void grabRedPacket(int redPacketId) {
//
//        redPacketApi.grabRedPacket(redPacketId, new ApiCallBack() {
//            @Override
//            public void onSuccess(boolean isCache, Response response) {
//                super.onSuccess(isCache, response);
//
//                mView.grabRedPacketCallback(isCache, response);
//            }
//        });
//    }


    /**
     * 获取系统配置，1、规则
     */
    public void getSystemConfig() {
        publicApi.getSystemConfig(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.getSystemConfigCallback(isCache, response);
            }
        });
    }

    /**
     * 获取公共信息
     */
    public void noticeInfo() {
        publicApi.noticeInfo(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.noticeInfoCallback(isCache, response);
            }
        });
    }


}
