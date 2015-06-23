package com.example.nancy.ntnu_ux;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Color;
import android.os.Bundle;
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

  int lastCircleX = 0;
  int lastCircleY = 0;
  int lastCircleRadius = 0;

  private ViewGroup parentView;
  private CircleView c;
  private final String TAG = "Nancy";

  private int circleTestIndex = 0;
  private int circleSizeIndex = 0;
//  private GestureDetectorCompat mDetector;
  long startTime;

//  private RelativeLayout.LayoutParams lp;
  private List<Long> mClickTime = new ArrayList<Long>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

//    mDetector = new GestureDetectorCompat(this, new MyGestureListener());
    parentView = (ViewGroup) this.findViewById(R.id.parentView);
    parentView.setOnDragListener(new MyDragListener());
//    parentView.setOnTouchListener(new View.OnTouchListener() {
//      @Override
//      public boolean onTouch(View v, MotionEvent event) {
//        return mDetector.onTouchEvent(event);
//      }
//    });
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
//          lp = (RelativeLayout.LayoutParams) c.getLayoutParams();
          Log.i(TAG, "===============> ACTION_DRAG_STARTED " + event.getX() + ", " + event.getY() );
          break;
        case DragEvent.ACTION_DRAG_ENDED:
          break;
        case DragEvent.ACTION_DRAG_ENTERED:
          Log.i(TAG, "===============> ACTION_DRAG_ENTERED " + event.getX() + ", " + event.getY() );
          break;
        case DragEvent.ACTION_DRAG_EXITED:
//          lp.topMargin = (int) event.getY();
//          lp.leftMargin = (int) event.getX();
//          Log.i(TAG, "===============> ACTION_DRAG_EXITED " + lp.leftMargin + ", " + lp.topMargin );
//          v.setLayoutParams(lp);
//          v.setVisibility(View.VISIBLE);
          break;
        case DragEvent.ACTION_DROP:
          Log.i(TAG, "===============> drop");
          View view = (View) event.getLocalState();
          Log.i(TAG, "===============> ACTION_DROP " + event.getX() + ", " + event.getY() );
//          lp.topMargin = (int) event.getY();
//          lp.leftMargin = (int) event.getX();
//          c.setLayoutParams(lp);
//          ViewGroup owner = (ViewGroup) view.getParent();
//          owner.removeView(view);
//
          view.setX(event.getX() - x_dist);
          view.setY(event.getY() - y_dist);
          view.setVisibility(View.VISIBLE);
//          ViewGroup container = (ViewGroup) v;
//          container.removeAllViews();
//          view.setBackgroundColor(Color.CYAN);
//          view.setVisibility(View.VISIBLE);
//          container.addView(view);
          break;
        case DragEvent.ACTION_DRAG_LOCATION:
          Log.i(TAG, "===============> ACTION_DRAG_LOCATION " + event.getX() + ", " + event.getY() );
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
      finish();
      return;
    }

    parentView.removeAllViews();

    c = new CircleView(getApplicationContext());
    c.setOnTouchListener(new MyTouchListener());
    c.setOnDragListener(new MyDragListener());
    // create fake group and append circle into fake
//    RelativeLayout fake = new RelativeLayout(this);
//    RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
//        RelativeLayout.LayoutParams.WRAP_CONTENT,
//        RelativeLayout.LayoutParams.WRAP_CONTENT);
//    fake.setLayoutParams(rlp);
//    fake.addView(c);

    parentView.addView(c);


    int size = c.getLayoutParams().width = c.getLayoutParams().height = Consts.CIRCLE_SIZE[this.circleSizeIndex];

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
      score.setRadius(lastCircleRadius);
      score.setStage(Test.STAGE_2);
      score.setSuccess(isSuccess);
      score.setTime(use);
      score.setDist(dist);
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
