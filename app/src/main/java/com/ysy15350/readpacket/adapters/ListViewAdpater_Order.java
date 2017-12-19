package com.ysy15350.readpacket.adapters;

import android.content.Context;

import com.ysy15350.readpacket.R;

import java.util.List;

import api.model.OrderInfo;
import base.ViewHolder;
import base.adapter.CommonAdapter;
import common.CommFun;

/**
 * 订单
 *
 * @author yangshiyou
 */
public class ListViewAdpater_Order extends CommonAdapter<OrderInfo> {


    public ListViewAdpater_Order(Context context, List<OrderInfo> list) {
        super(context, list, R.layout.list_item_order);


    }

    @Override
    public void convert(ViewHolder holder, OrderInfo t) {
        if (t != null) {

//          type:  类型：1：充值；2：提现；3：发红包；4：抢到的红包；5：幸运数字收获；6：抢到幸运数字支出；7：推荐奖励；8：分享奖励

            holder
                    .setText(R.id.tv_title, t.getTitle())//订单标题：充值、提现
                    .setText(R.id.tv_time, t.getCreattimeStr())//
                    .setText(R.id.tv_content, t.getContent());//金额


            // 类型：1：充值；2：提现；3：发红包；4：抢到的红包；5：幸运数字收获；6：抢到幸运数字支出；7：推荐奖励；8：分享奖励
            int type = t.getType();
            switch (type) {
                case 1:
                    holder.setImageResource(R.id.img_type, R.mipmap.icon_zhifubao);
                    break;
                case 2:
                    holder.setImageResource(R.id.img_type, R.mipmap.icon_withdraw);
                    break;
                case 3:
                    holder.setImageResource(R.id.img_type, R.mipmap.icon_red_packet_1);
                    break;
                case 4:
                    holder.setImageResource(R.id.img_type, R.mipmap.icon_grabed_red_packet);
                    break;
                case 5:
                    holder.setImageResource(R.id.img_type, R.mipmap.icon_tab2_menu6);
                    break;

                default:
                    holder.setImageResource(R.id.img_type, R.mipmap.icon_yu_e);
                    break;
            }

            String luckNum= t.getLucknum();
            if(!CommFun.isNullOrEmpty(luckNum)){
                int num=CommFun.toInt32(luckNum,-1);
                switch (num){
                    case 0:
                        holder.setImageResource(R.id.img_type, R.mipmap.icon_num0);
                        break;
                    case 1:
                        holder.setImageResource(R.id.img_type, R.mipmap.icon_num1);
                        break;
                    case 2:
                        holder.setImageResource(R.id.img_type, R.mipmap.icon_num2);
                        break;
                    case 3:
                        holder.setImageResource(R.id.img_type, R.mipmap.icon_num3);
                        break;
                    case 4:
                        holder.setImageResource(R.id.img_type, R.mipmap.icon_num4);
                        break;
                    case 5:
                        holder.setImageResource(R.id.img_type, R.mipmap.icon_num5);
                        break;
                    case 6:
                        holder.setImageResource(R.id.img_type, R.mipmap.icon_num6);
                        break;
                    case 7:
                        holder.setImageResource(R.id.img_type, R.mipmap.icon_num7);
                        break;
                    case 8:
                        holder.setImageResource(R.id.img_type, R.mipmap.icon_num8);
                        break;
                    case 9:
                        holder.setImageResource(R.id.img_type, R.mipmap.icon_num9);
                        break;
                }
            }


        }

    }


}
