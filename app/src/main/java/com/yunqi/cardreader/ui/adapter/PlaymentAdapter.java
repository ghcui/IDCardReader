package com.yunqi.cardreader.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.cardreader.R;
import com.yunqi.cardreader.model.bean.User;

import java.util.List;



public class PlaymentAdapter extends BaseQuickAdapter<User,BaseViewHolder> {
    public PlaymentAdapter(List<User> data) {
        super(R.layout.item_playment,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {
        TextView txtCustomerName=helper.getView(R.id.name);
        txtCustomerName.setText(item.getName());
    }
}
