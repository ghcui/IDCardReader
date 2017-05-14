package com.yunqi.cardreader.di.component;

import com.yunqi.cardreader.app.App;
import com.yunqi.cardreader.di.module.AppModule;
import com.yunqi.cardreader.di.module.AppModule_ProvideApplicationContextFactory;
import com.yunqi.cardreader.di.module.AppModule_ProvideGreenDaoHelperFactory;
import com.yunqi.cardreader.di.module.AppModule_ProvideRetrofitHelperFactory;
import com.yunqi.cardreader.model.db.GreenDaoHelper;
import com.yunqi.cardreader.model.http.RetrofitHelper;
import dagger.internal.ScopedProvider;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DaggerAppComponent implements AppComponent {
  private Provider<App> provideApplicationContextProvider;
  private Provider<RetrofitHelper> provideRetrofitHelperProvider;
  private Provider<GreenDaoHelper> provideGreenDaoHelperProvider;

  private DaggerAppComponent(Builder builder) {  
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {  
    return new Builder();
  }

  private void initialize(final Builder builder) {  
    this.provideApplicationContextProvider = ScopedProvider.create(AppModule_ProvideApplicationContextFactory.create(builder.appModule));
    this.provideRetrofitHelperProvider = ScopedProvider.create(AppModule_ProvideRetrofitHelperFactory.create(builder.appModule));
    this.provideGreenDaoHelperProvider = ScopedProvider.create(AppModule_ProvideGreenDaoHelperFactory.create(builder.appModule));
  }

  @Override
  public App getContext() {  
    return provideApplicationContextProvider.get();
  }

  @Override
  public RetrofitHelper retrofitHelper() {  
    return provideRetrofitHelperProvider.get();
  }

  @Override
  public GreenDaoHelper greenDaoHelper() {  
    return provideGreenDaoHelperProvider.get();
  }

  public static final class Builder {
    private AppModule appModule;
  
    private Builder() {  
    }
  
    public AppComponent build() {  
      if (appModule == null) {
        throw new IllegalStateException("appModule must be set");
      }
      return new DaggerAppComponent(this);
    }
  
    public Builder appModule(AppModule appModule) {  
      if (appModule == null) {
        throw new NullPointerException("appModule");
      }
      this.appModule = appModule;
      return this;
    }
  }
}

