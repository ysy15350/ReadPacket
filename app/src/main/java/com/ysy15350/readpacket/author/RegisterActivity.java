package com.ysy15350.readpacket.author;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.ysy15350.readpacket.R;
import com.ysy15350.readpacket.others.WebViewActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import api.base.model.Response;
import api.base.model.ResponseHead;
import base.data.ConfigHelper;
import base.mvp.MVPBaseActivity;
import common.CommFunAndroid;

/**
 * Created by yangshiyou on 2017/9/20.
 */

@ContentView(R.layout.activity_register)
public class RegisterActivity extends MVPBaseActivity<RegisterViewInterface, RegisterPresenter>
        implements RegisterViewInterface, Validator.ValidationListener {

    private static final String TAG = "RegisterActivity";


    @Override
    protected RegisterPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new RegisterPresenter(RegisterActivity.this);
    }

    @ViewInject(R.id.cb_is_room)
    private CheckBox cb_is_room;

    /**
     * 表单验证器
     */
    Validator validator;

    /**
     * 手机号
     */
    @ViewInject(R.id.et_mobile)
    @Pattern(regex = "^0?(13[0-9]|15[012356789]|17[0479]|18[01236789]|14[57])[0-9]{8}$", messageResId = R.string.register_phone_error)
    @Order(5)
    private EditText et_mobile;

    /**
     * 验证码
     */
    @ViewInject(R.id.et_verification_code)
    @NotEmpty(messageResId = R.string.register_code_error)
    @Order(6)
    private EditText et_verification_code;


    /**
     * 密码
     */
    @ViewInject(R.id.et_password)
    @Password(messageResId = R.string.register_password_error)
//,scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS
    @Order(7)
    private EditText et_password;


    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        //setFormHead("注册");

        validator = new Validator(this);

        validator.setValidationListener(this);
    }


    @Event(value = R.id.tv_agreement)
    private void tv_agreementClick(View view) {
        Intent intent = new Intent(RegisterActivity.this, WebViewActivity.class);
        intent.putExtra("param", ConfigHelper.getServerUrl(false) + "/home/Agreement/index?id=17");
        startActivity(intent);
    }

    /**
     * 登录
     *
     * @param view
     */
    @Event(value = R.id.tv_login)
    private void tv_loginClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);

        this.finish();
    }

    /**
     * 注册
     *
     * @param view
     */
    @Event(value = R.id.btn_ok)
    private void btn_okClick(View view) {

        validator.validate();

        if (validationSucceeded) {

            boolean isChecked = cb_is_room.isChecked();
            if (!isChecked) {
                showMsg("请勾选注册协议");
                return;
            }

            String mobile = mHolder.getViewText(R.id.et_mobile);//手机号
            String code = mHolder.getViewText(R.id.et_verification_code);//验证码
            String password = mHolder.getViewText(R.id.et_password);//密码
            String refereeCode = mHolder.getViewText(R.id.et_recommend);//邀请码


            mPresenter.register(mobile, password, code, refereeCode);//注册

        }
    }

    @Override
    public void getDynCodeCallback(boolean isCache, Response response) {
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
                            codeTimer();
                            String code = map.get("code");
                            Log.d(TAG, "getDynCodeCallback: code=" + code);
                            //showMsg(code);
                            showMsg("发送成功");
                        }

                    } else {
                        showMsg(msg);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerCallback(boolean isCache, Response response) {

        try {
            hideWaitDialog();
            if (response != null) {
                ResponseHead responseHead = response.getHead();
                if (responseHead != null) {
                    int status = responseHead.getResponse_status();
                    String msg = responseHead.getResponse_msg();
                    if (status == 100) {
                        Intent intent = new Intent(this, LoginActivity.class);
                        String mobile = mHolder.getViewText(R.id.et_mobile);
                        intent.putExtra("mobile", mobile);
                        startActivity(intent);
                        this.finish();
                    }
                    showMsg(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean validationSucceeded = false;

    @Override
    public void onValidationSucceeded() {
        validationSucceeded = true;
    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        if (errors != null && !errors.isEmpty()) {
            validationSucceeded = false;
            for (ValidationError error : errors) {
                View view = error.getView();
                String message = error.getCollatedErrorMessage(this);
                if (view instanceof EditText) {
                    ((EditText) view).setError(message);
                }
            }
        }
    }


    Timer timer;
    TimerTask task;

    @ViewInject(R.id.btn_send_code)
    private Button btn_send_code;

    @Event(value = R.id.btn_send_code)
    private void btn_get_codeClick(View view) {
        send_mobile_code();

    }


    /**
     * 发送验证码
     */
    private void send_mobile_code() {
        String phone = mHolder.getViewText(R.id.et_mobile);


        if (CommFunAndroid.isNullOrEmpty(phone)) {
            showMsg("请输入手机号");
            return;
        }

        showWaitDialog("短信发送中...");

        mPresenter.getDynCode(phone);
    }

    final Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            mHolder.setText(R.id.btn_send_code, "获取验证码(" + time_second_temp + ")");

            if (time_second_temp == 0) {
                time_second_temp = time_second;
                btn_send_code.setEnabled(true);
                mHolder.setText(R.id.btn_send_code, "获取验证码");
            }

            return false;
        }
    });


    int time_second = 60;

    private static int time_second_temp = 0;

    /**
     * 定时器
     */
    private void codeTimer() {
        try {
            if (time_second_temp == 0) {
                time_second_temp = time_second;
                if (timer != null)
                    timer.cancel();
                if (task != null)
                    task.cancel();
            }

            btn_send_code.setEnabled(false);

            timer = new Timer();

            long delay = 0;
            long intevalPeriod = 1 * 1000;
            // schedules the task to be run in an interval

            task = new TimerTask() {
                @Override
                public void run() {

                    time_second_temp--;

                    if (time_second_temp == 0) {
                        timer.cancel();
                    }

                    handler.sendEmptyMessage(0);
                }
            };

            timer.scheduleAtFixedRate(task, delay, intevalPeriod);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
