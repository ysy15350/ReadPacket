package com.ysy15350.readpacket.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ysy15350.readpacket.R;

import org.wlf.filedownloader.DownloadFileInfo;
import org.wlf.filedownloader.FileDownloadConfiguration;
import org.wlf.filedownloader.FileDownloader;
import org.wlf.filedownloader.listener.OnDeleteDownloadFileListener;
import org.wlf.filedownloader.listener.OnDownloadFileChangeListener;
import org.wlf.filedownloader.listener.OnFileDownloadStatusListener;
import org.wlf.filedownloader.listener.simple.OnSimpleFileDownloadStatusListener;

import java.io.File;

import common.CommFun;
import common.CommFunAndroid;
import common.message.MessageBox;

/**
 * Created by yangshiyou on 2017/12/14.
 */

public class DownloadDialog extends Dialog {

    //使用文件下载框架，xutils3下载有些文件onLoading不回调

    private static final String TAG = "DownloadDialog";


    private Context mContext;

    private String mFileSize, mUrl;

    private String mTitle, mVersionName, mContent;

    private View conentView;

    private ProgressBar progressBar;

    private TextView tv_version, tv_version_new, tv_title, tv_file_size, tv_content, tv_downloadInfo, tv_fileInfo;

    private View ll_close;
    private Button btn_cancel, btn_ok;

    public DownloadDialog(Context context, String title, String versionName, String content, String fileSize,
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

        initDownload();
    }

    private void initDownload() {
        //下载框架：http://blog.csdn.net/linergou/article/details/52780913
        // （https://github.com/wlfcolin/file-downloader/blob/master/README-zh.md）

        // 1、创建Builder
        FileDownloadConfiguration.Builder builder = new FileDownloadConfiguration.Builder(mContext);

        // 2.配置Builder
        // 配置下载文件保存的文件夹
        builder.configFileDownloadDir(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
                "FileDownloader");
        // 配置同时下载任务数量，如果不配置默认为2
        builder.configDownloadTaskSize(3);
        // 配置失败时尝试重试的次数，如果不配置默认为0不尝试
        builder.configRetryDownloadTimes(5);
        // 开启调试模式，方便查看日志等调试相关，如果不配置默认不开启
        builder.configDebugMode(true);
        // 配置连接网络超时时间，如果不配置默认为15秒
        builder.configConnectTimeout(25000);// 25秒

        // 3、使用配置文件初始化FileDownloader
        FileDownloadConfiguration configuration = builder.build();
        FileDownloader.init(configuration);

        FileDownloader.registerDownloadStatusListener(mOnFileDownloadStatusListener);
        FileDownloader.registerDownloadFileChangeListener(mOnDownloadFileChangeListener);

        //下载前删除
        //FileDownloader.delete(mUrl, true, mOnDeleteDownloadFileListener);// 删除单个下载文件
    }

    private void initView() {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        conentView = inflater.inflate(R.layout.dialog_download, null);

        tv_version = (TextView) conentView.findViewById(R.id.tv_version);
        tv_version_new = (TextView) conentView.findViewById(R.id.tv_version_new);
        tv_title = (TextView) conentView.findViewById(R.id.tv_title);
        tv_file_size = (TextView) conentView.findViewById(R.id.tv_file_size);
        tv_content = (TextView) conentView.findViewById(R.id.tv_content);
        tv_downloadInfo = (TextView) conentView.findViewById(R.id.tv_downloadInfo);
        tv_fileInfo = (TextView) conentView.findViewById(R.id.tv_fileInfo);

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

                //FileDownloader.pause(url);// 暂停单个下载任务
                //FileDownloader.pause(urls);// 暂停多个下载任务
                //FileDownloader.pauseAll();// 暂停所有下载任务

                FileDownloader.pauseAll();

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

                //FileDownloader.pause(url);// 暂停单个下载任务
                //FileDownloader.pause(urls);// 暂停多个下载任务
                //FileDownloader.pauseAll();// 暂停所有下载任务

                FileDownloader.pauseAll();

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


                download();
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

    private static final String BASE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator;

    private void download() {
        try {


            FileDownloader.start(mUrl);// 如果文件没被下载过，将创建并开启下载，否则继续下载，自动会断点续传（如果服务器无法支持断点续传将从头开始下载）

            Log.d(TAG, "download() called ,-------mUrl=" + mUrl);

//            FileDownloader.pause(url);// 暂停单个下载任务
//            FileDownloader.pause(urls);// 暂停多个下载任务
//            FileDownloader.pauseAll();// 暂停所有下载任务

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private OnFileDownloadStatusListener mOnFileDownloadStatusListener = new OnSimpleFileDownloadStatusListener() {
        @Override
        public void onFileDownloadStatusRetrying(DownloadFileInfo downloadFileInfo, int retryTimes) {
            // 正在重试下载（如果你配置了重试次数，当一旦下载失败时会尝试重试下载），retryTimes是当前第几次重试
            tv_downloadInfo.setText("下载失败，重试下载第" + retryTimes + "次");
        }

        @Override
        public void onFileDownloadStatusWaiting(DownloadFileInfo downloadFileInfo) {
            // 等待下载（等待其它任务执行完成，或者FileDownloader在忙别的操作）
            tv_downloadInfo.setText("等待下载");
        }

        @Override
        public void onFileDownloadStatusPreparing(DownloadFileInfo downloadFileInfo) {
            // 准备中（即，正在连接资源）
            tv_downloadInfo.setText("正在连接下载资源");
        }

        @Override
        public void onFileDownloadStatusPrepared(DownloadFileInfo downloadFileInfo) {
            // 已准备好（即，已经连接到了资源）
            tv_downloadInfo.setText("找到资源，即将开始下载");
        }

        @Override
        public void onFileDownloadStatusDownloading(DownloadFileInfo downloadFileInfo, float downloadSpeed, long
                remainingTime) {
            if (remainingTime < 0)
                remainingTime = 0;
            String timeStr = CommFun.toDateFormatDuring(remainingTime * 1000);
            timeStr = CommFun.isNullOrEmpty(timeStr) ? "未知" : timeStr;
            String downloadInfo = String.format("正在下载:\n下载速度:%.0fKB/s\n大约还需:%s", downloadSpeed, timeStr);
            tv_downloadInfo.setText(downloadInfo);

            // 正在下载，downloadSpeed为当前下载速度，单位KB/s，remainingTime为预估的剩余时间，单位秒
            Log.d(TAG, "onFileDownloadStatusDownloading() called with: downloadFileInfo = [" + downloadFileInfo + "], downloadSpeed = [" + downloadSpeed + "], remainingTime = [" + remainingTime + "]");
        }

        @Override
        public void onFileDownloadStatusPaused(DownloadFileInfo downloadFileInfo) {
            // 下载已被暂停
            tv_downloadInfo.setText("下载暂停");
        }

        @Override
        public void onFileDownloadStatusCompleted(DownloadFileInfo downloadFileInfo) {
            // 下载完成（整个文件已经全部下载完成）
            Log.d(TAG, "onFileDownloadStatusCompleted() called with: downloadFileInfo = [" + downloadFileInfo + "]");

            String path = downloadFileInfo.getFilePath();
            String fileName = downloadFileInfo.getFileName();

            //MessageBox.show("下载成功");
            tv_downloadInfo.setText("下载完成");
            installApk(path, fileName);

        }

        @Override
        public void onFileDownloadStatusFailed(String url, DownloadFileInfo downloadFileInfo, FileDownloadStatusFailReason failReason) {
            // 下载失败了，详细查看失败原因failReason，有些失败原因你可能必须关心

            String failType = failReason.getType();
            String failUrl = failReason.getUrl();// 或：failUrl = url，url和failReason.getType()会是一样的

            if (FileDownloadStatusFailReason.TYPE_URL_ILLEGAL.equals(failType)) {
                // 下载failUrl时出现url错误
            } else if (FileDownloadStatusFailReason.TYPE_STORAGE_SPACE_IS_FULL.equals(failType)) {
                // 下载failUrl时出现本地存储空间不足
            } else if (FileDownloadStatusFailReason.TYPE_NETWORK_DENIED.equals(failType)) {
                // 下载failUrl时出现无法访问网络
            } else if (FileDownloadStatusFailReason.TYPE_NETWORK_TIMEOUT.equals(failType)) {
                // 下载failUrl时出现连接超时
            } else {
                // 更多错误....
            }

            // 查看详细异常信息
            Throwable failCause = failReason.getCause();// 或：failReason.getOriginalCause()

            // 查看异常描述信息
            String failMsg = failReason.getMessage();// 或：failReason.getOriginalCause().getMessage()
            Log.d(TAG, "onFileDownloadStatusFailed() called with: url = [" + url + "], downloadFileInfo = [" + downloadFileInfo + "], failReason = [" + failReason + "]");
            tv_downloadInfo.setText("下载失败" + failMsg);
        }
    };


    /**
     * 安装APK包
     */
    private void installApk(String path, String fileName) {
        File apkfile = new File(path);
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

    private OnDeleteDownloadFileListener mOnDeleteDownloadFileListener = new OnDeleteDownloadFileListener() {
        @Override
        public void onDeleteDownloadFilePrepared(DownloadFileInfo downloadFileNeedDelete) {
            Log.d(TAG, "onDeleteDownloadFilePrepared() called with: downloadFileNeedDelete = [" + downloadFileNeedDelete + "]");
        }

        @Override
        public void onDeleteDownloadFileSuccess(DownloadFileInfo downloadFileDeleted) {
            Log.d(TAG, "onDeleteDownloadFileSuccess() called with: downloadFileDeleted = [" + downloadFileDeleted + "]");
        }

        @Override
        public void onDeleteDownloadFileFailed(DownloadFileInfo downloadFileInfo, DeleteDownloadFileFailReason failReason) {
            Log.d(TAG, "onDeleteDownloadFileFailed() called with: downloadFileInfo = [" + downloadFileInfo + "], failReason = [" + failReason + "]");
        }
    };

    private OnDownloadFileChangeListener mOnDownloadFileChangeListener = new OnDownloadFileChangeListener() {
        @Override
        public void onDownloadFileCreated(DownloadFileInfo downloadFileInfo) {
            Log.d(TAG, "onDownloadFileCreated() called with: downloadFileInfo = [" + downloadFileInfo + "]");
        }

        @Override
        public void onDownloadFileUpdated(DownloadFileInfo downloadFileInfo, OnDownloadFileChangeListener.Type type) {
            long downloadedSize = downloadFileInfo.getDownloadedSizeLong();
            long fileSize = downloadFileInfo.getFileSizeLong();

            double downloadedSize_1 = downloadedSize / (1024.0 * 1024);
            double fileSize_1 = fileSize / (1024.0 * 1024);

            String str = String.format("完成下载：%.1fM/%.1fM", downloadedSize_1, fileSize_1);

            tv_fileInfo.setVisibility(View.VISIBLE);
            tv_fileInfo.setText(str);

            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress((int) (downloadedSize * 100 / fileSize));
            Log.d(TAG, "onDownloadFileUpdated() called with: downloadFileInfo = [" + downloadFileInfo + "], type = [" + type + "]");
        }

        @Override
        public void onDownloadFileDeleted(DownloadFileInfo downloadFileInfo) {
            Log.d(TAG, "onDownloadFileDeleted() called with: downloadFileInfo = [" + downloadFileInfo + "]");
        }
    };

    @Override
    public void dismiss() {
        super.dismiss();

        //FileDownloader.pause(url);// 暂停单个下载任务
        //FileDownloader.pause(urls);// 暂停多个下载任务
        FileDownloader.pauseAll();// 暂停所有下载任务

        //下载前删除
        FileDownloader.delete(mUrl, true, mOnDeleteDownloadFileListener);// 删除单个下载文件

        FileDownloader.unregisterDownloadStatusListener(mOnFileDownloadStatusListener);//取消注册下载状态监听器(一般在fragment或activity的onDestroy方法中取消注册)
        FileDownloader.unregisterDownloadFileChangeListener(mOnDownloadFileChangeListener);//-取消注册文件数据变化监听器
    }
}