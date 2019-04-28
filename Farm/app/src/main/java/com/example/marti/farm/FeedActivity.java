package com.example.marti.farm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedActivity extends AppCompatActivity {
    EditText feedName;
    EditText feedAmount;
    EditText Date;





    String ID, name, amount, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        feedName = (EditText) findViewById(R.id.feedName);
        feedAmount = (EditText) findViewById(R.id.feedAmount);
        Date = (EditText) findViewById(R.id.Date);


        Button scan = (Button) findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(feedName.getText().toString().equalsIgnoreCase("")) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please Enter All Values",
                            Toast.LENGTH_SHORT);

                    toast.show();
                    return;

                }
                else {
                    name = feedName.getText().toString();
                }
                if(feedAmount.getText().toString().equalsIgnoreCase("")) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please Enter All Values",
                            Toast.LENGTH_SHORT);

                    toast.show();
                    return;
                }
                else {
                    amount = feedAmount.getText().toString();
                }
                if(Date.getText().toString().equalsIgnoreCase("")) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please Enter All Values",
                            Toast.LENGTH_SHORT);

                    toast.show();
                    return;
                }
                else {
                    date = Date.getText().toString();
                }


                Intent intent = new Intent(FeedActivity.this, ScanActivity.class);
                //startActivityForResult(intent, 0);
                //return true;
                // create a Bundle (MAP) container to ship data
                Bundle myDataBundle = new Bundle();
                ID = "3";
                // add <key,value> data items to the container
                myDataBundle.putString("val0", ID);
                myDataBundle.putString("val1", name);
                myDataBundle.putString("val2", amount);
                myDataBundle.putString("val3", date);


                // attach the container to the intent
                intent.putExtras(myDataBundle);

                // call Activity2, tell your local listener to wait a
                // response sent to a listener known as 101
                startActivityForResult(intent, 101);
            }
        });
    }
}
