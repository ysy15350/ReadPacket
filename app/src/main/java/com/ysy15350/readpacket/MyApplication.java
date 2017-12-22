package com.ysy15350.readpacket;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

import base.data.BaseData;
import base.data.ConfigHelper;
import cn.jpush.android.api.JPushInterface;
import common.CommFunAndroid;
import common.CrashHandler;
import custom_view.x_view.XListViewFactory;

/**
 * Created by yangshiyou on 2017/11/7.
 */

public class MyApplication extends Application {


    //友盟分享
    {
        //查看签名：keytool -v -list -keystore ***.keystore

        //微信开放平台：https://open.weixin.qq.com/cgi-bin/appdetail?t=manage/detail&type=app&lang=zh_CN&token=a37c4517478b300b4fc2bb7657be34ece7db1d8b&appid=wx0365398576a98012
        //填写MD5签名；去掉 : 号

//        MD5: B4:52:D0:D6:83:AE:AB:4E:C8:B1:74:52:E7:92:16:13
//        SHA1: 39:A9:88:25:2B:76:26:9C:98:B8:4E:87:1A:85:19:F0:15:B4:83:08
//        SHA256: 94:E3:90:8C:59:F7:CA:09:A2:78:2A:71:F0:11:A1:D7:3B:6C:2F:A7:0D:FF:04:96:83:59:8B:06:DC:14:5E:1B
//        签名算法名称: SHA256withRSA
//        版本: 3


        PlatformConfig.setWeixin("wx0365398576a98012", "2907e62f272bc554d25b17916710f24c");
        //PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }

    //Android 各种坑
    //http://blog.csdn.net/cjpx00008/article/details/52100755

    public static Context applicationContext;

    private static final String TAG = "MyApplication";




    public MyApplication getInstance() {
        return (MyApplication) getApplicationContext();
    }

    public static Context getContext() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        applicationContext = getApplicationContext();

        //FileUtils.writeActivityLog(">>>>>>>>>>>>>>>>>MyApplication   onCreate>>>>>>>>>>>>>>>" + this.toString());

        Log.i(TAG, this.toString() + ">>>>>>>>>>>>onCreate" + ">>>>>>>>>>");

        registerActivityLifecycleCallbacks();

        initUtils();//初始化工具类

        initData();//初始化数据，加载配置等

        //initService();//初始化服务
    }


    private void initUtils() {

        // 网址：https://github.com/wyouflf/xUtils3
        // 参考博客：http://blog.csdn.net/tyk9999tyk/article/details/53306035

        //xUtils缓存目录:/data/user/0/com.ysy15350.startcarunion/databases/xUtils_http_cookie.db  (2)(3)

        x.Ext.init(this);
        //x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.

        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        //友盟分享
        UMShareAPI.get(this);
        Config.DEBUG = true;

        ConfigHelper.initConfigInfo();//初始化配置信息

        XListViewFactory.createPageFactory(this);


        //------------------闪退错误捕获，记录日志位置：/aandroid_log/
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        //----------------------------------

        BaseData.getInstance(this);
        CommFunAndroid.mContext = getApplicationContext();


        //下载框架：http://blog.csdn.net/linergou/article/details/52780913

//        // 1、创建Builder
//        FileDownloadConfiguration.Builder builder = new FileDownloadConfiguration.Builder(this);
//
//        String fileDownloadDir= Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
//                "FileDownloader";
//
//        fileDownloadDir=CommFunAndroid.getCahePath(this);
//
//        // 2.配置Builder
//        // 配置下载文件保存的文件夹
//        builder.configFileDownloadDir(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
//                "FileDownloader");
//        // 配置同时下载任务数量，如果不配置默认为2
//        builder.configDownloadTaskSize(3);
//        // 配置失败时尝试重试的次数，如果不配置默认为0不尝试
//        builder.configRetryDownloadTimes(5);
//        // 开启调试模式，方便查看日志等调试相关，如果不配置默认不开启
//        builder.configDebugMode(true);
//        // 配置连接网络超时时间，如果不配置默认为15秒
//        builder.configConnectTimeout(25000);// 25秒
//
//        // 3、使用配置文件初始化FileDownloader
//        FileDownloadConfiguration configuration = builder.build();
//        FileDownloader.init(configuration);


    }

//    PublicApi publicApi = new PublicApiImpl();

    private void initData() {


        Log.d(TAG, "initData() called");

        String deviceId = CommFunAndroid.getDeviceId(getApplicationContext());

        CommFunAndroid.setSharedPreferences("device_id", deviceId);
    }


    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        Log.d(TAG, "onTerminate");
        super.onTerminate();
    }

    int level = 20;

    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        Log.d(TAG, "onLowMemory");
        super.onLowMemory();
        if (level == TRIM_MEMORY_UI_HIDDEN) {
//            isBackground = true;
//            notifyBackground();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        // 程序在内存清理的时候执行
        Log.d(TAG, "onTrimMemory");
        this.level = level;
        super.onTrimMemory(level);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    private void registerActivityLifecycleCallbacks() {
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

                Log.d(TAG, ">>>>>>>>>>>>>>>>>onActivityCreated>>>>>>>>>>>>>>>" + activity.toString());

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.d(TAG, ">>>>>>>>>>>>>>>>>onActivityResumed>>>>>>>>>>>>>>>" + activity.toString());
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                Log.d(TAG, ">>>>>>>>>>>>>>>>>onActivitySaveInstanceState>>>>>>>>>>>>>>>" + activity.toString());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.d(TAG, ">>>>>>>>>>>>>>>>>onActivityDestroyed>>>>>>>>>>>>>>>" + activity.toString());
            }
        });
    }

}
