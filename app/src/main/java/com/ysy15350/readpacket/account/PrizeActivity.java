package com.ysy15350.readpacket.account;

import com.ysy15350.readpacket.R;

import org.xutils.view.annotation.ContentView;

import api.base.model.Response;
import api.base.model.ResponseHead;
import api.model.PrizeInfo;
import base.data.BaseData;
import base.mvp.MVPBaseActivity;
import common.CommFunAndroid;
import common.string.JsonConvertor;

/**
 * Created by yangshiyou on 2017/11/18.
 */


@ContentView(R.layout.activity_prize)
public class PrizeActivity extends MVPBaseActivity<PrizeViewInterface, PrizePresenter> implements PrizeViewInterface {


    @Override
    protected PrizePresenter createPresenter() {
        // TODO Auto-generated method stub
        return new PrizePresenter(PrizeActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        setFormHead("我的奖励");

    }


    @Override
    protected void onResume() {
        super.onResume();

        String prizeInfoJson = BaseData.getCache("prizeInfoJson");
        if (!CommFunAndroid.isNullOrEmpty(prizeInfoJson)) {
            PrizeInfo prizeInfo = JsonConvertor.fromJson(prizeInfoJson, PrizeInfo.class);

            bindPrizeInfo(prizeInfo);
        }

        mPresenter.getMyPrizeInfo();
    }

    @Override
    public void getMyPrizeInfoCallback(boolean isCache, Response response) {
        try {

            hideWaitDialog();

            if (response != null) {
                ResponseHead responseHead = response.getHead();
                if (responseHead != null) {
                    int status = responseHead.getResponse_status();
                    String msg = responseHead.getResponse_msg();
                    if (status == 100) {

                        BaseData.setCache("prizeInfoJson", response.getBodyJson());
                        PrizeInfo prizeInfo = response.getData(PrizeInfo.class);


                        bindPrizeInfo(prizeInfo);


                    } else
                        showMsg(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindPrizeInfo(PrizeInfo prizeInfo) {

        try {
            if (prizeInfo != null) {

                mHolder.setText(R.id.tv_totalPrize, String.format("%.2f", prizeInfo.getTotalPrize()))//总收益
                        .setText(R.id.tv_yesterdaySharePrize, String.format("%.2f", prizeInfo.getYesterdaySharePrize()))//昨日分享奖励
                        .setText(R.id.tv_yesterdayRedPacketPrize, String.format("%.2f", prizeInfo.getYesterdayRedPacketPrize()))//昨日红包奖励
                        .setText(R.id.tv_totalSharePrize, String.format("%.2f", prizeInfo.getTotalSharePrize()))//分享总奖励
                        .setText(R.id.tv_totalRedPacketPrize, String.format("%.2f", prizeInfo.getTotalRedPacketPrize()))//红包总奖励
                ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
