package com.ysy15350.readpacket.adapters;

import android.content.Context;

import com.ysy15350.readpacket.R;

import java.util.List;

import base.ViewHolder;
import base.adapter.CommonAdapter;
import base.model.UserInfo;
import common.CommFun;

/**
 * 订单
 *
 * @author yangshiyou
 */
public class ListViewAdpater_Invite extends CommonAdapter<UserInfo> {


    public ListViewAdpater_Invite(Context context, List<UserInfo> list) {
        super(context, list, R.layout.list_item_invite);


    }

    @Override
    public void convert(ViewHolder holder, UserInfo t) {
        if (t != null) {
            String timeStr = "";
            long createtime = t.getCreatetime();
            if (createtime != 0) {
                timeStr = CommFun.stampToDateStr(createtime , "yyyy-MM-dd HH:mm");
            }
            holder.setText(R.id.tv_title, CommFun.getPhone(t.getMobile()))
                    .setText(R.id.tv_time, timeStr);

        }

    }


}
