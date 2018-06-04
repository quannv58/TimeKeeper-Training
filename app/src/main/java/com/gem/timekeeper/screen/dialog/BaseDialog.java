package com.gem.timekeeper.screen.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gemvietnam.base.BaseActivity;
import com.gem.timekeeper.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public abstract class BaseDialog {

  public interface OnDialogResult {
    void onDismiss(boolean isCancel);
  }

  @BindView(R.id.dialog_container_title_tv)
  TextView mTitleTv;
  @BindView(R.id.dialog_container_button)
  TextView mButton;
  @BindView(R.id.dialog_container_multi_button_ll)
  View mButtonLl;
  @BindView(R.id.dialog_container_negative_button)
  TextView mNegativeBtn;
  @BindView(R.id.dialog_container_positive_button)
  TextView mPositiveBtn;

  protected OnDialogResult mDialogResult;

  private AlertDialog mDialog;
  protected BaseActivity mActivity;
  private long mLastClickTime;

  public void show() {
    if (mDialog != null) mDialog.show();
  }

  public BaseDialog(BaseActivity activity, OnDialogResult dialogResult, @LayoutRes int contentResId) {
    this.mDialogResult = dialogResult;
    this.mActivity = activity;
    init(contentResId);
  }

  private void init(@LayoutRes int contentResId) {
    LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View view = inflater.inflate(R.layout.dialog_container, null);
    mDialog = new AlertDialog.Builder(mActivity)
        .setView(view)
        .setOnCancelListener(new DialogInterface.OnCancelListener() {
          @Override
          public void onCancel(DialogInterface dialog) {
            if (mDialogResult != null) {
              mDialogResult.onDismiss(true);
            }
          }
        })
        .create();
    mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    LinearLayout container = view.findViewById(R.id.dialog_container_content_view);
    container.addView(inflater.inflate(contentResId, null));
    ButterKnife.bind(this, view);
  }

  protected final void setupBaseView(String title, String positiveBtnText) {
    mButton.setVisibility(View.VISIBLE);
    mButtonLl.setVisibility(View.GONE);
    mTitleTv.setText(title);
    mButton.setText(positiveBtnText);
  }

  protected final void setupBaseView(String title, String positiveBtnText, String negativeBtnText) {
    mButton.setVisibility(View.GONE);
    mButtonLl.setVisibility(View.VISIBLE);
    mTitleTv.setText(title);
    mPositiveBtn.setText(positiveBtnText);
    mNegativeBtn.setText(negativeBtnText);
  }

  protected final boolean checkRequestTime() {
    long now = SystemClock.elapsedRealtime();
    if (now - mLastClickTime < 1000) {
      return true;
    }
    mLastClickTime = now;
    return false;
  }

  @OnClick({R.id.dialog_container_positive_button, R.id.dialog_container_button})
  void next() {
    onPositiveButtonClick();
  }

  protected abstract void onPositiveButtonClick();

  @OnClick(R.id.dialog_container_negative_button)
  void close() {
    if (!checkRequestTime()) {
      dismiss();
    }
  }

  public void dismiss() {
    mActivity.hideKeyboard();
    if (mDialog.isShowing()) {
      mDialog.dismiss();
      if (mDialogResult != null) {
        mDialogResult.onDismiss(false);
      }
    }
  }
}
