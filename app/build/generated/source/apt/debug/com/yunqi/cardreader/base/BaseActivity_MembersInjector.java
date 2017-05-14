package com.yunqi.cardreader.base;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import me.yokeyword.fragmentation.SupportActivity;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class BaseActivity_MembersInjector<T extends BasePresenter> implements MembersInjector<BaseActivity<T>> {
  private final MembersInjector<SupportActivity> supertypeInjector;
  private final Provider<T> mPresenterProvider;

  public BaseActivity_MembersInjector(MembersInjector<SupportActivity> supertypeInjector, Provider<T> mPresenterProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert mPresenterProvider != null;
    this.mPresenterProvider = mPresenterProvider;
  }

  @Override
  public void injectMembers(BaseActivity<T> instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.mPresenter = mPresenterProvider.get();
  }

  public static <T extends BasePresenter> MembersInjector<BaseActivity<T>> create(MembersInjector<SupportActivity> supertypeInjector, Provider<T> mPresenterProvider) {  
      return new BaseActivity_MembersInjector<T>(supertypeInjector, mPresenterProvider);
  }
}

