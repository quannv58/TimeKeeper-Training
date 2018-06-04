package com.gem.timekeeper.data.dto;

import com.gemvietnam.utils.StringUtils;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by BaVV on 03/20/2018.
 * SurveyDTO
 */

public class SurveyDTO implements Serializable {

  public enum SurveyType {
    @SerializedName("0")
    CLIENT_SURVEY(0),

    @SerializedName("1")
    BEST_SURVEY(1);

    private int mValue;

    SurveyType(int value) {
      mValue = value;
    }

    public int getValue() {
      return mValue;
    }

    public static SurveyType fromValue(int value) {
      if (value == CLIENT_SURVEY.getValue()) return CLIENT_SURVEY;
      else if (value == BEST_SURVEY.getValue()) return BEST_SURVEY;
      else return null;
    }
  }

  public enum QuestionType {
    @SerializedName("1")
    SINGLE_CHOICE,

    @SerializedName("2")
    MULTI_CHOICES,

    @SerializedName("3")
    TEXT,

    @SerializedName("4")
    RATING
  }

  @SerializedName("id")
  private int mId;

  @SerializedName("title")
  private String mTitle;

  @SerializedName("type")
  private SurveyType mType;

  @SerializedName("descriptions")
  private List<SurveyBlock> mSurveyBlocks;

  public int getId() {
    return mId;
  }

  public void setId(int id) {
    this.mId = id;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    this.mTitle = title;
  }

  public SurveyType getType() {
    return mType;
  }

  public void setType(SurveyType type) {
    this.mType = type;
  }

  public List<SurveyBlock> getSurveyBlocks() {
    return mSurveyBlocks;
  }

  public void setSurveyBlocks(List<SurveyBlock> surveyBlocks) {
    this.mSurveyBlocks = surveyBlocks;
  }

  public boolean validateValue() {
    if (mSurveyBlocks == null || mSurveyBlocks.size() == 0) return true;
    for (SurveyBlock surveyBlock : mSurveyBlocks) {
      if (surveyBlock.validateValue() >= 0) return false;
    }
    return true;
  }

  public static class SurveyBlock implements Serializable {
    @SerializedName("value")
    private String mDescription;

    @SerializedName("id")
    private int mId;

    @SerializedName("questions")
    private List<Question> mQuestions;

    public String getDescription() {
      return mDescription;
    }

    public List<Question> getQuestions() {
      return mQuestions;
    }

    public int validateValue() {
      if (mQuestions == null || mQuestions.size() == 0) return -1;
      for (int i = 0; i < mQuestions.size(); i++) {
        Question question = mQuestions.get(i);
        if (!question.validateValue()) return i;
      }
      return -1;
    }
  }

  public static class Question implements Serializable {
    @SerializedName("value")
    private String mValue;

    @SerializedName("id")
    private int mId;

    @SerializedName("required")
    private Boolean mRequired;

    @SerializedName("questionTypeId")
    private QuestionType mQuestionType;

    @SerializedName("answer")
    private String mAnswer;           // for submit text question

    @SerializedName("otherAnswer")
    private String mOtherAnswer;           // for submit text question

    @SerializedName("rating")
    private Integer mRating;          // for submit rating question

    @SerializedName("answers")
    private List<Answer> mAnswers;    // for single / multi choice question

    public String getValue() {
      return mValue;
    }

    public QuestionType getQuestionType() {
      return mQuestionType;
    }

    public String getAnswer() {
      return mAnswer;
    }

    public void setAnswer(String answer) {
      this.mAnswer = answer;
    }

    public String getOtherAnswer() {
      return mOtherAnswer;
    }

    public void setOtherAnswer(String otherAnswer) {
      this.mOtherAnswer = otherAnswer;
    }

    public Integer getRating() {
      return mRating;
    }

    public void setRating(Integer rating) {
      this.mRating = rating;
    }

    public List<Answer> getAnswers() {
      return mAnswers;
    }

    private boolean isRequired() {
      return mRequired != null && mRequired;
    }

    private boolean validateValue() {
      if (!isRequired()) return true;
      if (mQuestionType == SurveyDTO.QuestionType.SINGLE_CHOICE
          || mQuestionType == SurveyDTO.QuestionType.MULTI_CHOICES) {
        boolean valid = false;
        for (SurveyDTO.Answer ans : mAnswers) {
          if (ans.isSelected()) {
            if (ans.isOtherAnswer() && StringUtils.isEmpty(mOtherAnswer)) {
              return false;
            } else valid = true;
          }
        }
        return valid;
      } else if (mQuestionType == SurveyDTO.QuestionType.TEXT) {
        return !StringUtils.isEmpty(mAnswer);
      } else {
        return mRating != null;
      }
    }
  }

  public static class Answer implements Serializable {
    private static final String OTHER_ANSWER_VALUE = "other";

    @SerializedName("value")
    private String mValue;

    @SerializedName("id")
    private int mId;

    @SerializedName("selected")
    private Boolean mSelected;

    public String getValue() {
      return mValue;
    }

    public void setValue(String value) {
      this.mValue = value;
    }

    public int getId() {
      return mId;
    }

    public boolean isSelected() {
      return mSelected != null && mSelected;
    }

    public void setSelected(boolean selected) {
      this.mSelected = selected;
    }

    public boolean isOtherAnswer() {
      return OTHER_ANSWER_VALUE.equalsIgnoreCase(mValue);
    }
  }
}
