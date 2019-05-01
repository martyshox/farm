package com.example.marti.farmapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class HeatActivity extends AppCompatActivity {

    EditText bullID;
    TextView Date;

    String ID, name, amount, date;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heat);
        bullID = (EditText) findViewById(R.id.bullID);
        Date = (TextView) findViewById(R.id.date);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now().plusDays(283); //typical Gestation period of a cow
        Date.setText(dtf.format(localDate));
        date=dtf.format(localDate);
        System.out.println(dtf.format(localDate)); //2016/11/16*/


        Button scan = (Button) findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {
                if(bullID.getText().toString().equalsIgnoreCase("")) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please Enter All Values",
                            Toast.LENGTH_SHORT);

                    toast.show();
                    return;
                }
                else {
                    amount = bullID.getText().toString();
                }





                Intent intent = new Intent(HeatActivity.this, ScanActivity.class);
                //startActivityForResult(intent, 0);
                //return true;
                // create a Bundle (MAP) container to ship data
                Bundle myDataBundle = new Bundle();
                ID = "4";
                // add <key,value> data items to the container
                myDataBundle.putString("val0", ID);
                myDataBundle.putString("val1", amount);
                myDataBundle.putString("val2", date);


                // attach the container to the intent
                intent.putExtras(myDataBundle);

                // call Activity2, tell your local listener to wait a
                // response sent to a listener known as 101
                startActivityForResult(intent, 101);
            }
        });
    }
}