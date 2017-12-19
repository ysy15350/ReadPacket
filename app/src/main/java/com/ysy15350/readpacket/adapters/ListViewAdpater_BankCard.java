package com.ysy15350.readpacket.adapters;

import android.content.Context;
import android.view.View;

import com.ysy15350.readpacket.R;

import java.util.List;

import api.model.BankCardInfo;
import base.ViewHolder;
import base.adapter.CommonAdapter;
import base.data.ConfigHelper;
import common.CommFun;
import custom_view.dialog.ConfirmDialog;

/**
 * 银行卡
 *
 * @author yangshiyou
 */
public class ListViewAdpater_BankCard extends CommonAdapter<BankCardInfo> {


    public ListViewAdpater_BankCard(Context context, List<BankCardInfo> list) {
        super(context, list, R.layout.list_item_bank_card);


    }

    @Override
    public void convert(ViewHolder holder, final BankCardInfo t) {
        if (t != null) {

            String bankIcon = t.getBankIcon();
            if (!CommFun.isNullOrEmpty(bankIcon)) {
                holder.setImageURL(R.id.img_bank, t.getBankIcon());
            } else if (t.getBankIconId() != 0) {
                String url = ConfigHelper.getServerImageUrl();
                holder.setImageURL(R.id.img_bank, url + t.getBankIconId());
            }

            holder.setText(R.id.tv_title, t.getBankname())
                    .setText(R.id.tv_number, t.getCardnum())
            ;

            holder.getView(R.id.btn_del).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ConfirmDialog dialog = new ConfirmDialog(mContext, "是否删除【" + t.getBankname() + "】银行卡？");
                    dialog.setDialogListener(new ConfirmDialog.DialogListener() {
                        @Override
                        public void onCancelClick() {

                        }

                        @Override
                        public void onOkClick() {

                        }
                    });
                    dialog.show();
                }
            });
        }

    }

    private BankCardListener mListener;

    public void setListener(BankCardListener listener) {
        mListener = listener;
    }

    public interface BankCardListener {
        public void delSuccess();
    }


}
