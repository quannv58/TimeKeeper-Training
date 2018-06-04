package com.gem.timekeeper.screen.main.bestsurvey.list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.gem.timekeeper.R;
import com.gem.timekeeper.data.dto.LocationDTO;
import com.gem.timekeeper.data.dto.SurveyDTO;
import com.gem.timekeeper.pref.PrefWrapper;
import com.gem.timekeeper.screen.adapter.OnItemClickListener;
import com.gem.timekeeper.util.CommonUtils;
import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.utils.RecyclerUtils;
import com.gemvietnam.utils.StringUtils;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * The BestSurvey Fragment
 */
public class BestSurveyFragment extends ViewFragment<BestSurveyContract.Presenter> implements BestSurveyContract.View {

  @BindView(R.id.tv_best_survey_list_location)
  TextView mLocationTv;
  @BindView(R.id.tv_best_survey_list_shift_and_date)
  TextView mShiftAndDateTv;
  @BindView(R.id.edt_best_survey_list_search)
  EditText mSearchEdt;
  @BindView(R.id.srcv_best_survey_list)
  SuperRecyclerView mSuperRecyclerView;

  private BroadcastReceiver mClockReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      updateDateView();
    }
  };

  private TextWatcher mTextWatcher = new TextWatcher() {
    @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    private Timer mTimer = new Timer();
    private static final long DELAY = 500;

    @Override
    public void afterTextChanged(final Editable s) {
      mTimer.cancel();
      mTimer = new Timer();
      mTimer.schedule(new TimerTask() {
        @Override
        public void run() {
          mPresenter.searchSurveyTitle(mSearchEdt.getText().toString());
        }
      }, DELAY);
    }
  };

  private BestSurveyAdapter mAdapter;

  public static BestSurveyFragment getInstance(Bundle data) {
    BestSurveyFragment fragment = new BestSurveyFragment();
    fragment.setArguments(data);
    return fragment;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_best_survey_list;
  }

  @Override
  public void initLayout() {
    super.initLayout();
    mSearchEdt.addTextChangedListener(mTextWatcher);

    RecyclerUtils.setupGridRecyclerView(getViewContext(), mSuperRecyclerView.getRecyclerView(), 3);
    mAdapter = new BestSurveyAdapter(getViewContext(), new OnItemClickListener<SurveyDTO>() {
      @Override
      public void onItemClick(SurveyDTO item) {
        mPresenter.openSurveyForm(item);
      }
    });
    mSuperRecyclerView.setOnMoreListener(null);
    mSuperRecyclerView.setAdapter(mAdapter);

    updateLocationView(PrefWrapper.getLocationSetting(getContext()));
  }

  @Override
  public void updateLocationView(LocationDTO locationDTO) {
    if (isAdded()) {
      mLocationTv.setText(getString(R.string.home_label_location, locationDTO.getName()));
      updateDateView(locationDTO);
    }
  }

  @Override
  public void bindData(final List<SurveyDTO> surveyList) {
    if (isAdded()) {
      getActivity().runOnUiThread(new Runnable() {
        @Override
        public void run() {
          mAdapter.refresh(surveyList);
          mSuperRecyclerView.setRefreshing(false);
          mSuperRecyclerView.setLoadingMore(false);
        }
      });
    }
  }

  private void updateDateView() {
    updateDateView(PrefWrapper.getLocationSetting(getContext()));
  }

  private void updateDateView(LocationDTO locationDTO) {
    SimpleDateFormat df = new SimpleDateFormat("MMM dd", Locale.US);
    LocationDTO.ShiftDTO shift = CommonUtils.getShift(locationDTO);
    if (shift != null && !StringUtils.isEmpty(shift.getName())) {
      mShiftAndDateTv.setText(shift.getName() + ", " + df.format(new Date()));
    } else {
      mShiftAndDateTv.setText(df.format(new Date()));
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    updateDateView();
    if (getContext() != null) {
      getContext().registerReceiver(mClockReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }
  }

  @Override
  public void onStop() {
    super.onStop();
    if (getContext() != null) {
      getContext().unregisterReceiver(mClockReceiver);
    }
  }
}
