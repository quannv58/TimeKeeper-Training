package com.gem.timekeeper.screen.main.bestsurvey.form;

import com.gem.timekeeper.data.dto.CompleteSurveyListDTO;
import com.gem.timekeeper.data.remote.ServiceBuilder;
import com.gem.timekeeper.data.remote.callback.CommonCommandCallback;
import com.gemvietnam.base.viper.Interactor;

import java.util.ArrayList;
import java.util.List;

/**
 * The BestSurveyForm interactor
 */
class BestSurveyFormInteractor extends Interactor<BestSurveyFormContract.Presenter>
    implements BestSurveyFormContract.Interactor {

  BestSurveyFormInteractor(BestSurveyFormContract.Presenter presenter) {
    super(presenter);
  }

  @Override
  public void submitSurvey(int locationId, String submitted, String title, String surveyData, CommonCommandCallback callback) {
    CompleteSurveyListDTO.CompleteSurveyDTO completeSurveyDTO =
        new CompleteSurveyListDTO.CompleteSurveyDTO.Builder()
            .setLocationId(locationId).setSubmitted(submitted)
            .setTitle(title).setSurveyData(surveyData).build();
    List<CompleteSurveyListDTO.CompleteSurveyDTO> data = new ArrayList<>();
    data.add(completeSurveyDTO);

    CompleteSurveyListDTO completeSurveyListDTO = new CompleteSurveyListDTO();
    completeSurveyListDTO.setBestSurveySubmits(data);

    ServiceBuilder.getSurveyService().submitSurvey(completeSurveyListDTO).enqueue(callback);
  }
}
