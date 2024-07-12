package com.example.c196.Utility;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.R;
import com.example.c196.controllers.AssessmentDetail;
import com.example.c196.controllers.CourseDetail;
import com.example.c196.controllers.TermDetail;

import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private List<SearchResult> searchResults;
    private Context context;

    public SearchResultsAdapter(List<SearchResult> searchResults, Context context) {
        this.searchResults = searchResults;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchResult searchResult = searchResults.get(position);
        holder.textView.setText(searchResult.getTitle());

        holder.itemView.setOnClickListener(v -> {
            Intent intent;
            switch (searchResult.getType()) {
                case "Course":
                    intent = new Intent(context, CourseDetail.class);
                    intent.putExtra("courseId", searchResult.getId());
                    break;
                case "Assessment":
                    intent = new Intent(context, AssessmentDetail.class);
                    intent.putExtra("assessmentId", searchResult.getId());
                    break;
                case "Term":
                    intent = new Intent(context, TermDetail.class);
                    intent.putExtra("termId", searchResult.getId());
                    break;
                default:
                    throw new IllegalArgumentException("Invalid search result type");
            }
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
