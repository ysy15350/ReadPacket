package com.ysy15350.readpacket.account;

import android.content.Context;

import api.AccountApi;
import api.UserApi;
import api.base.model.ApiCallBack;
import api.base.model.Response;
import api.impl.AccountApiImpl;
import api.impl.UserApiImpl;
import base.mvp.BasePresenter;

public class WithdrawPresenter extends BasePresenter<WithdrawViewInterface> {

    public WithdrawPresenter(Context context) {
        super(context);

    }

    UserApi userApi = new UserApiImpl();

    AccountApi accountApi = new AccountApiImpl();

    public void userInfo() {
        userApi.userInfo(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.user_infoCallback(isCache, response);
            }
        });
    }

    /**
     * 提现
     *
     * @param price
     * @param bankcardId 银行卡ID
     */
    public void withdraw(int price, int bankcardId) {
        accountApi.withdraw(price, bankcardId, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.withdrawCallback(isCache, response);
            }
        });
    }


}
