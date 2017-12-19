package com.ysy15350.readpacket.author;

import android.content.Context;

import api.UserApi;
import api.base.model.ApiCallBack;
import api.base.model.Response;
import api.impl.UserApiImpl;
import base.mvp.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginViewInterface> {

    public LoginPresenter(Context context) {
        super(context);

    }

    UserApi userApi = new UserApiImpl();

    public void login(String mobile, String password) {
        userApi.login(mobile, password, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.loginCallback(isCache, response);
            }
        });
    }


    public void activate() {
        userApi.activate(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.activateCallback(isCache, response);
            }
        });
    }


}
