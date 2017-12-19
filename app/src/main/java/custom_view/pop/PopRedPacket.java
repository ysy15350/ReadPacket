package custom_view.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ysy15350.readpacket.R;

import api.model.RedPacketInfo;
import base.ViewHolder;
import common.image.ImageUtil;
import custom_view.red_packet.MoveModel;

/**
 * Created by yangshiyou on 2017/11/18.
 */

public class PopRedPacket extends PopupWindow {

    private Activity mActivity;
    private Context mContext;
    private MoveModel mMoveModel;
    private RedPacketInfo mRedPacketInfo;

    ImageUtil imageUtil;

    View ll_red_packet, ll_close;
    TextView tv_price;

    private ViewHolder mHolder;


    public PopRedPacket(Context context, MoveModel moveModel) {
        super(context);
        this.mContext = context;
        this.mActivity = (Activity) context;
        mMoveModel = moveModel;
        init();
    }

    public void init() {

        imageUtil = ImageUtil.getInstance(mContext);

        View mPopView = LayoutInflater.from(mContext).inflate(R.layout.pop_red_packet, null);

        mHolder = ViewHolder.get(mContext, mPopView);
        ll_close = mPopView.findViewById(R.id.ll_close);
        ll_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        ll_red_packet = mPopView.findViewById(R.id.ll_red_packet);
        ll_red_packet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mPopListener != null) {
                    mPopListener.onClick(mRedPacketInfo);
                }
                dismiss();
            }
        });

        tv_price = (TextView) mPopView.findViewById(R.id.tv_price);

        mRedPacketInfo = mMoveModel.redPacketInfo;
        if (mRedPacketInfo != null) {
            mHolder.setText(R.id.tv_price, mRedPacketInfo.getPrice() + "");
        }

        // final UMImage image = new UMImage(mContext, bitmap);


        setContentView(mPopView);
        setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private PopListener mPopListener;

    public void setPopListener(PopListener popListener) {
        this.mPopListener = popListener;
    }

    public interface PopListener {
        void onClick(RedPacketInfo redPacketInfo);
    }

    /**
     * @param locationView
     * @param gravity
     */
    public void showPop(View locationView, int gravity) {

        //设置透明：http://2960629.blog.51cto.com/2950629/742499
//
//        半透明<Button android:background="#e0000000" />
//                透明<Button android:background="#00000000" />

        //getBackground().setAlpha(0);//可实现透明

        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        showAtLocation(locationView, gravity, 0, 0);// 相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        setAnimationStyle(R.style.app_pop);
        setOutsideTouchable(true);
        setFocusable(true);
        update();
    }


}
