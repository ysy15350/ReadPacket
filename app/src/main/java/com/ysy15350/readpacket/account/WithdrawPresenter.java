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
    public void withdraw(int type, String alipayAccount, int price, int bankcardId, String realname) {
        // type:1:支付宝(绑定账号，通过id)；2；支付宝（通过账号，如邮箱）；3；银行卡
        accountApi.withdraw( type,  alipayAccount,  price,  bankcardId,  realname, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.withdrawCallback(isCache, response);
            }
        });
    }

}
