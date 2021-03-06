package com.example.nancy.ntnu_ux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nancy.ntnu_ux.com.example.nancy.ntnu_ux.bean.Test;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class UserInfoActivity extends Activity {

  private Spinner mAge;
  private Spinner mGender;
  private Spinner mOrientation;
  private EditText mFreeText;
  ViewGroup parentView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.userinfo);

    parentView = (ViewGroup) this.findViewById(R.id.parentView);
    ViewTreeObserver vto = parentView.getViewTreeObserver();
    vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
      public boolean onPreDraw() {
        parentView.getViewTreeObserver().removeOnPreDrawListener(this);
        ApplicationTest.viewHeight = parentView.getMeasuredHeight();
        ApplicationTest.viewWidth = parentView.getMeasuredWidth();
        return true;
      }
    });

    Button bt = (Button) this.findViewById(R.id.letsgo);
    bt.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(UserInfoActivity.this.getBaseContext(),"initial test, wait a minute", Toast.LENGTH_SHORT).show();
        Test t = ApplicationTest.initTest();
        t.setOrientation(mOrientation.getSelectedItemPosition());
        t.setAge(Integer.parseInt(mAge.getSelectedItem().toString()));
        t.setGender(mGender.getSelectedItem().toString());
        t.setFreeText(mFreeText.getText().toString());
        t.saveInBackground();
        t.saveInBackground(new SaveCallback() {
          @Override
          public void done(ParseException e) {
            if (e != null) {
              Toast.makeText(UserInfoActivity.this.getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
              return;
            }
            Intent intent = new Intent(UserInfoActivity.this, TestActivity.class);
            intent.addFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
          }
        });

      }
    });

    // free text
    mFreeText = (EditText) findViewById(R.id.free_text);

    // orientation
    mOrientation = (Spinner) findViewById(R.id.orientation);

    // gender
    mGender = (Spinner) findViewById(R.id.gender);

    // Age
    Integer[] age = new Integer[Consts.MAX_AGE-Consts.MIN_AGE+1];
    int idx = 0;
    for(int i=Consts.MIN_AGE; i<= Consts.MAX_AGE; i++){
      age[idx] = i;
      idx++;
    }

    mAge = (Spinner) findViewById(R.id.age);
    ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this.getBaseContext(),android.R.layout.simple_spinner_item, age);
    mAge.setAdapter(adapter);
  }

//  @Override
//  public boolean onCreateOptionsMenu(Menu menu) {
//    // Inflate the menu; this adds items to the action bar if it is present.
//    getMenuInflater().inflate(R.menu.menu_user_info, menu);
//    return true;
//  }

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
