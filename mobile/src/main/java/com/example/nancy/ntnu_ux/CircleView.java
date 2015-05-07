package com.example.nancy.ntnu_ux;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by jasoncheng on 15/5/7.
 */
public class CircleView extends View {

  private int minRadius = 30;
  private int maxRadius = 80;

  public CircleView(Context ctx) {
    super(ctx);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    Paint paint = new Paint();
    paint.setStyle(Paint.Style.FILL);
    paint.setColor(Color.WHITE);
    canvas.drawPaint(paint);

    // 畫圈圈
    int radius = getRandomRedius();
    circle(canvas, paint, getRandomX(radius), getRandomY(radius), "#99ccff", radius);

    radius = getRandomRedius();
    circle(canvas, paint, getRandomX(radius), getRandomY(radius), "#990000", radius);

    radius = getRandomRedius();
    circle(canvas, paint, getRandomX(radius), getRandomY(radius), "#00ff00", radius);

  }

  private int getRandomRedius(){
    return (int) (Math.random() * (maxRadius - minRadius + 1) + minRadius);
  }

  private int getRandomX(int radius){
    return (int) Math.ceil(Math.random() * (CircleView.this.getWidth()-radius));
  }

  private int getRandomY(int radius){
    return (int) Math.ceil(Math.random() * (CircleView.this.getHeight()-radius));
  }

  private void circle(Canvas canvas, Paint paint, int x, int y, String color, Integer radius){
    paint.setColor(Color.parseColor(color));
    canvas.drawCircle(x, y, radius, paint);
  }
}
