package com.ysy15350.readpacket.account;

import android.content.Context;

import api.OrderApi;
import api.base.model.ApiCallBack;
import api.base.model.Response;
import api.impl.OrderApiImpl;
import base.mvp.BasePresenter;

public class OrderListPresenter extends BasePresenter<OrderListViewInterface> {

    public OrderListPresenter(Context context) {
        super(context);

    }

    OrderApi orderApi = new OrderApiImpl();


    public void order_list(int page, int pageSize) {
        orderApi.orderList(page, pageSize, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                mView.order_listCallback(isCache, response);
            }
        });
    }

}
