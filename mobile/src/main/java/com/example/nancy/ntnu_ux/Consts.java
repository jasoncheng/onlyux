package com.example.nancy.ntnu_ux;

/**
 * Created by jasoncheng on 15/5/20.
 */
public class Consts {
  public static final String PARSE_APP_ID = "UvIFyvUB5DsoEIVGPbRPaapK3foQxO77tf0WpbuU";
  public static final String PARSE_APP_KEY = "eNKyX8sCeWQ7GKrmpFt4MLhyBOdPAp4KmVWRJ1n6";

  public static final int MAX_AGE = 45;
  public static final int MIN_AGE = 6;
  public static final int[] CIRCLE_SIZE = {90, 100, 120, 130, 140};
  public static final int EACH_CIRCLE_TEST_TIMES = 5;
  public static final int MAX_FAILURE = (3 * CIRCLE_SIZE.length * EACH_CIRCLE_TEST_TIMES)+1;
}
