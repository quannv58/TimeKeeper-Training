package com.gem.timekeeper.screen.main.bestsurvey.form;

import com.gem.timekeeper.data.dto.SurveyDTO;
import com.gem.timekeeper.data.remote.callback.CommonCommandCallback;
import com.gem.timekeeper.screen.model.SurveyBlockModel;
import com.gemvietnam.base.viper.interfaces.IInteractor;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.PresentView;

import java.util.List;

/**
 * The BestSurveyForm Contract
 */
interface BestSurveyFormContract {

  interface Interactor extends IInteractor<Presenter> {
    void submitSurvey(int locationId, String submitted, String title, String surveyData, CommonCommandCallback callback);
  }

  interface View extends PresentView<Presenter> {
  }

  interface Presenter extends IPresenter<View, Interactor> {
    void onSubmitClick(SurveyDTO mSurveyDTO, List<SurveyBlockModel> surveyBlockModelList);
  }
}



