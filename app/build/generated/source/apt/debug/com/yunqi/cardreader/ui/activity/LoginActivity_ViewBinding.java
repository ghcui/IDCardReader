// Generated code from Butter Knife. Do not modify!
package com.yunqi.cardreader.ui.activity;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import com.yunqi.cardreader.R;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class LoginActivity_ViewBinding<T extends LoginActivity> implements Unbinder {
  protected T target;

  public LoginActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.editAccount = finder.findRequiredViewAsType(source, R.id.edit_account, "field 'editAccount'", EditText.class);
    target.editPassword = finder.findRequiredViewAsType(source, R.id.edit_password, "field 'editPassword'", EditText.class);
    target.btnLogin = finder.findRequiredViewAsType(source, R.id.btn_login, "field 'btnLogin'", Button.class);
    target.imgDelUname = finder.findRequiredViewAsType(source, R.id.img_del_uname, "field 'imgDelUname'", ImageView.class);
    target.imgDelPwd = finder.findRequiredViewAsType(source, R.id.img_del_pwd, "field 'imgDelPwd'", ImageView.class);
    target.layoutLoginBg = finder.findRequiredViewAsType(source, R.id.layout_login_bg, "field 'layoutLoginBg'", ViewGroup.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.editAccount = null;
    target.editPassword = null;
    target.btnLogin = null;
    target.imgDelUname = null;
    target.imgDelPwd = null;
    target.layoutLoginBg = null;

    this.target = null;
  }
}
