package com.example.nancy.ntnu_ux;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends Application {

    private static int fail_count = 0;

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