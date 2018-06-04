package com.gem.timekeeper.realm.model;

import com.gem.timekeeper.data.dto.CompleteSurveyListDTO;

import io.realm.RealmObject;

/**
 * Created by BaVV on 03/20/2018.
 * SurveyRealmObject
 */
public class CompleteSurveyRealmObject extends RealmObject {
//  public static final String ID = "uuid";
//
//  @PrimaryKey
//  private String uuid;

  private int locationId;

  private String submitted;

  private String title;

  private String surveyData;

  public void setLocationId(int locationId) {
    this.locationId = locationId;
  }

  public void setSubmitted(String submitted) {
    this.submitted = submitted;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setSurveyData(String surveyData) {
    this.surveyData = surveyData;
  }

  public CompleteSurveyListDTO.CompleteSurveyDTO toDTO() {
    return new CompleteSurveyListDTO.CompleteSurveyDTO.Builder().setLocationId(locationId)
        .setSubmitted(submitted).setTitle(title).setSurveyData(surveyData).build();
  }
}
