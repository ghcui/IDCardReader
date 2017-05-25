package com.yunqi.cardreader.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.cardreader.R;
import com.yunqi.cardreader.model.bean.ClientInfo;

import java.util.List;


public class WillSendAdapter extends BaseQuickAdapter<ClientInfo,BaseViewHolder> {
    public WillSendAdapter(List<ClientInfo> data) {
        super(R.layout.item_data,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClientInfo item) {
        TextView txtRoomNo=helper.getView(R.id.txt_room_no);
        TextView txtCustomName=helper.getView(R.id.txt_custom_name);
        TextView txtIDCardCode=helper.getView(R.id.txt_id_card_code);
        txtRoomNo.setText(item.room_code);
        txtCustomName.setText(item.custom_name);
        txtIDCardCode.setText(item.custom_id_card);
    }
}
