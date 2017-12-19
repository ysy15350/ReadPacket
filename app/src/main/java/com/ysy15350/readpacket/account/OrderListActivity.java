package com.ysy15350.readpacket.account;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.readpacket.R;
import com.ysy15350.readpacket.adapters.ListViewAdpater_Order;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.base.model.ResponseHead;
import api.model.OrderInfo;
import base.data.BaseData;
import base.mvp.MVPBaseListViewActivity;
import common.CommFunAndroid;
import common.string.JsonConvertor;

/**
 * Created by yangshiyou on 2017/9/21.
 */

/**
 * 订单列表
 */
@ContentView(R.layout.activity_order_list)
public class OrderListActivity extends MVPBaseListViewActivity<OrderListViewInterface, OrderListPresenter>
        implements OrderListViewInterface {

    @Override
    protected OrderListPresenter createPresenter() {

        return new OrderListPresenter(OrderListActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        setFormHead("我的账单");
        //setMenu(R.mipmap.icon_add_bank, "添加");
        xListView.setDividerHeight(CommFunAndroid.dip2px(1)); // 设置间距高度(此必须设置在setDivider（）之后，否则无效果)

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        String orderlistJson = BaseData.getCache("orderlist");
        if (!CommFunAndroid.isNullOrEmpty(orderlistJson)) {
            List<OrderInfo> bankInfoList = JsonConvertor.fromJson(orderlistJson, new TypeToken<List<OrderInfo>>() {
            }.getType());

            bindData(bankInfoList);
        }

        page = 1;
        initData(page, pageSize);
    }

    String start_time;
    String end_time;

    @Override
    public void initData(int page, int pageSize) {
        mPresenter.order_list(page, pageSize);
    }

    ListViewAdpater_Order mAdapter;
    List<OrderInfo> mList = new ArrayList<>();


    @Override
    public void order_listCallback(boolean isCache, Response response) {


        try {

            hideWaitDialog();

            if (response != null) {
                ResponseHead responseHead = response.getHead();
                if (responseHead != null) {
                    int status = responseHead.getResponse_status();
                    String msg = responseHead.getResponse_msg();
                    if (status == 100) {

                        BaseData.setCache("orderlist", response.getBodyJson());

                        List<OrderInfo> list = response.getData(new TypeToken<List<OrderInfo>>() {
                        }.getType());
                        bindData(list);


                    } else
                        showMsg(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void bindData(List<OrderInfo> list) {


        if (page == 1) {
            mList.clear();
        } else {
            if (list == null || list.isEmpty()) {
                showMsg("没有更多了");
                xListView.stopLoadMore();
            }
        }

        if (list != null)
            mList.addAll(list);
        mAdapter = new ListViewAdpater_Order(this, mList);

        bindListView(mAdapter);// 调用父类绑定数据方法

        if (list != null || !list.isEmpty()) {
            page++;
        }
    }


}


