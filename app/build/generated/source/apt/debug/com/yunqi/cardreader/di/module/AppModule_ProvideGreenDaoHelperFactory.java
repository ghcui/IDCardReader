package com.yunqi.cardreader.di.module;

import com.yunqi.cardreader.model.db.GreenDaoHelper;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class AppModule_ProvideGreenDaoHelperFactory implements Factory<GreenDaoHelper> {
  private final AppModule module;

  public AppModule_ProvideGreenDaoHelperFactory(AppModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public GreenDaoHelper get() {  
    GreenDaoHelper provided = module.provideGreenDaoHelper();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<GreenDaoHelper> create(AppModule module) {  
    return new AppModule_ProvideGreenDaoHelperFactory(module);
  }
}

