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
 * Login callback
 * Created by BaVV on 03/21/2018.
 */
public abstract class BaseCallback<T> implements Callback<T> {

  private static final int HTTP_TOKEN_EXPIRED_CODE = 401;

  private boolean mReCall = false;

  @Override
  public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
    if (!mReCall && response.raw().code() == HTTP_TOKEN_EXPIRED_CODE) {
      refreshToken(call);
    } else {
      onResponse(response);
    }
  }

  private void refreshToken(@NonNull final Call<T> call) {
    mReCall = true;
    final Context context = App.getInstance();
    PrefWrapper.setDeviceTokenRegistered(context, false);
    final UserDTO userDTO = PrefWrapper.getUser(context);
    if (userDTO != null) {
      ServiceBuilder.getCommonService().refreshToken(userDTO.getUsername(), userDTO.getPassword(),
          "refresh_token", userDTO.getRefreshToken())
          .enqueue(new LoginCallback(null) {
            @Override
            protected void onSuccess(UserDTO newUserDTO) {
              onAuthenSuccess(context, call, newUserDTO,  userDTO.getUsername(), userDTO.getPassword());
            }

            @Override
            protected void onError(String error, String errorMessage) {
              if ("invalid_grant".equals(error)) {
                login(context, call, userDTO.getUsername(), userDTO.getPassword());
              } else {
                onRefreshTokenError(error, errorMessage);
              }
            }
          });
    }
  }

  private void login(@NonNull final Context context, @NonNull final Call<T> call,
                     final String username, final String password) {
    ServiceBuilder.getCommonService().login(username, password,"password", true)
        .enqueue(new LoginCallback(null) {
          @Override
          protected void onSuccess(UserDTO newUserDTO) {
            onAuthenSuccess(context, call, newUserDTO,  username, password);
          }

          @Override
          protected void onError(String error, String errorMessage) {
            if (LoginCallback.NETWORK_ERROR.equals(error)) {
              onRefreshTokenError(error, errorMessage);
            } else {
              PrefWrapper.clearUser(context);
              Intent intent = new Intent(context, AuthenticationActivity.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
              context.startActivity(intent);
            }
          }
        });
  }


  private void onAuthenSuccess(@NonNull Context context, @NonNull Call<T> call,
                               @NonNull UserDTO newUserDTO, String username, String password) {
    call.clone().enqueue(BaseCallback.this);
  }

  protected abstract void onResponse(Response<T> response);

  protected abstract void onRefreshTokenError(String error, String errorMessage);
}
