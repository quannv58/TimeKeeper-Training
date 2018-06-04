package com.gem.timekeeper.screen.main.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.gem.timekeeper.R;
import com.gem.timekeeper.data.dto.LocationDTO;
import com.gem.timekeeper.pref.PrefWrapper;
import com.gem.timekeeper.util.CommonUtils;
import com.gemvietnam.base.viper.ViewFragment;
import com.gemvietnam.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * The Home Fragment
 */
public class HomeFragment extends ViewFragment<HomeContract.Presenter> implements HomeContract.View {

  public static HomeFragment getInstance(Bundle data) {
    HomeFragment fragment = new HomeFragment();
    fragment.setArguments(data);
    return fragment;
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_home;
  }

  @Override
  public void updateLocationView(@NonNull LocationDTO locationDTO) {
  }

  @Override
  public void onStart() {
    super.onStart();
  }

  @Override
  public void onStop() {
    super.onStop();
  }
}
