package com.gem.timekeeper.screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.gem.timekeeper.screen.main.bestsurvey.form.BestSurveyFormPresenter;
import com.gem.timekeeper.screen.main.bestsurvey.list.BestSurveyPresenter;
import com.gemvietnam.base.viper.ViewFragment;
import com.gem.timekeeper.screen.base.AppBaseActivity;

public class SubActivity extends AppBaseActivity {

  private static final String FIRST_FRAGMENT_KEY = "first_fragment";
  private static final String BUNDLE_DATA_KEY = "bundle_data";

  public enum FirstScreen {
    SURVEY_LIST, SURVEY_FORM
  }

  public static void start(Context context, FirstScreen firstScreen, Bundle data) {
    Intent intent = new Intent(context, SubActivity.class);
    intent.putExtra(FIRST_FRAGMENT_KEY, firstScreen);
    intent.putExtra(BUNDLE_DATA_KEY, data);
    context.startActivity(intent);
  }

  public static void start(Context context, FirstScreen firstScreen) {
    start(context, firstScreen, null);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
  }

  @Override
  public ViewFragment onCreateFirstFragment() {
    ViewFragment firstFragment = null;
    FirstScreen firstFragmentId = (FirstScreen) getIntent().getSerializableExtra(FIRST_FRAGMENT_KEY);
    Bundle data = getIntent().getBundleExtra(BUNDLE_DATA_KEY) == null ? new Bundle() : getIntent().getBundleExtra(BUNDLE_DATA_KEY);

    if (firstFragmentId != null) {
      switch (firstFragmentId) {
        case SURVEY_LIST:
          firstFragment = (ViewFragment) new BestSurveyPresenter(this).getFragment();
          break;
        case SURVEY_FORM:
          firstFragment = (ViewFragment) new BestSurveyFormPresenter(this, data).getFragment();
          break;
      }
    }
    if (firstFragment != null) {
      return firstFragment;
    } else {
      finish();
      return null;
    }
  }
}
