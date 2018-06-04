package com.gem.timekeeper.data.remote.callback;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by admin on 11/16/2017.
 * SimpleQueryResponse
 */

public class SimpleQueryResponse<T> implements Serializable {
  @SerializedName("totalResults")
  private int mTotalResults;

//  @SerializedName("totalAmount")
//  private int mTotalAmount;

  @SerializedName("pageSize")
  private int mPageSize;

  @SerializedName("totalPages")
  private int mTotalPages;

  @SerializedName("skip")
  private int mSkip;

  @SerializedName("currentPage")
  private int mCurrentPage;

  @SerializedName("results")
  private T mResults;

  public T getData() {
    return mResults;
  }
}
