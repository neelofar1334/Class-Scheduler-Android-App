package com.karimi.c196.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.karimi.c196.R;
import com.karimi.c196.entities.Courses;

import java.util.ArrayList;
import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {
    private final List<Courses> mCourses;
    private final LayoutInflater mInflater;
    private final Context context;
    private final OnCourseListener listener;
    private final boolean showButtons; //for showing or hiding the edit, view, and delete buttons in the recyclerView row

    //for edit and delete buttons
    public interface OnCourseListener {
        void onEditClicked(int position); //for edit button
        void onDeleteClicked(int position); //for delete button
        void onViewClicked(int position); //for viewing course details
    }

    //constructor
    public CourseListAdapter(Context context, OnCourseListener listener, boolean showButtons) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.showButtons = showButtons;
        this.mCourses = new ArrayList<>();
        this.listener = (listener != null) ? listener : new OnCourseListener() {
            @Override
            public void onEditClicked(int position) { }
            @Override
            public void onDeleteClicked(int position) { }
            @Override
            public void onViewClicked(int position) { }
        };
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recycler_view_row, parent, false);
        return new CourseViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Courses current = mCourses.get(position);
        holder.bind(current);
        //set button visibility based on this
        if (!showButtons) {
            holder.editButton.setVisibility(View.GONE);
            holder.deleteButton.setVisibility(View.GONE);
            holder.viewButton.setVisibility(View.GONE);
        } else {
            holder.editButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setVisibility(View.VISIBLE);
            holder.viewButton.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return mCourses != null ? mCourses.size() : 0;
    }

    public void setCourses(List<Courses> courses) {
        mCourses.clear();
        if (courses != null) {
            mCourses.addAll(courses);
        }
        notifyDataSetChanged();
    }

    //for CourseList activity
    public Courses getCourseAtPosition(int position) {
        return mCourses.get(position);
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseTextView;
        private final TextView statusTextView;
        private final TextView startDateTextView;
        private final TextView endDateTextView;
        Button editButton, deleteButton, viewButton;
        private Courses currentCourse;

        //View Holder for Course List
        CourseViewHolder(View itemView, OnCourseListener listener) {
            super(itemView);
            courseTextView = itemView.findViewById(R.id.course);
            statusTextView = itemView.findViewById(R.id.status);
            startDateTextView = itemView.findViewById(R.id.startDate);
            endDateTextView = itemView.findViewById(R.id.endDate);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            viewButton = itemView.findViewById(R.id.viewButton);

            //setup click listeners
            editButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onEditClicked(position);
                }
            });

            deleteButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClicked(position);
                }
            });

            //view button click listener
            viewButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onViewClicked(position);
                }
            });

        }

        void bind(final Courses current) {
            Log.d("CourseListAdapter", "Binding course: " + current.getTitle() + ", Term: " + current.getTermId());

            courseTextView.setText(current.getTitle());
            statusTextView.setText(current.getStatus());
            startDateTextView.setText(current.getStartDate());
            endDateTextView.setText(current.getEndDate());

        }
    }
}
