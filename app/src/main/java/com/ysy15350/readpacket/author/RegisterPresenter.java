package com.ysy15350.readpacket.author;

import android.content.Context;

import api.UserApi;
import api.base.model.ApiCallBack;
import api.base.model.Response;
import api.impl.UserApiImpl;
import base.mvp.BasePresenter;

public class RegisterPresenter extends BasePresenter<RegisterViewInterface> {

    public RegisterPresenter(Context context) {
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


    public void register(String mobile, String password, String code,String refereeCode) {
        userApi.register(mobile, password, code,refereeCode, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.registerCallback(isCache, response);
            }

            @Override
            public void onSuccess(boolean isCache, String data) {
                super.onSuccess(isCache, data);
            }
        });
    }


}
