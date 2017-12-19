package com.ysy15350.readpacket.adapters;

import android.content.Context;

import com.ysy15350.readpacket.R;

import java.util.List;

import api.model.GridViewItemInfo;
import base.ViewHolder;
import base.adapter.CommonAdapter;

/**
 * 车辆品牌gridview
 *
 * @author yangshiyou
 */
public class GridViewAdpater_LuckNum extends CommonAdapter<GridViewItemInfo> {


    public GridViewAdpater_LuckNum(Context context, List<GridViewItemInfo> list) {
        super(context, list, R.layout.grid_item_luck_num);

    }

    @Override
    public void convert(ViewHolder holder, GridViewItemInfo t) {

        try {
            if (t != null) {

                holder
                        .setText(R.id.tv_title, t.getTitle());


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
