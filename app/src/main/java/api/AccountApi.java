package api;

import api.base.model.ApiCallBack;
import api.model.ShareInfo;

/**
 * Created by yangshiyou on 2017/11/16.
 */

public interface AccountApi {


    /**
     * 充值
     *
     * @param price
     * @param type
     * @param callBack
     */
    public void recharge(int price, int type, ApiCallBack callBack);

    public void rechargeResult(String alipay_trade_app_pay_response, String sign, String sign_type, ApiCallBack callBack);

    /**
     * 提现
     *
     * @param type          1:支付宝(绑定账号，通过id)；2；支付宝（通过账号，如邮箱）；3；银行卡
     * @param alipayAccount
     * @param price
     * @param bankcardId
     * @param realname
     * @param callBack
     */
    public void withdraw(int type, String alipayAccount, int price, int bankcardId, String realname, ApiCallBack callBack);

    /**
     * 获取抢红包机会数量
     *
     * @param callBack
     */
    public void getGrabChanceCount(ApiCallBack callBack);

    /**
     * 获取奖励信息
     *
     * @param callBack
     */
    public void getMyPrizeInfo(ApiCallBack callBack);

    /**
     * 平台分享
     *
     * @param shareInfo
     * @param callBack
     */
    public void sharePlatform(ShareInfo shareInfo, ApiCallBack callBack);

}
