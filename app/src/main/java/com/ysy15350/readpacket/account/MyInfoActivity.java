package com.ysy15350.readpacket.account;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.jph.takephoto.model.TImage;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.ysy15350.readpacket.R;
import com.ysy15350.readpacket.author.BindMobileActivity;
import com.ysy15350.readpacket.author.UpdatePwdActivity;
import com.ysy15350.readpacket.image_choose.ImgSelectActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import api.base.model.Response;
import api.base.model.ResponseHead;
import api.model.FileInfo;
import base.data.BaseData;
import base.data.ConfigHelper;
import base.model.UserInfo;
import base.mvp.MVPBaseActivity;
import common.CommFun;
import common.CommFunAndroid;
import common.image.ImageUtil;
import common.string.JsonConvertor;
import custom_view.pop.PhotoSelectPopupWindow;

/**
 * Created by yangshiyou on 2017/9/20.
 */

/**
 * 个人资料
 */
@ContentView(R.layout.activity_my_info)
public class MyInfoActivity extends MVPBaseActivity<MyInfoViewInterface, MyInfoPresenter> implements MyInfoViewInterface, Validator.ValidationListener {

    private static final String TAG = "MyInfoActivity";

    @Override
    protected MyInfoPresenter createPresenter() {
        // TODO Auto-generated method stub
        return new MyInfoPresenter(MyInfoActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.remove("android:support:fragments");   //注意：基类是Activity时参数为android:fragments， 一定要在super.onCreate函数前执行！！！
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {

        super.initView();
        setFormHead("个人资料");

        setMenuText("保存");
    }

    UserInfo mUserInfo;


    @Override
    protected void onResume() {
        super.onResume();

        UserInfo userInfo = BaseData.getInstance().getUserInfo();
        bindUserInfo(userInfo);
        mPresenter.userInfo();

    }


    @Override
    public void user_infoCallback(boolean isCache, Response response) {
        try {

            hideWaitDialog();

            if (response != null) {
                ResponseHead responseHead = response.getHead();
                if (responseHead != null) {
                    int status = responseHead.getResponse_status();
                    String msg = responseHead.getResponse_msg();
                    if (status == 100) {

                        UserInfo userInfo = response.getData(UserInfo.class);
                        if (userInfo != null) {
                            userInfo.setIsLogin(1);
                            BaseData.getInstance().setUserInfo(userInfo);
                        }

                        bindUserInfo(userInfo);


                    } else
                        showMsg(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindUserInfo(UserInfo userInfo) {

        try {

            mUserInfo = userInfo;

            if (userInfo != null && mHolder != null) {


                if (userInfo.getHeadimg() != 0) {
                    String img_headUrl = ConfigHelper.getServerImageUrl() + userInfo.getHeadimg();
                    mHolder.setImageURL(R.id.img_head, img_headUrl);
                }

                String tv_mobileStr = "点击绑定手机号";
                if (!CommFun.isNullOrEmpty(userInfo.getMobile())) {
                    tv_mobileStr = CommFun.getPhone(userInfo.getMobile());
                }

                String alipayStr = "点击绑定支付宝";
                if (!CommFun.isNullOrEmpty(userInfo.getUseridalipay())) {
                    alipayStr = "已绑定";
                }
                mHolder.setText(R.id.tv_alipay, alipayStr);

                mHolder.setText(R.id.et_nickName, userInfo.getNickname())
                        .setText(R.id.et_realName, userInfo.getRealname())
                        .setText(R.id.tv_mobile, tv_mobileStr);

                String headImgUrl = ConfigHelper.getServerImageUrl();
                if (userInfo.getHeadimg() != 0) {
                    headImgUrl += userInfo.getHeadimg();
                    mHolder.setImageURL(R.id.img_head, headImgUrl, true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUserInfoCallback(boolean isCache, Response response) {
        try {

            hideWaitDialog();


            if (response != null) {
                ResponseHead responseHead = response.getHead();
                if (responseHead != null) {
                    int status = responseHead.getResponse_status();
                    String msg = responseHead.getResponse_msg();
                    if (status == 100) {


                    }
                    showMsg(msg);

                }
            } else {
                showMsg("系统错误");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 上传头像
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_1)
    private void ll_menu_1Click(View view) {
        PhotoSelectPopupWindow popupWindow = new PhotoSelectPopupWindow(this);
        popupWindow.showPopupWindow(mContentView);

        popupWindow.setPopListener(new PhotoSelectPopupWindow.PopListener() {
            @Override
            public void onTakePhotoClick() {
                getPhoto(2);
            }

            @Override
            public void onSelectPhotoClick() {
                getPhoto(1);
            }

            @Override
            public void onCancelClick() {

            }
        });
    }

    private void getPhoto(int type) {
        Intent intent = new Intent(this, ImgSelectActivity.class);
        CommFunAndroid.setSharedPreferences("type", type + "");
        intent.putExtra("width", 300);
        intent.putExtra("height", 300);
        startActivityForResult(intent, PHOTO_REQUEST);
    }


    /**
     * 绑定手机号
     *
     * @param view
     */
    @Event(value = R.id.tv_mobile)
    private void tv_mobileClick(View view) {

        if (mUserInfo != null) {

            if (CommFun.isNullOrEmpty(mUserInfo.getMobile())) {


                Intent intent = new Intent(this, BindMobileActivity.class);

                startActivity(intent);

            }
        }

    }

    /**
     * 绑定支付宝
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_4)
    private void ll_menu_4Click(View view) {
        mPresenter.authV2();
    }


    /**
     * 修改密码
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_11)
    private void ll_menu_11Click(View view) {
        Intent intent = new Intent(this, UpdatePwdActivity.class);

        startActivity(intent);

    }

    /**
     * 修改密码
     *
     * @param view
     */
    @Event(value = R.id.ll_menu_12)
    private void ll_menu_12Click(View view) {
        Intent intent = new Intent(this, SetPayPwdActivity.class);

        startActivityForResult(intent, PAY_PWD_REQUEST);

    }

    @Event(value = R.id.ll_menu)
    private void ll_menuClick(View view) {

        String headimgurl = "";
        String nickname = mHolder.getViewText(R.id.et_nickName);
        String realName = mHolder.getViewText(R.id.et_realName);

        UserInfo userInfo = BaseData.getInstance().getUserInfo();
        if (userInfo != null) {
            userInfo.setHeadimgurl(headimgurl);
            userInfo.setNickname(nickname);
            userInfo.setRealname(realName);


//            BaseData.setCache("img_head", response.getBodyJson());
            String img_headJson = BaseData.getCache("img_head");
            if (!CommFun.isNullOrEmpty(img_headJson)) {
                FileInfo fileInfo = JsonConvertor.fromJson(img_headJson, FileInfo.class);
                if (fileInfo != null) {
                    int fid = fileInfo.getId();
                    userInfo.setHeadimg(fid);
                }
            }

        }
        save_info(userInfo);
    }

    private void save_info(UserInfo userInfo) {

        try {
            if (userInfo != null) {
                mPresenter.saveUserInfo(userInfo);
            } else
                showMsg("用户信息丢失，请重试");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    boolean validationSucceeded = false;

    @Override
    public void onValidationSucceeded() {
        validationSucceeded = true;
    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        if (errors != null && !errors.isEmpty()) {
            validationSucceeded = false;
            for (ValidationError error : errors) {
                View view = error.getView();
                String message = error.getCollatedErrorMessage(this);
                if (view instanceof EditText) {
                    ((EditText) view).setError(message);
                }
            }
        }
    }

    private static final int PHOTO_REQUEST = 100;// 选择照片
    private static final int PAY_PWD_REQUEST = 101;// 选择照片

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == PHOTO_REQUEST) {
                if (null != data) {//(resultCode == RESULT_OK) {
                    ArrayList<TImage> images = (ArrayList<TImage>) data.getSerializableExtra("images");
                    if (images != null && !images.isEmpty()) {
                        String path = images.get(0).getOriginalPath();
                        if (CommFunAndroid.isNullOrEmpty(path))
                            path = images.get(0).getCompressPath();

                        File file = new File(path);
                        if (file != null && file.exists()) {

                            ImageView img_head = mHolder.getView(R.id.img_head);

                            img_head.setImageURI(Uri.parse(path));
                        }
                        uploadImage(file);
                    }
                } else {
                    showMsg("图片获取失败");
                }
            } else if (requestCode == PAY_PWD_REQUEST) {
                String password = data.getStringExtra("password");
                UserInfo userInfo = BaseData.getInstance().getUserInfo();
                if (userInfo != null) {
                    userInfo.setPay_password(password);
                }
                save_info(userInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadImage(File file) {
        if (file != null && file.exists()) {

            String imgBase64Str = ImageUtil.getImageBase64(file);
            Log.d(TAG, "uploadImage: " + imgBase64Str);

            showWaitDialog("头像上传中...");
            String imgName = file.getName();
            mPresenter.imgUp(1, file.getName(), imgBase64Str);

        } else {
            showMsg("文件不存在");
        }

    }

    private int mHeadImg = 0;


    @Override
    public void imgUpCallback(boolean isCache, Response response) {
        try {

            hideWaitDialog();

            if (response != null) {
                ResponseHead responseHead = response.getHead();
                if (responseHead != null) {
                    int status = responseHead.getResponse_status();
                    String msg = responseHead.getResponse_msg();
                    if (status == 100) {
                        FileInfo fileInfo = response.getData(FileInfo.class);
                        if (fileInfo != null) {
                            int fid = fileInfo.getId();
                            BaseData.setCache("img_head", response.getBodyJson());
                            String img_headUrl = ConfigHelper.getServerImageUrl() + fid;
                            mHolder.setImageURL(R.id.img_head, img_headUrl, true);
                        }
                        showMsg("上传成功");
                    } else
                        showMsg(msg);

                }
            } else {
                showMsg("系统错误");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void oauth_tokenCallback(boolean isCache, Response response) {
        try {

            hideWaitDialog();


            if (response != null) {
                ResponseHead responseHead = response.getHead();
                if (responseHead != null) {
                    int status = responseHead.getResponse_status();
                    String msg = responseHead.getResponse_msg();
                    if (status == 100) {

                        UserInfo userInfo = response.getData(UserInfo.class);

                        if (userInfo != null) {
                            userInfo.setIsLogin(1);
                            BaseData.getInstance().setUserInfo(userInfo);

                            bindUserInfo(userInfo);
                        }

                        //gotoMainActivity();

                    }
                    showMsg(msg);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
