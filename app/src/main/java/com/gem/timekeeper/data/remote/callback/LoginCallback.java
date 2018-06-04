package com.gem.timekeeper.data.remote.callback;

import android.content.Context;
import android.support.annotation.NonNull;

import com.gem.timekeeper.App;
import com.gem.timekeeper.R;
import com.gem.timekeeper.data.dto.UserDTO;
import com.gemvietnam.utils.DialogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Login callback
 * Created by BaVV on 03/21/2018.
 */
public abstract class LoginCallback implements Callback<UserDTO> {
  private static final String SERVER_ERROR = "server_error";
  static final String NETWORK_ERROR = "network_error";

  private Context mContext;
  private boolean mErrorDialogAllowed = true;

  protected LoginCallback(Context context) {
    mContext = context;
  }

  protected LoginCallback(Context context, boolean mErrorDialogAllowed) {
    mContext = context;
    this.mErrorDialogAllowed = mErrorDialogAllowed;
  }

  @Override
  public void onResponse(@NonNull Call<UserDTO> call, @NonNull Response<UserDTO> response) {
    UserDTO loginDTO = response.body();
    ResponseBody errorBody = response.errorBody();
    if (loginDTO != null) {
      onSuccess(loginDTO);
    } else if (errorBody != null) {
      try {
        UserDTO.LoginErrorDTO loginErrorDTO = new Gson().fromJson(errorBody.string(), UserDTO.LoginErrorDTO.class);
        if (loginErrorDTO != null) {
          onError(loginErrorDTO.getError(), loginErrorDTO.getErrorDescription());
        } else {
          onError(SERVER_ERROR, getServerMsg());
        }
      } catch (IOException | JsonSyntaxException e) {
        e.printStackTrace();
        onError(SERVER_ERROR, getServerMsg());
      }
    } else {
      onError(SERVER_ERROR, getServerMsg());
    }
  }

  @Override
  public void onFailure(@NonNull Call<UserDTO> call, @NonNull Throwable t) {
    t.printStackTrace();
    try {
      onError(NETWORK_ERROR, getServerMsg());
    } catch (IllegalStateException | NullPointerException ex) {
      ex.printStackTrace();
    }
  }

  protected void onError(String error, String errorMessage) {
    DialogUtils.dismissProgressDialog();
    if (mContext != null && mErrorDialogAllowed) {
      DialogUtils.showErrorAlert(mContext, errorMessage);
    }
  }

  protected void onSuccess(UserDTO userDTO) {
    DialogUtils.dismissProgressDialog();
  }

  private String getServerMsg() {
    return App.getInstance().getString(R.string.server_error_message);
  }
}
