package com.ysy15350.readpacket.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.readpacket.R;
import com.ysy15350.readpacket.red_packet.HandOutRedPacketActivity;
import com.ysy15350.readpacket.red_packet.RedPacketDetailActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import api.base.model.Response;
import api.base.model.ResponseHead;
import api.model.RedPacketDetail;
import api.model.RedPacketInfo;
import base.data.BaseData;
import base.model.UserInfo;
import base.mvp.MVPBaseFragment;
import common.CommFunAndroid;
import custom_view.TextSwitchView;
import custom_view.pop.PopRedPacket;
import custom_view.red_packet.MoveModel;
import custom_view.red_packet.RedPacketView;


@ContentView(R.layout.activity_main_tab1)
public class MainTab1Fragment extends MVPBaseFragment<MainTab1ViewInterface, MainTab1Presenter>
        implements MainTab1ViewInterface {

    private static final String TAG = "MainTab1Fragment";


    //红包雨
    //https://github.com/daijianjun/SurfaceViewDemo
    //http://m.blog.csdn.net/wsdaijianjun/article/details/75646081
    //https://github.com/joselyncui/RedPackets


    //红包雨效果
    //http://www.jianshu.com/p/629e9971c48b?utm_source=tuicool&utm_medium=referral

    //红包雨效果
    //http://m.blog.csdn.net/wsdaijianjun/article/details/75646081


    public MainTab1Fragment() {
    }

    @Override
    public MainTab1Presenter createPresenter() {
        // TODO Auto-generated method stub
        return new MainTab1Presenter(getActivity());
    }

    @ViewInject(R.id.swipeRefreshLayout)
    private SwipeRefreshLayout swipeRefreshLayout;


    /**
     * 红包控件
     */
    @ViewInject(R.id.id_redPacket)
    private RedPacketView mRedPacketView;


    @Override
    public void initView() {
        super.initView();

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showWaitDialog("红包加载中...");
                updateUserInfo();
                getRedPacketList();
            }
        });

        mRedPacketView.setListener(new RedPacketView.Listener() {
            @Override
            public void redPacketClick(MoveModel moveModel) {
                showPopRedPacket(moveModel);
            }

            @Override
            public void notifyLoadRedPacket() {
                Log.d(TAG, "--------------------notifyLoadRedPacket() called 通知刷新红包数据--------------------");
                getRedPacketList();
            }
        });
    }

    public int status = 0;

    @Override
    public void onResume() {
        super.onResume();

        try {
            status = 1;
            if (getActivity() != null) {

                showWaitDialog("红包加载中...");
                getRedPacketList();
            }

            updateUserInfo();

            setTextSwitch("旺财红包,红包抢不停,红包新玩法");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUserInfo() {
        UserInfo userInfo = BaseData.getUserInfo();
        if (userInfo != null) {
            bindUserInfo(userInfo);
        }
    }

    /**
     * 绑定用户信息
     *
     * @param userInfo
     */
    private void bindUserInfo(UserInfo userInfo) {


        try {
            if (userInfo != null) {
                mHolder.setText(R.id.btn_createRedPacket, String.format("发红包(%d)", userInfo.getGrabchancecount()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            pauseRedPacket();
//            CommFunAndroid.hideSoftInput(mHolder.getView(R.id.et_tent));
        }
    }

    int page = 1, pageSize = 100;

    public void getRedPacketList() {
        Log.d(TAG, "getRedPacketList() called isLogin=" + isLogin());
        //if (isLogin()) {
        if (mPresenter != null) {
            mPresenter.getRedPacketList(page, pageSize);
        }
        //}
    }

    @Override
    public void getRedPacketListCallback(boolean isCache, Response response) {
        try {
            hideWaitDialog();
            swipeRefreshLayout.setRefreshing(false);
            if (response != null) {
                ResponseHead responseHead = response.getHead();
                if (responseHead != null) {
                    int code = responseHead.getResponse_status();
                    String msg = responseHead.getResponse_msg();
                    if (code == 100) {
                        List<RedPacketInfo> redPacketInfos = response.getData(new TypeToken<List<RedPacketInfo>>() {
                        }.getType());

                        Log.d(TAG, "getRedPacketListCallback: 更新红包数据数量：" + redPacketInfos.size());

                        bindRedPacketInfo(redPacketInfos);


                    } else {
                        showMsg(msg);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<RedPacketInfo> mRedPacketInfos;

    private void bindRedPacketInfo(List<RedPacketInfo> redPacketInfos) {
        try {
            mRedPacketInfos = redPacketInfos;
            if (redPacketInfos != null && redPacketInfos.size() > 0) {
                prepareRedPacket(redPacketInfos);
                startRedPacket();
                //view自带的定时器：postDelayed方法
//                if (mContentView != null) {
//                    mContentView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            startRedPacket();
//                        }
//                    }, 1 * 1000);//1秒后执行
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseRedPacket();
    }


    String[] messageArray;

    /**
     * 设置滚动通知
     *
     * @param result
     */
    private void setTextSwitch(String result) {
        if (!CommFunAndroid.isNullOrEmpty(result)) {
            messageArray = result.split(",");

            TextSwitchView tv_message = mHolder.getView(R.id.tv_message);
            // showMsg(result + (tv_message == null));

            tv_message.setResources(messageArray);
            tv_message.setTextStillTime(5000);
        }
    }


    /**
     * 发红包
     *
     * @param view
     */
    @Event(value = R.id.btn_createRedPacket)
    private void btn_createRedPacketClick(View view) {

        if (isLogin(true)) {

            int grabRedPacketChanceCount = 0;

            UserInfo userInfo = BaseData.getUserInfo();
            if (userInfo != null) {
                grabRedPacketChanceCount = userInfo.getGrabchancecount();
            }

            if (grabRedPacketChanceCount > 0) {
                startActivity(new Intent(getActivity(), HandOutRedPacketActivity.class));
            } else {
                showMsg("你没有发红包的机会了");
            }
        }

    }


    /**
     * 关闭公告
     *
     * @param view
     */
    @Event(value = R.id.btn_close)
    private void btn_closeClick(View view) {

        mHolder.setVisibility_GONE(R.id.rl_notify);

    }

    /**
     * 规则
     *
     * @param view
     */
    @Event(value = R.id.ll_rule)
    private void ll_ruleRedPacketClick(View view) {

        showMsg("规则");

    }


    private void prepareRedPacket(List<RedPacketInfo> redPacketInfos) {
        if (mRedPacketView != null)
            mRedPacketView.prepare(redPacketInfos);
    }


    /**
     * 红包开始
     */
    private void startRedPacket() {
        Log.d(TAG, "startRedPacket: ");
        if (mRedPacketView != null) {
            if (mRedPacketView.getStatus() == 0)
                mRedPacketView.start();
        }
    }

    /**
     * 红包暂停
     */
    private void pauseRedPacket() {
        if (mRedPacketView != null)
            mRedPacketView.pause();
    }

    /**
     * 红包重新开始
     */
    private void resumeRedPacket() {
        if (mRedPacketView != null)
            mRedPacketView.resume();
    }

    private PopRedPacket popRedPacket;
    private RedPacketInfo mRedPacketInfo;


    /**
     * 打开红包显示红包金额
     *
     * @param moveModel
     */
    private void showPopRedPacket(MoveModel moveModel) {

        try {
            if (moveModel != null) {

                popRedPacket = new PopRedPacket(getActivity(), moveModel);

                popRedPacket.setPopListener(new PopRedPacket.PopListener() {
                    @Override
                    public void onClick(RedPacketInfo redPacketInfo) {
                        if (redPacketInfo != null) {

                            mRedPacketInfo = redPacketInfo;
                            if (isLogin(true)) {

                                UserInfo userInfo = BaseData.getInstance().getUserInfo();
                                if (userInfo != null) {
                                    if (userInfo.getAccount() >= redPacketInfo.getPrice()) {
                                        showWaitDialog("服务器处理中...");
                                        mPresenter.grabRedPacket(redPacketInfo.getId());
                                    } else {
                                        showMsg("");
                                    }
                                }
                            }
                        }
                    }
                });


                popRedPacket.showPop(mContentView, Gravity.CENTER);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void grabRedPacketCallback(boolean isCache, Response response) {
        hideWaitDialog();

        if (mRedPacketView != null) {
            mRedPacketView.removeGrabedRedPacket(mRedPacketInfo);
        }

        if (response != null) {
            ResponseHead head = response.getHead();
            if (head != null) {
                int code = head.getResponse_status();
                String msg = head.getResponse_msg();
                if (code == 100) {
                    //抢到的红包明细
                    RedPacketDetail redPacketDetail = response.getData(RedPacketDetail.class);
                    if (redPacketDetail != null) {

                        Intent intent = new Intent(getActivity(), RedPacketDetailActivity.class);
                        intent.putExtra("redPacketDetail", redPacketDetail);
                        startActivity(intent);
                    }
                } else {
                    //
                    showMsg(msg);
                }
            }
        }
    }


}
