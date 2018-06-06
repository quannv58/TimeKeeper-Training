package com.gem.timekeeper.screen.main.bestsurvey.list;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.gem.timekeeper.data.dto.SurveyDTO;
import com.gem.timekeeper.data.dto.SurveyListDTO;
import com.gem.timekeeper.data.remote.callback.BaseResponse;
import com.gem.timekeeper.data.remote.callback.CommonCallback;
import com.gem.timekeeper.eventbus.SettingUpdateEvent;
import com.gem.timekeeper.pref.PrefWrapper;
import com.gem.timekeeper.realm.RealmController;
import com.gem.timekeeper.screen.SubActivity;
import com.gem.timekeeper.screen.main.bestsurvey.form.BestSurveyFormPresenter;
import com.gemvietnam.base.viper.Presenter;
import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.eventbus.EventBusWrapper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * The BestSurvey Presenter
 */
public class BestSurveyPresenter extends Presenter<BestSurveyContract.View, BestSurveyContract.Interactor>
    implements BestSurveyContract.Presenter {

  private List<SurveyDTO> mSurveyList = new ArrayList<>();
  private List<SurveyDTO> mSearchSurveyList = new ArrayList<>();
  private String mSearchString = "";

  public BestSurveyPresenter(ContainerView containerView) {
    super(containerView);
  }

  @Override
  public BestSurveyContract.View onCreateView(Bundle data) {
    return BestSurveyFragment.getInstance(data);
  }

  @Override
  public void start() {
    mSurveyList.clear();
    mSearchSurveyList.clear();
    mSurveyList.addAll(RealmController.getInstance().getAllSurvey());
    mSearchSurveyList.addAll(mSurveyList);
    if (mSurveyList.size() > 0) {
      mView.bindData(mSearchSurveyList);
    } else {
      getSurveyList();
    }
  }

  @Override
  public BestSurveyContract.Interactor onCreateInteractor() {
    return new BestSurveyInteractor(this);
  }

  private void getSurveyList() {
    mView.showProgress();
    mInteractor.getSurveyList(new CommonCallback<SurveyListDTO>(getViewContext()) {
      @Override
      public void onSuccess(BaseResponse<SurveyListDTO> responseBody) {
        RealmController.getInstance().saveSurveyList(responseBody.getData().getSurveyList());
        EventBusWrapper.post(new SettingUpdateEvent(SettingUpdateEvent.Type.SURVEY));
        super.onSuccess(responseBody);
      }
    });
  }

  @Override
  public void openSurveyForm(SurveyDTO item) {
    Bundle bundle = new Bundle();
    bundle.putInt(BestSurveyFormPresenter.SURVEY_ID_KEY, item.getId());
    SubActivity.start(getViewContext(), SubActivity.FirstScreen.SURVEY_FORM, bundle);
  }

  @Override
  public void searchSurveyTitle(@NonNull String strSearch) {
    mSearchString = strSearch.trim().toLowerCase();
    if (!"".equals(mSearchString)) {
      mSearchSurveyList.clear();
      for (SurveyDTO item : mSurveyList) {
        String title = item.getTitle().toLowerCase();
        String[] words = mSearchString.split(" ");
        boolean contain = true;
        for (String word : words) {
          if (!title.contains(word)) {
            contain = false;
            break;
          }
        }
        if (contain) mSearchSurveyList.add(item);
      }
      mView.bindData(mSearchSurveyList);
    } else {
      mSearchSurveyList.clear();
      mSearchSurveyList.addAll(mSurveyList);
      mView.bindData(mSurveyList);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onSettingUpdate(SettingUpdateEvent event) {
    if (event.getType() == SettingUpdateEvent.Type.LOCATION) {
      mView.updateLocationView(PrefWrapper.getLocationSetting(getViewContext()));
    } else if (event.getType() == SettingUpdateEvent.Type.SURVEY) {
      mSurveyList.clear();
      mSurveyList.addAll(RealmController.getInstance().getAllSurvey());
      searchSurveyTitle(mSearchString);
    }
  }
}
