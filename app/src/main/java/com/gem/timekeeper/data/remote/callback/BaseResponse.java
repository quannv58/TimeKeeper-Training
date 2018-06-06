package com.gem.timekeeper.data.remote.callback;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 11/16/2017.
 * SimpleCommandResponse
 */

public class BaseResponse<T> implements Serializable {

  @SerializedName("is_error")
  private boolean isError;

  @SerializedName("status_code")
  private int statusCode;

  @SerializedName("message_code")
  private String messageCode;

  @SerializedName("errors")
  private Error errors;

  @SerializedName("data")
  private T data;

  public T getData() {
    return data;
  }

  private static class Error {
    @SerializedName("first_error")
    private String firstError;

    @SerializedName("all")
    private String all;
  }
}
