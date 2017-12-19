package api;

import api.base.model.ApiCallBack;

/**
 * Created by yangshiyou on 2017/11/16.
 */

public interface FileApi {


    /**
     * 检测版本
     *
     * @param callBack
     */
    public void checkversion(ApiCallBack callBack);

    /**
     *  图片上传
     * @param type
     * @param imgName
     * @param imgData
     * @param callBack
     */
    public void imgUp(int type,String imgName, String imgData, ApiCallBack callBack);


}
