package com.ysy15350.readpacket.red_packet;


import android.content.Intent;
import android.view.View;

import com.ysy15350.readpacket.R;
import com.ysy15350.readpacket.account.OrderListActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import api.model.RedPacketDetail;
import base.mvp.MVPBaseActivity;
import common.string.JsonConvertor;

@ContentView(R.layout.activity_red_packet_detail)
public class RedPacketDetailActivity extends MVPBaseActivity<RedPacketDetailViewInterface, RedPacketDetailPresenter> implements RedPacketDetailViewInterface {

    View contentView;

    @Override
    protected RedPacketDetailPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new RedPacketDetailPresenter(RedPacketDetailActivity.this);
    }

    @Override
    public void initView() {
        super.initView();

        setFormHead("红包详情");

        try {
            Intent intent = getIntent();

            if (intent != null) {
                RedPacketDetail redPacketDetail = (RedPacketDetail) intent.getSerializableExtra("redPacketDetail");
                if (redPacketDetail != null) {
                    bindRedPacketDetail(redPacketDetail);
                } else {
                    finish();
                }
            }
        } catch (Exception e) {
            finish();
            e.printStackTrace();
        }
    }

    /**
     * 绑定红包信息
     *
     * @param redPacketDetail
     */
    private void bindRedPacketDetail(RedPacketDetail redPacketDetail) {

        try {
            if (redPacketDetail != null) {
                mHolder
                        .setText(R.id.tv_price, redPacketDetail.getPrice() + "")//红包金额
                        .setText(R.id.tv_luck_num, redPacketDetail.getLuckNum() == null ? "无" : redPacketDetail.getLuckNum())//幸运数字
                        .setText(R.id.tv_price_actual, redPacketDetail.getPriceActual() + "")//实得金额
                        .setText(R.id.tv_content, JsonConvertor.toJson(redPacketDetail));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void btn_okOnClick(View view) {
        super.btn_okOnClick(view);
        if (isLogin()) {
            startActivity(new Intent(this, HandOutRedPacketActivity.class));
            this.finish();
        }
    }

    /**
     * 查看明细
     *
     * @param view
     */
    @Event(value = R.id.tv_balance)
    private void tv_forgot_pwdClick(View view) {
        if (isLogin()) {

            Intent intent = new Intent(this, OrderListActivity.class);

            startActivity(intent);
            this.finish();
        }
    }

}
