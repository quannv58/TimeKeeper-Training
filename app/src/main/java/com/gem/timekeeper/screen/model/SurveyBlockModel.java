package com.gem.timekeeper.screen.model;

import com.gem.timekeeper.data.dto.SurveyDTO;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by BaVV on 03/20/2018.
 * SurveyModel
 */
public class SurveyBlockModel {

  private int mQuestionOffset;

  private int mInvalidQuestionIndex;

  private SurveyDTO.SurveyBlock mSurveyBlock;

  private SurveyBlockModel(SurveyDTO.SurveyBlock surveyBlock, int questionOffset) {
    mSurveyBlock = surveyBlock;
    mQuestionOffset = questionOffset;
  }

  public int getQuestionOffset() {
    return mQuestionOffset;
  }

  public SurveyDTO.SurveyBlock getSurveyBlock() {
    return mSurveyBlock;
  }

  public static List<SurveyBlockModel> listFromDTO(SurveyDTO surveyDTO) {
    List<SurveyBlockModel> result = new ArrayList<>();
    int index = 0;
    for (SurveyDTO.SurveyBlock surveyBlock : surveyDTO.getSurveyBlocks()) {
      result.add(new SurveyBlockModel(surveyBlock, index));
      index += surveyBlock.getQuestions().size();
    }
    return result;
  }
}
