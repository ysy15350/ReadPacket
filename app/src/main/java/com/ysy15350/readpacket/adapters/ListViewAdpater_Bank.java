package com.ysy15350.readpacket.adapters;

import android.content.Context;

import com.ysy15350.readpacket.R;

import java.util.List;

import api.model.BankInfo;
import base.ViewHolder;
import base.adapter.CommonAdapter;
import base.data.ConfigHelper;
import common.CommFun;

/**
 * 银行列表
 *
 * @author yangshiyou
 */
public class ListViewAdpater_Bank extends CommonAdapter<BankInfo> {


    public ListViewAdpater_Bank(Context context, List<BankInfo> list) {
        super(context, list, R.layout.list_item_bank);


    }

    @Override
    public void convert(ViewHolder holder, BankInfo t) {
        if (t != null) {

            String bankIcon = t.getBankicon();
            if (!CommFun.isNullOrEmpty(bankIcon)) {
                holder.setImageURL(R.id.img_bank, t.getBankicon());
            } else if (t.getBankiconid() != 0) {
                String url = ConfigHelper.getServerImageUrl();
                holder.setImageURL(R.id.img_bank, url + t.getBankiconid());
            }

            holder.setText(R.id.tv_title, t.getBankname());

        }

    }


}
