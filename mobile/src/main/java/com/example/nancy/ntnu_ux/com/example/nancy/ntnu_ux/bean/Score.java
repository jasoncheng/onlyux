package com.example.nancy.ntnu_ux.com.example.nancy.ntnu_ux.bean;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by jasoncheng on 15/5/25.
 */
@ParseClassName("Score")
public class Score extends ParseObject{
  public final static String TEST = "test";
  public final static String TIME = "time";
  public final static String SUCCESS = "success";
  public final static String DIST = "dist";
  public final static String STAGE = "stage";
  public final static String RADIUS = "radius";

  public void setTest(Test t){
    this.put(TEST, t);
  }

  public void setRadius(int radius){
    this.put(RADIUS, radius);
  }

  public void setStage(String stage){
    this.put(STAGE, stage);
  }

  public void setTime(int t){
    this.put(TIME, t);
  }

  public void setSuccess(boolean isSuccess){
    this.put(SUCCESS, isSuccess);
  }

  public void setDist(int dist){
    this.put(DIST, dist);
  }
}
