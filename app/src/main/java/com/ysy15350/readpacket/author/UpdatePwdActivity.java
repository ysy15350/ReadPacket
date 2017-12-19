package com.ysy15350.readpacket.author;


import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.ysy15350.readpacket.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import base.data.BaseData;
import base.model.UserInfo;
import base.mvp.MVPBaseActivity;
import common.CommFun;

/**
 * Created by yangshiyou on 2017/9/20.
 */

@ContentView(R.layout.activity_update_pwd)
public class UpdatePwdActivity extends MVPBaseActivity<UpdatePwdViewInterface, UpdatePwdPresenter>
        implements UpdatePwdViewInterface, Validator.ValidationListener {


    @Override
    protected UpdatePwdPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new UpdatePwdPresenter(UpdatePwdActivity.this);
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


    /**
     * 密码
     */
    @ViewInject(R.id.et_password)
    @Password(messageResId = R.string.register_password_error)
//,scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS
    @Order(7)
    private EditText et_password;


    /**
     * 重复密码
     */
    @ViewInject(R.id.et_password1)
    @ConfirmPassword(messageResId = R.string.register_password_different)
    @Order(8)
    private EditText et_password1;


    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        setFormHead("修改密码");

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

            String password = mHolder.getViewText(R.id.et_password);
            String mobile = mHolder.getViewText(R.id.et_mobile);
            String mobile_code = mHolder.getViewText(R.id.et_mobile_code);

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
}
