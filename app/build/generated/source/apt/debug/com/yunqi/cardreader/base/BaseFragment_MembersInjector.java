package com.yunqi.cardreader.base;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import me.yokeyword.fragmentation.SupportFragment;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class BaseFragment_MembersInjector<T extends BasePresenter> implements MembersInjector<BaseFragment<T>> {
  private final MembersInjector<SupportFragment> supertypeInjector;
  private final Provider<T> mPresenterProvider;

  public BaseFragment_MembersInjector(MembersInjector<SupportFragment> supertypeInjector, Provider<T> mPresenterProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert mPresenterProvider != null;
    this.mPresenterProvider = mPresenterProvider;
  }

  @Override
  public void injectMembers(BaseFragment<T> instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.mPresenter = mPresenterProvider.get();
  }

  public static <T extends BasePresenter> MembersInjector<BaseFragment<T>> create(MembersInjector<SupportFragment> supertypeInjector, Provider<T> mPresenterProvider) {  
      return new BaseFragment_MembersInjector<T>(supertypeInjector, mPresenterProvider);
  }
}

