package com.gem.timekeeper.screen.main.bestsurvey.form;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.gem.timekeeper.data.dto.SurveyDTO;
import com.gem.timekeeper.realm.RealmController;
import com.gem.timekeeper.screen.model.SurveyBlockModel;
import com.gemvietnam.base.viper.ViewFragment;
import com.gem.timekeeper.R;
import com.gemvietnam.utils.DialogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The BestSurveyForm Fragment
 */
public class BestSurveyFormFragment extends ViewFragment<BestSurveyFormContract.Presenter> implements BestSurveyFormContract.View {

  @BindView(R.id.tv_best_survey_form_title)
  TextView mTitleTv;
  @BindView(R.id.rcv_best_survey_form_content)
  RecyclerView mRecyclerView;

  private List<SurveyBlockModel> mSurveyBlockModelList;
  private SurveyDTO mSurveyDTO;

  private BestSurveyContentAdapter mAdapter;

  public static BestSurveyFormFragment getInstance(Bundle data) {
    BestSurveyFormFragment fragment = new BestSurveyFormFragment();
    fragment.setArguments(data);
    return fragment;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_best_survey_form;
  }

  @Override
  public void initLayout() {
    super.initLayout();
    mAdapter = new BestSurveyContentAdapter(getViewContext());
    mRecyclerView.setAdapter(mAdapter);

    if (getArguments() != null) {
      mSurveyDTO = RealmController.getInstance().getSurveyById(
          getArguments().getInt(BestSurveyFormPresenter.SURVEY_ID_KEY));
      if (mSurveyDTO != null) {
        mTitleTv.setText(mSurveyDTO.getTitle());
        mSurveyBlockModelList = SurveyBlockModel.listFromDTO(mSurveyDTO);
        mAdapter.refresh(mSurveyBlockModelList);
      }
    }
  }

  @OnClick(R.id.btn_best_survey_form_submit)
  void onSubmitClick() {
    mPresenter.onSubmitClick(mSurveyDTO, mSurveyBlockModelList);
  }

  @OnClick({R.id.btn_best_survey_form_cancel, R.id.iv_best_survey_form_close})
  void onCancelClick() {
    DialogUtils.showDialogMessage(getContext(), getString(R.string.common_confirm_title),
        getString(R.string.survey_cancel), getString(R.string.common_yes), getString(R.string.common_close),
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            mPresenter.back();
          }
        });
  }
}
