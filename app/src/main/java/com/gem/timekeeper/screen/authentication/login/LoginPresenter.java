package com.gem.timekeeper.screen.authentication.login;

import android.content.Intent;
import android.os.Bundle;

import com.gem.timekeeper.data.remote.callback.LoginCallback;
import com.gem.timekeeper.realm.RealmController;
import com.gem.timekeeper.screen.main.MainActivity;
import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gem.timekeeper.data.dto.UserDTO;
import com.gem.timekeeper.pref.PrefWrapper;
import com.gemvietnam.utils.StringUtils;

/**
 * Created by BaVV on 20/11/2017.
 * LoginPresenter
 */

public class LoginPresenter extends Presenter<LoginContract.View, LoginContract.Interactor>
    implements LoginContract.Presenter {

  public LoginPresenter(ContainerView containerView) {
    super(containerView);
  }

  @Override
  public LoginContract.View onCreateView(Bundle data) {
    return LoginFragment.getInstance(data);
  }

  @Override
  public void start() {
    UserDTO user = PrefWrapper.getUser(getViewContext());
    if (user != null && !StringUtils.isEmpty(user.getAccessToken())) {
      openMainScreen();
    } else {
      PrefWrapper.saveLocationSetting(getViewContext(), null);
      PrefWrapper.setDeviceTokenRegistered(getViewContext(), false);
      RealmController.getInstance().clearAllSurvey();
      RealmController.getInstance().deleteCompleteSurvey();
    }
  }

  private void openMainScreen() {
    getViewContext().startActivity(new Intent(getViewContext(), MainActivity.class));
    getViewContext().finish();
  }

  @Override
  public LoginContract.Interactor onCreateInteractor() {
    return new LoginInteractor(this);
  }

  @Override
  public void login(final String username, final String password) {
    mView.showProgress();
    mInteractor.login(username, password, new LoginCallback(getViewContext()) {
      @Override
      protected void onSuccess(UserDTO userDTO) {
        super.onSuccess(userDTO);
        openMainScreen();
      }
    });
  }
}
