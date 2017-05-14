package com.yunqi.cardreader.di.module;

import com.yunqi.cardreader.app.App;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class AppModule_ProvideApplicationContextFactory implements Factory<App> {
  private final AppModule module;

  public AppModule_ProvideApplicationContextFactory(AppModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public App get() {  
    App provided = module.provideApplicationContext();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<App> create(AppModule module) {  
    return new AppModule_ProvideApplicationContextFactory(module);
  }
}

