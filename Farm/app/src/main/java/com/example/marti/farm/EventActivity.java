package com.example.marti.farm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Button doseBTN = (Button) findViewById(R.id.doseBTN);
        doseBTN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), DoseActivity.class);
                startActivityForResult(intent, 0);
                //return true;
            }
        });


        Button weightBTN = (Button) findViewById(R.id.weightBTN);
        weightBTN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), WeightActivity.class);
                startActivityForResult(intent, 0);
                //return true;
            }
        });

        Button feedBTN = (Button) findViewById(R.id.feedBTN);
        feedBTN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), FeedActivity.class);
                startActivityForResult(intent, 0);
                //return true;
            }
        });
    }
}
