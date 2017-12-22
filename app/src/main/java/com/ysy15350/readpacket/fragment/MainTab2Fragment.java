package com.ysy15350.readpacket.fragment;


import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.ysy15350.readpacket.MainActivity;
import com.ysy15350.readpacket.R;
import com.ysy15350.readpacket.account.InvitationsListActivity;
import com.ysy15350.readpacket.account.MyInfoActivity;
import com.ysy15350.readpacket.account.OrderListActivity;
import com.ysy15350.readpacket.account.PrizeActivity;
import com.ysy15350.readpacket.account.RechargeActivity;
import com.ysy15350.readpacket.account.WithdrawActivity;
import com.ysy15350.readpacket.others.SettingActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import api.base.model.Response;
import api.base.model.ResponseHead;
import base.data.BaseData;
import base.data.ConfigHelper;
import base.model.UserInfo;
import base.mvp.MVPBaseFragment;
import common.CommFun;
import custom_view.pop.PopShare;


@ContentView(R.layout.activity_main_tab2)
public class MainTab2Fragment extends MVPBaseFragment<MainTab2ViewInterface, MainTab2Presenter>
        implements MainTab2ViewInterface {

    private static final String TAG = "MainTab2Fragment";


    public MainTab2Fragment() {
    }

    @Override
    public MainTab2Presenter createPresenter() {
        // TODO Auto-generated method stub
        return new MainTab2Presenter(getActivity());
    }

    @ViewInject(R.id.swipeRefreshLayout)
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void initView() {
        super.initView();
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume() called,getActivity()==null:" + (getActivity() == null));

        if (getActivity() != null) {

            if (isLogin()) {
                UserInfo userInfo = BaseData.getInstance().getUserInfo();

                bindUserInfo(userInfo);
            }

        }


    }

    @Override
    public void loadData() {
        super.loadData();
        if (isLogin()) {
            if (mPresenter != null)
                mPresenter.userInfo();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
        } else {

        }
    }

    private void bindUserInfo(UserInfo userInfo) {

        try {
            if (userInfo != null && mHolder != null) {

                String nickName = CommFun.isNullOrEmpty(userInfo.getNickname()) ? "未设置昵称" : userInfo.getNickname();
                String mobile = CommFun.isNullOrEmpty(userInfo.getMobile()) ? (isLogin() ? "未绑定手机号" : "点击登录") : userInfo.getMobile();

                mHolder.setText(R.id.tv_nickName, nickName)
                        .setText(R.id.tv_mobile, mobile)
                        .setText(R.id.tv_account, userInfo.getAccount() + "")
                        .setText(R.id.tv_balance, userInfo.getAccount() + "")
                        .setText(R.id.tv_chance_count, userInfo.getGrabchancecount() + " 次")
                ;

                String headImgUrl = ConfigHelper.getServerImageUrl();
                if (userInfo.getHeadimg() != 0) {
                    headImgUrl += userInfo.getHeadimg();
                    mHolder.setImageURL(R.id.img_head, headImgUrl, true);
                } else if (!CommFun.isNullOrEmpty(userInfo.getHeadimgurl())) {
                    mHolder.setImageURL(R.id.img_head, userInfo.getHeadimgurl(), true);
                } else if (!CommFun.isNullOrEmpty(userInfo.getAvatar())) {
                    mHolder.setImageURL(R.id.img_head, userInfo.getAvatar(), true);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void userInfoCallback(boolean isCache, Response response) {
        try {

            hideWaitDialog();
            swipeRefreshLayout.setRefreshing(false);

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

    /**
     * 设置
     *
     * @param view
     */
    @Event(value = R.id.btn_setting)
    private void btn_settingClick(View view) {
        startActivity(new Intent(getActivity(), SettingActivity.class));
    }


    /**
     * 个人资料
     *
     * @param view
     */
    @Event(value = R.id.ll_info)
    private void ll_infoClick(View view) {
        if (isLogin(true))
            startActivity(new Intent(mContext, MyInfoActivity.class));
        //startActivity(new Intent(mContext, PayDemoActivity.class));
    }

    /**
     * 充值
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_1)
    private void ll_menu_1Click(View view) {
        if (isLogin(true))
            startActivity(new Intent(mContext, RechargeActivity.class));
        //startActivity(new Intent(mContext, PayDemoActivity.class));
    }

    /**
     * 提现
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_2)
    private void ll_menu_2Click(View view) {
        if (isLogin(true))
            startActivity(new Intent(mContext, WithdrawActivity.class));//提现

    }

    /**
     * 账单
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_3)
    private void ll_menu_3Click(View view) {
        if (isLogin(true))
            startActivity(new Intent(mContext, OrderListActivity.class));

    }

    /**
     * 奖励
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_4)
    private void ll_menu_4Click(View view) {
        if (isLogin(true))
            startActivity(new Intent(mContext, PrizeActivity.class));//奖励

    }

    /**
     * 邀请
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_5)
    private void ll_menu_5Click(View view) {
        if (isLogin(true))
            startActivity(new Intent(mContext, InvitationsListActivity.class));//奖励

    }

    /**
     * 分享
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_6)
    private void ll_menu_6Click(View view) {
        if (isLogin()) {
            PopShare popShare = new PopShare(getActivity());
            popShare.showPop(mContentView, Gravity.BOTTOM);
            popShare.setPopListener(new PopShare.PopListener() {
                @Override
                public void weixinShare() {
                    umShare(SHARE_MEDIA.WEIXIN);
                }

                @Override
                public void wxFriendShare() {
                    umShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                }

                @Override
                public void qzoneShare() {
                    umShare(SHARE_MEDIA.QZONE);
                }

                @Override
                public void qqShare() {
                    umShare(SHARE_MEDIA.QQ);
                }
            });
        }

    }

    private void umShare(SHARE_MEDIA share_media) {
        ((MainActivity) getActivity()).umShare(share_media);
    }


}
