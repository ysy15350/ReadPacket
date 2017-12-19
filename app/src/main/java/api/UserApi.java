package api;

import api.base.model.ApiCallBack;
import base.model.UserInfo;

/**
 * Created by yangshiyou on 2017/11/16.
 */

public interface UserApi {

    /**
     * 获取验证码
     *
     * @param mobile
     * @param type     1：注册；2：其他（默认）
     * @param callBack
     */
    public void getDynCode(String mobile, int type, ApiCallBack callBack);


    /**
     * 用户注册
     * @param mobile
     * @param password
     * @param code
     * @param refereeCode
     * @param callBack
     */
    public void register(String mobile, String password, String code,String refereeCode, ApiCallBack callBack);

    /**
     * 登录
     *
     * @param mobile
     * @param password
     * @param callBack
     */
    public void login(String mobile, String password, ApiCallBack callBack);

    /**
     * 激活账户
     *
     * @param callBack
     */
    public void activate(ApiCallBack callBack);

    /**
     * 获取用户信息
     *
     * @param callBack
     */
    public void userInfo(ApiCallBack callBack);

    /**
     * 修改用户信息
     *
     * @param userInfo
     * @param callBack
     */
    public void saveUserInfo(UserInfo userInfo, ApiCallBack callBack);


    /**
     * 获取邀请列表
     *
     * @param page
     * @param pageSize
     * @param callBack
     */
    public void getInviteList(int page, int pageSize, ApiCallBack callBack);

}
