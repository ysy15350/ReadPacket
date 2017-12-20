package com.ysy15350.readpacket.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.readpacket.R;
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

    /**
     * 通知
     *
     * @param view
     */
    @Event(value = R.id.tv_notify)
    private void tv_notifyClick(View view) {

//        MessageBox.show("开始");
//        startRedPacket();


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
                                        showMsg("余额不足，请充值");
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
