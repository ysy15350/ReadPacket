package base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ysy15350.readpacket.author.LoginActivity;

import org.xutils.x;

import base.data.BaseData;
import common.CommFun;
import common.CommFunAndroid;
import common.message.MessageBox;

/**
 * Created by yangshiyou on 2017/10/30.
 */

public abstract class BaseFragment extends Fragment implements IView {
    private boolean injected = false;

    protected Activity mContext;

    /**
     * 控件ViewGroup
     */
    protected ViewGroup mContentView;

    protected ViewHolder mHolder;

    /**
     * 界面标题
     */
    protected String mTitle = "";

    /**
     * 是否需要登录
     */
    boolean mNeedLogin = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;

        mContext = getActivity();

        mContentView = (ViewGroup) x.view().inject(this, inflater, container);

        mHolder = ViewHolder.get(getActivity(), mContentView);

        init();

        return mContentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mContext = null;

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
        //CommFunAndroid.fullScreenStatuBar(this);
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
        try {
            String token = BaseData.getToken();
            boolean isLogin = !CommFun.isNullOrEmpty(token);


            return isLogin;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
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
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }

            return isLogin;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
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
        MessageBox.showWaitDialog(mContext, msg);
    }

    @Override
    public void hideWaitDialog() {

        MessageBox.hideWaitDialog();
        //CommFunMessage.hideWaitDialog();
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

}
