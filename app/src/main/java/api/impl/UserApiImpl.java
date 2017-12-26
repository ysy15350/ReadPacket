package api.impl;

import api.UserApi;
import api.base.IServer;
import api.base.Request;
import api.base.model.ApiCallBack;
import base.data.BaseData;
import base.model.UserInfo;
import common.CommFunAndroid;


public class UserApiImpl implements UserApi {

    private String moduleName = "user/";

    // private String mUrl = Config.getServer_url() + moduleName;

//
//    @Override
//    public void login(UserInfo userInfo, ApiCallBack callBack) {
//        try {
//            if (userInfo != null) {
//                IServer server = new Request();
//
//                server.setMethodName(moduleName + "login");
//
//                server.setParam("userName", userInfo.getUsername());
//                server.setParam("password", userInfo.getPassword());
//                server.setParam("deviceId", CommFunAndroid.getSharedPreferences("device_id"));//CommFunAndroid.setSharedPreferences("device_id", deviceId);
//
//                server.setApiCallBack(callBack);
//
//                server.request();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    @Override
    public void getDynCode(String mobile, int type, ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "getDynCode");

            server.setParam("phone", mobile);
            server.setParam("type", type);

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(String mobile, String password, String code, String refereeCode, ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "register");

            server.setParam("mobile", mobile);
            server.setParam("password", password);
            server.setParam("code", code);
            server.setParam("refereeCode", refereeCode);
            server.setParam("platform", 1);
            server.setParam("deviceId", CommFunAndroid.getSharedPreferences("device_id"));//CommFunAndroid.setSharedPreferences("device_id", deviceId);

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void login(String mobile, String password, ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "login");

            server.setParam("mobile", mobile);
            server.setParam("password", password);
            server.setParam("platform", 1);
            server.setParam("deviceId", CommFunAndroid.getSharedPreferences("device_id"));//CommFunAndroid.setSharedPreferences("device_id", deviceId);

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loginout(ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "loginout");

            server.setParam("platform", 1);
            server.setParam("deviceId", CommFunAndroid.getSharedPreferences("device_id"));//CommFunAndroid.setSharedPreferences("device_id", deviceId);

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activate(ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "activate");

            server.setParam("token", BaseData.getToken());

            String deviceInfo = CommFunAndroid.getDeviceInfoStr();

            server.setParam("deviceInfo", deviceInfo);


            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void userInfo(ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "userInfo");

            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUserInfo(UserInfo userInfo, ApiCallBack callBack) {
        try {
            if (userInfo == null)
                return;
            IServer server = new Request();

            server.setMethodName(moduleName + "saveUserInfo");
            server.setParam("nickName", userInfo.getNickname());
            server.setParam("realName", userInfo.getRealname());
            server.setParam("headimg", userInfo.getHeadimg());
            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMobile(String mobile, String code, ApiCallBack callBack) {
        try {

            IServer server = new Request();

            server.setMethodName(moduleName + "updateMobile");
            server.setParam("mobile", mobile);
            server.setParam("code", code);
            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getInviteList(int page, int pageSize, ApiCallBack callBack) {
        try {
            IServer server = new Request();

            server.setMethodName(moduleName + "getInviteList");
            server.setParam("page", page);
            server.setParam("pageSize", pageSize);
            server.setApiCallBack(callBack);

            server.request();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
