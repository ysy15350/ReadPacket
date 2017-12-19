package com.ysy15350.readpacket.account;

import android.content.Context;

import api.UserApi;
import api.base.model.ApiCallBack;
import api.base.model.Response;
import api.impl.UserApiImpl;
import base.mvp.BasePresenter;

public class InviteListPresenter extends BasePresenter<InviteListViewInterface> {

    public InviteListPresenter(Context context) {
        super(context);

    }


    UserApi userApi = new UserApiImpl();

    public void userInfo() {
        userApi.userInfo(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.user_infoCallback(isCache, response);
            }
        });
    }

    public void getInviteList(int page, int pageSize) {
        userApi.getInviteList(page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.getInviteList(isCache, response);
            }
        });
    }


}
