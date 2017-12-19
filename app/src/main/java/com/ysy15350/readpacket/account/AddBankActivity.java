package com.ysy15350.readpacket.account;

/**
 * Created by yangshiyou on 2017/9/20.
 */


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

import api.BankApi;
import api.base.model.ApiCallBack;
import api.base.model.Response;
import api.base.model.ResponseHead;
import api.impl.BankApiImpl;
import api.model.BankCardInfo;
import base.BaseActivity;
import base.data.BaseData;
import base.model.UserInfo;


/**
 * 添加银行
 */
@ContentView(R.layout.activity_add_bank)
public class AddBankActivity extends BaseActivity implements Validator.ValidationListener {

    private static final String TAG = "AddBankActivity";

    @Override
    public void initView() {

        super.initView();
        setFormHead("添加银行卡");

        validator = new Validator(this);

        validator.setValidationListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserInfo userInfo = BaseData.getInstance().getUserInfo();
        if (userInfo != null) {
            mHolder.setText(R.id.et_mobile, userInfo.getMobile());
        }
    }

    /**
     * 持卡人
     */
    @ViewInject(R.id.et_userName)
    @NotEmpty(messageResId = R.string.add_bank_card_mustName)
    @Order(1)
    private EditText et_userName;

    /**
     * 手机号
     */
    @ViewInject(R.id.et_mobile)
    @Pattern(regex = "^0?(13[0-9]|15[012356789]|17[03479]|18[01236789]|14[57])[0-9]{8}$", messageResId = R.string.register_phone_error)
    @Order(2)
    private EditText et_mobile;

    /**
     * 持卡人
     */
    @ViewInject(R.id.et_bank_card)
    @NotEmpty(messageResId = R.string.add_bank_card_mustCard)
    @Order(3)
    private EditText et_bank_card;


    private final int REQUEST_CODE_BANK = 1;

    /**
     * 选择银行
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_4)
    private void ll_menu_4Click(View view) {
        Intent intent = new Intent(this, BankListActivity.class);
        startActivityForResult(intent, REQUEST_CODE_BANK);
        //startActivity(new Intent(this, BankListActivity.class));
    }

    /**
     * 表单验证器
     */
    Validator validator;

    @Override
    protected void btn_okOnClick(View view) {
        super.btn_okOnClick(view);

        validator.validate();

        if (validationSucceeded) {

            String et_userName = mHolder.getViewText(R.id.et_userName);
            String mobile = mHolder.getViewText(R.id.et_mobile);
            String et_bank_card = mHolder.getViewText(R.id.et_bank_card);
            String et_open_bank = mHolder.getViewText(R.id.et_open_bank);

            if (mBankId == -1) {
                showMsg("请选择银行");
                return;
            }

            BankCardInfo bankCardInfo = new BankCardInfo();
            bankCardInfo.setFullname(et_userName);
            bankCardInfo.setPhone(mobile);
            bankCardInfo.setCardnum(et_bank_card);
            bankCardInfo.setBankid(mBankId);
            bankCardInfo.setBanksubname(et_open_bank);


            addBankCardInfo(bankCardInfo);


            //add_user_bank(2, et_userName, mobile, et_bank_card, mBankId, et_open_bank);
        }
    }

    BankApi bankApi = new BankApiImpl();

    private void addBankCardInfo(BankCardInfo bankCardInfo) {
        showWaitDialog("服务器处理中...");
        bankApi.addBankCardInfo(bankCardInfo, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);

                try {

                    hideWaitDialog();


                    if (response != null) {
                        ResponseHead responseHead = response.getHead();
                        if (responseHead != null) {
                            int status = responseHead.getResponse_status();
                            String msg = responseHead.getResponse_msg();
                            if (status == 100) {
                                showMsg("添加成功");
                                AddBankActivity.this.finish();
                            } else
                                showMsg(msg);

                        }
                    } else {
                        showMsg("系统错误");
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
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

    int mBankId = -1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        switch (requestCode) {
            case REQUEST_CODE_BANK:
                if (resultCode == RESULT_OK) {
                    int bank_id = data.getIntExtra("bank_id", 0);
                    String bank_name = data.getStringExtra("bank_name");
                    mBankId = bank_id;
                    mHolder.setText(R.id.tv_bank, bank_name);
//                    showMsg(bank_id + "接收银行id" + bank_name);
                }
                break;
        }
    }
}
