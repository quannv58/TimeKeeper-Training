package com.gem.timekeeper.screen.main.bestsurvey.form;

import android.os.Bundle;
import android.widget.Toast;

import com.gem.timekeeper.R;
import com.gem.timekeeper.data.dto.LocationDTO;
import com.gem.timekeeper.data.dto.SurveyDTO;
import com.gem.timekeeper.data.remote.callback.CommonCommandCallback;
import com.gem.timekeeper.data.remote.callback.CommandResponse;
import com.gem.timekeeper.pref.PrefWrapper;
import com.gem.timekeeper.realm.RealmController;
import com.gem.timekeeper.screen.model.SurveyBlockModel;
import com.gem.timekeeper.util.CommonUtils;
import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.utils.DialogUtils;
import com.gemvietnam.utils.NetworkUtils;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * The BestSurveyForm Presenter
 */
public class BestSurveyFormPresenter extends Presenter<BestSurveyFormContract.View, BestSurveyFormContract.Interactor>
    implements BestSurveyFormContract.Presenter {

  public static final String SURVEY_ID_KEY = "survey_id";

  public BestSurveyFormPresenter(ContainerView containerView, Bundle data) {
    super(containerView, data);
  }

  @Override
  public BestSurveyFormContract.View onCreateView(Bundle data) {
    return BestSurveyFormFragment.getInstance(data);
  }

  @Override
  public void start() {
    // Start getting data here
  }

  @Override
  public BestSurveyFormContract.Interactor onCreateInteractor() {
    return new BestSurveyFormInteractor(this);
  }

  @Override
  public void onSubmitClick(final SurveyDTO surveyDTO, final List<SurveyBlockModel> surveyBlockModelList) {
    for (SurveyBlockModel surveyBlockModel : surveyBlockModelList) {
      if (surveyBlockModel.getSurveyBlock().validateValue() >= 0) {
        CommonUtils.showToast(getViewContext(), R.string.survey_submit_required, Toast.LENGTH_LONG);
        // TODO smooth scroll
        return;
      }
    }
    LocationDTO locationDTO = PrefWrapper.getLocationSetting(getViewContext());
    final int locationId = locationDTO.getId();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
    final String submitted = df.format(new Date());
    final String surveyData = new Gson().toJson(surveyDTO);
    if (NetworkUtils.isNoNetworkAvailable(getViewContext(), false)) {
      processOffline(locationId, submitted, surveyDTO.getTitle(), surveyData);
    } else{
      mView.showProgress();
      mInteractor.submitSurvey(locationId, submitted, surveyDTO.getTitle(), surveyData, new CommonCommandCallback(getViewContext()) {
        @Override
        protected void onSuccess(CommandResponse responseBody) {
          super.onSuccess(responseBody);
          CommonUtils.showToast(getViewContext(), R.string.survey_submit_success, Toast.LENGTH_LONG);
          back();
        }

        @Override
        protected void onNetworkError(String message) {
          DialogUtils.dismissProgressDialog();
          processOffline(locationId, submitted, surveyDTO.getTitle(), surveyData);
        }
      });
    }
  }

  private void processOffline(int locationId, String submitted, String title, String surveyData) {
    RealmController.getInstance().saveCompleteSurvey(locationId, submitted, title, surveyData);
    CommonUtils.showToast(getViewContext(), R.string.survey_submit_offline, Toast.LENGTH_LONG);
    // start KeepRunningService to start jobService register networkChangeCallback here
    // instead of start on application start
    back();
  }
}
