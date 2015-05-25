package com.example.nancy.ntnu_ux;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.example.nancy.ntnu_ux.com.example.nancy.ntnu_ux.bean.Score;
import com.example.nancy.ntnu_ux.com.example.nancy.ntnu_ux.bean.Test;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends Application {

    private static int fail_count = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "dsafqdDEKeWoHvl1ETKW24SuHbvQb5Fu7GbDZLaP", "9hO4w06jev9DxWcwYaM9E41H02Dj5NDxHSt2Z5EY");
        ParseObject.registerSubclass(Test.class);
        ParseObject.registerSubclass(Score.class);
    }
//    public ApplicationTest() {
//        super(Application.class);
//    }

    public static void addFail(){
        fail_count++;
    }

    public static void restartTest(){
        fail_count = 0;
    }

    public static boolean isFail(){
        return Consts.MAX_FAILURE <= fail_count;
    }
}