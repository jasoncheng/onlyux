package com.example.nancy.ntnu_ux;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

/**
 * Created by jasoncheng on 15/5/20.
 */
public class Consts {
  public static final String PARSE_APP_ID = "UvIFyvUB5DsoEIVGPbRPaapK3foQxO77tf0WpbuU";
  public static final String PARSE_APP_KEY = "eNKyX8sCeWQ7GKrmpFt4MLhyBOdPAp4KmVWRJ1n6";

  public static final int MAX_AGE = 45;
  public static final int MIN_AGE = 6;
  public static final String[] CIRCLE_SIZE = {"2", "4", "6", "8","10"};
  public static final int EACH_CIRCLE_TEST_TIMES = 5;
  public static final int MAX_FAILURE = (3 * CIRCLE_SIZE.length * EACH_CIRCLE_TEST_TIMES)+1;

  public static int mmToPixel(final Context ctx, String mm){
    return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, Float.valueOf(mm),
        ctx.getResources().getDisplayMetrics());
  }

  public static int pxToMm(final int px, final Context context){
    final DisplayMetrics dm = context.getResources().getDisplayMetrics();
    Log.i("Test", "==============> " + (px / TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 1, dm)));
    return Math.round(px / TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 1, dm));
//    return (int)(px / dm.xdpi * 25.4f);
  }
}
