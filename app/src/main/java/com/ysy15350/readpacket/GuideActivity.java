package com.ysy15350.readpacket;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;

import com.ysy15350.readpacket.author.LoginActivity;

import org.xutils.view.annotation.ContentView;

import java.util.Timer;
import java.util.TimerTask;

import api.UserApi;
import api.base.model.ApiCallBack;
import api.base.model.Response;
import api.base.model.ResponseHead;
import api.impl.UserApiImpl;
import base.BaseActivity;
import base.data.BaseData;
import common.CommFun;
import common.CommFunAndroid;
import common.ExitApplication;
import custom_view.dialog.ConfirmDialog;

/**
 * Created by yangshiyou on 2017/11/16.
 */

@ContentView(R.layout.activity_guide)
public class GuideActivity extends BaseActivity {

    private static final String TAG = "GuideActivity";

    @Override
    public void initView() {
        super.initView();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        startTimer();

    }

    int status = 0;

    @Override
    protected void onResume() {
        super.onResume();

        int type = CommFunAndroid.getConnectedType(this);

        checkNetWork(type);//检查网络


    }

    private void postDelayed() {
        //http://blog.csdn.net/wangshihui512/article/details/50768294
        //view自带的定时器：postDelayed方法
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {

                status = 1;
                goActivity();


//                MessageBox.show("跳转");

            }
        }, 3 * 1000);//3秒后执行
    }

    int network_type = -1;

    private void checkNetWork(int type) {

        Log.d(TAG, "checkNetWork() called with: type = [" + type + "]");


        try {

            network_type = CommFunAndroid.getConnectedType(this);

            if (network_type == -1) {

                stopTimer();

                showConfirm();//提示确认消息

                return;
            } else if (0 == network_type) {
//            0:mobile(手机网路);1:WIFI;
                showMsg("正在使用手机网络");

            } else if (1 == network_type) {
                showMsg("正在使用WIFI网络");
            }


            startTimer();
            postDelayed();
            activate();


        } catch (Exception e) {
            stopTimer();
            e.printStackTrace();
        }
    }

    /**
     * 提示框，设置网络
     */
    private void showConfirm() {

        Log.d(TAG, "showConfirm() called");

        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {

                //new MessageToast(mContext, R.mipmap.icon_no_network, "手机无网络").show();
                ConfirmDialog confirmDialog = new ConfirmDialog(GuideActivity.this, "网络连接错误", "去设置网络?", "设置", "退出", R.mipmap.icon_no_network);
                confirmDialog.setDialogListener(new ConfirmDialog.DialogListener() {
                    @Override
                    public void onCancelClick() {
                        GuideActivity.this.finish();
                        ExitApplication.getInstance().exit();
                    }

                    @Override
                    public void onOkClick() {
                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        startActivity(intent);
                    }
                });
                confirmDialog.show();

            }
        }, 1 * 1000);//1秒后执行


    }

    private void goActivity() {
        Intent intent = null;

        if (status == 1 && ativateStatus == 0) {
            showWaitDialog("网络较慢，信息读取中...");
        }

        if (ativateStatus != 0 && status != 0) {
            if (ativateStatus == 100)
                intent = new Intent(GuideActivity.this, MainActivity.class);
            else {
                intent = new Intent(GuideActivity.this, LoginActivity.class);
            }

            intent = new Intent(GuideActivity.this, MainActivity.class);

            GuideActivity.this.startActivity(intent);
            GuideActivity.this.finish();
        }
    }

    UserApi userApi = new UserApiImpl();

    private int ativateStatus = 0;

    public void activate() {

        if (CommFun.isNullOrEmpty(BaseData.getToken())) {
            ativateStatus=-1;
            return;
        }
        userApi.activate(new ApiCallBack() {
            @Override
            public void onSuccess(boolean isCache, Response response) {
                super.onSuccess(isCache, response);
                try {

                    hideWaitDialog();


                    if (response != null) {
                        ResponseHead responseHead = response.getHead();
                        if (responseHead != null) {
                            int status = responseHead.getResponse_status();
                            String msg = responseHead.getResponse_msg();
                            ativateStatus = status;
                            if (status == 100) {
                                goActivity();
                            }
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private Timer timer;// 定时器，用于更新下载进度
    private TimerTask task;// 定时器执行的任务

    int seconds = 0;

    private void startTimer() {

        try {
            if (timer != null)
                timer.cancel();

            timer = new Timer();

            if (task != null)
                task.cancel();

            seconds = 0;

            task = new TimerTask() {

                @Override
                public void run() {
                    seconds++;
                    mHandler.sendEmptyMessage(0);
                }
            };

            timer.schedule(task, 0, 1000);
        } catch (Exception e) {
            mHolder.setText(R.id.tv_content, "系统错误：" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void stopTimer() {
        Log.d(TAG, "stopTimer() called");
        try {
            if (timer != null)
                timer.cancel();
            if (task != null) {
                task.cancel();
            }
            seconds = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 广告时间
     */
    private final int ad_time = 4;

    Handler mHandler = new Handler() {

        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {

            Log.d(TAG, msg.what + "handleMessage: " + seconds);

            int time = ad_time - seconds;

            if (time >= 0)
                mHolder.setText(R.id.tv_time, time + "");

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}
