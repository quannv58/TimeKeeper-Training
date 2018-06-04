package com.gem.timekeeper.screen.main.bestsurvey.list;

import com.gem.timekeeper.data.dto.LocationDTO;
import com.gem.timekeeper.data.dto.SurveyDTO;
import com.gem.timekeeper.data.dto.SurveyListDTO;
import com.gem.timekeeper.data.remote.callback.CommonQueryCallback;
import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;

import java.util.List;

/**
 * The BestSurvey Contract
 */
interface BestSurveyContract {

  interface Interactor extends IInteractor<Presenter> {
    void getSurveyList(CommonQueryCallback<SurveyListDTO> callback);
  }

  interface View extends PresentView<Presenter> {
    void updateLocationView(LocationDTO locationSetting);

    void bindData(List<SurveyDTO> surveyList);
  }

  interface Presenter extends IPresenter<View, Interactor> {
    void openSurveyForm(SurveyDTO item);

    void searchSurveyTitle(String s);
  }
}