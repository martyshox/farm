package com.example.marti.farm;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    TextView textView;
    TextView textView2;
    TextView txtMsg;
    SQLiteDatabase db;
    //String tag,name,amount,date,period,s2;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_scan);
        //setupToolbar();

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView);
        txtMsg = (TextView) findViewById(R.id.textMsg);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {

        Toast.makeText(this, "Contents = " + rawResult.getText(), Toast.LENGTH_SHORT).show();

        textView.setText(rawResult.getText());

        String s2 = rawResult.getText();


        final Intent myLocalIntent = getIntent();
        Bundle myBundle = myLocalIntent.getExtras();


        myBundle.putString("tagNo", s2);
        myLocalIntent.putExtras(myBundle);
        setResult(Activity.RESULT_OK, myLocalIntent);


        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(ScanActivity.this);
            }
        }, 2000);
    }

    public void onClickUpdateDB(View view)
    {
        try
        {
            openDatabase();      // open (create if needed) database
            insertData();        // insert data
            db.close();         // make sure to release the DB
            txtMsg.append("\n- All Done!");
        }
        catch (Exception e)
        {
            txtMsg.append("\n- #Error onCreate: " + e.getMessage());
            finish();
        }
    }


    /*******************************************************************************************/

    private void openDatabase()
    {
        try
        {
            // path to private memory:
            String SDcardPath = "data/data/com.example.marti.farm/databases";

            // this provides the path name to the SD card
            // String SDcardPath = Environment.getExternalStorageDirectory().getPath();

            String myDbPath = SDcardPath + "/" + "Farm.db";
            //txtMsg.append("\n-DB Path: " + myDbPath);

            db = SQLiteDatabase.openDatabase(myDbPath, null,
                    SQLiteDatabase.CREATE_IF_NECESSARY);

            txtMsg.append("\n- Database was opened");
        }
        catch (SQLiteException e)
        {
            txtMsg.append("\n- #Error openDatabase: " + e.getMessage());
            finish();
        }
    }
    /*******************************************************************************************/

    private void insertData()
    {
        try
        {

            // an alternative to SQL "insert into table values(...)"
            // ContentValues is an Android dynamic row-like container
            final Intent myLocalIntent = getIntent();
            Bundle myBundle = myLocalIntent.getExtras();

            //ContentValues initialValues = new ContentValues();
            String ID = myBundle.getString("val0");



            //int result = Integer.parseInt(v0);

            switch(ID){
                case "1":

                    String name = myBundle.getString("val1");
                    String amount = myBundle.getString("val2");
                    String date = myBundle.getString("val3");
                    String period = myBundle.getString("val4");
                    String tag = myBundle.getString("tagNo");
                    textView2.setText("Data received is \n"
                            + "Dose Name: = " + name + "\nAmount: = " + amount + "\nDate: = " + date + "\nWithdrawal Period: = " + period + "\n Ear Tag No. =" + tag);


                    ContentValues initialValues = new ContentValues();
                    initialValues.put("Eartag", tag);
                    initialValues.put("DoseName", name);
                    initialValues.put("Amount", amount);
                    initialValues.put("Date", date);
                    initialValues.put("WithdrawalPeriod", period);



                    int rowPosition = (int) db.insert("doses", "v1", initialValues);
                    txtMsg.append("\n- Data Inserted to Database Successfully: " + rowPosition);
                    new Send_dose_Server().execute();



                    break;



                case "2":
                    String weight = myBundle.getString("val1");
                    String wdate = myBundle.getString("val2");
                    String wtag = myBundle.getString("tagNo");

                    textView2.setText("Data received is \n"
                            + "Weight: = " + weight + "\nDate: = " + wdate + "\n Ear Tag No. =" + wtag);


                    ContentValues initialValues1 = new ContentValues();
                    initialValues1.put("Eartag", wtag);
                    initialValues1.put("Weight", weight);
                    initialValues1.put("Date", wdate);


                    int rowPosition1 = (int) db.insert("weight", "v1", initialValues1);
                    txtMsg.append("\n- Data Inserted to Database Successfully: " + rowPosition1);
                    new Send_weight_Server().execute();
                    break;

                case "3":
                    String feed = myBundle.getString("val1");
                    String feedAmount = myBundle.getString("val2");
                    String fdate = myBundle.getString("val3");
                    String ftag = myBundle.getString("tagNo");
                    textView2.setText("Data received is \n"
                            + "Feed Name: = " + feed + "\nAmount: = " + feedAmount + "\nDate: = " + fdate + "\n Ear Tag No. =" + ftag);


                    ContentValues initialValues2 = new ContentValues();
                    initialValues2.put("Eartag", ftag);
                    initialValues2.put("FeedName", feed);
                    initialValues2.put("FeedAmount", feedAmount);
                    initialValues2.put("Date", fdate);

                    int rowPosition2 = (int) db.insert("feed", "v1", initialValues2);
                    txtMsg.append("\n- Data Inserted to Database Successfully: " + rowPosition2);
                    new Send_feed_Server().execute();
                    break;

                case "4":
                    String fertName = myBundle.getString("val1");
                    String cdate = myBundle.getString("val2");
                    String camount = myBundle.getString("val3");
                    String field = myBundle.getString("tagNo");
                    textView2.setText("Data received is \n"
                            + "Dose Name: = " + fertName + "\nAmount: = " + camount + "\nDate: = " + cdate +  "\n field =" + field);


                    ContentValues initialValues3 = new ContentValues();
                    initialValues3.put("Field", field);
                    initialValues3.put("Name", fertName);
                    initialValues3.put("Date", cdate);
                    initialValues3.put("Amount", camount);


                    int rowPosition3 = (int) db.insert("crops", "v1", initialValues3);
                    txtMsg.append("\n- Data Inserted to Database Successfully: " + rowPosition3);
                    break;



            }




        }
        catch (Exception e)
        {
            txtMsg.append("\n- #Error sending data to Database: " + e.getMessage());
        }
    }

    /*******************************************************************************************/

    private void dropTable()
    {
        // (clean start) action query to drop table
        try
        {
            db.execSQL("DROP TABLE IF EXISTS results;");
            txtMsg.append("\n- DropTable - dropped!!");
        }
        catch (Exception e)
        {
            txtMsg.append("\n- #Error dropTable: " + e.getMessage());
            finish();
        }
    }

    /*******************************************************************************************/

    private void CreateTable()
    { // create table: results
        db.beginTransaction();
        try
        {
            // create table
            db.execSQL("create table if not exists doses ("
                    + " recID integer PRIMARY KEY autoincrement, "
                    + " Eartag  TEXT, " + " DoseName TEXT, " + " Amount TEXT, " +" Date TEXT, " +" WithdrawalPeriod TEXT);  ");

            // create table
            db.execSQL("create table if not exists weight ("
                    + " recID integer PRIMARY KEY autoincrement, "
                    + " Eartag  TEXT, " + " Weight TEXT, " + " Date TEXT);  ");

            // create table
            db.execSQL("create table if not exists feed ("
                    + " recID integer PRIMARY KEY autoincrement, "
                    + " Eartag  TEXT, " + " FeedName TEXT, " + " FeedAmount TEXT, " +" DAte TEXT);  ");

            // create table
            db.execSQL("create table if not exists crops ("
                    + " recID integer PRIMARY KEY autoincrement, "
                    + " Field  TEXT, " + " Name TEXT, " + " Date TEXT, " +" Amount TEXT);  ");
            // commit your changes
            db.setTransactionSuccessful();

            txtMsg.append("\n- Table was created");

        }

        catch (SQLException e1)
        {
            txtMsg.append("\n- #Error Creating Table: " + e1.getMessage());
            finish();
        }

        finally
        {
            db.endTransaction();
        }
    }

    /*******************************************************************************************/
    private class Send_dose_Server extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            //startingMillis = System.currentTimeMillis();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {



                URL link = new URL("http://172.20.10.8:8080/web_war_exploded/"+"farm");

                HttpURLConnection urlconnection = (HttpURLConnection) link.openConnection();

                urlconnection.setDoOutput(true);
                urlconnection.setDoInput(true);
                urlconnection.setUseCaches(false);
                urlconnection.setDefaultUseCaches(false);

                // Specify the content type that we will send binary data
                urlconnection.setRequestProperty("Content_Type","application/octet-stream");

                ObjectOutputStream oos = new ObjectOutputStream(urlconnection.getOutputStream());
                //System.out.println((weight_datas instanceof List));
                final Intent myLocalIntent = getIntent();
                Bundle myBundle = myLocalIntent.getExtras();
                String name = myBundle.getString("val1");
                String amount = myBundle.getString("val2");
                String date = myBundle.getString("val3");
                String period = myBundle.getString("val4");
                String tag = myBundle.getString("tagNo");

                ContentValues initialValues = new ContentValues();
                initialValues.put("Eartag", tag);
                initialValues.put("DoseName", name);
                initialValues.put("Amount", amount);
                initialValues.put("Date", date);
                initialValues.put("WithdrawalPeriod", period);

                //String weight = "hello";
                ArrayList<String> dose = new ArrayList<>();

                //for(int i = 0; i > weight_datas.size(); i++) {
                dose.add(tag);
                dose.add(name);
                dose.add(amount);
                dose.add(date);
                dose.add(period);


                oos.writeObject(dose);
                oos.flush();
                //}
                //}


                //ArrayList<weightDatas> weight = new ArrayList<weightDatas>();




                ObjectInputStream ois = new ObjectInputStream(urlconnection.getInputStream());
                int count = ois.readInt();
                oos.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(final Void unused) {
            //finish();
        }
    }
    /*******************************************************************************************/
    private class Send_weight_Server extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            //startingMillis = System.currentTimeMillis();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {



                URL link = new URL("http://172.20.10.8:8080/web_war_exploded/"+"weight");

                HttpURLConnection urlconnection = (HttpURLConnection) link.openConnection();

                urlconnection.setDoOutput(true);
                urlconnection.setDoInput(true);
                urlconnection.setUseCaches(false);
                urlconnection.setDefaultUseCaches(false);

                // Specify the content type that we will send binary data
                urlconnection.setRequestProperty("Content_Type","application/octet-stream");

                ObjectOutputStream oos = new ObjectOutputStream(urlconnection.getOutputStream());
                //System.out.println((weight_datas instanceof List));
                final Intent myLocalIntent = getIntent();
                Bundle myBundle = myLocalIntent.getExtras();
                String weight = myBundle.getString("val1");
                String wdate = myBundle.getString("val2");
                String wtag = myBundle.getString("tagNo");




                ContentValues initialValues1 = new ContentValues();
                initialValues1.put("Eartag", wtag);
                initialValues1.put("Weight", weight);
                initialValues1.put("Date", wdate);

                //String weight = "hello";
                ArrayList<String> weightData = new ArrayList<>();

                //for(int i = 0; i > weight_datas.size(); i++) {
                weightData.add(wtag);
                weightData.add(weight);
                weightData.add(wdate);



                oos.writeObject(weightData);
                oos.flush();
                //}
                //}


                //ArrayList<weightDatas> weight = new ArrayList<weightDatas>();




                ObjectInputStream ois = new ObjectInputStream(urlconnection.getInputStream());
                int count = ois.readInt();
                oos.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(final Void unused) {
            //finish();
        }
    }
    /**************************************************************************************/
    private class Send_feed_Server extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            //startingMillis = System.currentTimeMillis();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {



                URL link = new URL("http://172.20.10.8:8080/web_war_exploded/"+"feed");

                HttpURLConnection urlconnection = (HttpURLConnection) link.openConnection();

                urlconnection.setDoOutput(true);
                urlconnection.setDoInput(true);
                urlconnection.setUseCaches(false);
                urlconnection.setDefaultUseCaches(false);

                // Specify the content type that we will send binary data
                urlconnection.setRequestProperty("Content_Type","application/octet-stream");

                ObjectOutputStream oos = new ObjectOutputStream(urlconnection.getOutputStream());
                //System.out.println((weight_datas instanceof List));
                final Intent myLocalIntent = getIntent();
                Bundle myBundle = myLocalIntent.getExtras();
                String feed = myBundle.getString("val1");
                String feedAmount = myBundle.getString("val2");
                String fdate = myBundle.getString("val3");
                String ftag = myBundle.getString("tagNo");



                ContentValues initialValues2 = new ContentValues();
                initialValues2.put("Eartag", ftag);
                initialValues2.put("FeedName", feed);
                initialValues2.put("FeedAmount", feedAmount);
                initialValues2.put("Date", fdate);

                //String weight = "hello";
                ArrayList<String> feedData = new ArrayList<>();

                //for(int i = 0; i > weight_datas.size(); i++) {
                feedData.add(ftag);
                feedData.add(feed);
                feedData.add(feedAmount);
                feedData.add(fdate);



                oos.writeObject(feedData);
                oos.flush();
                //}
                //}


                //ArrayList<weightDatas> weight = new ArrayList<weightDatas>();




                ObjectInputStream ois = new ObjectInputStream(urlconnection.getInputStream());
                int count = ois.readInt();
                oos.close();
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(final Void unused) {
            //finish();
        }
    }
}
