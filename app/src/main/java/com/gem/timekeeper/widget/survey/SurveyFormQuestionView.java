package com.gem.timekeeper.widget.survey;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.view.View;

import com.gem.timekeeper.R;
import com.gem.timekeeper.data.dto.SurveyDTO;
import com.gemvietnam.utils.ViewUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by BaVV on 09/03/2018.
 * SurveyFormQuestionView
 */
public class SurveyFormQuestionView extends LinearLayout {

  private EditText mAnswerEdt;
  private EditText mOtherAnswerEdt;
  private View mClearBtn;
  private View mOtherAnswerRl;

  private SurveyDTO.Question mQuestion;
  private int mQuestionNumber;

  private TextWatcher mTextWatcher = new TextWatcher() {
    @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    private Timer mTimer = new Timer();
    private static final long DELAY = 1000;

    @Override
    public void afterTextChanged(final Editable s) {
      if (s.length() == 0 && mClearBtn.getVisibility() == VISIBLE) mClearBtn.setVisibility(INVISIBLE);
      if (s.length() > 0 && mClearBtn.getVisibility() == INVISIBLE) mClearBtn.setVisibility(VISIBLE);

      mTimer.cancel();
      mTimer = new Timer();
      mTimer.schedule(new TimerTask() {
        @Override
        public void run() {
          if (mAnswerEdt != null) {
            mQuestion.setAnswer(mAnswerEdt.getText().toString());
          } else if (mOtherAnswerRl != null && mOtherAnswerRl.getVisibility() == VISIBLE) {
            mQuestion.setOtherAnswer(mOtherAnswerEdt.getText().toString());
          }
        }
      }, DELAY);
    }
  };

  public SurveyFormQuestionView(Context context, @NonNull SurveyDTO.Question question, int questionNumber) {
    super(context);
    mQuestion = question;
    mQuestionNumber = questionNumber;
    initLayout();
  }

  private void initLayout() {
    switch (mQuestion.getQuestionType()) {
      case SINGLE_CHOICE:
      case MULTI_CHOICES:
        initListAnswerType();
        break;
      case TEXT:
        initLayoutForTextType();
        break;
      case RATING:
      default:
        initLayoutForRatingType();
        break;
    }
    setTitle();
  }

  private void initLayoutForTextType() {
    inflate(getContext(), R.layout.view_survey_form_question_text, this);
    mAnswerEdt = findViewById(R.id.edt_view_survey_form_question_answer);
    mClearBtn = findViewById(R.id.btn_view_survey_form_question_clear_answer);
    mClearBtn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        mAnswerEdt.setText("");
      }
    });
    mAnswerEdt.addTextChangedListener(mTextWatcher);
    mAnswerEdt.setText(mQuestion.getAnswer());
  }

  private void initLayoutForRatingType() {
    inflate(getContext(), R.layout.view_survey_form_question_rating, this);
    RatingBar ratingBar = findViewById(R.id.rating_bar_view_survey_form_question);
    if (mQuestion.getRating() != null) {
      ratingBar.setRating(mQuestion.getRating());
    }
    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
      @Override
      public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        mQuestion.setRating((int) rating);
      }
    });
  }

  private void setTitle() {
    TextView titleTv = findViewById(R.id.tv_view_survey_form_question_title);
    titleTv.setText(mQuestionNumber + ". " + mQuestion.getValue());
  }

  private void initListAnswerType() {
    ViewGroup viewGroup;
    if (mQuestion.getQuestionType() == SurveyDTO.QuestionType.SINGLE_CHOICE) {
      inflate(getContext(), R.layout.view_survey_form_question_single_choice, this);
      viewGroup = findViewById(R.id.rg_view_survey_form_question_answers);
    } else {
      inflate(getContext(), R.layout.view_survey_form_question_multi_choice, this);
      viewGroup = findViewById(R.id.ll_view_survey_form_question_answers);
    }
    mOtherAnswerEdt = findViewById(R.id.edt_view_survey_form_question_other_answer);
    mClearBtn = findViewById(R.id.btn_view_survey_form_question_clear_answer);
    mClearBtn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        mOtherAnswerEdt.setText("");
      }
    });
    mOtherAnswerRl = findViewById(R.id.rl_view_survey_form_question_other_answer);
    mOtherAnswerEdt.addTextChangedListener(mTextWatcher);
    mOtherAnswerEdt.setText(mQuestion.getOtherAnswer());

    for (final SurveyDTO.Answer answer : mQuestion.getAnswers()) {
      CompoundButton answerCb = mQuestion.getQuestionType() == SurveyDTO.QuestionType.SINGLE_CHOICE ?
          new RadioButton(getContext()) : new CheckBox(getContext());
      answerCb.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/sfui_display_medium.otf"));
      answerCb.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);
      if (answer.isSelected()) {
        answerCb.setChecked(true);
        if (answer.isOtherAnswer()) {
          mOtherAnswerRl.setVisibility(VISIBLE);
        }
      }
      answerCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
          if (answer.isOtherAnswer()) {
            if (isChecked) {
              mOtherAnswerRl.setVisibility(VISIBLE);
              mOtherAnswerEdt.requestFocus();
            } else {
              mOtherAnswerRl.setVisibility(GONE);
              ViewUtils.hideKeyboard(getContext(), mOtherAnswerEdt);
            }
          }
          updateSelectedAnswer(answer, isChecked);
        }
      });
      if (answer.getValue() != null && answer.getValue().length() > 1) {
        String value = Character.toUpperCase(answer.getValue().charAt(0)) + answer.getValue().substring(1);
        answerCb.setText(value);
      } else {
        answerCb.setText(answer.getValue());
      }
      viewGroup.addView(answerCb);
    }
  }

  private void updateSelectedAnswer(SurveyDTO.Answer answer, boolean isChecked) {
    if (isChecked && mQuestion.getQuestionType() == SurveyDTO.QuestionType.SINGLE_CHOICE) {
      for (SurveyDTO.Answer ans : mQuestion.getAnswers()) {
        ans.setSelected(false);
      }
    }
    answer.setSelected(isChecked);
  }
}
