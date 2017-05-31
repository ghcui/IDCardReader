package com.yunqi.cardreader.ui.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunqi.cardreader.R;
import com.yunqi.cardreader.model.bean.Module;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 *
 *
 * @author RXXU
 */
public class MyListAdapter extends BaseAdapter {

    /**
     * 转化器
     */
    private LayoutInflater layoutInflater;
    /**
     * 数据源
     */
    private String[] arrayStr;
    /**
     * 上下文
     */
    private Context context;
    /**
     * Application
     */

    public MyListAdapter(Context context,String[] arrayStr) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.arrayStr = arrayStr;

    }

    @Override
    public int getCount() {
        return arrayStr.length;
    }

    @Override
    public String getItem(int position) {
        return arrayStr[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_spinner, null);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String item=arrayStr[position];
        holder.text.setText(item);
        return convertView;
    }
    class ViewHolder {
        TextView text;//
    }


}
