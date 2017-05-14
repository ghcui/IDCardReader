package com.yunqi.cardreader.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.yunqi.cardreader.di.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author ghcui
 * @time 2017/1/11
 */
@Module
public class FragmentModule {
    private Fragment fragment;
    public FragmentModule(Fragment fragment){
        this.fragment=fragment;
    }
    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}
