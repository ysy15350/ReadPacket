package com.ysy15350.readpacket.author;

import android.content.Context;

import api.UserApi;
import api.base.model.ApiCallBack;
import api.base.model.Response;
import api.impl.UserApiImpl;
import base.mvp.BasePresenter;

public class BindMobilePresenter extends BasePresenter<BindMobileViewInterface> {

    public BindMobilePresenter(Context context) {
        super(context);

    }


    UserApi userApi = new UserApiImpl();

    public void getDynCode(String mobile) {
        userApi.getDynCode(mobile, 1, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.getDynCodeCallback(isCache, response);
            }
        });
    }

    public void updateMobile(String mobile, String code) {
        userApi.updateMobile(mobile, code, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.updateMobileCallback(isCache, response);
            }
        });
    }


}
