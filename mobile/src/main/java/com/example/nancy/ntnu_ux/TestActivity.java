package com.example.nancy.ntnu_ux;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.nancy.ntnu_ux.com.example.nancy.ntnu_ux.bean.Test;
import com.parse.ParseObject;

import java.util.ArrayList;


public class TestActivity extends Activity {


//  private int minSize = 50;
//  private int maxSize = 120;


  // Configurable
  private int[] circleSize = {50, 60, 70, 90, 110};
  private int circleTestTimes = 5;

  // Score
  ArrayList<Integer> mTimeTest = new ArrayList<Integer>();
  ArrayList<Boolean> mScoreSuccess = new ArrayList<Boolean>();


  int viewWidth = 0;
  int viewHeight = 0;

  int lastCircleX = 0;
  int lastCircleY = 0;
  int lastCircleRadius = 0;

  private ViewGroup parentView;
  private CircleView c;
  private final String TAG = "Nancy";

  private int circleTestIndex = 0;
  private int circleSizeIndex = 0;
  private Test mTest;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    String testId = this.getIntent().getStringExtra("TestId");
    mTest = ParseObject.createWithoutData(Test.class, testId);
    Log.i(TAG, "============> TestID: " + testId);

    parentView = (ViewGroup) this.findViewById(R.id.parentView);
    parentView.setOnTouchListener(new View.OnTouchListener() {

      long startTime;

      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            startTime = System.currentTimeMillis();
            break;
          case MotionEvent.ACTION_UP:
            int dist = getDist(event.getX(), event.getY());
            boolean isSuccess = lastCircleRadius - dist >= 0;
            int use = (int)(System.currentTimeMillis() - startTime);
            Log.i(TAG, "=======> " + use + ":" + isSuccess);

            mTimeTest.add(use);
            mScoreSuccess.add(isSuccess);
            drawCircle();
            break;
          default:
            break;
        }
        return true;
      }
    });

    ViewTreeObserver vto = parentView.getViewTreeObserver();
    vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
      public boolean onPreDraw() {
        parentView.getViewTreeObserver().removeOnPreDrawListener(this);
        viewHeight = parentView.getMeasuredHeight();
        viewWidth = parentView.getMeasuredWidth();

        drawCircle();
        return true;
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  private void runCounter(){
    // when same size circle test finish
    if(this.circleTestTimes - 1 == this.circleTestIndex) {
      this.circleSizeIndex++;
      this.circleTestIndex = 0;
      return;
    }
    this.circleTestIndex++;
  }

  private boolean isFinish(){
    return this.circleSize.length - 1 == this.circleSizeIndex && this.circleTestTimes - 1 == this.circleTestIndex;
  }

  private void drawCircle() {

    if(this.isFinish()){
      Log.d(TAG, "test done!!!!");
      return;
    }

    parentView.removeAllViews();

    c = new CircleView(getApplicationContext());
    parentView.addView(c);

    int size = c.getLayoutParams().width = c.getLayoutParams().height = this.circleSize[this.circleSizeIndex];

    lastCircleRadius = size;
    lastCircleX = getRandomX(size);
    lastCircleY = getRandomY(size);
    c.setX(lastCircleX);
    c.setY(lastCircleY);

    runCounter();
  }

  private int getDist(float x2, float y2) {
    return (int) Math.ceil(Math.sqrt(Math.pow(lastCircleX - x2, 2) + Math.pow(lastCircleY - y2, 2)));
  }

//  private int getRandomSize() {
//    return (int) (Math.random() * (maxSize - minSize + 1) + minSize);
//  }

  private int getRandomX(int size) {
    return (int) Math.ceil(Math.random() * (viewWidth - size));
  }

  private int getRandomY(int size) {
    return (int) Math.ceil(Math.random() * (viewHeight - size));
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}