package com.ysy15350.readpacket;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.ysy15350.readpacket.fragment.MainTab1Fragment;
import com.ysy15350.readpacket.fragment.MainTab2Fragment;
import com.ysy15350.readpacket.red_packet.HandOutRedPacketActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import api.model.ShareInfo;
import api.model.ShareModel;
import base.data.BaseData;
import base.data.ConfigHelper;
import base.model.UserInfo;
import base.mvp.MVPBaseActivity;
import common.ExitApplication;
import common.message.MessageBox;
import custom_view.pop.PopExit;

@ContentView(R.layout.activity_main)
public class MainActivity extends MVPBaseActivity<MainViewInterface, MainPresenter>
        implements MainViewInterface {

    private static final String TAG = "MainActivity";

    @Override
    protected MainPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new MainPresenter(MainActivity.this);
    }


    @ViewInject(R.id.ll_tab1)
    private View ll_tab1;

    @ViewInject(R.id.ll_tab3)
    private View ll_tab3;

    @ViewInject(R.id.tab)
    private TabLayout tab;

    @ViewInject(R.id.pager)
    private ViewPager mViewPager;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * 显示指定选项卡
     */
    public static int tab_position = 0;

    @Event(value = R.id.ll_tab1)
    private void ll_tab1Click(View view) {
        setSelect(0);
    }

    @Event(value = R.id.ll_tab3)
    private void ll_tab3Click(View view) {
        setSelect(1);
    }

    @Event(value = R.id.ll_tab2)
    private void ll_tab2Click(View view) {

        if (isLogin(true)) {

            int grabRedPacketChanceCount = 0;

            UserInfo userInfo = BaseData.getUserInfo();
            if (userInfo != null) {
                grabRedPacketChanceCount = userInfo.getGrabchancecount();
            }

            if (grabRedPacketChanceCount > 0) {
                startActivity(new Intent(this, HandOutRedPacketActivity.class));
            } else {
                showMsg("你没有发红包的机会了");
            }
        }

    }


    @Override
    public void initView() {
        super.initView();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment fragment = mSectionsPagerAdapter.getItem(position);
                if (fragment != null) {
                    boolean isHidden = fragment.isHidden();
                    Log.d(TAG, "onPageSelected() called with: position = [" + position + "],isHidden=" + isHidden);
                    //fragment.onResume();
                    switch (position) {
                        case 0:
                            if (mainTab1Fragment != null) {
                                if (mainTab1Fragment.status == 1) {
                                    if (mainTab1Fragment.mRedPacketInfos == null || mainTab1Fragment.mRedPacketInfos.size() == 0) {
                                        mainTab1Fragment.getRedPacketList();
                                    }
                                }
                            }
                            break;
                        case 1:
                            break;
                    }
                }
                setTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tab.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (!isLogin()) {
//            this.finish();
//        }

        if (!isCheckVersion) {
            mPresenter.checkversion();//检测版本号
        }

        setSelect(tab_position);


    }

    /**
     * 点击事件1:设置tab(改变图片和字体)和2:切换fragment
     *
     * @param position
     */
    protected void setSelect(int position) {

        mViewPager.setCurrentItem(position);
        setTab(position);
    }

    /**
     * 滑动viewpager时设置tab(改变图片和字体)
     *
     * @param position
     */
    private void setTab(int position) {

        tab_position = position;// 记录已打开选项卡位置，当跳转到登录界面或者其他界面，显示此界面

        switch (position) {
            case 0:
                setViewImage(ll_tab1);
                break;
            case 1:
                setViewImage(ll_tab3);
                break;


            default:
                break;
        }
    }

    MainTab1Fragment mainTab1Fragment;
    MainTab2Fragment mainTab2Fragment;


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<String> titles = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            titles.add("首页");
            titles.add("我的");

        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    if (mainTab1Fragment == null)
                        mainTab1Fragment = new MainTab1Fragment();
                    return mainTab1Fragment;
                }
                case 1: {
                    if (mainTab2Fragment == null)
                        mainTab2Fragment = new MainTab2Fragment();
                    return mainTab2Fragment;
                }
                default:
                    break;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // return super.getPageTitle(position);
            return titles.get(position);
        }
    }


    /**
     * 记录当前view（图片切换）
     */
    private View currentView;
    /**
     * 记录当前textview(切换字体颜色)
     */
    private View currentTeView;

    /**
     * 切换图片（background 设置背景：xml->selector）
     *
     * @param v
     */
    private void setViewImage(View v) {
        if (currentView != null) {
            if (currentView.getId() != v.getId()) {
                View imgview = currentView.findViewWithTag("tab_img");
                View textview = currentView.findViewWithTag("tab_txt");
                if (imgview != null)
                    imgview.setEnabled(true);
                if (textview != null) {
                    textview.setEnabled(true);
                }
            }
        }
        if (v != null) {
            View imgview = v.findViewWithTag("tab_img");
            View textview = v.findViewWithTag("tab_txt");
            if (imgview != null)
                imgview.setEnabled(false);
            if (textview != null) {
                textview.setEnabled(false);
            }
        }
        currentView = v;
    }


    /**
     * 点击返回按钮间隔时间
     */
    private long exitTime = 0;

    private PopExit popExit = null;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            // mPopupWindow.setBackgroundDrawable(new
            // ColorDrawable(Color.parseColor("#b0000000")));
            // mPopupWindow.showAtLocation(form_bottom, Gravity.BOTTOM, 0, 0);
            // mPopupWindow.setAnimationStyle(R.style.app_pop);
            // mPopupWindow.setOutsideTouchable(true);
            // mPopupWindow.setFocusable(true);
            // mPopupWindow.update();
            showExitPop();
        }

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                MessageBox.show("再按一次退出程序");

                //showExitPop();

                exitTime = System.currentTimeMillis();
            } else {
                // stopService(new Intent(getApplicationContext(),
                // TestService.class));// stop

                // CommFunAndroid.setSharedPreferences("JSESSIONID", "");

                ExitApplication.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showExitPop() {
        if (popExit == null) {
            popExit = new PopExit(this);
        }

        popExit.setExitPopListener(new PopExit.PopExitListener() {
            @Override
            public void cancle() {

            }

            @Override
            public void change() {

            }

            @Override
            public void exit() {

            }
        });

        popExit.showPop(mContentView, Gravity.BOTTOM);
    }

    /**
     * 记录当前是否已经检测过更新了，如果已经检测，下次启动再检测，避免多次提醒更新
     */
    private static boolean isCheckVersion = false;

    @Override
    public void updateVersion(String title, String versionName, String content, String fileSize, String url) {
        super.updateVersion(title, versionName, content, fileSize, url);

        isCheckVersion = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public static final int MY_PERMIEAD_SHARE = 123;
    private ShareModel mShareModel;

    public void umShare(SHARE_MEDIA share_media) {
//        if (Build.VERSION.SDK_INT >= 23) {
//            String[] mPermissionList = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.CALL_PHONE, android.Manifest.permission.READ_LOGS, android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.SET_DEBUG_APP, android.Manifest.permission.SYSTEM_ALERT_WINDOW, android.Manifest.permission.GET_ACCOUNTS, android.Manifest.permission.WRITE_APN_SETTINGS};
//            ActivityCompat.requestPermissions(this, mPermissionList, 123);
//        }

        mShareModel = new ShareModel();

        mShareModel.setShare_media(share_media);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //动态注册权限：
// 首先是判断
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // 显示给用户的解释
                    MessageBox.show("请同意权限后分享");
                } else {
                    // No explanation needed, we can request the permission.
                    String[] mPermissionList = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.CALL_PHONE, android.Manifest.permission.READ_LOGS, android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.SET_DEBUG_APP, android.Manifest.permission.SYSTEM_ALERT_WINDOW, android.Manifest.permission.GET_ACCOUNTS, android.Manifest.permission.WRITE_APN_SETTINGS};
                    ActivityCompat.requestPermissions(this, mPermissionList, MY_PERMIEAD_SHARE);
                }
            } else {
                umShare();
            }


        } else {
            umShare();
        }


    }

    ShareInfo shareInfo = new ShareInfo();

    private void umShare() {

        showWaitDialog("程序处理中...");

        String recommendationCode = "";
        String imgUrl = "http://www.360vrdh.com:8080/upload/images/logo.png";

        UserInfo userInfo = BaseData.getUserInfo();
        if (userInfo != null) {
            recommendationCode = userInfo.getRecommendationcode();
            String url = ConfigHelper.getServerImageUrl();
            imgUrl=url+userInfo.getInviteimg();
        }

        SHARE_MEDIA share_media = SHARE_MEDIA.WEIXIN_CIRCLE;
        String title = "旺财红包", content = "旺财红包是一款非常好玩的抢红包", url = ConfigHelper.getServerUrl(true) + "appShare?code=" + recommendationCode;



        if (mShareModel != null) {
            share_media = mShareModel.getShare_media();
        }

        shareInfo.setContent(title + "," + content);
        shareInfo.setImageurl(imgUrl);
        shareInfo.setUrl(url);

        if (share_media == SHARE_MEDIA.WEIXIN) {
            shareInfo.setPlatform(1);
        } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {
            shareInfo.setPlatform(2);
        } else if (share_media == SHARE_MEDIA.QZONE) {
            shareInfo.setPlatform(3);
        } else if (share_media == SHARE_MEDIA.QQ) {
            shareInfo.setPlatform(4);
        }


        UMImage thumb = new UMImage(MainActivity.this,
                imgUrl);//"http://www.360vrdh.com:8080/upload/images/logo.png"


        //分享博客：  http://blog.csdn.net/qq_26589227/article/details/52423138


        //官方文档：http://dev.umeng.com/social/android/quick-integration#2_3_3


        UMWeb web = new UMWeb(url);//"http://www.ysy15350.com"
        web.setTitle(title);//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription(content);//描述

        new ShareAction(MainActivity.this)
                .setPlatform(share_media)//传入平台,如：SHARE_MEDIA.WEIXIN_CIRCLE
                .withText(content)//分享内容
                .withMedia(thumb)
                .setCallback(umShareListener)//回调监听器
                .share();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {




        switch (requestCode) {
            case MY_PERMIEAD_SHARE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限请求成功的操作
                    umShare();
                } else {
                    MessageBox.show("权限请求失败");
                    // 权限请求失败的操作
                }
                return;
            }

            // case其他权限结果。。
        }

    }


    private UMShareListener umShareListener = new UMShareListener() {


        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Log.d(TAG, "UMShareListener 分享开始onStart() called with: platform = [" + platform + "]");
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            hideWaitDialog();
            showMsg("分享" + platform + "成功");
            Log.d(TAG, "UMShareListener onResult() called with: platform = [" + platform + "]");
//            Toast.makeText(MainActivity.this, platform + "成功了", Toast.LENGTH_LONG).show();
            shareInfo.setStatus(1);
            mPresenter.sharePlatform(shareInfo);
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            hideWaitDialog();
            showMsg("分享" + platform + "失败");
            Log.d(TAG, "UMShareListener onError() called with: platform = [" + platform + "], t = [" + t + "]");
            // Toast.makeText(MainActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
            shareInfo.setStatus(-1);
            mPresenter.sharePlatform(shareInfo);
        }

        /**
         * @descrption 分享取消的回调SocialSDK_whatsapp.jar
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            hideWaitDialog();
            showMsg("分享" + platform + "取消");
            Log.d(TAG, "UMShareListener onCancel() called with: platform = [" + platform + "]");
            //Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_LONG).show();
            shareInfo.setStatus(-2);
            mPresenter.sharePlatform(shareInfo);
        }
    };

}
