package com.yunqi.cardreader.di.module;

import com.yunqi.cardreader.model.http.RetrofitHelper;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class AppModule_ProvideRetrofitHelperFactory implements Factory<RetrofitHelper> {
  private final AppModule module;

  public AppModule_ProvideRetrofitHelperFactory(AppModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public RetrofitHelper get() {  
    RetrofitHelper provided = module.provideRetrofitHelper();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<RetrofitHelper> create(AppModule module) {  
    return new AppModule_ProvideRetrofitHelperFactory(module);
  }
}

