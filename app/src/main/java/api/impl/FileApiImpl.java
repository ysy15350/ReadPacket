package api.impl;

import android.content.pm.PackageInfo;

import api.FileApi;
import api.base.IServer;
import api.base.Request;
import api.base.model.ApiCallBack;
import common.CommFunAndroid;


public class FileApiImpl implements FileApi {

    private String moduleName = "file/";


    @Override
    public void checkversion(ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "checkversion");

            PackageInfo packageInfo = CommFunAndroid.getPackageInfo();
            if (packageInfo != null) {

                server.setParam("package", packageInfo.packageName);
                server.setParam("versionCode", packageInfo.versionCode);
            }

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void imgUp(int type,String imgName, String imgData, ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "imgUp");

            server.setParam("type", type);//1头像
            server.setParam("imgName", imgName);
            server.setParam("imgData", imgData);


            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
