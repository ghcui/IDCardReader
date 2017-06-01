package com.yunqi.cardreader.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.cardreader.R;
import com.yunqi.cardreader.model.bean.Notice;
import com.yunqi.cardreader.model.bean.Room;

import java.util.List;


public class NoticeAdapter extends BaseQuickAdapter<Notice,BaseViewHolder> {
    public NoticeAdapter(List<Notice> data) {
        super(R.layout.item_notice,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Notice item) {
        TextView txtTitle=helper.getView(R.id.txt_title);
        TextView txtTime=helper.getView(R.id.txt_time);
        TextView txtContent=helper.getView(R.id.txt_content);
        txtTitle.setText(item.title);
        txtTime.setText(item.create_time);
        txtContent.setText(item.content);
    }
}
