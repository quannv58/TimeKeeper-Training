package com.gem.timekeeper.screen.authentication.login;

import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;
import com.gem.timekeeper.data.dto.UserDTO;

import retrofit2.Callback;

/**
 * Created by phamhaidang on 20/11/2017.
 * LoginContract
 */

interface LoginContract {
  interface Interactor extends IInteractor<Presenter> {
    void login(String username, String password, Callback<UserDTO> callback);
  }

  interface View extends PresentView<Presenter> {
  }

  interface Presenter extends IPresenter<View, Interactor> {
    void login(String username, String password);
  }
}
