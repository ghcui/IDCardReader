package com.yunqi.cardreader.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yunqi.cardreader.R;
import com.yunqi.cardreader.ui.adapter.MyListAdapter;


/**
 * 列表Dialog
 */

public class ListDialog extends Dialog {
    private Context context;
    private OnSelectedListener listener;
    private String tip;
    private  String[] arrayStr;
    private int resId;
    public ListDialog(Context context, String tip,String[] arrayStr,int resId,OnSelectedListener listener) {
        super(context, R.style.MyDialog);
        this.context=context;
        this.listener=listener;
        this.tip=tip;
        this.arrayStr=arrayStr;
        this.resId=resId;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(resId, null);
        TextView txtTitler= (TextView) view.findViewById(R.id.txt_title);
        final ListView listView= (ListView) view.findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                dismiss();
                listener.onItemSelected(position,arrayStr[position]);
            }
        });
        txtTitler.setText(tip);
        MyListAdapter adapter=new MyListAdapter(context,arrayStr);
        listView.setAdapter(adapter);
        setContentView(view);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        setCanceledOnTouchOutside(true);
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = d.widthPixels; // 高度设置为屏幕的
        dialogWindow.setAttributes(lp);
    }

    public interface OnSelectedListener {
        void onItemSelected(int position,String str);
    }
}
