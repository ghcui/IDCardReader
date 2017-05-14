package com.yunqi.cardreader.di.component;

import android.app.Activity;
import com.yunqi.cardreader.base.BaseActivity;
import com.yunqi.cardreader.base.BaseActivity_MembersInjector;
import com.yunqi.cardreader.di.module.ActivityModule;
import com.yunqi.cardreader.di.module.ActivityModule_ProvideActvityFactory;
import com.yunqi.cardreader.model.db.GreenDaoHelper;
import com.yunqi.cardreader.model.http.RetrofitHelper;
import com.yunqi.cardreader.presenter.LoginPresenter;
import com.yunqi.cardreader.presenter.LoginPresenter_Factory;
import com.yunqi.cardreader.presenter.MainPresenter;
import com.yunqi.cardreader.presenter.MainPresenter_Factory;
import com.yunqi.cardreader.presenter.RegisterPresenter;
import com.yunqi.cardreader.presenter.RegisterPresenter_Factory;
import com.yunqi.cardreader.ui.activity.LoginActivity;
import com.yunqi.cardreader.ui.activity.MainActivity;
import com.yunqi.cardreader.ui.activity.RegisterActivity;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DaggerActivityComponent implements ActivityComponent {
  private Provider<Activity> provideActvityProvider;
  private Provider<MainPresenter> mainPresenterProvider;
  private MembersInjector<BaseActivity<MainPresenter>> baseActivityMembersInjector;
  private MembersInjector<MainActivity> mainActivityMembersInjector;
  private Provider<RetrofitHelper> retrofitHelperProvider;
  private Provider<GreenDaoHelper> greenDaoHelperProvider;
  private Provider<LoginPresenter> loginPresenterProvider;
  private MembersInjector<BaseActivity<LoginPresenter>> baseActivityMembersInjector1;
  private MembersInjector<LoginActivity> loginActivityMembersInjector;
  private Provider<RegisterPresenter> registerPresenterProvider;
  private MembersInjector<BaseActivity<RegisterPresenter>> baseActivityMembersInjector2;
  private MembersInjector<RegisterActivity> registerActivityMembersInjector;

  private DaggerActivityComponent(Builder builder) {  
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {  
    return new Builder();
  }

  private void initialize(final Builder builder) {  
    this.provideActvityProvider = ScopedProvider.create(ActivityModule_ProvideActvityFactory.create(builder.activityModule));
    this.mainPresenterProvider = MainPresenter_Factory.create((MembersInjector) MembersInjectors.noOp());
    this.baseActivityMembersInjector = BaseActivity_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), mainPresenterProvider);
    this.mainActivityMembersInjector = MembersInjectors.delegatingTo(baseActivityMembersInjector);
    this.retrofitHelperProvider = new Factory<RetrofitHelper>() {
      private final AppComponent appComponent = builder.appComponent;
      @Override public RetrofitHelper get() {
        RetrofitHelper provided = appComponent.retrofitHelper();
        if (provided == null) {
          throw new NullPointerException("Cannot return null from a non-@Nullable component method");
        }
        return provided;
      }
    };
    this.greenDaoHelperProvider = new Factory<GreenDaoHelper>() {
      private final AppComponent appComponent = builder.appComponent;
      @Override public GreenDaoHelper get() {
        GreenDaoHelper provided = appComponent.greenDaoHelper();
        if (provided == null) {
          throw new NullPointerException("Cannot return null from a non-@Nullable component method");
        }
        return provided;
      }
    };
    this.loginPresenterProvider = LoginPresenter_Factory.create((MembersInjector) MembersInjectors.noOp(), retrofitHelperProvider, greenDaoHelperProvider);
    this.baseActivityMembersInjector1 = BaseActivity_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), loginPresenterProvider);
    this.loginActivityMembersInjector = MembersInjectors.delegatingTo(baseActivityMembersInjector1);
    this.registerPresenterProvider = RegisterPresenter_Factory.create((MembersInjector) MembersInjectors.noOp());
    this.baseActivityMembersInjector2 = BaseActivity_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), registerPresenterProvider);
    this.registerActivityMembersInjector = MembersInjectors.delegatingTo(baseActivityMembersInjector2);
  }

  @Override
  public Activity getActivity() {  
    return provideActvityProvider.get();
  }

  @Override
  public void inject(MainActivity activity) {  
    mainActivityMembersInjector.injectMembers(activity);
  }

  @Override
  public void inject(LoginActivity activity) {  
    loginActivityMembersInjector.injectMembers(activity);
  }

  @Override
  public void inject(RegisterActivity activity) {  
    registerActivityMembersInjector.injectMembers(activity);
  }

  public static final class Builder {
    private ActivityModule activityModule;
    private AppComponent appComponent;
  
    private Builder() {  
    }
  
    public ActivityComponent build() {  
      if (activityModule == null) {
        throw new IllegalStateException("activityModule must be set");
      }
      if (appComponent == null) {
        throw new IllegalStateException("appComponent must be set");
      }
      return new DaggerActivityComponent(this);
    }
  
    public Builder activityModule(ActivityModule activityModule) {  
      if (activityModule == null) {
        throw new NullPointerException("activityModule");
      }
      this.activityModule = activityModule;
      return this;
    }
  
    public Builder appComponent(AppComponent appComponent) {  
      if (appComponent == null) {
        throw new NullPointerException("appComponent");
      }
      this.appComponent = appComponent;
      return this;
    }
  }
}

