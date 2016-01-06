package com.example.nancy.ntnu_ux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.example.nancy.ntnu_ux.com.example.nancy.ntnu_ux.bean.Score;
import com.example.nancy.ntnu_ux.com.example.nancy.ntnu_ux.bean.Test;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.ArrayList;


public class TestActivity extends Activity {

  int lastCircleX = 0;
  int lastCircleY = 0;
  int lastCircleRadius = 0;

  private ViewGroup parentView;
  private CircleView c;
  private final String TAG = "Nancy";

  private int circleTestIndex = 0;
  private int circleSizeIndex = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

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
//            Log.i(TAG, "=======> " +  event.getX() + ", " + event.getY());
            int dist = ApplicationTest.getDist(lastCircleX, lastCircleY, event.getX(), event.getY());
            boolean isSuccess = lastCircleRadius - dist >= 0;
            int use = (int)(System.currentTimeMillis() - startTime);
            Log.i(TAG, "=======> " + use + ":" + isSuccess +", dist: "+dist+ ", pixel: " + lastCircleRadius + ", convert to mm: " + Consts.pxToMm(lastCircleRadius, getBaseContext()));
//            mTimeTest.add(use);
//            mScoreSuccess.add(isSuccess);

            ApplicationTest.getTest().increment(Test.STAGE_1_COUNT);

            Score score = new Score();
            score.setTest(ApplicationTest.getTest());
            score.setRadius(Consts.pxToMm(lastCircleRadius, getBaseContext()));
            score.setStage(Test.STAGE_1);
            score.setSuccess(isSuccess);
            score.setTime(use);
            score.setDist(Consts.pxToMm(dist, getBaseContext()));
            score.saveInBackground(new SaveCallback() {
              @Override
              public void done(ParseException e) {
                if (e != null){
                  Log.e(TAG, e.getMessage());
                }
              }
            });


            if (isSuccess == false){
              ApplicationTest.addFail();
            }

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
        ApplicationTest.viewHeight = parentView.getMeasuredHeight();
        ApplicationTest.viewWidth = parentView.getMeasuredWidth();
        drawCircle();
        return true;
      }
    });
  }

  private void runCounter(){
    // when same size circle test finish
    if(Consts.EACH_CIRCLE_TEST_TIMES - 1 == this.circleTestIndex) {
      this.circleSizeIndex++;
      this.circleTestIndex = 0;
      return;
    }
    this.circleTestIndex++;
  }

  private boolean isFinish(){
//    return Consts.CIRCLE_SIZE.length - 1 == this.circleSizeIndex && Consts.EACH_CIRCLE_TEST_TIMES - 1 == this.circleTestIndex;
    return ApplicationTest.getTest().getInt(Test.STAGE_1_COUNT) >= Consts.CIRCLE_SIZE.length * Consts.EACH_CIRCLE_TEST_TIMES;
  }

  private void drawCircle() {
    if(ApplicationTest.isFail()) {
      String msg = "test fail reach " + Consts.MAX_FAILURE + " times.";
      Log.d(TAG, "========>  " + msg);
      Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
      ApplicationTest.getTest().saveInBackground();
      finish();
      return;
    }

    if(this.isFinish()){
      Log.d(TAG, "========> test done!!!!");
      Toast.makeText(this, Test.STAGE_1 + " done! ", Toast.LENGTH_LONG).show();
      ApplicationTest.getTest().setStageDone(Test.STAGE_1);
      ApplicationTest.getTest().saveInBackground();
      Intent i = new Intent(this, TestActivity2.class);
      startActivity(i);
      finish();
      return;
    }

    parentView.removeAllViews();

    c = new CircleView(getApplicationContext());
    parentView.addView(c);

    int realPixel = Consts.mmToPixel(this, String.valueOf(Consts.CIRCLE_SIZE[this.circleSizeIndex]));
    int size = c.getLayoutParams().width = c.getLayoutParams().height = realPixel;

    lastCircleRadius = size;
    lastCircleX = ApplicationTest.getRandomX(size);
    lastCircleY = ApplicationTest.getRandomY(size);
    c.setX(lastCircleX);
    c.setY(lastCircleY);

    runCounter();
  }

//  private int getDist(float x2, float y2) {
//    return (int) Math.ceil(Math.sqrt(Math.pow(lastCircleX - x2, 2) + Math.pow(lastCircleY - y2, 2)));
//  }

//  private int getRandomSize() {
//    return (int) (Math.random() * (maxSize - minSize + 1) + minSize);
//  }

//  private int getRandomX(int size) {
//    return (int) Math.ceil(Math.random() * (viewWidth - size));
//  }
//
//  private int getRandomY(int size) {
//    return (int) Math.ceil(Math.random() * (viewHeight - size));
//  }

//
//  @Override
//  public boolean onOptionsItemSelected(MenuItem item) {
//    // Handle action bar item clicks here. The action bar will
//    // automatically handle clicks on the Home/Up button, so long
//    // as you specify a parent activity in AndroidManifest.xml.
//    int id = item.getItemId();
//
//    //noinspection SimplifiableIfStatement
//    if (id == R.id.action_settings) {
//      return true;
//    }
//
//    return super.onOptionsItemSelected(item);
//  }
}
