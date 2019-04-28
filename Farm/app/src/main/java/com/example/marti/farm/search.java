package com.example.marti.farm;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class search extends AppCompatActivity {

    String la, ir, lp, mp, tp,laf, irf, lpf, mpf,law, irw, lpw,name;
    EditText search;

    List<doseData> doses_data = new ArrayList<>();
    List<feedData> feed_data = new ArrayList<>();
    List<weightData> weight_data = new ArrayList<>();
    SQLiteDatabase db;
    private TextView results;
    Long startingMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search = (EditText) findViewById(R.id.search);
        results = (TextView)findViewById(R.id.results);
        results.setMovementMethod(new ScrollingMovementMethod());


        try
        {
            openDatabase();      // open (create if needed) database
            CreateTable();     // insert data
            db.close();         // make sure to release the DB
            //txtMsg.append("\n- All Done!");
        }
        catch (Exception e)
        {
            //txtMsg.append("\n- #Error onCreate: " + e.getMessage());
            finish();
        }

    }

    /*******************************************************************************************/
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

            //txtMsg.append("\n- Table was created");

        }

        catch (SQLException e1)
        {
            //txtMsg.append("\n- #Error Creating Table: " + e1.getMessage());
            finish();
        }

        finally
        {
            db.endTransaction();
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

            //txtMsg.append("\n- Database was opened");
        }
        catch (SQLiteException e)
        {
            //txtMsg.append("\n- #Error openDatabase: " + e.getMessage());
            finish();
        }
    }
    /*******************************************************************************************/
    private void QueryDatabase()
    {

        try
        {
            String mySQL = "select * from doses where Eartag LIKE '%"+name+"%'";
            Cursor c1 = db.rawQuery(mySQL,null);
            doses_data = get_All_The_Doses(c1);
        }
        catch (Exception e)
        {
            Log.e("\n#Error: ", "QueryDatabase(): " + e.getMessage());
        }
    }

    /**********************************************************************************************/

    private List<doseData> get_All_The_Doses(Cursor cursor)
    {
        cursor.moveToPosition(-1); //reset cursor's top

        List<doseData> data = new ArrayList<>();
        try
        {
            // now get the rows
            cursor.moveToPosition(-1); //reset cursor's top
            while (cursor.moveToNext())
            {
                for (int i = 0; i < cursor.getColumnCount(); i++)
                {
                    la = cursor.getString(1);
                    ir = cursor.getString(2);
                    lp = cursor.getString(3);
                    mp = cursor.getString(4);
                    tp = cursor.getString(5);

                }
                data.add(new doseData(la, ir, lp, mp, tp));
            }
        }
        catch (Exception e)
        {
            Log.e("\n#Error:", "Retrieving info from database: " + e.getMessage());
        }
        return data;
    }

    public void RetrieveFromDoses (View view)
    {
        new Query_dose_Database().execute();
    }
    private class Query_dose_Database extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            startingMillis = System.currentTimeMillis();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(search.getText().toString().equalsIgnoreCase("")) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Please Enter All Values",
                        Toast.LENGTH_SHORT);

                toast.show();
                //return;

            }
            else {
                name = search.getText().toString();
            }



            results.setText("");
            try
            {
                openDatabase();
                QueryDatabase();
                db.close();
            }
            catch (Exception e)
            {
                Log.e("\n#Error:", " insert_To_Database: Opening database or inserting to DB " + e.getMessage());
                finish();
            }
            return null;
        }

        protected void onPostExecute(final Void unused)
        {
            for (int i =0; i< doses_data.size();i++)
            {
                results.append("***********************************************\n");
                results.append("Eartag: " + doses_data.get(i).getEartag() +"\n");
                results.append("Dose Name: " + doses_data.get(i).getDoseName() +"\n");
                results.append("Dose Amount: " + doses_data.get(i).getAmounts() +"\n");
                results.append("Date given: " + doses_data.get(i).getDates() +"\n");
                results.append("Withdrawal Period: " + doses_data.get(i).getPeriod() +"\n");
                results.append("***********************************************\n\n");
            }

            results.append("Time Taken: "+ (System.currentTimeMillis() -startingMillis) +"msec\n\n");
        }
    }

    /*******************************************************************************************/
    private void QueryDatabasefeed()
    {

        try
        {
            String mySQL = "select * from feed where Eartag LIKE '%"+name+"%'";
            Cursor c1 = db.rawQuery(mySQL,null);
            feed_data = get_All_The_Feed(c1);
        }
        catch (Exception e)
        {
            Log.e("\n#Error: ", "QueryDatabase(): " + e.getMessage());
        }
    }

    /**********************************************************************************************/

    private List<feedData> get_All_The_Feed(Cursor cursor)
    {
        cursor.moveToPosition(-1); //reset cursor's top

        List<feedData> data = new ArrayList<>();
        try
        {
            // now get the rows
            cursor.moveToPosition(-1); //reset cursor's top
            while (cursor.moveToNext())
            {
                for (int i = 0; i < cursor.getColumnCount(); i++)
                {
                    laf = cursor.getString(1);
                    irf = cursor.getString(2);
                    lpf = cursor.getString(3);
                    mpf = cursor.getString(4);
                    //tp = cursor.getString(5);

                }
                data.add(new feedData(laf, irf, lpf, mpf));
            }
        }
        catch (Exception e)
        {
            Log.e("\n#Error:", "Retrieving info from database: " + e.getMessage());
        }
        return data;
    }

    public void RetrieveFromFeed (View view)
    {
        new Query_feed_Database().execute();
    }
    private class Query_feed_Database extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            startingMillis = System.currentTimeMillis();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(search.getText().toString().equalsIgnoreCase("")) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Please Enter All Values",
                        Toast.LENGTH_SHORT);

                toast.show();
                //return;

            }
            else {
                name = search.getText().toString();
            }



            results.setText("");
            try
            {
                openDatabase();
                QueryDatabasefeed();
                db.close();
            }
            catch (Exception e)
            {
                Log.e("\n#Error:", " insert_To_Database: Opening database or inserting to DB " + e.getMessage());
                finish();
            }
            return null;
        }

        protected void onPostExecute(final Void unused)
        {

            for (int i =0; i< feed_data.size();i++)
            {
                results.append("***********************************************\n");
                results.append("Eartag: " + feed_data.get(i).getEartag() +"\n");
                results.append("Feed Name: " + feed_data.get(i).getFeedName() +"\n");
                results.append("Feed Amount: " + feed_data.get(i).getFeedAmounts() +"\n");
                results.append("Date given: " + feed_data.get(i).getFeedDates() +"\n");
                results.append("***********************************************\n\n");
            }


            results.append("Time Taken: "+ (System.currentTimeMillis() -startingMillis) +"msec\n\n");
        }
    }
    /*******************************************************************************************/
    private void QueryDatabaseweight()
    {

        try
        {
            String mySQL = "select * from weight where Eartag LIKE '%"+name+"%'";
            Cursor c1 = db.rawQuery(mySQL,null);
            weight_data = get_All_The_Weight(c1);
        }
        catch (Exception e)
        {
            Log.e("\n#Error: ", "QueryDatabase(): " + e.getMessage());
        }
    }

    /**********************************************************************************************/

    private List<weightData> get_All_The_Weight(Cursor cursor)
    {
        cursor.moveToPosition(-1); //reset cursor's top

        List<weightData> data = new ArrayList<>();
        try
        {
            // now get the rows
            cursor.moveToPosition(-1); //reset cursor's top
            while (cursor.moveToNext())
            {
                for (int i = 0; i < cursor.getColumnCount(); i++)
                {
                    law = cursor.getString(1);
                    irw = cursor.getString(2);
                    lpw = cursor.getString(3);


                }
                data.add(new weightData(law, irw, lpw));
            }
        }
        catch (Exception e)
        {
            Log.e("\n#Error:", "Retrieving info from database: " + e.getMessage());
        }
        return data;
    }


    /*****************************************************************************************/

    public void RetrieveFromWeight (View view)
    {
        new Query_weight_Database().execute();
    }
    private class Query_weight_Database extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            startingMillis = System.currentTimeMillis();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(search.getText().toString().equalsIgnoreCase("")) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Please Enter All Values",
                        Toast.LENGTH_SHORT);

                toast.show();
                //return;

            }
            else {
                name = search.getText().toString();
            }



            results.setText("");
            try
            {
                openDatabase();
                QueryDatabaseweight();
                db.close();
            }
            catch (Exception e)
            {
                Log.e("\n#Error:", " insert_To_Database: Opening database or inserting to DB " + e.getMessage());
                finish();
            }
            return null;
        }

        protected void onPostExecute(final Void unused)
        {
            for (int i =0; i< weight_data.size();i++)
            {
                results.append("***********************************************\n");
                results.append("Eartag: " + weight_data.get(i).getEartag() +"\n");
                results.append("Feed Name: " + weight_data.get(i).getWeight() +"\n");
                results.append("Feed Amount: " + weight_data.get(i).getWeightDates() +"\n");
                results.append("***********************************************\n\n");
            }
            results.append("Time Taken: "+ (System.currentTimeMillis() -startingMillis) +"msec\n\n");
        }
    }
    /**********************************************************************************************/
    public void RetrieveFromAll (View view)
    {

        new Query_all_Database().execute();

    }
    private class Query_all_Database extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            startingMillis = System.currentTimeMillis();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(search.getText().toString().equalsIgnoreCase("")) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Please Enter All Values",
                        Toast.LENGTH_SHORT);

                toast.show();
                //return;

            }
            else {
                name = search.getText().toString();
            }



            results.setText("");
            try
            {
                openDatabase();
                QueryDatabase();
                QueryDatabasefeed();
                QueryDatabaseweight();
                db.close();
            }
            catch (Exception e)
            {
                Log.e("\n#Error:", " insert_To_Database: Opening database or inserting to DB " + e.getMessage());
                finish();
            }
            return null;
        }

        protected void onPostExecute(final Void unused)
        {
            for (int i =0; i< doses_data.size();i++)
            {
                results.append("***********************************************\n");
                results.append("Eartag: " + doses_data.get(i).getEartag() +"\n");
                results.append("Dose Name: " + doses_data.get(i).getDoseName() +"\n");
                results.append("Dose Amount: " + doses_data.get(i).getAmounts() +"\n");
                results.append("Date given: " + doses_data.get(i).getDates() +"\n");
                results.append("Withdrawal Period: " + doses_data.get(i).getPeriod() +"\n");
                results.append("***********************************************\n\n");
            }
            for (int i =0; i< feed_data.size();i++)
            {
                results.append("***********************************************\n");
                results.append("Eartag: " + feed_data.get(i).getEartag() +"\n");
                results.append("Feed Name: " + feed_data.get(i).getFeedName() +"\n");
                results.append("Feed Amount: " + feed_data.get(i).getFeedAmounts() +"\n");
                results.append("Date given: " + feed_data.get(i).getFeedDates() +"\n");
                results.append("***********************************************\n\n");
            }

            for (int i =0; i< weight_data.size();i++)
            {
                results.append("***********************************************\n");
                results.append("Eartag: " + weight_data.get(i).getEartag() +"\n");
                results.append("Feed Name: " + weight_data.get(i).getWeight() +"\n");
                results.append("Feed Amount: " + weight_data.get(i).getWeightDates() +"\n");
                results.append("***********************************************\n\n");
            }
            results.append("Time Taken: "+ (System.currentTimeMillis() -startingMillis) +"msec\n\n");
        }
    }

}
