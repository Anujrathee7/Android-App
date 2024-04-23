package com.example.finalproject;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

// This is vew Holder for the SearchAdapter
public class SearchViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewSearchItem;

    public SearchViewHolder(View itemView) {
        super(itemView);
        textViewSearchItem = itemView.findViewById(R.id.textViewSearchItem);
    }
}

