package com.ysy15350.readpacket.image_choose;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;

import java.io.File;
import java.util.ArrayList;

import common.CommFunAndroid;
import common.message.MessageBox;

/**
 * Created by yangshiyou on 2017/9/12.
 */

public class ImgSelectActivity extends TakePhotoActivity {


    //https://github.com/darsh2/MultipleImageSelect
    //https://github.com/crazycodeboy/TakePhoto#安装说明

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private int type = 1, width = 300, height = 300;


    public void initData() {

        //Toast.makeText(this, CommFunAndroid.getSharedPreferences("type") + "", Toast.LENGTH_SHORT).show();

        Intent intent = null;
        try {
            intent = getIntent();

            if (intent != null) {
                type = CommFunAndroid.toInt32(CommFunAndroid.getSharedPreferences("type"), 0);//1:选择相册;2:拍照
                width = intent.getIntExtra("width", 300);
                height = intent.getIntExtra("height", 300);
            }

            if (type != 0)
                initTakePhoto();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    TakePhoto takePhoto;

    private void initTakePhoto() {
        takePhoto = getTakePhoto();

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);


        File file = new File(CommFunAndroid.getDiskCachePath(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);

        if (type == 1) {
            takePhoto.onPickMultipleWithCrop(1, getCropOptions(width, height));
        } else if (type == 2) {
            takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions(width, height));
        } else {
//            this.finish();
        }

        CommFunAndroid.setSharedPreferences("type", "0");

    }


    @Override
    public void takeSuccess(TResult result) {

        ArrayList<TImage> list = result.getImages();

        Intent intent = getIntent();
        intent.putExtra("images", list);
        setResult(RESULT_OK, intent);

        type = 0;

        MessageBox.show("获取图片成功");

        this.finish();

    }

    @Override
    public void takeFail(TResult result, String msg) {
        MessageBox.show(msg);
        this.finish();
    }

    @Override
    public void takeCancel() {
        MessageBox.show("已取消");
        this.finish();
    }


    private CropOptions getCropOptions(int width, int height) {


        boolean withWonCrop = true;//false;

        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(width).setAspectY(height);//尺寸

//        if (rgCropSize.getCheckedRadioButtonId() == R.id.rbAspect) {
//            builder.setAspectX(width).setAspectY(height);//尺寸
//        } else {
//            builder.setOutputX(width).setOutputY(height);//比例
//        }
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }

    /**
     * 配置是否压缩
     *
     * @param takePhoto
     */
    private void configCompress(TakePhoto takePhoto) {
        //takePhoto.onEnableCompress(null,false);//不压缩
        int maxSize = 1024;//b
        int width = 300;
        int height = 300;
        boolean showProgressBar = true;//是否显示压缩进度条rgShowProgressBar.getCheckedRadioButtonId() == R.id.rbShowYes ? true : false;
        boolean enableRawFile = false;//拍照压缩后是否保存原图rgRawFile.getCheckedRadioButtonId() == R.id.rbRawYes ? true : false;

        CompressConfig config;

        int compressType = 2;

        if (compressType == 1) {//自带压缩工具
            config = new CompressConfig.Builder()
                    .setMaxSize(maxSize)
                    .setMaxPixel(width >= height ? width : height)
                    .enableReserveRaw(enableRawFile)
                    .create();
        } else {//Luban压缩
            LubanOptions option = new LubanOptions.Builder()
                    .setMaxHeight(height)
                    .setMaxWidth(width)
                    .setMaxSize(maxSize)
                    .create();
            config = CompressConfig.ofLuban(option);
            config.enableReserveRaw(enableRawFile);
        }
        takePhoto.onEnableCompress(config, showProgressBar);


    }

    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);//是否使用TakePhoto自带相册
        builder.setCorrectImage(true);//是否纠正拍照的照片旋转角度

        takePhoto.setTakePhotoOptions(builder.create());

    }

}
