package com.gem.timekeeper.screen.authentication.login;

import com.gemvietnam.base.viper.Interactor;
import com.gem.timekeeper.data.dto.UserDTO;
import com.gem.timekeeper.data.remote.ServiceBuilder;

import retrofit2.Callback;

/**
 * Created by BaVV on 20/11/2017.
 * LoginInteractor
 */

class LoginInteractor extends Interactor<LoginContract.Presenter> implements LoginContract.Interactor {
  LoginInteractor(LoginContract.Presenter presenter) {
    super(presenter);
  }

  @Override
  public void login(String username, String password, Callback<UserDTO> callback) {
    ServiceBuilder.getCommonService().login(username, password, "password", true).enqueue(callback);
  }
}
