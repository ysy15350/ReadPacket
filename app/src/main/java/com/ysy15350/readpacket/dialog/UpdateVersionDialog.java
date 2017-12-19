package com.ysy15350.readpacket.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ysy15350.readpacket.R;

import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.concurrent.Executor;

import common.CommFunAndroid;
import common.message.MessageBox;

/**
 * 清除缓存
 *
 * @author yangshiyou
 */
public class UpdateVersionDialog extends Dialog {

    private static final String TAG = "UpdateVersionDialog";

    private Context mContext;

    private String mFileSize, mUrl;

    private String mTitle, mVersionName, mContent;

    private View conentView;

    private ProgressBar progressBar;

    private TextView tv_version, tv_version_new, tv_title, tv_file_size, tv_content;

    private View ll_close;
    private Button btn_cancel, btn_ok;

    public UpdateVersionDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub

        mContext = context;

        initView();// 初始化按钮事件

    }

    public UpdateVersionDialog(Context context, String title, String versionName, String content, String fileSize,
                               String url) {

        super(context);
        // TODO Auto-generated constructor stub

        mContext = context;
        mUrl = url;
        mTitle = title;
        mFileSize = fileSize;
        mVersionName = versionName;
        mContent = content;

        initView();// 初始化按钮事件
    }

    private void initView() {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        conentView = inflater.inflate(R.layout.dialog_download, null);

        tv_version = (TextView) conentView.findViewById(R.id.tv_version);
        tv_version_new = (TextView) conentView.findViewById(R.id.tv_version_new);
        tv_title = (TextView) conentView.findViewById(R.id.tv_title);
        tv_file_size = (TextView) conentView.findViewById(R.id.tv_file_size);
        tv_content = (TextView) conentView.findViewById(R.id.tv_content);

        if (!CommFunAndroid.isNullOrEmpty(mTitle)) {
            tv_title.setText(mTitle);
        }

        String versionName = CommFunAndroid.getAppVersionName(mContext);
        if (!CommFunAndroid.isNullOrEmpty(versionName)) {
            tv_version.setText("当前版本：" + versionName);
        }

        if (!CommFunAndroid.isNullOrEmpty(mVersionName)) {
            tv_version_new.setText("最新版本：" + mVersionName);
        } else {
            tv_version_new.setText("最新版本：无");
        }

        if (!CommFunAndroid.isNullOrEmpty(mFileSize)) {
            tv_file_size.setText("文件大小：" + mFileSize);
        } else {
            tv_file_size.setText("文件大小：未知");
        }

        if (!CommFunAndroid.isNullOrEmpty(mContent)) {
            tv_content.setText(mContent);
        }

        progressBar = (ProgressBar) conentView.findViewById(R.id.progressBar);

        ll_close = conentView.findViewById(R.id.ll_close);
        btn_cancel = (Button) conentView.findViewById(R.id.btn_cancel);
        btn_ok = (Button) conentView.findViewById(R.id.btn_ok);

        ll_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mListener != null) {
                    mListener.onCancelClick();
                }
                dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mListener != null) {
                    mListener.onCancelClick();
                }
                dismiss();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mListener != null) {
                    mListener.onOkClick();
                }
                btn_ok.setEnabled(false);
                btn_ok.setText("正在下载");

                download("platform.apk");
                // dismiss();
            }
        });

        // WindowManager.LayoutParams params = this.getWindow().getAttributes();
        // params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        // params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        // dialog.getWindow().setBackgroundDrawable(new
        // ColorDrawable(android.R.color.transparent));
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b0000000")));
        this.setCanceledOnTouchOutside(false);

        // 解决圆角黑边
        // getWindow().setBackgroundDrawable(new BitmapDrawable());
        // 或者
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(conentView);

    }

    /**
     * 点击监听
     */
    private DialogListener mListener;

    /**
     * 设置popupwindow中按钮监听
     *
     * @param listener
     */
    public void setDialogListener(DialogListener listener) {
        this.mListener = listener;
    }

    /**
     * 点击事件监听
     *
     * @author yangshiyou
     */
    public interface DialogListener {

        public void onCancelClick();

        public void onOkClick();

    }

    private final Executor executor = new PriorityExecutor(1, true);


    private void download(final String fileName) {
        try {

            String BASE_PATH = CommFunAndroid.getDiskCachePath();

            String path = BASE_PATH + "/" + fileName;
            File file = new File(path);
            if (file.exists())
                file.delete();

            // private static final String BASE_PATH =
            // Environment.getExternalStorageDirectory().getPath() +
            // File.separator;
            String url = mUrl;// "http://192.168.0.109:8080/api/api/file/platform.apk";
            // url = "http://www.ysy15350.com/video1.mp4";
            //url = "http://www.ysy15350.com/upload/uploadFiles/apks/red_packet(1.0.1).apk";
            RequestParams requestParams = new RequestParams(url);
            requestParams.setAutoResume(true);//设置是否在下载是自动断点续传
            //requestParams.setAutoRename(true);//设置是否根据头信息自动命名文件
            requestParams.setSaveFilePath(path);
            requestParams.setLoadingUpdateMaxTimeSpan(300);
            requestParams.setExecutor(executor);
            requestParams.setCancelFast(true);//是否可以被立即停止.
            x.http().get(requestParams, new Callback.ProgressCallback<File>() {
                @Override
                public void onWaiting() {

                    progressBar.setProgress(0);
                }

                @Override
                public void onStarted() {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(1);
                }

                @Override
                public void onLoading(long total, long current, boolean isDownloading) {
                    // progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    // progressDialog.setMessage("亲，努力下载中。。。");
                    // progressDialog.show();
                    // progressDialog.setMax((int) total);
                    // progressDialog.setProgress((int) current);
                    Log.d(TAG, "onLoading() called with: total = [" + total + "], current = [" + current + "], isDownloading = [" + isDownloading + "]");
                    progressBar.setProgress((int) (current * 100 / total));
                    btn_ok.setText("正在下载( " + (int) (current * 100 / total) + " % )");
                }

                @Override
                public void onSuccess(File result) {
                    progressBar.setProgress(100);

                    dismiss();

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ex.printStackTrace();
                    MessageBox.show("下载失败，请检查网络和SD卡");
                    // Toast.makeText(MainActivity.this, "下载失败，请检查网络和SD卡",
                    // Toast.LENGTH_SHORT).show();
                    // progressDialog.dismiss();
                }

                @Override
                public void onCancelled(CancelledException cex) {
                }

                @Override
                public void onFinished() {
                    progressBar.setVisibility(View.GONE);

                    final String path = CommFunAndroid.getDiskCachePath();

                    MessageBox.show("下载成功");
                    installApk(path, fileName);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 安装APK包
     */
    private void installApk(String path, String fileName) {
        File apkfile = new File(path, fileName);
        if (!apkfile.exists()) {
            // SysFunction.ShowMsgBox(mContext, "安装程序未找到！");
            MessageBox.show("安装程序未找到！");
            return;
        }
        //
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// FLAG_ACTIVITY_NEW_TASK:安装成功后有“完成”和“打开”按钮
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }

}
