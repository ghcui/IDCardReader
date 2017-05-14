package com.yunqi.cardreader.presenter;

import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class RegisterPresenter_Factory implements Factory<RegisterPresenter> {
  private final MembersInjector<RegisterPresenter> membersInjector;

  public RegisterPresenter_Factory(MembersInjector<RegisterPresenter> membersInjector) {  
    assert membersInjector != null;
    this.membersInjector = membersInjector;
  }

  @Override
  public RegisterPresenter get() {  
    RegisterPresenter instance = new RegisterPresenter();
    membersInjector.injectMembers(instance);
    return instance;
  }

  public static Factory<RegisterPresenter> create(MembersInjector<RegisterPresenter> membersInjector) {  
    return new RegisterPresenter_Factory(membersInjector);
  }
}

