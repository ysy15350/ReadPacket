package api;

import api.base.model.ApiCallBack;

/**
 * Created by yangshiyou on 2017/12/21.
 */

public interface AliPayApi {

    public void oauth_token(String auth_code,String userid,ApiCallBack callBack);

}
