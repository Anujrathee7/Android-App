package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Map;

public class InformationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_information_quiz);

        // Get the data from the intent
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            // Retrieve the HashMap and the String from the bundle
            Map<Integer, Integer> changePopulation = (HashMap<Integer, Integer>) bundle.getSerializable("hashMap");
            String dataString = bundle.getString("dataString");
            if (dataString == null){
                finish();
                Toast.makeText(this, "Enter a Valid City Name", Toast.LENGTH_SHORT).show();
            }

            // Set up ViewPager2 and TabLayout
            FragmentManager fragmentManager = getSupportFragmentManager();
            ViewPager2 viewPager = findViewById(R.id.viewArea);
            TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(this);
            viewPager.setAdapter(tabPagerAdapter);

            TabLayout tabLayout = findViewById(R.id.TabLayout);
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {}

                @Override
                public void onTabReselected(TabLayout.Tab tab) {}
            });

            // Pass data to the adapter
            tabPagerAdapter.setArguments(changePopulation, dataString);
        }

    }
}
