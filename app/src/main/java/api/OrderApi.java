package api;

import api.base.model.ApiCallBack;

/**
 * Created by yangshiyou on 2017/11/16.
 */

public interface OrderApi {


    public void orderList(int page, int pageSize, ApiCallBack callBack);



}
