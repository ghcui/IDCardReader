package com.yunqi.cardreader.di.component;

import android.app.Activity;

import com.yunqi.cardreader.di.FragmentScope;
import com.yunqi.cardreader.di.module.FragmentModule;

import dagger.Component;

/**
 * @author ghcui
 * @time 2017/1/11
 */
@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();




}
