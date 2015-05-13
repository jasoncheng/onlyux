package com.example.nancy.ntnu_ux;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jasoncheng on 15/5/7.
 */
public class CircleView extends View {

  public CircleView(Context ctx) {
    super(ctx);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    Paint paint = new Paint();
    paint.setStyle(Paint.Style.FILL);
    paint.setColor(Color.TRANSPARENT);
    canvas.drawPaint(paint);

    // 畫圈圈
//    int radius = getRandomRedius();
//    circle(canvas, paint, getRandomX(radius), getRandomY(radius), "#99ccff", radius);
//
//    radius = getRandomRedius();
//    circle(canvas, paint, getRandomX(radius), getRandomY(radius), "#990000", radius);
//
//    radius = getRandomRedius();
//    circle(canvas, paint, getRandomX(radius), getRandomY(radius), "#00ff00", radius);

    int x = this.getWidth() / 2;
    int y = this.getHeight() / 2;
    int r = x;
    paint.setColor(Color.parseColor("#99ccff"));
    canvas.drawCircle(x, y, r, paint);

  }


  @Override
  public boolean onTouchEvent(MotionEvent event) {
    return super.onTouchEvent(event);
  }

  private void circle(Canvas canvas, Paint paint, int x, int y, String color, Integer radius){
    paint.setColor(Color.parseColor(color));
    canvas.drawCircle(x, y, radius, paint);
  }

}
