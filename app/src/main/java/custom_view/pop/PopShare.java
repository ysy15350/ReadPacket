package custom_view.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.ysy15350.readpacket.R;

import common.image.ImageUtil;

/**
 * Created by yangshiyou on 2017/11/18.
 */

public class PopShare extends PopupWindow {

    private Activity mActivity;
    private Context mContext;

    ImageUtil imageUtil;

    RelativeLayout rl_pop_main;

    LinearLayout ll_pop_main, ll_weixin, ll_friend, ll_qzone, ll_qq;

    public PopShare(Context context) {
        super(context);
        this.mContext = context;
        this.mActivity = (Activity) context;
        init();
    }

    public void init() {

        imageUtil = ImageUtil.getInstance(mContext);

        View mPopView = LayoutInflater.from(mContext).inflate(R.layout.pop_share, null);

        rl_pop_main = (RelativeLayout) mPopView.findViewById(R.id.rl_pop_main);
        rl_pop_main.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dismiss();
            }
        });

        ll_weixin = (LinearLayout) mPopView.findViewById(R.id.ll_weixin);
        ll_friend = (LinearLayout) mPopView.findViewById(R.id.ll_friend);
        ll_qzone = (LinearLayout) mPopView.findViewById(R.id.ll_qzone);
        ll_qq = (LinearLayout) mPopView.findViewById(R.id.ll_qq);

        Bitmap bitmap = imageUtil.getBitmap(R.mipmap.ic_launcher);

        // final UMImage image = new UMImage(mContext, bitmap);

        ll_weixin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if(mPopListener!=null){
                    mPopListener.weixinShare();
                }

                // UMImage image = new UMImage(mActivity,
                // "http://www.umeng.com/images/pic/social/integrated_3.png");
                // UMusic music = new
                // UMusic("http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3");
                // music.setTitle("sdasdasd");
                // music.setThumb(new UMImage(mActivity,
                // "http://www.umeng.com/images/pic/social/chart_1.png"));
                //
                // music.setTargetUrl("http://www.baidu.com");

                // new
                // ShareAction(mActivity).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener).withMedia(image)
                // .withText("一款可以用颜值来赚钱的APP").withTitle("约人看电影APP").withTargetUrl("http://app.023yue.com/")
                // // .withMedia(new
                // //
                // UMEmoji(ShareActivity.this,"http://img.newyx.net/news_img/201306/20/1371714170_1812223777.gif"))
                // .share();

                dismiss();
            }
        });

        ll_friend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if(mPopListener!=null){
                    mPopListener.wxFriendShare();
                }

                // UMImage image = new UMImage(mActivity,
                // "http://www.umeng.com/images/pic/social/integrated_3.png");
                // UMusic music = new
                // UMusic("http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3");
                // music.setTitle("sdasdasd");
                // music.setThumb(new UMImage(mActivity,
                // "http://www.umeng.com/images/pic/social/chart_1.png"));
                //
                // music.setTargetUrl("http://www.baidu.com");

                // new
                // ShareAction(mActivity).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener)
                // .withMedia(image).withText("一款可以用颜值来赚钱的APP").withTitle("约人看电影APP")
                // .withTargetUrl("http://app.023yue.com/")
                // // .withMedia(new
                // //
                // UMEmoji(ShareActivity.this,"http://img.newyx.net/news_img/201306/20/1371714170_1812223777.gif"))
                // .share();

                dismiss();

            }
        });

        ll_qzone.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings({"unchecked", "rawtypes"})
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if(mPopListener!=null){
                    mPopListener.qzoneShare();
                }

                // UMusic music = new
                // UMusic("http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3");
                // music.setTitle("sdasdasd");
                // music.setThumb(new UMImage(mActivity,
                // "http://www.umeng.com/images/pic/social/chart_1.png"));
                //
                // music.setTargetUrl("http://www.baidu.com");


//				new ShareAction(mActivity).setPlatform(SHARE_MEDIA.QZONE).setCallback(umShareListener).withMedia(image)
//						.withText("一款可以用颜值来赚钱的APP").withTitle("约人看电影APP").withTargetUrl("http://app.023yue.com/")
//						.share();

                dismiss();

            }
        });

        ll_qq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if(mPopListener!=null){
                    mPopListener.qqShare();
                }

                // UMusic music = new
                // UMusic("http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3");
                // music.setTitle("sdasdasd");
                // music.setThumb(new UMImage(mActivity,
                // "http://www.umeng.com/images/pic/social/chart_1.png"));
                //
                // music.setTargetUrl("http://www.baidu.com");


                // new
                // ShareAction(mActivity).setPlatform(SHARE_MEDIA.QQ).setCallback(umShareListener).withMedia(image)
                // .withText("一款可以用颜值来赚钱的APP").withTitle("约人看电影APP").withTargetUrl("http://app.023yue.com/")
                // .share();

                dismiss();
            }
        });

        // 实例化级联菜单
        setContentView(mPopView);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private PopListener mPopListener;

    public void setPopListener(PopListener popListener) {
        this.mPopListener = popListener;
    }

    public interface PopListener {

        void weixinShare();

        void wxFriendShare();

        void qzoneShare();

        void qqShare();
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

//    private UMShareListener umShareListener = new UMShareListener() {
//        @Override
//        public void onResult(SHARE_MEDIA platform) {
//            MessageBox.show(platform + "分享成功啦");
//
//        }
//
//        @Override
//        public void onError(SHARE_MEDIA platform, Throwable t) {
//            MessageBox.show(platform + "分享成功啦");
//        }
//
//        @Override
//        public void onCancel(SHARE_MEDIA platform) {
//            MessageBox.show(platform + "分享成功啦");
//        }
//    };


}
