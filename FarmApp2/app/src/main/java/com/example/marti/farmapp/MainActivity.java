package com.example.marti.farmapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marti.farmapp.model.Weather;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Copyright (C) 2013 Surviving with Android (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class MainActivity extends Activity {


    private TextView cityText;
    private TextView condDescr;
    private TextView temp;
    private TextView press;
    private TextView windSpeed;
    private TextView windDeg;
    private TextView sunRise;
    private TextView sunSet;
    private TextView Lat;
    private TextView Longi;
    String formatted;
    String formatted1;
    String windDirec;
    private TextView hum;
    private ImageView imgView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String city = "Renmore,Galway,Ireland&appid=9574f7eb673d8328c7abb26436268bb7";

        cityText = (TextView) findViewById(R.id.cityText);
        condDescr = (TextView) findViewById(R.id.condDescr);
        temp = (TextView) findViewById(R.id.temp);
        hum = (TextView) findViewById(R.id.hum);
        press = (TextView) findViewById(R.id.press);
        windSpeed = (TextView) findViewById(R.id.windSpeed);
        windDeg = (TextView) findViewById(R.id.windDeg);
        sunRise = (TextView) findViewById(R.id.sunRise);
        sunSet = (TextView) findViewById(R.id.sunSet);
        Lat = (TextView) findViewById(R.id.Lat);
        Longi = (TextView) findViewById(R.id.Longi);
        imgView = (ImageView) findViewById(R.id.condIcon);





        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(new String[]{city});
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/


    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();






            String data = ( (new WeatherHttpClient()).getWeatherData(params[0]));

            try {
                weather = JSONWeatherParser.getWeather(data);

                // Let's retrieve the icon
                weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

                long seconds = weather.location.getSunrise();
                long millis = seconds * 1000;
                Date date = new Date(millis);
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                formatted = format.format(date);
                long seconds1 = weather.location.getSunset();
                long millis1 = seconds1 * 1000;
                Date date1 = new Date(millis1);
                DateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                formatted1 = format1.format(date1);


                float degrees = weather.wind.getDeg();
                System.out.println(degrees);
                if(degrees>=0 && degrees<=22.5){
                    windDirec = "North";
                }
                else if(degrees>=22.6 && degrees<=67.5){
                    windDirec = "NorthEast";
                }
                else if(degrees>=67.6 && degrees<=112.5){
                    windDirec = "East";
                }
                else if(degrees>=112.6 && degrees<=157.5){
                    windDirec = "SouthEast";
                }
                else if(degrees>=157.6 && degrees<=202.5){
                    windDirec = "South";
                }
                else if(degrees>=202.6 && degrees<=247.5){
                    windDirec = "SouthWest";
                }
                else if(degrees>=247.6 && degrees<=292.5){
                    windDirec = "West";
                }
                else if(degrees>=292.6 && degrees<=337.5){
                    windDirec = "NorthWest";
                }
                else if(degrees>=337.6 && degrees<=360){
                    windDirec = "North";
                }
                System.out.println(windDirec);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;

        }




        @Override
        protected void onPostExecute(Weather weather) {

            super.onPostExecute(weather);

            if (weather.iconData != null && weather.iconData.length > 0) {
                Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
                imgView.setImageBitmap(img);
            }

            cityText.setText(weather.location.getCity() + "," + weather.location.getCountry());
            condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
            temp.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "C");
            hum.setText("" + weather.currentCondition.getHumidity() + "%");
            press.setText("" + weather.currentCondition.getPressure() + " mBar");
            windSpeed.setText("" + weather.wind.getSpeed() + " mps");
            windDeg.setText("Winds from the:" + windDirec+ "");
            sunRise.setText("Sunrise: " + formatted + "");
            sunSet.setText("Sunset: " + formatted1 + "");
            Lat.setText("Latitude: " + weather.location.getLatitude() + "");
            Longi.setText("Longitude: " + weather.location.getLongitude() + "");



        }

    }

}
