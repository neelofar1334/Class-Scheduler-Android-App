package com.example.c196.Utility;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.R;
import com.example.c196.Utility.SearchResult;

import java.util.ArrayList;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.SearchResultViewHolder> {

    private ArrayList<SearchResult> searchResults;

    public SearchResultsAdapter(ArrayList<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        SearchResult searchResult = searchResults.get(position);
        holder.textView.setText(searchResult.getType() + ": " + searchResult.getTitle());
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public static class SearchResultViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public SearchResultViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
