package com.ysy15350.readpacket.account;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.ysy15350.readpacket.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import base.BaseActivity;
import base.data.BaseData;
import base.model.UserInfo;

/**
 * Created by yangshiyou on 2017/9/20.
 */

/**
 * 设置金额
 */
@ContentView(R.layout.activity_set_pay_pwd)
public class SetPayPwdActivity extends BaseActivity
        implements Validator.ValidationListener {


    /**
     * 表单验证器
     */
    Validator validator;


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
        setFormHead("设置支付密码");

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

            Intent intent = getIntent();
            intent.putExtra("password", password);
            setResult(RESULT_OK, intent);

            finish();
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
