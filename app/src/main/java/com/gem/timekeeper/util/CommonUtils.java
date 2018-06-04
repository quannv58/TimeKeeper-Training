package com.gem.timekeeper.util;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gem.timekeeper.data.dto.LocationDTO;

/**
 * CommonUtils
 * Created by BaVV on 03/22/2018.
 */
public class CommonUtils {
  private static Toast sToast;

  public static LocationDTO.ShiftDTO getShift(LocationDTO locationDTO) {
    return new LocationDTO.ShiftDTO();  // TODO
  }

  public static void setTextHtml(TextView textView, String textHtml) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      textView.setText(Html.fromHtml(textHtml, Html.FROM_HTML_MODE_COMPACT));
    } else {
      textView.setText(Html.fromHtml(textHtml));
    }
  }

  public static void showToast(@NonNull Context context, int messageResId, int duration) {
    showToast(context, context.getString(messageResId), duration);
  }

  public static void showToast(@NonNull Context context, CharSequence message, int duration) {
    if (sToast != null) sToast.cancel();
    sToast = Toast.makeText(context.getApplicationContext(), message, duration);
    ViewGroup toastLayout = (ViewGroup) sToast.getView();
    TextView toastTV = (TextView) toastLayout.getChildAt(0);
    toastTV.setTextSize(18);
    Typeface font = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/sfui_display_medium.otf");
    toastTV.setTypeface(font);
    sToast.show();
  }
}
