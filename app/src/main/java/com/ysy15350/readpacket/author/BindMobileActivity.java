package com.ysy15350.readpacket.author;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.ysy15350.readpacket.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import api.base.model.Response;
import api.base.model.ResponseHead;
import base.data.BaseData;
import base.model.UserInfo;
import base.mvp.MVPBaseActivity;
import common.CommFun;
import common.CommFunAndroid;

/**
 * Created by yangshiyou on 2017/9/20.
 */

@ContentView(R.layout.activity_bind_mobile)
public class BindMobileActivity extends MVPBaseActivity<BindMobileViewInterface, BindMobilePresenter>
        implements BindMobileViewInterface, Validator.ValidationListener {

    private static final String TAG = "BindMobileActivity";


    @Override
    protected BindMobilePresenter createPresenter() {
        // TODO Auto-generated method stub
        return new BindMobilePresenter(BindMobileActivity.this);
    }

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
    @ViewInject(R.id.et_mobile_code)
    @NotEmpty(messageResId = R.string.register_code_error)
    @Order(6)
    private EditText et_verification_code;


    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        setFormHead("绑定手机号");

        Intent intent = getIntent();
        String mobile = intent.getStringExtra("mobile");

        if (!CommFun.isNullOrEmpty(mobile) && CommFun.isPhone(mobile)) {
            mHolder.setText(R.id.et_mobile, mobile);
        }

        validator = new Validator(this);

        validator.setValidationListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        UserInfo userInfo = BaseData.getInstance().getUserInfo();
        if (userInfo != null) {
            mHolder.setText(R.id.et_mobile, userInfo.getMobile());
        }
    }

    @Override
    protected void btn_okOnClick(View view) {
        super.btn_okOnClick(view);

        validator.validate();

        if (validationSucceeded) {

            String mobile = mHolder.getViewText(R.id.et_mobile);
            String mobile_code = mHolder.getViewText(R.id.et_mobile_code);

            mPresenter.updateMobile(mobile, mobile_code);

        }
    }

    @Override
    public void updateMobileCallback(boolean isCache, Response response) {
        try {
            hideWaitDialog();
            if (response != null) {
                ResponseHead responseHead = response.getHead();
                if (responseHead != null) {
                    int status = responseHead.getResponse_status();
                    String msg = responseHead.getResponse_msg();
                    if (status == 100) {
                        showMsg("绑定成功");
                        finish();
                    } else {
                        showMsg(msg);
                    }
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
