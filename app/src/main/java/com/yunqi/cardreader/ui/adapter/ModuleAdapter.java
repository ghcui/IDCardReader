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
 * 扩展应用适配器
 *
 * @author RXXU
 */
public class ModuleAdapter extends BaseAdapter {

    /**
     * 转化器
     */
    private LayoutInflater layoutInflater;
    /**
     * 数据源
     */
    private List<Module> moduleList;
    /**
     * 上下文
     */
    private Context context;
    /**
     * Application
     */

    public ModuleAdapter(Context context, List<Module> moduleList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.moduleList = moduleList;

    }

    @Override
    public int getCount() {
        return moduleList.size();
    }

    @Override
    public Module getItem(int position) {
        return moduleList.get(position);
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
            convertView = layoutInflater.inflate(R.layout.item_module, null);
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Module module=moduleList.get(position);
        holder.text.setText(module.name);
        holder.icon.setImageBitmap(getImageFromAssetsFile("icons/"+module.icon+".png"));
        return convertView;
    }
    class ViewHolder {
        ImageView icon;//模块图标
        TextView text;//模块名字
    }

    private Bitmap getImageFromAssetsFile(String fileName)
    {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try
        {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return image;
    }
}
