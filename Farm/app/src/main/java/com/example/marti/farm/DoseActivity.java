package com.example.marti.farm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DoseActivity extends AppCompatActivity {

    EditText doseName;
    EditText doesAmount;
    EditText Date;
    EditText wPeriod;




    String ID, name, amount, date,period ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dose);

        doseName = (EditText) findViewById(R.id.doseName);
        doesAmount = (EditText) findViewById(R.id.doseAmount);
        Date = (EditText) findViewById(R.id.Date);
        wPeriod = (EditText) findViewById(R.id.wPeriod);

        Button scan = (Button) findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(doseName.getText().toString().equalsIgnoreCase("")) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please Enter All Values",
                            Toast.LENGTH_SHORT);

                    toast.show();
                    return;

                }
                else {
                    name = doseName.getText().toString();
                }
                if(doesAmount.getText().toString().equalsIgnoreCase("")) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please Enter All Values",
                            Toast.LENGTH_SHORT);

                    toast.show();
                    return;
                }
                else {
                    amount = doesAmount.getText().toString();
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
                if(wPeriod.getText().toString().equalsIgnoreCase("")) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please Enter All Values",
                            Toast.LENGTH_SHORT);

                    toast.show();
                    return;
                }
                else {
                    period = wPeriod.getText().toString();
                }

                Intent intent = new Intent(DoseActivity.this, ScanActivity.class);
                //startActivityForResult(intent, 0);
                //return true;
                // create a Bundle (MAP) container to ship data
                Bundle myDataBundle = new Bundle();
                ID = "1";
                // add <key,value> data items to the container
                myDataBundle.putString("val0", ID);
                myDataBundle.putString("val1", name);
                myDataBundle.putString("val2", amount);
                myDataBundle.putString("val3", date);
                myDataBundle.putString("val4", period);

                // attach the container to the intent
                intent.putExtras(myDataBundle);

                // call Activity2, tell your local listener to wait a
                // response sent to a listener known as 101
                startActivityForResult(intent, 101);
            }
        });


    }
}
