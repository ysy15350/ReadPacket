package com.ysy15350.readpacket.account;

import android.content.pm.PackageManager;
import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.ysy15350.readpacket.R;
import com.ysy15350.readpacket.adapters.ListViewAdpater_Invite;
import com.ysy15350.readpacket.dialog.QrCodeDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.base.model.ResponseHead;
import base.data.BaseData;
import base.data.ConfigHelper;
import base.model.UserInfo;
import base.mvp.MVPBaseListViewActivity;
import common.CommFun;
import common.CommFunAndroid;
import common.model.RequestPermissionType;
import common.string.JsonConvertor;
import custom_view.dialog.ConfirmDialog;
import custom_view.qrcode.CanvasRQ;

/**
 * Created by yangshiyou on 2017/12/8.
 */

/**
 * 我的邀请列表
 */
@ContentView(R.layout.activity_invite_list)
public class InvitationsListActivity extends MVPBaseListViewActivity<InviteListViewInterface, InviteListPresenter>
        implements InviteListViewInterface {

    @Override
    protected InviteListPresenter createPresenter() {

        return new InviteListPresenter(InvitationsListActivity.this);
    }

    @Override
    public void initView() {

        super.initView();
        setFormHead("我的邀请");
        //setMenu(R.mipmap.icon_add_bank, "添加");
        xListView.setDividerHeight(CommFunAndroid.dip2px(1)); // 设置间距高度(此必须设置在setDivider（）之后，否则无效果)

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        String invitelistJson = BaseData.getCache("invitelist");
        if (!CommFunAndroid.isNullOrEmpty(invitelistJson)) {
            List<UserInfo> inviteList = JsonConvertor.fromJson(invitelistJson, new TypeToken<List<UserInfo>>() {
            }.getType());

            bindData(inviteList);
        }

        UserInfo userInfo = BaseData.getUserInfo();
        bindUserInfo(userInfo);

        page = 1;
        initData(page, pageSize);
    }

    @Override
    public void initData(int page, int pageSize) {
        mPresenter.userInfo();
        mPresenter.getInviteList(page, pageSize);
    }

    UserInfo mUserInfo;

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

                        mUserInfo = userInfo;

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
                String recommendationCode = userInfo.getRecommendationcode();
                String qrCodeUrl = ConfigHelper.getServerUrl(true) + "appShare?code=" + recommendationCode;

                CanvasRQ qr_code = mHolder.getView(R.id.qr_code);

                mHolder
                        .setText(R.id.tv_recommendationCode, recommendationCode)
                        .setText(R.id.tv_refereecount, userInfo.getRefereecount() + "")
                ;

                if (!CommFun.isNullOrEmpty(recommendationCode)) {
                    qr_code.setUrl(qrCodeUrl);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getInviteList(boolean isCache, Response response) {

        try {

            hideWaitDialog();

            if (response != null) {
                ResponseHead responseHead = response.getHead();
                if (responseHead != null) {
                    int status = responseHead.getResponse_status();
                    String msg = responseHead.getResponse_msg();
                    if (status == 100) {

                        BaseData.setCache("invitelist", response.getBodyJson());

                        List<UserInfo> list = response.getData(new TypeToken<List<UserInfo>>() {
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

    ListViewAdpater_Invite mAdapter;
    List<UserInfo> mList = new ArrayList<>();

    public void bindData(List<UserInfo> list) {


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
        mAdapter = new ListViewAdpater_Invite(this, mList);

        bindListView(mAdapter);// 调用父类绑定数据方法

        if (list != null || !list.isEmpty()) {
            page++;
        }
    }


    @Event(value = R.id.qr_code)
    private void qr_codeClick(View view) {
        openQrCodeDialog();
    }

    @Event(value = R.id.tv_qrcode)
    private void tv_qrcodeClick(View view) {
        openQrCodeDialog();
    }

    private void openQrCodeDialog() {

        boolean isGranted = checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, RequestPermissionType.REQUEST_CODE_ASK_READ_EXTERNAL_STORAGE, new PermissionsResultListener() {
            @Override
            public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
                boolean isGranted = false;
                if (grantResults != null && grantResults != null) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        isGranted = true;

                    }
                }

                if (isGranted) {
                    showQrCodeDialog();
                } else {
                    ConfirmDialog confirmDialog = new ConfirmDialog(InvitationsListActivity.this, "你已拒绝读写手机存储，去权限设置页面打开？");
                    confirmDialog.setDialogListener(new ConfirmDialog.DialogListener() {
                        @Override
                        public void onCancelClick() {

                        }

                        @Override
                        public void onOkClick() {
                            gotoMiuiPermission();
                        }
                    });
                    confirmDialog.show();

                }

            }
        });


    }

    private void showQrCodeDialog() {
        QrCodeDialog dialog = new QrCodeDialog(this);
        dialog.setDialogListener(new QrCodeDialog.DialogListener() {
            @Override
            public void onCancelClick() {

            }

            @Override
            public void btn_1Click() {

            }

            @Override
            public void btn_2Click() {

            }
        });

        dialog.show();
    }


}
