package com.ysy15350.readpacket.fragment;

import android.content.Context;

import api.UserApi;
import api.base.model.ApiCallBack;
import api.base.model.Response;
import api.impl.UserApiImpl;
import base.mvp.BasePresenter;


public class MainTab2Presenter extends BasePresenter<MainTab2ViewInterface> {

    public MainTab2Presenter() {
    }

    public MainTab2Presenter(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    UserApi userApi = new UserApiImpl();

    public void userInfo() {
        userApi.userInfo(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.userInfoCallback(isCache, response);
            }
        });
    }


}
