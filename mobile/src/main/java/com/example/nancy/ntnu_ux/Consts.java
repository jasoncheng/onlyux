package com.example.nancy.ntnu_ux;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import java.math.BigDecimal;

/**
 * Created by jasoncheng on 15/5/20.
 */
public class Consts {
  // production
  public static final String PARSE_APP_ID = "UvIFyvUB5DsoEIVGPbRPaapK3foQxO77tf0WpbuU";
  public static final String PARSE_APP_KEY = "eNKyX8sCeWQ7GKrmpFt4MLhyBOdPAp4KmVWRJ1n6";


  // Test
//  public static final String PARSE_APP_ID = "dsafqdDEKeWoHvl1ETKW24SuHbvQb5Fu7GbDZLaP";
//  public static final String PARSE_APP_KEY = "9hO4w06jev9DxWcwYaM9E41H02Dj5NDxHSt2Z5EY";

  // The padding of screen (mapping to activity_main padding)
  public static final int PADDING = 20;

  public static final int MAX_AGE = 45;
  public static final int MIN_AGE = 6;
//  public static final String[] CIRCLE_SIZE = {"2", "4", "6", "8","10"};

  public static final String[] CIRCLE_SIZE = {"10", "8", "6", "4","2"};
  public static final int EACH_CIRCLE_TEST_TIMES = 2;

  public static final int MAX_FAILURE = (3 * CIRCLE_SIZE.length * EACH_CIRCLE_TEST_TIMES)+1;

  public static int mmToPixel(final Context ctx, String mm){
    return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, Float.valueOf(mm),
        ctx.getResources().getDisplayMetrics());
  }

  public static float pxToMm(final int px, final Context context){
    final DisplayMetrics dm = context.getResources().getDisplayMetrics();
    Log.i("Test", "==============> " + (px / TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 1, dm)));
    float f = px / TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 1, dm);
    BigDecimal b = new BigDecimal(f);
    return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
//    return (int)(px / dm.xdpi * 25.4f);
  }
}
