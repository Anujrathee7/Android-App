package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchAdapter adapter;

    private ArrayList<MunicipalityData> check;
    private EditText editCityName;
    private Button searchBtn;

    private Button weatherBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView for displaying latest search history

        recyclerView = findViewById(R.id.rvlatestSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter with latest search data

        adapter = new SearchAdapter(LatestSearch.getInstance().getSearches());
        recyclerView.setAdapter(adapter);

        // Initialize UI elements

        editCityName = findViewById(R.id.editCityName);
        searchBtn = findViewById(R.id.searchBtn);
        weatherBtn = findViewById(R.id.weatherBtn);

        // Set click listener for search button

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input city name

                String cityName = editCityName.getText().toString().trim();
                MunicipalityDataRetriever municipalityDataRetriever = new MunicipalityDataRetriever();

                // Here we fetch the municipality data in a background service, so that we do not disturb the UI
                ExecutorService service = Executors.newSingleThreadExecutor();
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        MunicipalityDataRetriever.getMunicipalityCodesMap();
                        ArrayList<MunicipalityData> municipalityDataArrayList = municipalityDataRetriever.getData(MainActivity.this, cityName);
                        municipalityDataRetriever.getWorkplaceandEmploymentData(MainActivity.this,cityName);
                        check = municipalityDataArrayList;
                        if (municipalityDataArrayList == null) {
                            return;
                        }

                        // When we want to update values we got from the API to the UI, we must do it inside runOnUiThread -method
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String dataString = "";
                                Map<Integer, Integer> changePopulation = new HashMap<>();
                                for (MunicipalityData data : municipalityDataArrayList) {

                                    if (data.getYear() > 2014) {
                                        changePopulation.put(data.getYear(), data.getPopulation());
                                    }

                                    if (data.getYear() > 2015) {
                                        dataString = dataString + data.getYear() + ": " + data.getPopulation() + "\n";

                                    }
                                }
                                // Prepare intent to start InformationActivity and pass data to it

                                Intent intent = new Intent(MainActivity.this,InformationActivity.class);
                                Bundle bundle = new Bundle();

                                bundle.putSerializable("hashMap",(Serializable) changePopulation);
                                bundle.putString("dataString",dataString);
                                intent.putExtras(bundle);

                                startActivity(intent);

                            }
                        });
                    }
                });

                // Check if city name is empty

                if (!cityName.isEmpty()) {
                    LatestSearch.getInstance().addSearch(cityName);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a city name", Toast.LENGTH_SHORT).show();
                }

                if(check == null){
                    Toast.makeText(MainActivity.this, "Enter a Valid City Name", Toast.LENGTH_SHORT).show();
                }
                // Start InformationActivity

                else{
                    Intent intent = new Intent(MainActivity.this,InformationActivity.class);
                    startActivity(intent);
                }
            }
        });
        // Set click listener for weather button

        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start WeatherActivity and pass city name as extra

                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);

                intent.putExtra("cityName",editCityName.getText().toString().trim());

                startActivity(intent);
            }
        });


    }
}