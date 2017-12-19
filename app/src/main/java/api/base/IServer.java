package api.base;

import api.base.model.ApiCallBack;

public interface IServer {

    /**
     * 设置服务器地址
     *
     * @param url
     */
    public void setUrl(String url);

    /**
     * 设置方法名称(如果有模块名称:/user/login)
     *
     * @param methodName
     */
    public void setMethodName(String methodName);

    /**
     * 设置参数
     *
     * @param key
     * @param value
     */
    public void setParam(String key, Object value);

    /**
     * 设置回调
     *
     * @param apiCallBack
     */
    public void setApiCallBack(ApiCallBack apiCallBack);


    /**
     * 设置缓存时间，默认不缓存
     *
     * @param cacheTime 单位(秒)
     */
    public void setCacheTime(int cacheTime);

    /**
     * 执行网络请求
     */
    public void request();

}
