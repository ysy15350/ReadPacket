package api;

import api.base.model.ApiCallBack;

/**
 * Created by yangshiyou on 2017/12/25.
 */

public interface PublicApi {

    public void getSystemConfig(ApiCallBack callBack);

    public void noticeInfo(ApiCallBack callBack);

}


