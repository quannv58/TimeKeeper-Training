package com.gem.timekeeper.data.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by BaVV on 03/20/2018.
 * CompleteSurveyDTO
 */
public class CompleteSurveyListDTO implements Serializable {

  @SerializedName("bestSurveySubmits")
  private List<CompleteSurveyDTO> mBestSurveySubmits;

  public void setBestSurveySubmits(List<CompleteSurveyDTO> bestSurveySubmits) {
    this.mBestSurveySubmits = bestSurveySubmits;
  }

  public static class CompleteSurveyDTO implements Serializable {
    @SerializedName("locationId")
    private int mLocationId;

    @SerializedName("submitted")
    private String mSubmitted;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("surveyData")
    private String mSurveyData;

    private CompleteSurveyDTO(int locationId, String submitted, String title, String surveyData) {
      this.mLocationId = locationId;
      this.mSubmitted = submitted;
      this.mTitle = title;
      this.mSurveyData = surveyData;
    }

    public static class Builder {
      private int mLocationId;
      private String mSubmitted;
      private String mTitle;
      private String mSurveyData;

      public Builder setLocationId(int locationId) {
        this.mLocationId = locationId;
        return this;
      }

      public Builder setSubmitted(String submitted) {
        this.mSubmitted = submitted;
        return this;
      }

      public Builder setTitle(String title) {
        this.mTitle = title;
        return this;
      }

      public Builder setSurveyData(String surveyData) {
        this.mSurveyData = surveyData;
        return this;
      }

      public CompleteSurveyDTO build() {
        return new CompleteSurveyDTO(mLocationId, mSubmitted, mTitle, mSurveyData);
      }
    }
  }
}
