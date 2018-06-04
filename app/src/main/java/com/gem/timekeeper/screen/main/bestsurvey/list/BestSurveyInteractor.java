package com.gem.timekeeper.screen.main.bestsurvey.list;

import com.gem.timekeeper.data.dto.SurveyListDTO;
import com.gem.timekeeper.data.remote.ServiceBuilder;
import com.gem.timekeeper.data.remote.callback.CommonQueryCallback;
import com.gemvietnam.base.viper.Interactor;

/**
 * The BestSurvey interactor
 */
class BestSurveyInteractor extends Interactor<BestSurveyContract.Presenter>
    implements BestSurveyContract.Interactor {

  BestSurveyInteractor(BestSurveyContract.Presenter presenter) {
    super(presenter);
  }

  @Override
  public void getSurveyList(CommonQueryCallback<SurveyListDTO> callback) {
    ServiceBuilder.getSurveyService().getSurveyList("").enqueue(callback);
  }
}
