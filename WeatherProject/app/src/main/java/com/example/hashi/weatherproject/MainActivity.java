package com.example.hashi.weatherproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kwabenaberko.openweathermaplib.Lang;
import com.kwabenaberko.openweathermaplib.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;

public class MainActivity extends AppCompatActivity {

    private Context context;
    OpenWeatherMapHelper helper = new OpenWeatherMapHelper();
    String TAG;
    private TextView tvDescription, tvTemperature, tvWindSpeed, tvCity, tvCountry;
    private String description, temperature, windSpeed, city, country;
    private Button btnSave;
    private EditText etZipCode, etCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        linkViews();

        //an api key requested from Open weather map
        helper.setApiKey(getString(R.string.OPEN_WEATHER_MAP_API_KEY));

        //this will set the temperature unit to fahrenheit
        helper.setUnits(Units.IMPERIAL);
        helper.setLang(Lang.ENGLISH);
    }

    private void getWeatherByZipCode(String zip){
        helper.getCurrentWeatherByZipCode(zip, new OpenWeatherMapHelper.CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                Log.v(TAG,
                        "Coordinates: " + currentWeather.getCoord().getLat() + ", "+currentWeather.getCoord().getLon() +"\n"
                                +"Weather Description: " + currentWeather.getWeatherArray().get(0).getDescription() + "\n"
                                +"Max Temperature: " + currentWeather.getMain().getTempMax()+"\n"
                                +"Wind Speed: " + currentWeather.getWind().getSpeed() + "\n"
                                +"City, Country: " + currentWeather.getName() + ", " + currentWeather.getSys().getCountry()
                );

                //assigning value to global variables which will be send as parametr to the alert dialog
                description = currentWeather.getWeatherArray().get(0).getDescription();
                temperature = String.valueOf(currentWeather.getMain().getTempMax());
                windSpeed = String.valueOf(currentWeather.getWind().getSpeed());
                city = currentWeather.getName();
                country = currentWeather.getSys().getCountry();

                //call alert dialog
                loadWeather(description, temperature, windSpeed, city, country);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.v(TAG, throwable.getMessage());
            }
        });
    }

    private void getWeatherByCityName(String citySearch){
        helper.getCurrentWeatherByCityName(citySearch, new OpenWeatherMapHelper.CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                Log.v(TAG,
                        "Coordinates: " + currentWeather.getCoord().getLat() + ", "+currentWeather.getCoord().getLon()+"\n"
                                +"Weather Description: " + currentWeather.getWeatherArray().get(0).getDescription() + "\n"
                                +"Max Temperature: " + currentWeather.getMain().getTempMax()+"\n"
                                +"Wind Speed: " + currentWeather.getWind().getSpeed() + "\n"
                                +"City, Country: " + currentWeather.getName() + ", " + currentWeather.getSys().getCountry()
                );

                description = currentWeather.getWeatherArray().get(0).getDescription();
                temperature = String.valueOf(currentWeather.getMain().getTempMax());
                windSpeed = String.valueOf(currentWeather.getWind().getSpeed());
                city = currentWeather.getName();
                country = currentWeather.getSys().getCountry();

                loadWeather(description, temperature, windSpeed, city, country);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.v(TAG, throwable.getMessage());
                Toast.makeText(MainActivity.this, "Out of range", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void linkViews() {
        etZipCode = findViewById(R.id.etZipCode);
        etCity = findViewById(R.id.etCity);
    }

    public void loadWeather(final String description, final String temperature, final String windspeed, final String city, final String country) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.alert_layout);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //accessing widgets of alert dialog
        tvTemperature = alertDialog.findViewById(R.id.tvTemperature);
        tvDescription = alertDialog.findViewById(R.id.tvDescription);
        tvWindSpeed = alertDialog.findViewById(R.id.tvWindSpeed);
        tvCity = alertDialog.findViewById(R.id.tvCity);
        tvCountry = alertDialog.findViewById(R.id.tvCountry);

        //assigning values to widgets of alert dialog
        tvDescription.setText(description);
        tvTemperature.setText(temperature);
        tvWindSpeed.setText(windspeed);
        tvCity.setText(city);
        tvCountry.setText(country);

        //below code will save the temperature details into SQLite database
        btnSave = alertDialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppDataBase appDataBase = new AppDataBase(context);
                appDataBase.open();
                appDataBase.insertWeather(description, temperature, windspeed, city, country);
                appDataBase.close();
                alertDialog.dismiss();
            }
        });
    }

    //it will check whether the city field is empty otherwise call the getWeatherByCityName to load temperature details
    public void loadCity(View view) {
        String city = etCity.getText().toString();
        if (TextUtils.isEmpty(city)){
            Toast.makeText(context, "City is empty", Toast.LENGTH_SHORT).show();
        }else {
            getWeatherByCityName(city);
        }
    }

    //it will check whether the zip field is empty otherwise call the getWeatherByZipCode to load temperature details
    public void loadZip(View view) {
        String zip = etZipCode.getText().toString();
        if (TextUtils.isEmpty(zip)){
            Toast.makeText(context, "Zip is empty", Toast.LENGTH_SHORT).show();
        }else {
            getWeatherByZipCode(zip);
        }
    }

    //open a fragment that contains recyler view its its layout
    public void openDatabase(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new FragmentWeather();
        fragmentTransaction.replace(R.id.containerFragment, fragment);
        fragmentTransaction.addToBackStack("close");
        fragmentTransaction.commit();
    }
}
