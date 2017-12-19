package api;

import api.base.model.ApiCallBack;
import api.model.BankCardInfo;

/**
 * Created by yangshiyou on 2017/11/16.
 */

public interface BankApi {


    public void getBankInfoList(ApiCallBack callBack);

    public void getBankCardInfoList(ApiCallBack callBack);

    /**
     * 添加银行卡
     *
     * @param bankCardInfo
     * @param callBack
     */
    public void addBankCardInfo(BankCardInfo bankCardInfo, ApiCallBack callBack);

}
