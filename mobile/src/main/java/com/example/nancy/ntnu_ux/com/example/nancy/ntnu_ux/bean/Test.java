package com.example.nancy.ntnu_ux.com.example.nancy.ntnu_ux.bean;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by jasoncheng on 15/5/25.
 */
@ParseClassName("Test")
public class Test extends ParseObject {
  public static final String GENDER = "gender";
  public static final String AGE = "age";
  public static final String DEVICE_MODEL = "device_model";
  public static final String DEVICE_MANUFACTURER = "device_manufacturer";
  public static final String STAGE_1 = "stage_1";
  public static final String STAGE_2 = "stage_2";
  public static final String STAGE_3 = "stage_3";
  public static final String STAGE_1_COUNT = "stage_1_c";
  public static final String STAGE_2_COUNT = "stage_2_c";
  public static final String STAGE_3_COUNT = "stage_3_c";
  public static final String FAIL_COUNT = "fail_c";

  public Test(){
    this.put(FAIL_COUNT, 0);
    this.put(STAGE_1, false);
    this.put(STAGE_2, false);
    this.put(STAGE_3, false);

    this.put(STAGE_1_COUNT, 0);
    this.put(STAGE_2_COUNT, 0);
    this.put(STAGE_3_COUNT, 0);

    this.put(DEVICE_MODEL, android.os.Build.MODEL);
    this.put(DEVICE_MANUFACTURER, android.os.Build.MANUFACTURER);
  }

  public void setAge(int age){
    this.put(AGE, age);
  }

  public void setStageDone(String stage){
    this.put(stage, true);
    this.saveInBackground();
  }

  public void setGender(String gender){
    this.put(GENDER, gender);
  }
}
