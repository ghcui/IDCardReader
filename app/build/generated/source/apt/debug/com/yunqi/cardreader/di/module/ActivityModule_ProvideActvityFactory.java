package com.yunqi.cardreader.di.module;

import android.app.Activity;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class ActivityModule_ProvideActvityFactory implements Factory<Activity> {
  private final ActivityModule module;

  public ActivityModule_ProvideActvityFactory(ActivityModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public Activity get() {  
    Activity provided = module.provideActvity();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<Activity> create(ActivityModule module) {  
    return new ActivityModule_ProvideActvityFactory(module);
  }
}

