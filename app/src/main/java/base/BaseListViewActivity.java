package base;

import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import com.ysy15350.readpacket.R;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import common.CommFunAndroid;
import custom_view.x_view.XListView;

public abstract class BaseListViewActivity extends BaseActivity
        implements XListView.IXListViewListener, OnItemClickListener {

    /**
     * 下拉刷新列表
     */
    @ViewInject(R.id.xListView)
    protected XListView xListView;

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();

        if (xListView != null) {

            xListView.setDivider(new ColorDrawable(getResources().getColor(R.color.devider_color))); // 设置间距颜色
            xListView.setDividerHeight(CommFunAndroid.dip2px(10)); // 设置间距高度(此必须设置在setDivider（）之后，否则无效果)

            xListView.setPullLoadEnable(true);

            xListView.setXListViewListener(this);

            xListView.setOnItemClickListener(this);

        }
    }

    protected int page = 1, pageSize = 10;

    public abstract void initData(int page, int pageSize);

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        page = 1;
        showWaitDialog("数据加载中...");
        initData(page, pageSize);
    }

    @Override
    public void onLoadMore() {
        showWaitDialog("数据加载中...");
        initData(page, pageSize);
    }

    /**
     * 绑定下拉列表
     */
    protected void bindListView(BaseAdapter mAdapter) {
        // TODO Auto-generated method stub

        if (mAdapter != null) {

            int count = mAdapter.getCount();
            if (count == 0) {
                mHolder.setVisibility_GONE(R.id.xListView)
                        .setVisibility_VISIBLE(R.id.ll_nodata)
                //.setText(R.id.tv_nodata, "暂无数据，点击刷新")
                ;

            } else {
                mHolder.setVisibility_GONE(R.id.ll_nodata)
                        .setVisibility_VISIBLE(R.id.xListView)
                ;
            }

            if (page == 1) {

                String timeStr = CommFunAndroid.getDateString("yyyy-MM-dd HH:mm:ss");
                xListView.setRefreshTime(timeStr);

                xListView.setAdapter(mAdapter);

            } else {
                mAdapter.notifyDataSetChanged();


            }

            xListView.stopRefresh();
            xListView.stopLoadMore();


            //

            // // listview布局动画
            // if (mContext != null) {
            // LayoutAnimationController lac = new LayoutAnimationController(
            // AnimationUtils.loadAnimation(mContext, R.anim.zoom_in));
            // lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
            // xListView.setLayoutAnimation(lac);
            // xListView.startLayoutAnimation();
            // }
        }

    }


    /**
     * 条目是否填满整个屏幕
     */
    private boolean isFillScreenItem() {
        final int firstVisiblePosition = xListView.getFirstVisiblePosition();
        final int lastVisiblePostion = xListView.getLastVisiblePosition() - xListView.getFooterViewsCount();
        final int visibleItemCount = lastVisiblePostion - firstVisiblePosition + 1;
        final int totalItemCount = xListView.getCount() - xListView.getFooterViewsCount();
        if (visibleItemCount < totalItemCount) return true;
        return false;
    }

    private static final String TAG = "BaseListViewActivity";

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        Log.d(TAG, "onItemClick: ");
    }

    @Event(value = R.id.ll_nodata)
    private void ll_nodataClick(View view) {
        //mHolder.setText(R.id.tv_nodata, "数据加载中....");
        onRefresh();
    }

}
