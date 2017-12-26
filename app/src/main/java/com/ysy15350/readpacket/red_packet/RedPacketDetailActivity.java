package com.ysy15350.readpacket.red_packet;


import android.content.Intent;
import android.view.View;

import com.ysy15350.readpacket.R;
import com.ysy15350.readpacket.account.OrderListActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import api.base.model.Response;
import api.base.model.ResponseHead;
import api.model.RedPacketDetail;
import api.model.RedPacketInfo;
import base.data.BaseData;
import base.model.UserInfo;
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
//                RedPacketDetail redPacketDetail = (RedPacketDetail) intent.getSerializableExtra("redPacketDetail");
//                if (redPacketDetail != null) {
//                    bindRedPacketDetail(redPacketDetail);
//                } else {
//                    finish();
//                }
                RedPacketInfo redPacketInfo = (RedPacketInfo) intent.getSerializableExtra("redPacketInfo");
                if (redPacketInfo != null) {

                    bindRedPacket(redPacketInfo);

                    UserInfo userInfo = BaseData.getInstance().getUserInfo();
                    if (userInfo != null) {
                        if (userInfo.getAccount() >= redPacketInfo.getPrice()) {
                            showWaitDialog("服务器处理中...");
                            mPresenter.grabRedPacket(redPacketInfo.getId());
                        } else {
                            showMsg("您的诚信金不足，无法拆开当前红包");
                        }
                    }
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
                        .setText(R.id.tv_luck_num, redPacketDetail.getLuckNum() == null || redPacketDetail.getType() != 1 ? "无" : redPacketDetail.getLuckNum())//幸运数字
                        .setText(R.id.tv_price_actual, redPacketDetail.getPriceActual() + "")//实得金额
                        .setText(R.id.tv_content, JsonConvertor.toJson(redPacketDetail));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑定红包（主信息）
     *
     * @param redPacketInfo
     */
    private void bindRedPacket(RedPacketInfo redPacketInfo) {

        try {
            if (redPacketInfo != null) {
                String red_packet_info = String.format("%d个红包共%d元,幸运数字%d", redPacketInfo.getNum(), redPacketInfo.getPrice(), redPacketInfo.getLuck());
                mHolder
                        .setText(R.id.tv_red_packet_info, red_packet_info)
                ;
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

    @Override
    public void grabRedPacketCallback(boolean isCache, Response response) {
        hideWaitDialog();


        try {
            if (response != null) {
                ResponseHead head = response.getHead();
                if (head != null) {
                    int code = head.getResponse_status();
                    String msg = head.getResponse_msg();
                    if (code == 100) {
                        //抢到的红包明细
                        RedPacketDetail redPacketDetail = response.getData(RedPacketDetail.class);
                        if (redPacketDetail != null) {

//                            Intent intent = new Intent(getActivity(), RedPacketDetailActivity.class);
//                            intent.putExtra("redPacketDetail", redPacketDetail);
//                            startActivity(intent);
                            bindRedPacketDetail(redPacketDetail);
                        }
                    } else {
                        //
                        showMsg(msg);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
