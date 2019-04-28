package com.example.marti.farm;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    List<weightDatas> weight_datas = new ArrayList<>();
    String laws, irws, lpws;
    SQLiteDatabase db;
    TextView results;
    List<weightDatas> datas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button searchBTN = (Button) findViewById(R.id.seachBTN);
        Button eventBTN = (Button) findViewById(R.id.eventBTN);
        results = (TextView)findViewById(R.id.results);
        results.setMovementMethod(new ScrollingMovementMethod());


        eventBTN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), EventActivity.class);
                startActivityForResult(intent, 0);
                //return true;
            }
        });
        searchBTN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), search.class);
                startActivityForResult(intent, 0);
                //return true;
            }
        });



    }



}
