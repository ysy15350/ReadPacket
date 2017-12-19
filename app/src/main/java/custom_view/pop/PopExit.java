package custom_view.pop;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ysy15350.readpacket.R;

;

/**
 * Created by yangshiyou on 2017/11/18.
 */

public class PopExit extends PopupWindow {

    private Context mContext;

    private TextView app_cancle;
    private TextView app_exit;
    private TextView app_change;

    public PopExit(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public void init() {

        View mPopView = LayoutInflater.from(mContext).inflate(R.layout.pop_exit, null);

        app_cancle = (TextView) mPopView.findViewById(R.id.app_cancle);
        app_change = (TextView) mPopView.findViewById(R.id.app_change_user);
        app_exit = (TextView) mPopView.findViewById(R.id.app_exit);

        app_cancle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (mExitListener != null) {
                    mExitListener.cancle();
                    dismiss();
                }
            }
        });

        app_change.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (mExitListener != null) {
                    mExitListener.change();
                    dismiss();
                }
            }
        });

        app_exit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (mExitListener != null) {
                    mExitListener.exit();
                    dismiss();
                }
            }
        });

        // 实例化级联菜单
        setContentView(mPopView);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private PopExitListener mExitListener;

    public void setExitPopListener(PopExitListener exitListener) {
        this.mExitListener = exitListener;
    }

    public interface PopExitListener {
        void cancle();

        void change();

        void exit();
    }

    /**
     * @param locationView
     * @param gravity
     */
    public void showPop(View locationView, int gravity) {

        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b0000000")));
        showAtLocation(locationView, gravity, 0, 0);// 相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        setAnimationStyle(R.style.app_pop);
        setOutsideTouchable(true);
        setFocusable(true);
        update();
    }

}