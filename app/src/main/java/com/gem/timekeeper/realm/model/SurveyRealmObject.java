package com.gem.timekeeper.realm.model;

import com.gem.timekeeper.data.dto.SurveyDTO;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by BaVV on 03/20/2018.
 * SurveyRealmObject
 */
public class SurveyRealmObject extends RealmObject {

  @PrimaryKey
  private int id;

  private String title;

  private int type;

  private String surveyBlocks;

  public SurveyRealmObject() {
  }

  public SurveyRealmObject(SurveyDTO surveyDTO) {
    id = surveyDTO.getId();
    title = surveyDTO.getTitle();
    type = surveyDTO.getType().getValue();
    surveyBlocks = new Gson().toJson(surveyDTO.getSurveyBlocks());
  }

  public SurveyDTO toDTO() {
    SurveyDTO result = new SurveyDTO();
    result.setId(id);
    result.setTitle(title);
    result.setType(SurveyDTO.SurveyType.fromValue(type));
    SurveyDTO.SurveyBlock[] items = new Gson().fromJson(surveyBlocks, SurveyDTO.SurveyBlock[].class);
    List<SurveyDTO.SurveyBlock> list = new ArrayList<>();
    if (items != null) {
      list.addAll(Arrays.asList(items));
    }
    result.setSurveyBlocks(list);
    return result;
  }
}
