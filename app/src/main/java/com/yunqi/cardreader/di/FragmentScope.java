package com.yunqi.cardreader.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author ghcui
 * @time 2017/1/11
 */
@Scope
@Retention(RUNTIME)
public @interface FragmentScope {
}
