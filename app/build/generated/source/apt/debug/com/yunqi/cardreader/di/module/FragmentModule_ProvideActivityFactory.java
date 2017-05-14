package com.yunqi.cardreader.di.module;

import android.app.Activity;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class FragmentModule_ProvideActivityFactory implements Factory<Activity> {
  private final FragmentModule module;

  public FragmentModule_ProvideActivityFactory(FragmentModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public Activity get() {  
    Activity provided = module.provideActivity();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<Activity> create(FragmentModule module) {  
    return new FragmentModule_ProvideActivityFactory(module);
  }
}

