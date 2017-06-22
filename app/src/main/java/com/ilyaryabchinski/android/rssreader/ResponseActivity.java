package com.ilyaryabchinski.android.rssreader;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.ilyaryabchinski.android.rssreader.R;

/**
 * Created by ilya on 6/20/17.
 */

public class ResponseActivity extends AppCompatActivity {

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        RSSParser parser = new RSSParser();
        String link = pref.getString("link","");
        final Container container = parser.parse(link);
        if(container.size() == 0){
            showMessage();
            setTitle("Channel is empty");
        }
        else {
            setTitle("Channel: " + container.getName());

            ListView listView = (ListView) findViewById(R.id.listView_content);
            listView.setClickable(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(container.getItem(position).getLink()));
                    startActivity(browserIntent);
                }
            });

            ArrayAdapter<RSSItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, container.getList());

            listView.setAdapter(adapter);
        }

    }

    public void showMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error")
                .setMessage("Please, check the link and internet connection")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("ОK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent intent = new Intent(ResponseActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }


}
