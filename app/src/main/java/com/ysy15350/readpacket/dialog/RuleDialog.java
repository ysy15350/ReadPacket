package com.ysy15350.readpacket.dialog;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.ysy15350.readpacket.R;

/**
 * 清除缓存
 *
 * @author yangshiyou
 */
public class RuleDialog extends Dialog {

    private static final String TAG = "RuleDialog";
    private Context mContext;


    private View conentView;

    private TextView tv_content;

    private View ll_close;

    private String mContent;


    public RuleDialog(Context context, String content) {
        super(context);
        // TODO Auto-generated constructor stub

        mContext = context;

        mContent = content;

        initView();// 初始化按钮事件


    }


    private void initView() {


        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        conentView = inflater.inflate(R.layout.dialog_rule, null);

        tv_content = (TextView) conentView.findViewById(R.id.tv_content);

        tv_content.setText(mContent);


        ll_close = conentView.findViewById(R.id.ll_close);


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
