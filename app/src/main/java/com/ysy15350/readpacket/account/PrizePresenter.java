package com.ysy15350.readpacket.account;

import android.content.Context;

import api.AccountApi;
import api.base.model.ApiCallBack;
import api.base.model.Response;
import api.impl.AccountApiImpl;
import base.mvp.BasePresenter;

public class PrizePresenter extends BasePresenter<PrizeViewInterface> {

    public PrizePresenter(Context context) {
        super(context);

    }


    AccountApi accountApi=new AccountApiImpl();


    public void getMyPrizeInfo(){
        accountApi.getMyPrizeInfo(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.getMyPrizeInfoCallback(isCache, response);
            }
        });
    }



}
