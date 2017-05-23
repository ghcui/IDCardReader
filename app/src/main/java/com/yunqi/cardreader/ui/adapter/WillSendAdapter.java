package com.yunqi.cardreader.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.cardreader.R;
import com.yunqi.cardreader.model.bean.ClientInfo;

import java.util.List;


public class WillSendAdapter extends BaseQuickAdapter<ClientInfo,BaseViewHolder> {
    public WillSendAdapter(List<ClientInfo> data) {
        super(R.layout.item_playment,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClientInfo item) {
        TextView txtName=helper.getView(R.id.name);
    }
}
