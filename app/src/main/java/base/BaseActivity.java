package base;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.ysy15350.readpacket.BuildConfig;
import com.ysy15350.readpacket.GuideActivity;
import com.ysy15350.readpacket.R;
import com.ysy15350.readpacket.author.LoginActivity;
import com.ysy15350.readpacket.dialog.DownloadDialog;

import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import base.data.BaseData;
import common.AppStatusManager;
import common.CommFun;
import common.CommFunAndroid;
import common.message.MessageBox;
import common.model.RequestPermissionType;
import custom_view.dialog.ConfirmDialog;


/**
 * Created by yangshiyou on 2016/11/29.
 */

public class BaseActivity extends AppCompatActivity implements IView {

    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;

    /**
     * 控件ViewGroup
     */
    protected View mContentView;

    protected ViewHolder mHolder;

    /**
     * 界面标题
     */
    protected String mTitle = "";

    /**
     * 是否需要登录
     */
    boolean mNeedLogin = false;


    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ACTIVITY_COUNT++;
        String str = this.toString();
        activityNames.add(str);

        StringBuilder sb = new StringBuilder();

        for (String activityName :
                activityNames) {
            sb.append(activityName + "\n");
        }
        Log.d(TAG, "onCreate() called with: ******************activityNames****************** = [" + sb.toString() + "]");

        Log.d(TAG, "onCreate() called with: savedInstanceState = [" + savedInstanceState + "]" + "------------------ACTIVITY_COUNT=" + ACTIVITY_COUNT);

        if (1 == 1) {
            //showMsg("不检查");
        } else {
            checkAppStatus();//如果是重新打开的应用，重新从入口进入，缺陷：不会回到选择照片的页面
        }


        x.view().inject(this);

        //ExitApplication.getInstance().addActivity(this);// 添加当前Activity


        mContentView = getWindow().getDecorView();

        mHolder = ViewHolder.get(this, mContentView);


    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    //http://blog.csdn.net/u011511577/article/details/54603256
    protected void checkAppStatus() {
        if (AppStatusManager.getInstance().getAppStatus() == AppStatusManager.AppStatusConstant.APP_FORCE_KILLED) {
            //该应用已被回收，应用启动入口SplashActivity，走重启流程
            Intent intent = new Intent(this, GuideActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //showMsg("应用被回收重新执行");
        }
    }


    /**
     * 初始化，1：initView；2：readCahce；3：loadData；4：bindData
     */
    private void init() {

        initView();

        initData();

        readCahce();

        loadData();

        bindData();
    }

    /**
     * 初始化，1：initView；2：readCahce；3：loadData；4：bindData
     *
     * @param context
     * @param contentView
     * @param title
     * @param isNeedLogin
     */
    public void init(Context context, View contentView, String title, boolean isNeedLogin) {

        mContentView = contentView;
        mTitle = title;
        mNeedLogin = isNeedLogin;


        initView();

        initData();

        readCahce();

        loadData();

        bindData();
    }


    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        // 填充状态栏
        CommFunAndroid.fullScreenStatuBar(this);
    }

    @Override
    public void readCahce() {
    }

    @Override
    public void loadData() {
    }

    @Override
    public void bindData() {

    }

    protected boolean isLogin() {

        String token = BaseData.getToken();
        boolean isLogin = !CommFun.isNullOrEmpty(token);
//        if (isLogin) {
//            isLogin = BaseData.getInstance().getUserInfo() != null;
//        }
        return isLogin;

    }

    /**
     * 是否登录
     * @param must 是否必须登录，如果true,跳转到登录页面
     * @return
     */
    protected boolean isLogin(boolean must) {
        try {
            String token = BaseData.getToken();
            boolean isLogin = !CommFun.isNullOrEmpty(token);

            if (!isLogin && must) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                this.finish();
            }

            return isLogin;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 设置标题（不带返回箭头）
     *
     * @param title
     */
    protected void setTitle(String title) {
        if (!CommFunAndroid.isNullOrEmpty(title))
            mHolder.setText(R.id.tv_form_title, title);
    }

    /**
     * 设置是否显示返回箭头
     *
     * @param isBack
     */
    protected void setBtnBack(boolean isBack) {
        if (isBack)
            mHolder.setVisibility_VISIBLE(R.id.btn_back);
        else
            mHolder.setVisibility_GONE(R.id.btn_back);
    }


    /**
     * 设置头部(带返回箭头)
     *
     * @param title
     */
    protected void setFormHead(String title) {
        setTitle(title);
        setBtnBack(true);
    }

    /**
     * 设置菜单
     *
     * @param menu
     */
    protected void setMenuText(String menu) {
        if (!CommFunAndroid.isNullOrEmpty(menu)) {
            mHolder.setVisibility_VISIBLE(R.id.ll_menu).setVisibility_VISIBLE(R.id.tv_menu).setText(R.id.tv_menu, menu);
        }
    }

    /**
     * 设置菜单
     *
     * @param menu
     */
    protected void setMenu(int icon, String menu) {
        if (!CommFunAndroid.isNullOrEmpty(menu)) {
            mHolder.setVisibility_VISIBLE(R.id.ll_menu)
                    .setVisibility_VISIBLE(R.id.img_menu)
                    .setImageResource(R.id.img_menu, icon)
                    .setText(R.id.tv_menu, menu);
        }
    }

    @Override
    public void showMsg(String msg) {
        if (CommFunAndroid.isNullOrEmpty(msg))
            return;
        MessageBox.show(msg);
    }

    @Override
    public void showWaitDialog(String msg) {
        if (CommFunAndroid.isNullOrEmpty(msg))
            return;
        MessageBox.showWaitDialog(this, msg);
    }

    @Override
    public void hideWaitDialog() {

        MessageBox.hideWaitDialog();
    }

    @Override
    public void setViewText(int id, CharSequence text) {
        if (mHolder != null)
            mHolder.setText(id, text);
    }

    @Override
    public String getViewText(int id) {
        if (mHolder != null)
            return mHolder.getViewText(id);
        return "";
    }

    @Override
    public void setTextColor(int id, int color) {
        if (mHolder != null)
            mHolder.setTextColor(id, color);
    }

    @Override
    public void setBackgroundColor(int id, int color) {
        if (mHolder != null)
            mHolder.setBackgroundColor(id, color);
    }

    @Override
    public void setVisibility_GONE(int id) {
        if (mHolder != null)
            mHolder.setVisibility_GONE(id);
    }

    @Override
    public void setVisibility_VISIBLE(int id) {
        if (mHolder != null)
            mHolder.setVisibility_VISIBLE(id);

    }

    /**
     * 确认按钮实际
     *
     * @param view
     */
    @Event(value = R.id.btn_ok)
    private void btn_okClick(View view) {
        btn_okOnClick(view);
    }

    /**
     * 确认按钮点击事件调用方法
     *
     * @param view
     */
    protected void btn_okOnClick(View view) {

    }

    /**
     * 返回
     *
     * @param view
     */
    @Event(value = R.id.btn_back)
    private void btn_backClick(View view) {
        btn_backOnClick(view);
        finish();

    }

    /**
     * 返回事件
     *
     * @param view
     */
    protected void btn_backOnClick(View view) {
    }

    /**
     * 标题右侧菜单点击事件
     *
     * @param view
     */
    @Event(value = R.id.ll_menu)
    private void ll_menuClick(View view) {
        ll_menuOnClick(view);
    }

    /**
     * 标题右侧菜单点击事件
     *
     * @param view
     */
    protected void ll_menuOnClick(View view) {
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //btn_backOnClick(null);
        }

        return super.onKeyDown(keyCode, event);

    }

    /**
     * 打开更新弹窗，下载更新
     *
     * @param title
     * @param versionName
     * @param content
     * @param fileSize
     * @param url
     */
    protected void updateVersion(final String title,final String versionName,final String content,final String fileSize,final String url) {

        boolean isGranted = checkPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, RequestPermissionType.REQUEST_CODE_ASK_READ_EXTERNAL_STORAGE, new PermissionsResultListener() {
            @Override
            public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
                boolean isGranted = false;
                if (grantResults != null && grantResults != null) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        isGranted = true;

                    }
                }

                if (isGranted) {

                    DownloadDialog dialog = new DownloadDialog(BaseActivity.this, title,
                            versionName, content, fileSize,
                            url);

                    dialog.setDialogListener(new DownloadDialog.DialogListener() {
                        @Override
                        public void onCancelClick() {

                        }

                        @Override
                        public void onOkClick() {

                        }
                    });

                    dialog.show();


                } else {
                    ConfirmDialog confirmDialog = new ConfirmDialog(BaseActivity.this, "你已拒绝读写手机存储，去权限设置页面打开？");
                    confirmDialog.setDialogListener(new ConfirmDialog.DialogListener() {
                        @Override
                        public void onCancelClick() {

                        }

                        @Override
                        public void onOkClick() {
                            gotoMiuiPermission();
                        }
                    });
                    confirmDialog.show();

                }

            }
        });


    }


    //重写会导致Android4.4报错
    //java.lang.ClassNotFoundException: Didn’t find class “android.os.PersistableBundle” on path: DexPathList
    //    @Override
    //    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
    //        super.onSaveInstanceState(outState, outPersistentState);
    //        Log.i(TAG, "<<<<<<<<<<<<<<<**********onSaveInstanceState*************>>>>>>>>>>>>>>" + this.toString());
    //    }

    //    在手机上调用系统相机的时候，有很大的几率会导致内存不足从而调用相机的app或者app的调用相机的Activity界面被强制回收
    // ，所以调用相机的Activity重写 onSaveInstanceState 是非常必要的，
    // 在onSaveInstanceState方法中将局部变量保存起来，
    // 同时在  onRestoreInstanceState  方法中重新获取这些局部变量并做必要的逻辑处理。

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "<<<<<<<<<<<<<<<**********onRestoreInstanceState*************>>>>>>>>>>>>>>" + this.toString());
    }

    /**
     * 申请读取文件权限
     */
    public boolean callReadExtrnalStoreagePermission(Activity activity) {
        Log.d(TAG, "callReadExtrnalStoreagePermission() called with: activity = [" + activity + "]");

        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            Log.d(TAG, "callReadExtrnalStoreagePermission() called with: checkCallPhonePermission = [" + checkCallPhonePermission + "]");

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        activity
                        , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}
                        , RequestPermissionType.REQUEST_CODE_ASK_READ_EXTERNAL_STORAGE);
                return false;
            } else {
                return true;
            }
        }

        return true;
    }

    public void getRequestPermissions(int requestCode) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //动态注册权限：
// 首先是判断
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // 显示给用户的解释
                    MessageBox.show("请同意权限");
                } else {
                    // No explanation needed, we can request the permission.
                    String[] mPermissionList = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.CALL_PHONE, android.Manifest.permission.READ_LOGS, android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.SET_DEBUG_APP, android.Manifest.permission.SYSTEM_ALERT_WINDOW, android.Manifest.permission.GET_ACCOUNTS, android.Manifest.permission.WRITE_APN_SETTINGS};
                    ActivityCompat.requestPermissions(this, mPermissionList, requestCode);
                }
            } else {
                //umShare();
            }


        } else {
            //umShare();
        }


    }


    /**
     * 检查是否拥有权限
     *
     * @param permission
     * @param requestCode
     */
    protected boolean checkPermission(String permission, int requestCode, PermissionsResultListener permissionsResultListener) {

        this.mPermissionsResultListener = permissionsResultListener;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //判断当前Activity是否已经获得了该权限
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {

                //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
                boolean isTip = ActivityCompat.shouldShowRequestPermissionRationale(this,
                        permission);
                if (isTip) {////表明用户没有彻底禁止弹出权限请求

                    //进行权限请求
                    ActivityCompat.requestPermissions(this,
                            new String[]{permission},
                            requestCode);

                } else {//表明用户已经彻底禁止弹出权限请求

                    //这里一般会提示用户进入权限设置界面

                    //进行权限请求
                    ActivityCompat.requestPermissions(this,
                            new String[]{permission},
                            requestCode);
                }
            } else {
                if (mPermissionsResultListener != null) {
                    mPermissionsResultListener.onRequestPermissionsResult(requestCode, new String[]{permission}, new int[]{PackageManager.PERMISSION_GRANTED});
                }
                return true;
            }
        } else {
            if (mPermissionsResultListener != null) {
                mPermissionsResultListener.onRequestPermissionsResult(requestCode, new String[]{permission}, new int[]{PackageManager.PERMISSION_GRANTED});
            }
            return true;
        }

        return false;
    }

    /**
     * 注册权限申请回调
     *
     * @param requestCode  申请码
     * @param permissions  申请的权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {


        for (int i = 0; i < permissions.length; i++) {
            Log.d(TAG, "onRequestPermissionsResult() called with: requestCode = [" + requestCode + "], permissions = [" + permissions[i] + "], grantResults = [" + grantResults[i] + "]");
        }

        if (mPermissionsResultListener != null) {
            mPermissionsResultListener.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }


        switch (requestCode) {
            case RequestPermissionType.REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // showMsg("允许");
                    CommFunAndroid.callPhone(this, CommFunAndroid.getSharedPreferences("phone"));
                } else {
                    //showMsg("拒绝");
                    // Permission Denied
                    showMsg("你已拒绝拨打手机权限");
                }
                break;
            case RequestPermissionType.REQUEST_CODE_ASK_READ_EXTERNAL_STORAGE:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    showMsg("你允许读取文件");
//                } else {
//                    showMsg("你已拒绝读取文件请求");
//                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private PermissionsResultListener mPermissionsResultListener;

    public interface PermissionsResultListener {
        void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);
    }


    /**
     * 跳转到miui的权限管理页面
     */
    protected void gotoMiuiPermission() {
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        i.setComponent(componentName);
        i.putExtra("extra_pkgname", getPackageName());
        try {
            startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            gotoMeizuPermission();
        }
    }


    /**
     * 跳转到魅族的权限管理系统
     */
    private void gotoMeizuPermission() {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            gotoHuaweiPermission();
        }
    }

    /**
     * 华为的权限管理页面
     */
    private void gotoHuaweiPermission() {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            startActivity(getAppDetailSettingIntent());
        }

    }

    /**
     * 获取应用详情页面intent
     *
     * @return
     */
    private Intent getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        return localIntent;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        getWindow().setBackgroundDrawable(null);// android的默认背景是不是为空。
        super.onDestroy();
        Log.d(TAG, "onDestroy() called" + this + "------------------ACTIVITY_COUNT=" + ACTIVITY_COUNT);
        System.gc();

        //mHolder.destoryImageView();
    }

    public static int ACTIVITY_COUNT = 0;
    public static List<String> activityNames = new ArrayList<>();

    @Override
    protected void finalize() throws Throwable {
        ACTIVITY_COUNT--;
        String str = this.toString();
        if (activityNames.contains(str)) {
            activityNames.remove(str);
        }
        Log.d(TAG, "finalize() called" + this + "------------------ACTIVITY_COUNT=" + ACTIVITY_COUNT);
        super.finalize();
    }
}