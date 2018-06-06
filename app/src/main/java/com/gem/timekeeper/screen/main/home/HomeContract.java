package com.gem.timekeeper.screen.main.home;

import android.support.annotation.NonNull;

import com.gem.timekeeper.data.dto.LocationDTO;
import com.gem.timekeeper.data.remote.callback.CommonCallback;
import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;

/**
 * The Home Contract
 */
interface HomeContract {

  interface Interactor extends IInteractor<Presenter> {
    void getLocationSetting(CommonCallback<LocationDTO> callback);
  }

  interface View extends PresentView<Presenter> {
    void updateLocationView(@NonNull LocationDTO locationDTO);
  }

  interface Presenter extends IPresenter<View, Interactor> {
    void onBestSurveySelected();
  }
}



