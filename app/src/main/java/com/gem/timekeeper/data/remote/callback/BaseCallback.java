package com.gem.timekeeper.data.remote.callback;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.gem.timekeeper.App;
import com.gem.timekeeper.data.dto.UserDTO;
import com.gem.timekeeper.data.remote.ServiceBuilder;
import com.gem.timekeeper.pref.PrefWrapper;
import com.gem.timekeeper.screen.authentication.AuthenticationActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BaVV on 03/21/2018.
 */
public abstract class BaseCallback<T> implements Callback<T> {

  @Override
  public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
    onResponse(response);
  }

  protected abstract void onResponse(Response<T> response);
}
