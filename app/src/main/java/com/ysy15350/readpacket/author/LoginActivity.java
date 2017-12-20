package com.ysy15350.readpacket.author;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.ysy15350.readpacket.MainActivity;
import com.ysy15350.readpacket.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import api.base.model.Response;
import api.base.model.ResponseHead;
import base.data.BaseData;
import base.model.UserInfo;
import base.mvp.MVPBaseActivity;
import common.CommFun;
import common.CommFunAndroid;
import custom_view.pop.PopExit;

@ContentView(R.layout.activity_login)
public class LoginActivity extends MVPBaseActivity<LoginViewInterface, LoginPresenter> implements LoginViewInterface {

    View contentView;

    @Override
    protected LoginPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new LoginPresenter(LoginActivity.this);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        contentView = getWindow().getDecorView();

        String userName = CommFunAndroid.getSharedPreferences("userName");
        if (!CommFunAndroid.isNullOrEmpty(userName))
            mHolder.setText(R.id.et_userName, userName);
        else {
            Intent intent = getIntent();
            if (intent != null) {
                String mobile = intent.getStringExtra("mobile");
                if (!CommFun.isNullOrEmpty(mobile)) {
                    mHolder.setText(R.id.et_userName, mobile);
                }
            }
        }

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        try {
//            String token = BaseData.getToken();
//            if (!CommFun.isNullOrEmpty(token))
//                mPresenter.activate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 登录按钮点击事件
     *
     * @param view
     */
    @Event(value = R.id.btn_login)
    private void btn_loginClick(View view) {
        String userName = mHolder.getViewText(R.id.et_userName);
        String password = mHolder.getViewText(R.id.et_password);
        // String code = mHolder.getViewText(R.id.et_code);
        if (CommFunAndroid.isNullOrEmpty(userName)) {
            showMsg("请输入用户名或手机号");
            return;
        }

        if (CommFunAndroid.isNullOrEmpty(password)) {
            showMsg("请输入密码");
            return;
        }

        showWaitDialog("正在进行身份验证...");


        mPresenter.login(userName, password);

        // mPresenter.getData();//
        // 调用presenter的获取数据方法，在presenter类中调用bindData接口，本类实现了bindData方法
        //
    }

    @Override
    public void loginCallback(boolean isCache, Response response) {
        try {

            hideWaitDialog();

            if (response != null) {
                ResponseHead responseHead = response.getHead();
                if (responseHead != null) {
                    int status = responseHead.getResponse_status();
                    String msg = responseHead.getResponse_msg();
                    if (status == 100) {

                        String token = response.getData(String.class);

                        BaseData.setToken(token);
                        mPresenter.activate();

//                        UserInfo userInfo = response.getData(UserInfo.class);
//                        if (userInfo != null) {
//                            userInfo.setIsLogin(1);
//                            BaseData.getInstance().setUserInfo(userInfo);
//                        }


                    }
                    showMsg(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activateCallback(boolean isCache, Response response) {
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
                            BaseData.getInstance().setUserInfo(userInfo);
                        }

                        gotoMainActivity();

                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转主页面
     */
    private void gotoMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);

        this.finish();
    }

    /**
     * 忘记密码
     *
     * @param view
     */
    @Event(value = R.id.tv_forgot_pwd)
    private void tv_forgot_pwdClick(View view) {

        String userName = mHolder.getViewText(R.id.et_userName);

        Intent intent = new Intent(this, UpdatePwdActivity.class);

        if (CommFunAndroid.isPhone(userName)) {
            intent.putExtra("mobile", userName);
        }

        startActivity(intent);

    }

    /**
     * 注册
     *
     * @param view
     */
    @Event(value = R.id.tv_register)
    private void tv_registerClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);

        startActivity(intent);

        this.finish();
    }

    /**
     * 点击返回按钮间隔时间
     */
    private long exitTime = 0;

    private PopExit popExit = null;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {



        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            gotoMainActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
