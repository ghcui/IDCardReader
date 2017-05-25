package com.yunqi.cardreader.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.cardreader.R;
import com.yunqi.cardreader.model.bean.Room;

import java.util.List;


public class RoomAdapter extends BaseQuickAdapter<Room,BaseViewHolder> {
    public RoomAdapter(List<Room> data) {
        super(R.layout.item_room,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Room item) {
        TextView roomNo=helper.getView(R.id.room_no);
        roomNo.setText(item.room_code);
        TextView roomAddress=helper.getView(R.id.room_address);
        roomAddress.setText(item.room_address);
        TextView roomTotalNum=helper.getView(R.id.room_total_num);
        roomTotalNum.setText(item.room_num+"");
        TextView roomLeftNum=helper.getView(R.id.room_left_num);
        roomLeftNum.setText((item.room_num-item.sum)+"");
    }
}
