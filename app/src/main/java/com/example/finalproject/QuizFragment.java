package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizFragment extends Fragment {
    private Map<Integer, String> correctAnswers; // Map to store correct answers (question index -> correct option)
    private int score;

    public QuizFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_quiz, container, false);
        ListView listView = view.findViewById(R.id.listView);
        final Button submitButton = view.findViewById(R.id.submitButton);

        // Initialize correct answers
        correctAnswers = new HashMap<>();
        correctAnswers.put(0, WorkData.getInstance().employmentRate[0]); // For question 1, correct answer is Option 1
        correctAnswers.put(1, WorkData.getInstance().workPlaceRate.get(0));
        correctAnswers.put(2, WorkData.getInstance().employmentRate[1]);
        correctAnswers.put(3, WorkData.getInstance().workPlaceRate.get(1));
        correctAnswers.put(4, WorkData.getInstance().employmentRate[2]);
        correctAnswers.put(5, WorkData.getInstance().workPlaceRate.get(2));
        // Add more correct answers as needed

        // Create some sample questions with options
        List<MCQItem> mcqItems = new ArrayList<>();
        mcqItems.add(new MCQItem("What is the Employment rate of the city in 2016", new String[]{WorkData.getInstance().employmentRate[0], "67.1", "66.1"}));
        mcqItems.add(new MCQItem("What is the Workplace Self Sufficiency rate of the city in 2016", new String[]{"107.1", "106.1", WorkData.getInstance().workPlaceRate.get(0)}));
        mcqItems.add(new MCQItem("What is the Employment rate of the city in 2017", new String[]{WorkData.getInstance().employmentRate[1], "69.5", "62.4"}));
        mcqItems.add(new MCQItem("What is the Workplace Self Sufficiency rate of the city in 2017", new String[]{"105.1", WorkData.getInstance().workPlaceRate.get(1), "110.1"}));
        mcqItems.add(new MCQItem("What is the Employment rate of the city in 2018", new String[]{WorkData.getInstance().employmentRate[2], "63.7", "61.8"}));
        mcqItems.add(new MCQItem("What is the Workplace Self Sufficiency rate of the city in 2018", new String[]{WorkData.getInstance().workPlaceRate.get(2), "117.1", "103.1"}));


        // Add more questions as needed

        // Set up the adapter for the ListView
        ArrayAdapter<MCQItem> adapter = new ArrayAdapter<MCQItem>(getContext(), R.layout.list_item_mcq, mcqItems) {
            @Override
            public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_mcq, parent, false);
                }

                // Get the current MCQItem
                final MCQItem mcqItem = getItem(position);

                // Bind data to views
                TextView questionTextView = convertView.findViewById(R.id.questionTextView);
                questionTextView.setText(mcqItem.getQuestion());

                final RadioGroup optionsRadioGroup = convertView.findViewById(R.id.optionsRadioGroup);
                optionsRadioGroup.removeAllViews();
                for (final String option : mcqItem.getOptions()) {
                    RadioButton radioButton = new RadioButton(getContext());
                    radioButton.setText(option);
                    optionsRadioGroup.addView(radioButton);

                    // Set up listener for radio button selection
                    radioButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Check if the selected option is correct
                            if (option.equals(correctAnswers.get(position))) {
                                score++; // Increase score if the answer is correct
                                Toast.makeText(getContext(), "Correct! Score: " + score, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Incorrect", Toast.LENGTH_SHORT).show();
                            }

                            // Disable other options after selection
                            for (int i = 0; i < optionsRadioGroup.getChildCount(); i++) {
                                optionsRadioGroup.getChildAt(i).setEnabled(false);
                            }
                        }
                    });
                }

                return convertView;
            }
        };
        listView.setAdapter(adapter);

        // Submit button click listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display final score
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}