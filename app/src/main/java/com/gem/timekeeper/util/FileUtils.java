package com.gem.timekeeper.util;

import android.content.Context;

import com.gem.timekeeper.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * File Utils
 * Created by NEO on 2/14/2017.
 */

public class FileUtils {

  /**
   * Read asset file as string
   */
  public static String readAssetFile(Context context, String filePath) {
    StringBuilder buf = new StringBuilder();
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(
          new InputStreamReader(context.getAssets().open(filePath)));

      // do reading, usually loop until end of file reading
      String line;
      while ((line = reader.readLine()) != null) {
        buf.append(line);

      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return buf.toString();
  }

  /**
   * read streaming from asset file
   */
  public static InputStream readAssetFile(String filePath) {
    try {
      return App.getInstance().getAssets().open(filePath);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
