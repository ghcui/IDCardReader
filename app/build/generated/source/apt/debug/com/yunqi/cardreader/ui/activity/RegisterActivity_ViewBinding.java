// Generated code from Butter Knife. Do not modify!
package com.yunqi.cardreader.ui.activity;

import android.support.v7.widget.Toolbar;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.yunqi.cardreader.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class RegisterActivity_ViewBinding<T extends RegisterActivity> implements Unbinder {
  protected T target;

  public RegisterActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.toolBar = finder.findRequiredViewAsType(source, R.id.tool_bar, "field 'toolBar'", Toolbar.class);
    target.btnSearch = finder.findRequiredViewAsType(source, R.id.btn_search, "field 'btnSearch'", Button.class);
    target.btnConnect = finder.findRequiredViewAsType(source, R.id.btn_connect, "field 'btnConnect'", Button.class);
    target.btnReadCard = finder.findRequiredViewAsType(source, R.id.btn_readCard, "field 'btnReadCard'", Button.class);
    target.btnDisconnect = finder.findRequiredViewAsType(source, R.id.btn_disconnect, "field 'btnDisconnect'", Button.class);
    target.btnSleep = finder.findRequiredViewAsType(source, R.id.btn_sleep, "field 'btnSleep'", Button.class);
    target.btnAwake = finder.findRequiredViewAsType(source, R.id.btn_awake, "field 'btnAwake'", Button.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.toolBar = null;
    target.btnSearch = null;
    target.btnConnect = null;
    target.btnReadCard = null;
    target.btnDisconnect = null;
    target.btnSleep = null;
    target.btnAwake = null;

    this.target = null;
  }
}
