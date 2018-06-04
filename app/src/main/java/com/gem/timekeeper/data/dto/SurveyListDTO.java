package com.gem.timekeeper.data.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by BaVV on 03/21/2018.
 * SurveyListDTO
 */
public class SurveyListDTO implements Serializable {

  @SerializedName("surveyList")
  private List<SurveyDTO> mSurveyList;

  public List<SurveyDTO> getSurveyList() {
    return mSurveyList;
  }
}
