package com.ysy15350.readpacket.account;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.readpacket.R;
import com.ysy15350.readpacket.adapters.ListViewAdpater_Bank;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import api.BankApi;
import api.base.model.ApiCallBack;
import api.base.model.Response;
import api.base.model.ResponseHead;
import api.impl.BankApiImpl;
import api.model.BankInfo;
import base.BaseListViewActivity;
import base.data.BaseData;
import common.CommFun;
import common.CommFunAndroid;
import common.string.JsonConvertor;

/**
 * Created by yangshiyou on 2017/9/21.
 */

/**
 * 选择银行卡
 */
@ContentView(R.layout.activity_list)
public class BankListActivity extends BaseListViewActivity {


    @Override
    public void initView() {

        super.initView();
        setFormHead("选择银行");
        //setMenu(R.mipmap.icon_add_bank, "添加");
        xListView.setDividerHeight(CommFunAndroid.dip2px(1)); // 设置间距高度(此必须设置在setDivider（）之后，否则无效果)
        xListView.setPullRefreshEnable(false);
        xListView.setPullLoadEnable(false);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();


        String banklistJson = BaseData.getCache("banklist");
        if (!CommFun.isNullOrEmpty(banklistJson)) {
            List<BankInfo> list = JsonConvertor.fromJson(banklistJson, new TypeToken<List<BankInfo>>() {
            }.getType());
            bindData(list);
        }

        page = 1;
        pageSize = 100;
        initData(page, pageSize);
    }

    BankApi bankApi = new BankApiImpl();

    @Override
    public void initData(int page, int pageSize) {

        showWaitDialog("数据加载中...");

        bankApi.getBankInfoList(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);

                try {

                    hideWaitDialog();


                    if (response != null) {
                        ResponseHead responseHead = response.getHead();
                        if (responseHead != null) {
                            int status = responseHead.getResponse_status();
                            String msg = responseHead.getResponse_msg();
                            if (status == 100) {

                                String banklistJson = JsonConvertor.toJson(response.getBody());//BaseData.getCache("banklist");
                                BaseData.setCache("banklist", banklistJson);

                                List<BankInfo> bankInfos = response.getData(new TypeToken<List<BankInfo>>() {
                                }.getType());
                                if (bankInfos != null) {
                                    bindData(bankInfos);
                                }
                            } else
                                showMsg(msg);

                        }
                    } else {
                        showMsg("系统错误");
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    ListViewAdpater_Bank mAdapter;
    List<BankInfo> mList = new ArrayList<>();


    public void bindData(List<BankInfo> list) {


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
        mAdapter = new ListViewAdpater_Bank(this, mList);

        bindListView(mAdapter);// 调用父类绑定数据方法

        if (list != null || !list.isEmpty()) {
            page++;
        }
    }

    /**
     * 标题栏菜单点击事件，添加银行卡
     *
     * @param view
     */
    @Override
    protected void ll_menuOnClick(View view) {
        super.ll_menuOnClick(view);

        Intent intent = new Intent(this, AddBankActivity.class);

        startActivity(intent);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);

        BankInfo bankInfo = (BankInfo) parent.getItemAtPosition(position);
        if (bankInfo != null) {
            //showMsg(bankInfo.getTitle());
            Intent intent = getIntent();
            intent.putExtra("bank_id", bankInfo.getId());
            intent.putExtra("bank_name", bankInfo.getBankname());
            setResult(RESULT_OK, intent);
            BankListActivity.this.finish();
        }
    }
}


