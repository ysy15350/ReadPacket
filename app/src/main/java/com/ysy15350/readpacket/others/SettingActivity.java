package com.ysy15350.readpacket.others;


import android.content.Intent;
import android.view.View;

import com.ysy15350.readpacket.R;
import com.ysy15350.readpacket.author.LoginActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.io.File;

import api.FileApi;
import api.base.model.ApiCallBack;
import api.base.model.Response;
import api.base.model.ResponseHead;
import api.impl.FileApiImpl;
import api.model.FileInfo;
import base.BaseActivity;
import base.data.BaseData;
import common.CommFunAndroid;


/**
 * 设置
 *
 * @author yangshiyou
 */
@ContentView(R.layout.activity_setting)
public class SettingActivity extends BaseActivity {


    @Override
    public void initView() {

        super.initView();
        setFormHead("设置");
    }

    @Override
    protected void onResume() {
        super.onResume();

        String vesionName = CommFunAndroid.getAppVersionName(getApplicationContext());
        mHolder.setText(R.id.tv_version, "当前版本号：" + vesionName);
    }

    /**
     * 清除缓存
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_1)
    private void ll_menu_1Click(View view) {

        String path = CommFunAndroid.getCahePath(this);
        File file = new File(path);
        clearErrorLog(file);

    }

    private void clearErrorLog(File file) {
        try {

            if (!file.exists())
                return;
            if (file.isFile()) {
                file.delete();
                return;
            }
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                clearErrorLog(files[i]);
            }
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 检测更新
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_2)
    private void ll_menu_2Click(View view) {

        showWaitDialog("版本检测中...");

        FileApi fileApi = new FileApiImpl();

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

            hideWaitDialog();

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
                            String url = fileInfo.getDownloadurl();
                            updateVersion(title, versionName, content, fileSize, url);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注销
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_3)
    private void ll_menu_3Click(View view) {

        BaseData.getInstance().loginOut();//修改本地登录状态

        //UserInfo userInfo = BaseData.getInstance().getUserInfo();

//        if (userInfo != null) {
//
//            JPushInterface.deleteAlias(getApplicationContext(), userInfo.getId());//删除别名，LoginActivity登录成功添加的别名
//
//        }

        Intent intent = getIntent();
        setResult(1, intent);//让主页关闭
        this.finish();
        startActivity(new Intent(this, LoginActivity.class));
        //new MessageToast(this, R.mipmap.icon_success, "操作成功").show();
    }


}
