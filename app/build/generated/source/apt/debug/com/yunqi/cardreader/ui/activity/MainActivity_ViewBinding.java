// Generated code from Butter Knife. Do not modify!
package com.yunqi.cardreader.ui.activity;

import android.widget.Button;
import android.widget.GridView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.yunqi.cardreader.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class MainActivity_ViewBinding<T extends MainActivity> implements Unbinder {
  protected T target;

  public MainActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.gridModule = finder.findRequiredViewAsType(source, R.id.grid_module, "field 'gridModule'", GridView.class);
    target.btnRegister = finder.findRequiredViewAsType(source, R.id.btn_register, "field 'btnRegister'", Button.class);
    target.btnCheckOut = finder.findRequiredViewAsType(source, R.id.btn_check_out, "field 'btnCheckOut'", Button.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.gridModule = null;
    target.btnRegister = null;
    target.btnCheckOut = null;

    this.target = null;
  }
}
