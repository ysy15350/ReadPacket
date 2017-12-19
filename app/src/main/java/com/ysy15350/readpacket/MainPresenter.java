package com.ysy15350.readpacket;

import android.content.Context;

import api.AccountApi;
import api.FileApi;
import api.base.model.ApiCallBack;
import api.base.model.Response;
import api.base.model.ResponseHead;
import api.impl.AccountApiImpl;
import api.impl.FileApiImpl;
import api.model.FileInfo;
import api.model.ShareInfo;
import base.mvp.BasePresenter;

public class MainPresenter extends BasePresenter<MainViewInterface> {

    public MainPresenter(Context context) {
        super(context);

    }

    FileApi fileApi = new FileApiImpl();
    AccountApi accountApi = new AccountApiImpl();


    public void sharePlatform(ShareInfo shareInfo) {
        accountApi.sharePlatform(shareInfo, new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);

            }
        });
    }


    /**
     * 检测版本
     */
    public void checkversion() {
        fileApi.checkversion(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                getFileInfo(response);
            }
        });
    }

    /**
     * 获取版本信息
     *
     * @param response
     */
    private void getFileInfo(Response response) {

        try {
            if (response != null) {
                ResponseHead responseHead = response.getHead();
                if (responseHead != null) {
                    int status = responseHead.getResponse_status();
                    String msg = responseHead.getResponse_msg();
                    if (status == 100) {//检测到新版本
                        FileInfo fileInfo = response.getData(FileInfo.class);
                        if (fileInfo != null) {
                            String title = "版本更新(" + fileInfo.getVersionname() + ")";
                            String versionName = fileInfo.getVersionname();
                            String content = fileInfo.getDes();
                            String fileSize = fileInfo.getSize() + " M";
                            String url = fileInfo.getDownloadurl();//ConfigHelper.getServerUrl(true)+"/file/downloadApk/com.ysy15350.readpacket_31.apk";
                            mView.updateVersion(title, versionName, content, fileSize, url);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
