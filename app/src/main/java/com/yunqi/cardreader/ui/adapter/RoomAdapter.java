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
        TextView roomStatus=helper.getView(R.id.room_status);
        if(item.sum>0){
            roomStatus.setText("有人");
            roomStatus.setBackgroundResource(R.drawable.room_status2);
        }
        else{
            roomStatus.setText("空闲");
            roomStatus.setBackgroundResource(R.drawable.room_status1);
        }

    }
}
