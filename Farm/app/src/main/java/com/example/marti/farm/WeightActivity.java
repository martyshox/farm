package com.example.marti.farm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WeightActivity extends AppCompatActivity {

    EditText weightAmount;
    EditText Date;

    String ID, name, amount, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);
        weightAmount = (EditText) findViewById(R.id.weightAmount);
        Date = (EditText) findViewById(R.id.Date);


        Button scan = (Button) findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(weightAmount.getText().toString().equalsIgnoreCase("")) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Please Enter All Values",
                            Toast.LENGTH_SHORT);

                    toast.show();
                    return;
                }
                else {
                    amount = weightAmount.getText().toString();
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


                Intent intent = new Intent(WeightActivity.this, ScanActivity.class);
                //startActivityForResult(intent, 0);
                //return true;
                // create a Bundle (MAP) container to ship data
                Bundle myDataBundle = new Bundle();
                ID = "2";
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
