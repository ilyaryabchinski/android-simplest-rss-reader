package com.ilyaryabchinski.android.rssreader;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;




public class ResponseActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private SwipeRefreshLayout layout;
    private Container container;
    ListView listView;
    ArrayAdapter<RSSItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        layout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout.setRefreshing(true);
                ( new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout.setRefreshing(false);
                        getData();

                    }

                }, 3000);
            }
        });
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        getData();

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


    private void getData(){

        RSSParser parser = new RSSParser();
        String link = pref.getString("link","");
        container = parser.parse(link);
        if(container.size() == 0){
            showMessage();
            setTitle("Channel is empty");
        }
        else {
            setTitle("Channel: " + container.getName());

            listView = (ListView) findViewById(R.id.listView_content);
            listView.setClickable(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(container.getItem(position).getLink()));
                    startActivity(browserIntent);
                }
            });

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, container.getList());
            listView.setAdapter(adapter);

        }
    }
}
