package com.ysy15350.readpacket.account;

/**
 * Created by yangshiyou on 2017/9/20.
 */

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.ysy15350.readpacket.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import api.base.model.Response;
import api.base.model.ResponseHead;
import api.model.BankCardInfo;
import base.data.BaseData;
import base.data.ConfigHelper;
import base.model.UserInfo;
import base.mvp.MVPBaseActivity;
import common.CommFun;
import common.CommFunAndroid;
import common.string.JsonConvertor;

/**
 * 提现
 */
@ContentView(R.layout.activity_withdraw)
public class WithdrawActivity_old extends MVPBaseActivity<WithdrawViewInterface, WithdrawPresenter> implements WithdrawViewInterface {


    @Override
    protected WithdrawPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new WithdrawPresenter(WithdrawActivity_old.this);
    }

    @ViewInject(R.id.et_price)
    private EditText et_price;

    @Override
    public void initView() {

        super.initView();
        setFormHead("提现");

        et_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                calculateRatePrice();

            }
        });
    }

    private void calculateRatePrice() {
        String textPrice = mHolder.getViewText(R.id.et_price);


        if (!CommFunAndroid.isNullOrEmpty(textPrice)) {
            int price = CommFunAndroid.toInt32(textPrice, 0);

            if (mUserInfo == null) {
                showMsg("用户信息获取失败");
                return;
            }

            if (price > mUserInfo.getAccount()) {
                mHolder.setVisibility_VISIBLE(R.id.tv_txRatePrcie).setText(R.id.tv_txRatePrcie, "余额不足");
            } else {

                double ratePrice = price * mUserInfo.getWithdrawrate() / 100;

                if (price > 0 && ratePrice < 0.1) {// 最低0.1元手续费
                    ratePrice = mUserInfo.getWithdrawrate();
                }

                mHolder.setVisibility_VISIBLE(R.id.tv_txRatePrcie).setText(R.id.tv_txRatePrcie, String.format("提现手续费 %.1f 元", ratePrice));

            }

        } else {
            mHolder.setVisibility_GONE(R.id.tv_txRatePrcie);
        }


    }


    @Override
    protected void onResume() {
        super.onResume();


        String bankCardInfoSelectJson = BaseData.getCache("bankCardInfoSelect");
        if (!CommFun.isNullOrEmpty(bankCardInfoSelectJson)) {
            BankCardInfo bankCardInfo = JsonConvertor.fromJson(bankCardInfoSelectJson, BankCardInfo.class);
            bindBankCardInfo(bankCardInfo);
        }

        mPresenter.userInfo();

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
                mHolder
                        .setText(R.id.tv_withdrawRate, String.format("（收取%.1f服务费）", userInfo.getWithdrawrate()))
                        .setText(R.id.tv_account, String.format("可用余额 %.2f 元", userInfo.getAccount()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int mBankId = 0;

    private void bindBankCardInfo(BankCardInfo bankCardInfo) {

        try {
            if (bankCardInfo != null) {
                mBankId = bankCardInfo.getId();

                String bankIcon = bankCardInfo.getBankIcon();
                if (!CommFun.isNullOrEmpty(bankIcon)) {
                    mHolder.setImageURL(R.id.img_bank, bankCardInfo.getBankIcon());
                } else if (bankCardInfo.getBankIconId() != 0) {
                    String url = ConfigHelper.getServerImageUrl();
                    mHolder.setImageURL(R.id.img_bank, url + bankCardInfo.getBankIconId());
                }

                mHolder
                        .setText(R.id.tv_bank, bankCardInfo.getBankname())
                        .setText(R.id.tv_bank_card, bankCardInfo.getCardnum())
                ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 选择银行卡
     *
     * @param view
     */
    @Event(value = R.id.ll_select_bank)
    private void ll_select_bankClick(View view) {
        Intent intent = new Intent(this, BankCardListActivity.class);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void btn_okOnClick(View view) {
        String et_price = mHolder.getViewText(R.id.et_price);

        try {
            if (CommFunAndroid.isNullOrEmpty(et_price)) {
                showMsg("请输入提现金额");
                return;
            }

            int price = CommFun.toInt32(et_price, 0);
            if (mBankId == 0) {
                showMsg("请选择银行卡");
                return;
            }
            if (price == 0) {
                showMsg("请输入提现金额");
                return;
            }

            UserInfo userInfo = BaseData.getInstance().getUserInfo();
            if (userInfo == null) {
                showMsg("用户数据获取失败");
                return;
            }
            double balance = userInfo.getAccount();
            if (price > balance) {
                showMsg("提现金额不能大于余额");
                return;
            }

            showWaitDialog("服务器处理中，请稍后...");
            //mPresenter.withdraw(price, mBankId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void withdrawCallback(boolean isCache, Response response) {
        try {

            hideWaitDialog();


            if (response != null) {
                ResponseHead responseHead = response.getHead();
                if (responseHead != null) {
                    int status = responseHead.getResponse_status();
                    String msg = responseHead.getResponse_msg();
                    if (status == 100) {
                        withdrawSuccess();
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

    /**
     * 提现成功
     */
    private void withdrawSuccess() {
        Intent intent = new Intent(this, AccountResultActivity.class);
        intent.putExtra("title", "提现成功");
        intent.putExtra("content", "你的提现将会在次日24点前到账");
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                BankCardInfo bankCardInfo = (BankCardInfo) data.getSerializableExtra("bankCardInfo");
                BaseData.setCache("bankCardInfoSelect", JsonConvertor.toJson(bankCardInfo));
                bindBankCardInfo(bankCardInfo);
            }
        }
    }

}
