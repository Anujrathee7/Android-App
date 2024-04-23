package com.example.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<String> searchList;

    // Constructor to initialize the adapter with a list of search items
    public SearchAdapter(List<String> searchList) {
        this.searchList = searchList;
    }
    // Create new views (invoked by the layout manager)

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for a search item view

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }
    // Replace the contents of a view (invoked by the layout manager)

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position
        // Replace the contents of the view with that element
        String searchItem = searchList.get(position);
        holder.textViewSearchItem.setText(searchItem);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSearchItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the TextView from the layout

            textViewSearchItem = itemView.findViewById(R.id.textViewSearchItem);
        }
    }
}
