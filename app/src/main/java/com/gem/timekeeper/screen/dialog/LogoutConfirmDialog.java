package com.gem.timekeeper.screen.dialog;

import com.gemvietnam.base.BaseActivity;
import com.gem.timekeeper.R;

/**
 * Created by admin on 11/22/2017.
 * LogoutConfirmDialog
 */

public class LogoutConfirmDialog extends BaseDialog {

  public interface OnResult extends OnDialogResult {
    void onPositiveBtnClick();
  }

  private BaseActivity mActivity;

  public LogoutConfirmDialog(BaseActivity activity, OnDialogResult dialogResult) {
    super(activity, dialogResult, R.layout.dialog_logout_confirm);
    mActivity = activity;
    initView();
  }

  private void initView() {
    setupBaseView("Đăng xuất", "Đồng ý", "Hủy");
  }

  @Override
  protected void onPositiveButtonClick() {
    if (checkRequestTime()) {
      return;
    }
    if (mDialogResult != null) {
      ((OnResult) mDialogResult).onPositiveBtnClick();
      dismiss();
    }
  }
}
