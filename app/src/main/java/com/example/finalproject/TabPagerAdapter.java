package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Map;

public class TabPagerAdapter extends FragmentStateAdapter {
    private Map<Integer, Integer> changePopulation;
    private String dataString;

    private WorkData workData;

    // Constructor

    public TabPagerAdapter(FragmentActivity fm){
        super(fm);
    }


    // Create a new fragment instance based on position

    @NonNull
    @Override
    public Fragment createFragment(int position){
        switch (position){
            case 0:
                // Create a new CityFragment instance with fetched data

                return CityFragment.newInstance(changePopulation, dataString);
            case 1:
                // Create a new QuizFragment instance

                return new QuizFragment();

        }
        return null;
    }

    // Return the number of fragments

    @Override
    public int getItemCount() {
        return 2;
    }

    // Method to set arguments for fragments
    public void setArguments(Map<Integer, Integer> changePopulation, String dataString) {
        this.changePopulation = changePopulation;
        this.dataString = dataString;
    }
}
