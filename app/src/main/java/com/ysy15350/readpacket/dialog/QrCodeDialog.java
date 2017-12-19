package com.ysy15350.readpacket.dialog;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.ysy15350.readpacket.R;

import base.data.BaseData;
import base.data.ConfigHelper;
import base.model.UserInfo;
import common.CommFun;
import common.image.ImageUtil;
import common.message.MessageBox;
import custom_view.qrcode.CanvasRQ;

/**
 * 清除缓存
 *
 * @author yangshiyou
 */
public class QrCodeDialog extends Dialog {

    private static final String TAG = "QrCodeDialog";
    private Context mContext;


    private View conentView;

    private CanvasRQ qr_code;


    private View ll_close;
    private Button btn_1, btn_2;

    public QrCodeDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub

        mContext = context;

        initView();// 初始化按钮事件

    }


    private void initView() {
        UserInfo userInfo = BaseData.getUserInfo();
        if (userInfo == null) {
            dismiss();
            return;
        }

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        conentView = inflater.inflate(R.layout.dialog_qrcode, null);

        qr_code = conentView.findViewById(R.id.qr_code);


        final String recommendationCode = userInfo.getRecommendationcode();

        if (userInfo != null) {
            String qrCodeUrl = ConfigHelper.getServerUrl(true) + "appShare?code=" + recommendationCode;
            qr_code.setUrl(qrCodeUrl);
        }


        ll_close = conentView.findViewById(R.id.ll_close);
        btn_1 = (Button) conentView.findViewById(R.id.btn_1);
        btn_2 = (Button) conentView.findViewById(R.id.btn_2);

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

        btn_1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Bitmap bitmap = qr_code.getBitmap();
                if (bitmap != null) {
                    String path = ImageUtil.saveBitmap(mContext, bitmap, recommendationCode);
                    if (CommFun.isNullOrEmpty(path)) {
                        MessageBox.show("保存失败");
                    } else {
                        MessageBox.show("已保存至：" + path);
                    }
                }

                if (mListener != null) {
                    mListener.btn_1Click();
                }
                dismiss();
            }
        });

        btn_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mListener != null) {
                    mListener.btn_2Click();
                }
                dismiss();
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

        public void btn_1Click();

        public void btn_2Click();

    }


}
