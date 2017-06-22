package com.ilyaryabchinski.android.rssreader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText link;
    private SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        link = (EditText) findViewById(R.id.editText);
        link.setText(pref.getString("link",""));
    }


    public void onClick(View view){
        switch (view.getId()) {
            case R.id.button:
                Intent intent = new Intent(this, ResponseActivity.class);
                pref.edit().putString("link", link.getText().toString()).commit();
                startActivity(intent);
                break;
            default:
                break;
        }
    }


}
