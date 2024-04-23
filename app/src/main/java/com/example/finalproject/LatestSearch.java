package com.example.finalproject;

import java.util.ArrayList;
import java.util.List;

public class LatestSearch {

    private List<String> searches = new ArrayList<>();
    private static LatestSearch latestSearch = null;

    private LatestSearch() {
    }

    public static LatestSearch getInstance() {
        if (latestSearch == null) {
            latestSearch = new LatestSearch();
        }
        return latestSearch;
    }

    public List<String> getSearches() {
        return searches;
    }

    public void addSearch(String city) {
        searches.add(0,city);
        if (searches.size() > 5) {
            searches.remove(0); // Remove the oldest search if more than 5
        }
        System.out.println(searches);
    }
}
