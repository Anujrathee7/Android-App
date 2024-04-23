package com.example.finalproject;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CityFragment extends Fragment {
    private Map<Integer, Integer> changePopulation;
    private String dataString;

    public CityFragment() {
        // Required empty public constructor
    }

    public static CityFragment newInstance(Map<Integer, Integer> changePopulation, String dataString) {
        CityFragment fragment = new CityFragment();
        Bundle args = new Bundle();
        args.putSerializable("changePopulation", (Serializable) changePopulation);
        args.putString("dataString", dataString);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            changePopulation = (Map<Integer, Integer>) getArguments().getSerializable("changePopulation");
            dataString = getArguments().getString("dataString");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_city, container, false);


        // Use the data as needed
        TextView txtPopulation = view.findViewById(R.id.txtPopulation);
        TextView txtEmployment = view.findViewById(R.id.txtEmployment);
        TextView txtWorkplace = view.findViewById(R.id.txtWorkplace);

        // Prepare data for bar chart

        BarChart barChart = view.findViewById(R.id.barChart);

            ArrayList<BarEntry> changeData = new ArrayList<>();

        Map<Integer, Integer> sortedMap = new TreeMap<>(changePopulation);

// Now iterate over the entries in the sorted TreeMap
        Set<Map.Entry<Integer, Integer>> entrySet = sortedMap.entrySet();
        Iterator<Map.Entry<Integer, Integer>> iterator = entrySet.iterator();

        StringBuilder change = new StringBuilder();

// Check if there are entries in the set
        if (iterator.hasNext()) {
            // Get the first entry
            Map.Entry<Integer, Integer> previousEntry = iterator.next();

            // Iterate over the remaining entries
            while (iterator.hasNext()) {
                // Get the next entry
                Map.Entry<Integer, Integer> currentEntry = iterator.next();

                // Get the years and populations for the current and previous entries
                int previousYear = previousEntry.getKey();
                int previousPopulation = previousEntry.getValue();
                int currentYear = currentEntry.getKey();
                int currentPopulation = currentEntry.getValue();

                // Calculate the change in population
                int populationChange = currentPopulation - previousPopulation;

                changeData.add(new BarEntry(currentYear,populationChange));

                // Append the change to the StringBuilder
                change.append(populationChange).append("\n");

                // Update previous entry for the next iteration
                previousEntry = currentEntry;
            }
        }
        // Set up BarDataSet for the bar chart

        BarDataSet barDataSet = new BarDataSet(changeData,"Change in Population");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        // Set up BarData with the BarDataSet
        BarData barData = new BarData(barDataSet);

        // Configure the BarChart
        barChart.setFitBars(true);
        barChart.setData(barData);

        // Set text for TextViews
        txtPopulation.setText(dataString);

        txtEmployment.setText(String.join("\n",WorkData.getInstance().employmentRate));
        txtWorkplace.setText(String.join("\n",WorkData.getInstance().workPlaceRate));
        return view;
    }
}

