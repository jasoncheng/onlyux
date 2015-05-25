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

  public void setAge(int age){
    this.put(AGE, age);
  }

  public void setGender(String gender){
    this.put(GENDER, gender);
  }
}
