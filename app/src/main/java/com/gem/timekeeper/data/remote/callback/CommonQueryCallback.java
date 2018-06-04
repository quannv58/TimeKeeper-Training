package com.gem.timekeeper.data.remote.callback;

import android.content.Context;
import android.support.annotation.NonNull;

import com.gem.timekeeper.App;
import com.gem.timekeeper.R;
import com.gemvietnam.utils.DialogUtils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Common callback for APIs
 * Created by NEO on 11/7/2016.
 */

public abstract class CommonQueryCallback<T> extends BaseCallback<SimpleQueryResponse<T>> {
  protected static final int SERVER_ERROR = 9999;
  protected static final int NETWORK_ERROR = 9998;

  private Context mContext;
  private boolean mErrorDialogAllowed = true;

  protected CommonQueryCallback(Context context) {
    mContext = context;
  }

  protected CommonQueryCallback(Context context, boolean mErrorDialogAllowed) {
    mContext = context;
    this.mErrorDialogAllowed = mErrorDialogAllowed;
  }

  @Override
  public void onResponse(@NonNull Response<SimpleQueryResponse<T>> response) {
    SimpleQueryResponse<T> body = response.body();
    if (body != null && body.getData() != null) {
      onSuccess(body);
    } else {
      onError(SERVER_ERROR, getServerMsg());
    }
  }

  @Override
  public void onFailure(@NonNull Call<SimpleQueryResponse<T>> call, @NonNull Throwable t) {
    t.printStackTrace();
    try {
      onError(NETWORK_ERROR, getServerMsg());
    } catch (IllegalStateException | NullPointerException ex) {
      ex.printStackTrace();
    }
  }

  protected final String getServerMsg() {
    return App.getInstance().getString(R.string.server_error_message);
  }

  public void onError(int errorCode, String errorMessage) {
    DialogUtils.dismissProgressDialog();
    if (mErrorDialogAllowed) {
      DialogUtils.showErrorAlert(mContext, errorMessage);
    }
  }

  public void onSuccess(SimpleQueryResponse<T> responseBody) {
    DialogUtils.dismissProgressDialog();
  }

  @Override
  protected void onRefreshTokenError(String error, String errorMessage) {
    DialogUtils.dismissProgressDialog();
    if (mErrorDialogAllowed) {
      DialogUtils.showErrorAlert(mContext, errorMessage);
    }
  }
}
