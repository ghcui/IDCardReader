package com.yunqi.cardreader.presenter;

import com.yunqi.cardreader.model.db.GreenDaoHelper;
import com.yunqi.cardreader.model.http.RetrofitHelper;
import dagger.MembersInjector;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class LoginPresenter_Factory implements Factory<LoginPresenter> {
  private final MembersInjector<LoginPresenter> membersInjector;
  private final Provider<RetrofitHelper> retrofitHelperProvider;
  private final Provider<GreenDaoHelper> greenDaoHelperProvider;

  public LoginPresenter_Factory(MembersInjector<LoginPresenter> membersInjector, Provider<RetrofitHelper> retrofitHelperProvider, Provider<GreenDaoHelper> greenDaoHelperProvider) {  
    assert membersInjector != null;
    this.membersInjector = membersInjector;
    assert retrofitHelperProvider != null;
    this.retrofitHelperProvider = retrofitHelperProvider;
    assert greenDaoHelperProvider != null;
    this.greenDaoHelperProvider = greenDaoHelperProvider;
  }

  @Override
  public LoginPresenter get() {  
    LoginPresenter instance = new LoginPresenter(retrofitHelperProvider.get(), greenDaoHelperProvider.get());
    membersInjector.injectMembers(instance);
    return instance;
  }

  public static Factory<LoginPresenter> create(MembersInjector<LoginPresenter> membersInjector, Provider<RetrofitHelper> retrofitHelperProvider, Provider<GreenDaoHelper> greenDaoHelperProvider) {  
    return new LoginPresenter_Factory(membersInjector, retrofitHelperProvider, greenDaoHelperProvider);
  }
}

