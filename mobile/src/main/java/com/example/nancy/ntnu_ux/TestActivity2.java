package com.example.nancy.ntnu_ux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nancy.ntnu_ux.com.example.nancy.ntnu_ux.bean.Score;
import com.example.nancy.ntnu_ux.com.example.nancy.ntnu_ux.bean.Test;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public class TestActivity2 extends Activity {

  int lastCircleX = 0;
  int lastCircleY = 0;
  int lastCircleRadius = 0;

  private ViewGroup parentView;
  private CircleView c;
  private final String TAG = "Nancy";

  private int circleTestIndex = 0;
  private int circleSizeIndex = 0;
  private GestureDetectorCompat mDetector;
  long startTime;

  private List<Long> mClickTime = new ArrayList<Long>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mDetector = new GestureDetectorCompat(this, new MyGestureListener());
    parentView = (ViewGroup) this.findViewById(R.id.parentView);
    parentView.setOnTouchListener(new View.OnTouchListener() {


      @Override
      public boolean onTouch(View v, MotionEvent event) {
        return mDetector.onTouchEvent(event);
      }
    });
    drawCircle();
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
    return ApplicationTest.getTest().getInt(Test.STAGE_2_COUNT) >= Consts.CIRCLE_SIZE.length * Consts.EACH_CIRCLE_TEST_TIMES;
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
      Log.d(TAG, "===========> test done!!!!");
      Toast.makeText(this, Test.STAGE_2 + " done! ", Toast.LENGTH_LONG).show();
      ApplicationTest.getTest().setStageDone(Test.STAGE_2);
      ApplicationTest.getTest().saveInBackground();
      Intent i = new Intent(this, TestActivity3.class);
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

  private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
      Log.i(TAG, "==============> single tap");
//      startTime = System.currentTimeMillis();
      return super.onSingleTapUp(e);
    }

    @Override
    public boolean onDown(MotionEvent e) {
      Log.i(TAG, "===============> onDown");
      mClickTime.add(System.currentTimeMillis());
      if(mClickTime.size() == 3){
        mClickTime.remove(0);
      }
      return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {
      startTime = mClickTime.get(0);
      int dist = ApplicationTest.getDist(lastCircleX, lastCircleY, event.getX(), event.getY());
      boolean isSuccess = lastCircleRadius - dist >= 0;
      int use = (int)(System.currentTimeMillis() - startTime);
      Log.i(TAG, "=======> " + use + ":" + isSuccess);
      ApplicationTest.getTest().increment(Test.STAGE_2_COUNT);

      Score score = new Score();
      score.setTest(ApplicationTest.getTest());
      score.setRadius(Consts.pxToMm(lastCircleRadius, getBaseContext()));
      score.setStage(Test.STAGE_2);
      score.setSuccess(isSuccess);
      score.setTime(use);
//      score.setDist(dist);
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
      return super.onDoubleTap(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
      return super.onSingleTapConfirmed(e);
    }

  }

}
