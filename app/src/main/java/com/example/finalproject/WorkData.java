package com.example.finalproject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;

public class WorkData{
    public ArrayList<String> workPlaceRate;
    public String[] employmentRate;

    public static WorkData workData = null;

    public WorkData(){
    }

    public static WorkData getInstance(){
        if (workData == null){
            workData = new WorkData();
        }
        return workData;
    }

    public void setArray(ArrayList<String> workPlaceRate, String[] employmentRate){
        this.workPlaceRate = workPlaceRate;
        this.employmentRate = employmentRate;
    }


}
