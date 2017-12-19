package com.ysy15350.readpacket.account;

import android.content.Context;

import api.FileApi;
import api.UserApi;
import api.base.model.ApiCallBack;
import api.base.model.Response;
import api.impl.FileApiImpl;
import api.impl.UserApiImpl;
import base.model.UserInfo;
import base.mvp.BasePresenter;

public class MyInfoPresenter extends BasePresenter<MyInfoViewInterface> {

    public MyInfoPresenter(Context context) {
        super(context);

    }


    UserApi userApi = new UserApiImpl();
    FileApi fileApi = new FileApiImpl();

    public void userInfo() {
        userApi.userInfo(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.user_infoCallback(isCache, response);
            }
        });
    }

    public void saveUserInfo(UserInfo userInfo) {
        userApi.saveUserInfo(userInfo, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.saveUserInfoCallback(isCache, response);
            }
        });
    }

    public void imgUp(int type,String imgName, String imgData) {
        fileApi.imgUp(type,imgName, imgData, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.imgUpCallback(isCache, response);
            }
        });
    }


}
