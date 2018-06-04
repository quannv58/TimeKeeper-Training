package com.gem.timekeeper.data.remote.callback;

import android.content.Context;
import android.support.annotation.NonNull;

import com.gem.timekeeper.App;
import com.gem.timekeeper.R;
import com.gemvietnam.utils.DialogUtils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Base Command callback
 * Created by BaVV on 03/21/2018.
 */
public abstract class CommonCommandCallback extends BaseCallback<CommandResponse> {

  private Context mContext;
  private boolean mErrorDialogAllowed = true;

  protected CommonCommandCallback(Context context) {
    mContext = context;
  }

  protected CommonCommandCallback(Context context, boolean mErrorDialogAllowed) {
    mContext = context;
    this.mErrorDialogAllowed = mErrorDialogAllowed;
  }

  @Override
  public void onResponse(@NonNull Response<CommandResponse> response) {
    CommandResponse responseBody = response.body();
    if (responseBody != null) {
      onSuccess(responseBody);
    } else {
      onServerError(getServerMsg());
    }
  }

  @Override
  public void onFailure(@NonNull Call<CommandResponse> call, @NonNull Throwable t) {
    t.printStackTrace();
    try {
      onNetworkError(App.getInstance().getString(com.gemvietnam.common.R.string.msg_network_lost));
    } catch (IllegalStateException | NullPointerException ex) {
      ex.printStackTrace();
    }
  }

  protected void onNetworkError(String message) {
    DialogUtils.dismissProgressDialog();
    if (mContext != null && mErrorDialogAllowed) {
      DialogUtils.showErrorAlert(mContext, message);
    }
  }

  protected void onServerError(String errorMessage) {
    DialogUtils.dismissProgressDialog();
    if (mContext != null && mErrorDialogAllowed) {
      DialogUtils.showErrorAlert(mContext, errorMessage);
    }
  }

  @Override
  protected void onRefreshTokenError(String error, String errorMessage) {
    onServerError(getServerMsg());
  }

  protected void onSuccess(CommandResponse responseBody) {
    DialogUtils.dismissProgressDialog();
  }

  private String getServerMsg() {
    return App.getInstance().getString(R.string.server_error_message);
  }
}
