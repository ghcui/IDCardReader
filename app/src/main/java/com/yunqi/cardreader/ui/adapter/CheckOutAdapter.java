package com.yunqi.cardreader.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yunqi.cardreader.R;
import com.yunqi.cardreader.model.bean.ClientInfo;
import com.yunqi.cardreader.model.request.CheckOutRequest;
import com.yunqi.cardreader.presenter.CheckOutPresenter;
import com.yunqi.cardreader.ui.activity.ChangeRoomActivity;

import java.util.List;


public class CheckOutAdapter extends BaseQuickAdapter<ClientInfo,BaseViewHolder> {
    private CheckOutPresenter presenter;
    private Activity context;
    private int type;//0表示退房，1表示换房
    public CheckOutAdapter(Activity context,List<ClientInfo> data, CheckOutPresenter presenter, int type) {
        super(R.layout.item_check_out,data);
        this.presenter=presenter;
        this.type=type;
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ClientInfo item) {
        TextView txtName=helper.getView(R.id.txt_name);
        txtName.setText(item.custom_name);
        TextView txtRegisterTime=helper.getView(R.id.txt_register_time);
        txtRegisterTime.setText(item.sign_time);
        TextView txtIDCardCode=helper.getView(R.id.txt_id_card_code);
        txtIDCardCode.setText(item.custom_id_card);
        Button btnCheckOut=helper.getView(R.id.btn_check_out);
        if(type==1){
            btnCheckOut.setText("换房");
        }
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //换房
                if(type==1){
                    Intent intent=new Intent(context, ChangeRoomActivity.class);
                    intent.putExtra("ClientInfo",item);
                    context.startActivityForResult(intent,1);
                }
                else{
                    CheckOutRequest request=new CheckOutRequest();
                    request.order_id=item.order_id;
//                request.retinue=item.retinue+"";
//                request.room_number=item.room_number+"";
                    presenter.checkOut(request);
                }


            }
        });
    }


}
