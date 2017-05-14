package com.yunqi.cardreader.di.component;

import android.app.Activity;
import com.yunqi.cardreader.di.module.FragmentModule;
import com.yunqi.cardreader.di.module.FragmentModule_ProvideActivityFactory;
import dagger.internal.ScopedProvider;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DaggerFragmentComponent implements FragmentComponent {
  private Provider<Activity> provideActivityProvider;

  private DaggerFragmentComponent(Builder builder) {  
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {  
    return new Builder();
  }

  private void initialize(final Builder builder) {  
    this.provideActivityProvider = ScopedProvider.create(FragmentModule_ProvideActivityFactory.create(builder.fragmentModule));
  }

  @Override
  public Activity getActivity() {  
    return provideActivityProvider.get();
  }

  public static final class Builder {
    private FragmentModule fragmentModule;
    private AppComponent appComponent;
  
    private Builder() {  
    }
  
    public FragmentComponent build() {  
      if (fragmentModule == null) {
        throw new IllegalStateException("fragmentModule must be set");
      }
      if (appComponent == null) {
        throw new IllegalStateException("appComponent must be set");
      }
      return new DaggerFragmentComponent(this);
    }
  
    public Builder fragmentModule(FragmentModule fragmentModule) {  
      if (fragmentModule == null) {
        throw new NullPointerException("fragmentModule");
      }
      this.fragmentModule = fragmentModule;
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

