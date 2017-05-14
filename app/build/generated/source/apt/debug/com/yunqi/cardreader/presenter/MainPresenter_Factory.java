package com.yunqi.cardreader.presenter;

import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class MainPresenter_Factory implements Factory<MainPresenter> {
  private final MembersInjector<MainPresenter> membersInjector;

  public MainPresenter_Factory(MembersInjector<MainPresenter> membersInjector) {  
    assert membersInjector != null;
    this.membersInjector = membersInjector;
  }

  @Override
  public MainPresenter get() {  
    MainPresenter instance = new MainPresenter();
    membersInjector.injectMembers(instance);
    return instance;
  }

  public static Factory<MainPresenter> create(MembersInjector<MainPresenter> membersInjector) {  
    return new MainPresenter_Factory(membersInjector);
  }
}

