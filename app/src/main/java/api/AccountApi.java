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

    public void rechargeResult(String alipay_trade_app_pay_response,String sign,String sign_type, ApiCallBack callBack);

    /**
     * 提现
     *
     * @param price
     * @param bankcardId 银行卡ID
     * @param callBack
     */
    public void withdraw(int price, int bankcardId, ApiCallBack callBack);

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
