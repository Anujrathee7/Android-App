package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class WeatherActivity extends AppCompatActivity {
    private RelativeLayout homeRl;
    private TextView cityNameTV,temperatureTV,conditionTV;
    private ProgressBar loadingPB;

    private RecyclerView weatherRv;

    private ImageView backIV,iconIV;
    private ArrayList<WeatherRVModal> weatherRVModalArrayList;
    private WeatherRVAdapter weatherRVAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make the activity fullscreen

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_weather);
        // Initialize views

        homeRl = findViewById(R.id.idRlHome);
        cityNameTV = findViewById(R.id.idTVCityName);
        temperatureTV = findViewById(R.id.idTVTemperature);
        conditionTV = findViewById(R.id.idTVCondition);
        loadingPB = findViewById(R.id.idPbLoading);
        weatherRv = findViewById(R.id.idRVWeather);
        backIV = findViewById(R.id.idIVBack);
        iconIV = findViewById(R.id.idTVIcon);

        // Initialize ArrayList and Adapter for RecyclerView
        weatherRVModalArrayList = new ArrayList<>();
        weatherRVAdapter = new WeatherRVAdapter(this,weatherRVModalArrayList);
        weatherRv.setAdapter(weatherRVAdapter);

        // Get city name from intent
        Intent intent = getIntent();

        String cityName = intent.getStringExtra("cityName");

        System.out.println(cityName);



        getWeatherInfo(cityName);


    }


    // Method to fetch weather information from API

    private void getWeatherInfo(String cityName){
        // API URL for weather data

        String url = "http://api.weatherapi.com/v1/forecast.json?key=ad2bb395500e421bb24220328241204&q="+ cityName +"&days=1&aqi=yes&alerts=yes";
        Log.d("WeatherActivity", "Request URL: " + url); // Add this line to log the formed URL
        // Set city name in TextView

        cityNameTV.setText(cityName);
        // Instantiate RequestQueue

        RequestQueue requestQueue = Volley.newRequestQueue(WeatherActivity.this);
        // Request a JSON response from the provided URL

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("WeatherActivity", "Response received: " + response.toString()); // Add this line to log the response
                // Hide loading progress bar and display weather information layout

                loadingPB.setVisibility(View.GONE);
                homeRl.setVisibility(View.VISIBLE);
                weatherRVModalArrayList.clear();
                try {
                    // Retrieve current temperature, condition, and icon

                    String temperature = response.getJSONObject("current").getString("temp_c");
                    temperatureTV.setText(temperature+"°c");

                    System.out.println(response);
                    int isDay = response.getJSONObject("current").getInt("is_day");
                    String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    String conditionIcon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("http:".concat(conditionIcon)).into(iconIV);
                    conditionTV.setText(condition);
                    // Set background image based on whether it's day or night

                    if(isDay == 1){
                        Picasso.get().load("https://i.pinimg.com/564x/9f/bf/d9/9fbfd98a7b87662c0f1207e8d144995d.jpg").into(backIV);
                    }
                    else{
                        Picasso.get().load("https://i.pinimg.com/564x/9f/bf/d9/9fbfd98a7b87662c0f1207e8d144995d.jpg").into(backIV);
                    }
                    // Retrieve hourly forecast data

                    JSONObject forecastObj = response.getJSONObject("forecast");
                    JSONObject forecastO =  forecastObj.getJSONArray("forecastday").getJSONObject(0);
                    JSONArray hourArray = forecastO.getJSONArray("hour");
                    // Iterate through hourly data and add to RecyclerView

                    for (int i=0; i<hourArray.length();i++){
                        JSONObject hourObj = hourArray.getJSONObject(i);
                        String time =  hourObj.getString("time");
                        String temper =  hourObj.getString("temp_c");
                        String img =  hourObj.getJSONObject("condition").getString("icon");
                        String wind =  hourObj.getString("wind_kph");
                        weatherRVModalArrayList.add(new WeatherRVModal(time,temper,img,wind));

                    }
                    weatherRVAdapter.notifyDataSetChanged();
                } catch (JSONException e){

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // Display error message

                Log.e("WeatherActivity", "Error: " + volleyError.toString()); // Log the error
                Toast.makeText(WeatherActivity.this,"Please Give a Valid City Name",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}






