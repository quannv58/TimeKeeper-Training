package com.gem.timekeeper.data.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by BaVV on 03/21/2018.
 * UserDTO
 */
public class UserDTO implements Serializable {

  @SerializedName("access_token")
  private String mAccessToken;

  @SerializedName("token_type")
  private String mTokenType;

  @SerializedName("expires_in")
  private int mExpiresIn;

  @SerializedName("refresh_token")
  private String mRefreshToken;

  @SerializedName("username")
  private String mUsername;

  @SerializedName("password")
  private String mPassword;

  public String getAccessToken() {
    return mAccessToken;
  }

  public String getTokenType() {
    return mTokenType;
  }

  public int getExpiresIn() {
    return mExpiresIn;
  }

  public String getRefreshToken() {
    return mRefreshToken;
  }

  public String getUsername() {
    return mUsername;
  }

  public void setUsername(String mUsername) {
    this.mUsername = mUsername;
  }

  public String getPassword() {
    return mPassword;
  }

  public void setPassword(String mPassword) {
    this.mPassword = mPassword;
  }

  public class LoginErrorDTO implements Serializable {
    @SerializedName("error")
    private String mError;

    @SerializedName("error_description")
    private String mErrorDescription;

    public String getError() {
      return mError;
    }

    public String getErrorDescription() {
      return mErrorDescription;
    }
  }
}
