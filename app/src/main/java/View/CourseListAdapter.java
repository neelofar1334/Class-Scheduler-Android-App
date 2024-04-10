package View;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.R;
import com.example.c196.controllers.CourseDetail;
import com.example.c196.entities.Courses;

import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {
    private List<Courses> mCourses;
    private LayoutInflater mInflater;
    private Context context;
    private OnCourseListener listener;

    //for edit and delete buttons
    public interface OnCourseListener {
        void onEditClicked(int position);
        void onDeleteClicked(int position);
    }

    // Constructor
    public CourseListAdapter(Context context, List<Courses> courses, OnCourseListener listener) {
        this.context = context;
        this.mCourses = courses;
        this.mInflater = LayoutInflater.from(context);
        this.listener = listener;
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
    }

    @Override
    public int getItemCount() {
        return mCourses != null ? mCourses.size() : 0;
    }

    public void setCourses(List<Courses> courses) {
        mCourses.clear();
        mCourses.addAll(courses);
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
        Button editButton, deleteButton;

        //View Holder for Course List
        CourseViewHolder(View itemView, OnCourseListener listener) {
            super(itemView);
            courseTextView = itemView.findViewById(R.id.course);
            statusTextView = itemView.findViewById(R.id.status);
            startDateTextView = itemView.findViewById(R.id.startDate);
            endDateTextView = itemView.findViewById(R.id.endDate);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

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
        }

        void bind(final Courses current) {
            Log.d("CourseListAdapter", "Binding course: " + current.getTitle() + ", Term: " + current.getTermName());

            courseTextView.setText(current.getTitle());
            statusTextView.setText(current.getStatus());
            startDateTextView.setText(current.getStartDate());
            endDateTextView.setText(current.getEndDate());

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(v.getContext(), CourseDetail.class);
                    intent.putExtra("course", current.getTitle());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("start date", current.getStartDate());
                    intent.putExtra("end date", current.getEndDate());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
