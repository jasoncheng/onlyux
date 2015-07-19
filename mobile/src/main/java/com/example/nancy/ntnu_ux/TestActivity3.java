package com.example.nancy.ntnu_ux;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.nancy.ntnu_ux.com.example.nancy.ntnu_ux.bean.Score;
import com.example.nancy.ntnu_ux.com.example.nancy.ntnu_ux.bean.Test;
import com.parse.ParseException;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public class TestActivity3 extends Activity {

  int waitToNextTest = 2000;

  int lastCircleX = 0;
  int lastCircleY = 0;
  int lastTargetX = 0;
  int lastTargetY = 0;
  int lastCircleRadius = 0;

  // previous drag position
  int prevX = 0;
  int prevY = 0;

  int totalDist = 0;

  private ViewGroup parentView;
  private CircleView c;

  // drop target
  private CircleView ct;

  private final String TAG = "Nancy";

  private int circleTestIndex = 0;
  private int circleSizeIndex = 0;
  private long startTime;
  private Handler mHandler = new Handler();
  private List<Long> mClickTime = new ArrayList<Long>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    parentView = (ViewGroup) this.findViewById(R.id.parentView);
    parentView.setOnDragListener(new MyDragListener());
    drawCircle();
  }

  private final class MyTouchListener implements View.OnTouchListener {

    @Override
    public boolean onTouch(View v, MotionEvent event) {
      if(event.getAction() == MotionEvent.ACTION_DOWN){
        ClipData clip = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        v.startDrag(clip, shadowBuilder, v, 0);
        v.setVisibility(View.INVISIBLE);

        // caculate click on circle
        x_dist = (int)event.getX();
        y_dist = (int)event.getY();
        return true;
      }
      return false;
    }
  }

  private int x_dist = 0;
  private int y_dist = 0;

  private final class MyDragListener implements View.OnDragListener {

    @Override
    public boolean onDrag(View v, DragEvent event) {
      int act = event.getAction();
      switch(act){
        case DragEvent.ACTION_DRAG_STARTED:
//          Log.i(TAG, "===============> ACTION_DRAG_STARTED " + event.getX() + ", " + event.getY() );
          break;
        case DragEvent.ACTION_DRAG_ENDED:
          break;
        case DragEvent.ACTION_DRAG_ENTERED:
          totalDist = 0;
          startTime = System.currentTimeMillis();
          Log.i(TAG, "===============> ACTION_DRAG_ENTERED " + event.getX() + ", " + event.getY() );
          break;
        case DragEvent.ACTION_DRAG_EXITED:
          break;
        case DragEvent.ACTION_DROP:
          View view = (View) event.getLocalState();
          view.setX(event.getX() - x_dist);
          view.setY(event.getY() - y_dist);
          view.setVisibility(View.VISIBLE);

          int use = (int)(System.currentTimeMillis() - startTime);
          int dist = ApplicationTest.getDist(lastTargetX, lastTargetY, event.getX(), event.getY());
          boolean isSuccess = dist <= lastCircleRadius*2;
          Log.i(TAG, "===============> ACTION_DROP " + isSuccess + ", " + dist + ", " + totalDist);

          // save data
          String result = isSuccess ? "success" : "failure";
          Toast.makeText(TestActivity3.this, "test " + result + ", please wait", Toast.LENGTH_SHORT).show();

          ApplicationTest.getTest().increment(Test.STAGE_3_COUNT);
          Score score = new Score();
          score.setTest(ApplicationTest.getTest());
//          score.setRadius(lastCircleRadius);
          score.setRadius(Consts.pxToMm(lastCircleRadius, getBaseContext()));
          score.setStage(Test.STAGE_3);
          score.setSuccess(isSuccess);
          score.setTime(use);
          score.setDistDrag(Consts.pxToMm(totalDist, getBaseContext()));
          score.setDist(Consts.pxToMm(dist, getBaseContext()));
          score.setDistBetween(Consts.pxToMm(ApplicationTest.getDist(lastCircleX, lastCircleY, lastTargetX, lastTargetY), getBaseContext()));
          score.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
              if (e != null) {
                Log.e(TAG, e.getMessage());
              }
            }
          });


          if (isSuccess == false){
            ApplicationTest.addFail();
          }

          // go to next size
          mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
              totalDist = 0;
              drawCircle();
            }
          }, waitToNextTest);

          break;
        case DragEvent.ACTION_DRAG_LOCATION:
//          Log.i(TAG, "===============> ACTION_DRAG_LOCATION " + event.getX() + ", " + event.getY() );
          totalDist += ApplicationTest.getDist(prevX, prevY, event.getX(), event.getY());
          prevX = (int)event.getX();
          prevY = (int)event.getY();
          break;
      }
      return true;
    }
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
    return ApplicationTest.getTest().getInt(Test.STAGE_3_COUNT) >= Consts.CIRCLE_SIZE.length * Consts.EACH_CIRCLE_TEST_TIMES;
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
      Toast.makeText(this, Test.STAGE_3 + " done! ", Toast.LENGTH_LONG).show();
      ApplicationTest.getTest().setStageDone(Test.STAGE_3);
      ApplicationTest.getTest().saveInBackground();
      finish();
      return;
    }

    parentView.removeAllViews();

    // create dragable circle
    c = new CircleView(getApplicationContext());
    c.setOnTouchListener(new MyTouchListener());
    c.setOnDragListener(new MyDragListener());
    parentView.addView(c);

    int realPixel = Consts.mmToPixel(this, String.valueOf(Consts.CIRCLE_SIZE[this.circleSizeIndex]));
    int size = c.getLayoutParams().width = c.getLayoutParams().height = realPixel;

    lastCircleRadius = size;
    prevX = lastCircleX = ApplicationTest.getRandomX(size);
    prevY = lastCircleY = ApplicationTest.getRandomY(size);
    c.setX(lastCircleX);
    c.setY(lastCircleY);

    // create target
    lastTargetX = ApplicationTest.getRandomX(size);
    lastTargetY = ApplicationTest.getRandomY(size);
    ct = new CircleView(getApplicationContext(), Color.argb(100, 255, 0, 0));
    parentView.addView(ct);

    ct.getLayoutParams().width = ct.getLayoutParams().height = size;
    ct.setX(lastTargetX);
    ct.setY(lastTargetY);

    runCounter();
  }
}
