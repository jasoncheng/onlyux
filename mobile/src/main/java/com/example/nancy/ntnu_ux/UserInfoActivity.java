package com.example.nancy.ntnu_ux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class UserInfoActivity extends Activity {

  private int startAge = 6;
  private int endAge = 40;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.userinfo);

    Button bt = (Button) this.findViewById(R.id.letsgo);
    bt.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(UserInfoActivity.this, TestActivity.class);
        startActivity(intent);
      }
    });

    // Age
    Integer[] age = new Integer[endAge-startAge+1];
    int idx = 0;
    for(int i=startAge; i<=endAge; i++){
      age[idx] = i;
      idx++;
    }

    Spinner mspin=(Spinner) findViewById(R.id.age);
    ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this.getBaseContext(),android.R.layout.simple_spinner_item, age);
    mspin.setAdapter(adapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_user_info, menu);
    return true;
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
