package com.gem.timekeeper.screen.authentication;

import com.gem.timekeeper.screen.authentication.login.LoginPresenter;
import com.gemvietnam.base.viper.ViewFragment;
import com.gem.timekeeper.screen.base.AppBaseActivity;

public class AuthenticationActivity extends AppBaseActivity {

  @Override
  public ViewFragment onCreateFirstFragment() {
    return (ViewFragment) new LoginPresenter(this).getFragment();
  }
}
