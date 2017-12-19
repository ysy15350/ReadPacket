package com.ysy15350.readpacket.red_packet;


import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.readpacket.R;
import com.ysy15350.readpacket.account.RechargeActivity;
import com.ysy15350.readpacket.adapters.GridViewAdpater_LuckNum;
import com.ysy15350.readpacket.adapters.GridViewAdpater_RedPacket;
import com.ysy15350.readpacket.author.LoginActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import api.base.model.Response;
import api.base.model.ResponseHead;
import api.model.GridViewItemInfo;
import base.data.BaseData;
import base.model.UserInfo;
import base.mvp.MVPBaseActivity;
import common.CommFun;
import common.message.MessageBox;
import custom_view.dialog.ConfirmDialog;

@ContentView(R.layout.activity_hand_out_red_packet)
public class HandOutRedPacketActivity extends MVPBaseActivity<HandOutRedPacketViewInterface, HandOutRedPacketPresenter> implements HandOutRedPacketViewInterface {


    @Override
    protected HandOutRedPacketPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new HandOutRedPacketPresenter(HandOutRedPacketActivity.this);
    }


    @ViewInject(R.id.gv_red_packet)
    private GridView gv_red_packet;


    @ViewInject(R.id.gv_luck_num)
    private GridView gv_luck_num;

    int grabChanceCount = 0;


    @Override
    public void initView() {
        super.initView();

        setFormHead("发红包");

        gv_red_packet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Object item = adapterView.getItemAtPosition(i);
                if (item != null && item instanceof GridViewItemInfo) {
                    GridViewItemInfo gridViewItemInfo = (GridViewItemInfo) item;
                    changeSelectRedPacketBg(view, gridViewItemInfo);
                }
            }
        });

        gv_luck_num.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Object item = adapterView.getItemAtPosition(i);
                if (item != null && item instanceof GridViewItemInfo) {
                    GridViewItemInfo gridViewItemInfo = (GridViewItemInfo) item;
                    changeSelectLuckNumBg(view, gridViewItemInfo);
                }
            }
        });

    }


    private View seletRedPacketView;
    private GridViewItemInfo seletRedPacketInfo;

    private void changeSelectRedPacketBg(View view, GridViewItemInfo gridViewItemInfo) {
        if (seletRedPacketView != null) {
            seletRedPacketView.findViewById(R.id.ll_main).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        if (gridViewItemInfo != null) {
            seletRedPacketInfo = gridViewItemInfo;
            mHolder.setText(R.id.tv_red_packet, "红包金额（" + gridViewItemInfo.getTitle() + "）");
        }
        view.findViewById(R.id.ll_main).setBackgroundColor(Color.parseColor("#ffe8ecef"));

        seletRedPacketView = view;
    }

    private View seletLuckNumView;
    private GridViewItemInfo seletLuckNumInfo;

    private void changeSelectLuckNumBg(View view, GridViewItemInfo gridViewItemInfo) {
        if (seletLuckNumView != null) {
            seletLuckNumView.findViewById(R.id.ll_main).setBackgroundColor(Color.parseColor("#ffffff"));
        }

        if (gridViewItemInfo != null) {
            seletLuckNumInfo = gridViewItemInfo;
            mHolder.setText(R.id.tv_luck_num, "幸运数字（" + gridViewItemInfo.getTitle() + "）");
        }

        view.findViewById(R.id.ll_main).setBackgroundColor(Color.parseColor("#ffe8ecef"));

        seletLuckNumView = view;
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.getGrabChanceCount();

        mPresenter.userInfo();
        UserInfo userInfo = BaseData.getInstance().getUserInfo();
        bindUserInfo(userInfo);

        bindRedPacketInfoGridView();
        bindLuckNumGridView();
    }


    @Override
    public void user_infoCallback(boolean isCache, Response response) {
        try {

            hideWaitDialog();

            if (response != null) {
                ResponseHead responseHead = response.getHead();
                if (responseHead != null) {
                    int status = responseHead.getResponse_status();
                    String msg = responseHead.getResponse_msg();
                    if (status == 100) {

                        UserInfo userInfo = response.getData(UserInfo.class);
                        if (userInfo != null) {
                            userInfo.setIsLogin(1);
                            BaseData.getInstance().setUserInfo(userInfo);
                        }

                        bindUserInfo(userInfo);


                    } else
                        showMsg(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindUserInfo(UserInfo userInfo) {

        try {
            if (userInfo != null) {
                mHolder.setText(R.id.tv_account, userInfo.getAccount() + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getGrabChanceCountCallback(boolean isCache, Response response) {
        try {

            hideWaitDialog();

            if (response != null) {
                ResponseHead responseHead = response.getHead();
                if (responseHead != null) {
                    int status = responseHead.getResponse_status();
                    String msg = responseHead.getResponse_msg();
                    if (status == 100) {

                        Map<String, String> map = response.getData(new TypeToken<Map<String, String>>() {
                        }.getType());
                        if (map != null) {
                            String count = map.get("count");
                            if (!CommFun.isNullOrEmpty(count)) {
                                grabChanceCount = CommFun.toInt32(count, 0);
                            }

                            mHolder.setText(R.id.tv_count, grabChanceCount + "");
                        }

                    } else
                        showMsg(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    GridViewAdpater_RedPacket gridViewAdpaterRedPacket;


    private void bindRedPacketInfoGridView() {

        try {

            List<GridViewItemInfo> list = new ArrayList<>();

            GridViewItemInfo gridViewItemInfo = new GridViewItemInfo();
            gridViewItemInfo.setId(0);
            gridViewItemInfo.setTitle("500元");
            gridViewItemInfo.setValue(500);
            list.add(gridViewItemInfo);

            gridViewItemInfo = new GridViewItemInfo();
            gridViewItemInfo.setId(1);
            gridViewItemInfo.setTitle("200元");
            gridViewItemInfo.setValue(200);
            list.add(gridViewItemInfo);

            gridViewItemInfo = new GridViewItemInfo();
            gridViewItemInfo.setId(2);
            gridViewItemInfo.setTitle("100元");
            gridViewItemInfo.setValue(100);
            list.add(gridViewItemInfo);

            gridViewItemInfo = new GridViewItemInfo();
            gridViewItemInfo.setId(3);
            gridViewItemInfo.setTitle("50元");
            gridViewItemInfo.setValue(50);
            list.add(gridViewItemInfo);

            gridViewItemInfo = new GridViewItemInfo();
            gridViewItemInfo.setId(4);
            gridViewItemInfo.setTitle("30元");
            gridViewItemInfo.setValue(30);
            list.add(gridViewItemInfo);

            gridViewItemInfo = new GridViewItemInfo();
            gridViewItemInfo.setId(5);
            gridViewItemInfo.setTitle("20元");
            gridViewItemInfo.setValue(20);
            list.add(gridViewItemInfo);

            gridViewItemInfo = new GridViewItemInfo();
            gridViewItemInfo.setId(6);
            gridViewItemInfo.setTitle("10元");
            gridViewItemInfo.setValue(10);
            list.add(gridViewItemInfo);

            gridViewItemInfo = new GridViewItemInfo();
            gridViewItemInfo.setId(7);
            gridViewItemInfo.setTitle("5元");
            gridViewItemInfo.setValue(5);
            list.add(gridViewItemInfo);


            gridViewAdpaterRedPacket = new GridViewAdpater_RedPacket(this, list);
            gv_red_packet.setAdapter(gridViewAdpaterRedPacket);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindLuckNumGridView() {

        try {

            GridViewAdpater_LuckNum gridViewAdpaterLuckNum;

            List<GridViewItemInfo> list = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                GridViewItemInfo gridViewItemInfo = new GridViewItemInfo();
                gridViewItemInfo.setId(i);
                gridViewItemInfo.setTitle(i + "");
                gridViewItemInfo.setValue(i);
                list.add(gridViewItemInfo);
            }


            gridViewAdpaterLuckNum = new GridViewAdpater_LuckNum(this, list);
            gv_luck_num.setAdapter(gridViewAdpaterLuckNum);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 充值
     *
     * @param view
     */
    @Event(value = R.id.btn_recharge)
    private void btn_rechargeClick(View view) {
        if (isLogin())
            startActivity(new Intent(this, RechargeActivity.class));
        //startActivity(new Intent(mContext, PayDemoActivity.class));
    }

    @Override
    protected void btn_okOnClick(View view) {
        super.btn_okOnClick(view);

        if (grabChanceCount <= 0) {
            MessageBox.show("你没有发红包的机会了");
            return;
        }

        if (seletRedPacketInfo == null) {
            MessageBox.show("请选择红包");
            return;
        }
        if (seletLuckNumInfo == null) {
            MessageBox.show("请选择幸运数字");
            return;
        }


        int price = seletRedPacketInfo.getValue();
        int luckNum = seletLuckNumInfo.getValue();


        if (isLogin()) {
            UserInfo userInfo = BaseData.getInstance().getUserInfo();
            if (userInfo != null) {
                if (price > userInfo.getAccount()) {
                    showMsg("余额不足,请充值");
                    return;
                } else {
                    showWaitDialog("服务器处理中...");
                    mPresenter.createRedPacket(price, luckNum);
                }
            }
        } else {
            showMsg("请登录");
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }


    }

    @Override
    public void createRedPacketCallback(boolean isCache, Response response) {
        try {
            MessageBox.hideWaitDialog();


            if (response != null) {
                ResponseHead head = response.getHead();
                if (head != null) {
                    int code = head.getResponse_status();
                    String msg = head.getResponse_msg();
                    if (code == 100) {

                        mPresenter.getGrabChanceCount();

                        ConfirmDialog dialog = new ConfirmDialog(this, "系统提示", "发送成功，再发一个？", "再发一个", "下次再发");
                        dialog.setDialogListener(new ConfirmDialog.DialogListener() {
                            @Override
                            public void onCancelClick() {
                                HandOutRedPacketActivity.this.finish();
                            }

                            @Override
                            public void onOkClick() {

                            }
                        });

                        dialog.show();


                    } else {
                        showMsg(msg);
                    }
                }
            }
            mPresenter.userInfo();

//            mContentView.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    mPresenter.userInfo();
//
//                }
//            }, 3 * 1000);//3秒后执行

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
