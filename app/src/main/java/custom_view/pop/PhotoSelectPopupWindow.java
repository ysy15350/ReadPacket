package custom_view.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ysy15350.readpacket.R;


public class PhotoSelectPopupWindow extends PopupWindow {

    private Activity mContext;

    private View conentView;

    private TextView tv_cancle;
    private TextView tv_takephoto;
    private TextView tv_selectphoto;


    public PhotoSelectPopupWindow(final Activity context) {

        mContext = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.pop_photo_select, null);

        init();
        initView();// 初始化按钮事件

    }

    private void init() {
        conentView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                dismiss();
            }
        });


        int h = mContext.getWindowManager().getDefaultDisplay().getHeight();
        int w = mContext.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        // ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        // this.setBackgroundDrawable(dw);
        this.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b0000000")));
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.app_pop);
    }

    private void initView() {

        tv_cancle = (TextView) conentView.findViewById(R.id.tv_cancle);
        tv_takephoto = (TextView) conentView.findViewById(R.id.tv_takephoto);
        tv_selectphoto = (TextView) conentView.findViewById(R.id.tv_selectphoto);

        // 拍照
        tv_takephoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onTakePhotoClick();
                dismiss();
            }
        });
        // 选择相册
        tv_selectphoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onSelectPhotoClick();
                dismiss();
            }
        });
        // 取消
        tv_cancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onCancelClick();
                dismiss();
            }
        });
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);

        } else {
            this.dismiss();
        }
    }

    /**
     * 点击监听
     */
    private PopListener mListener;

    /**
     * 设置popupwindow中按钮监听
     *
     * @param listener
     */
    public void setPopListener(PopListener listener) {
        this.mListener = listener;
    }

    /**
     * 点击事件监听
     *
     * @author yangshiyou
     */
    public interface PopListener {

        public void onTakePhotoClick();

        public void onSelectPhotoClick();

        public void onCancelClick();

    }

}
