package com.example.nancy.ntnu_ux;

import android.app.Application;
import com.example.nancy.ntnu_ux.com.example.nancy.ntnu_ux.bean.Score;
import com.example.nancy.ntnu_ux.com.example.nancy.ntnu_ux.bean.Test;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends Application {

    private static int fail_count = 0;
    private static Test mTest;
    public static int viewWidth = 0;
    public static int viewHeight = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, Consts.PARSE_APP_ID, Consts.PARSE_APP_KEY);
        ParseObject.registerSubclass(Test.class);
        ParseObject.registerSubclass(Score.class);
    }

    public static void addFail(){
        mTest.increment(Test.FAIL_COUNT);
        fail_count++;
    }

    public static void resetTest(){
        fail_count = 0;
    }

    public static boolean isFail(){
        return Consts.MAX_FAILURE <= fail_count;
    }

    public static Test initTest(){
        mTest = new Test();
        resetTest();
        return mTest;
    }

    public static Test getTest(){
        return mTest;
    }

    public static void setTestScore(String stage){

    }

    public static int getDist(int x1, int y1, float x2, float y2) {
        return (int) Math.ceil(Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
    }

    public static int getRandomX(int size) {
        return (int) Math.ceil(Math.random() * (viewWidth - size - (Consts.PADDING * 2)));
    }

    public static int getRandomY(int size) {
        return (int) Math.ceil(Math.random() * (viewHeight - size- (Consts.PADDING * 2)));
    }

}