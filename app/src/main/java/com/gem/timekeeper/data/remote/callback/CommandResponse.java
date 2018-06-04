package com.gem.timekeeper.data.remote.callback;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 11/16/2017.
 * SimpleCommandResponse
 */

public class CommandResponse implements Serializable {

  public enum Status {
    @SerializedName("Complete")
    COMPLETE,

    @SerializedName("Failed")
    FAILED
  }

  @SerializedName("commandName")
  private String mCommandName;

  @SerializedName("created")
  private String mCreated;

  @SerializedName("id")
  private String id;

  @SerializedName("status")
  private Status mStatus;

  @SerializedName("statusCode")
  private int mStatusCode;

  @SerializedName("updated")
  private String mUpdated;
}
