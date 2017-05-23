package com.yunqi.cardreader.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.yunqi.cardreader.R;
import com.yunqi.cardreader.app.App;
import com.yunqi.cardreader.di.component.ActivityComponent;
import com.yunqi.cardreader.di.component.DaggerActivityComponent;
import com.yunqi.cardreader.di.module.ActivityModule;


import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by codeest on 2016/8/2.
 * MVP activity基类
 */
public abstract class BaseActivity<T extends BasePresenter> extends SupportActivity implements BaseView{
    protected String TAG = getClass().getName();
    @Inject
    protected T mPresenter;
    protected Activity mContext;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onTitleBackClikced();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 点击返回按钮
     */
    protected void onTitleBackClikced() {
        this.finish();
    }



    private void init() {
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);
        App.getInstance().addActivity(this);
        initEventAndData();
    }

    /**
     * 设置ToolBar（包含返回按钮+中间title）
     *
     * @param toolbar
     * @param title
     */
    protected void setToolBar(Toolbar toolbar, String title) {
        this.setToolBar(toolbar, title, -1, null, null);
    }

    private void setToolBar(Toolbar toolbar, String title, int rightImgRes, String strRight, View.OnClickListener listener) {
        toolbar.setTitle("");//使用处于中间位置自定义Title
        TextView titleCenter = (TextView) toolbar.findViewById(R.id.title_center);
        titleCenter.setText(title);
        if (rightImgRes > 0) {
            ViewGroup layoutRight = (ViewGroup) toolbar.findViewById(R.id.layout_right);
            layoutRight.setVisibility(View.VISIBLE);
            layoutRight.setOnClickListener(listener);
            ImageView imgRight = (ImageView) toolbar.findViewById(R.id.img_right);
            imgRight.setImageResource(rightImgRes);
            imgRight.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(strRight)) {
            ViewGroup layoutRight = (ViewGroup) toolbar.findViewById(R.id.layout_right);
            layoutRight.setOnClickListener(listener);
            layoutRight.setVisibility(View.VISIBLE);
            TextView txtRight = (TextView) toolbar.findViewById(R.id.txt_right);
            txtRight.setText(strRight);
            txtRight.setVisibility(View.VISIBLE);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
    }

    /**
     * 设置ToolBar（包含返回按钮+中间title+右边文字按钮）
     *
     * @param toolbar
     * @param title
     */
    protected void setToolBar(Toolbar toolbar, String title, String strRight, View.OnClickListener listener) {
        this.setToolBar(toolbar, title, -1, strRight, listener);
    }

    /**
     * 设置ToolBar（包含返回按钮+中间title+右边图片按钮）
     *
     * @param toolbar
     * @param title
     */
    protected void setToolBar(Toolbar toolbar, String title, int rightImgRes, View.OnClickListener listener) {
        this.setToolBar(toolbar, title, rightImgRes, null, listener);
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        mUnBinder.unbind();
        App.getInstance().removeActivity(this);
    }

    protected abstract void initInject();

    protected abstract int getLayout();

    protected abstract void initEventAndData();
}