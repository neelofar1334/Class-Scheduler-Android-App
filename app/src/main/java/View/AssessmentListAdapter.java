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
import com.example.c196.controllers.AssessmentDetail;
import com.example.c196.controllers.CourseDetail;
import com.example.c196.entities.Assessments;
import com.example.c196.entities.Courses;

import java.util.ArrayList;
import java.util.List;

public class AssessmentListAdapter extends RecyclerView.Adapter<AssessmentListAdapter.AssessmentViewHolder> {
    private List<Assessments> mAssessments;
    private LayoutInflater mInflater;
    private Context mContext;
    private final onAssessmentListener mListener;
    private boolean showButtons; //for showing or hiding the edit, view, and delete buttons in the recyclerView row

    //for edit and delete buttons
    public interface onAssessmentListener {
        void onEditClicked(int position);
        void onDeleteClicked(int position);
        void onViewClicked(int position); //for viewing course details
    }

    // Constructor
    public AssessmentListAdapter(Context context, List<Assessments> assessments, onAssessmentListener listener, boolean showButtons) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.showButtons = showButtons;
        mAssessments = assessments != null ? assessments : new ArrayList<>();
        mListener = listener;
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_recycler_view_row, parent, false);
        return new AssessmentViewHolder(itemView, mListener, showButtons);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        Assessments current = mAssessments.get(position);
        holder.bind(current);
    }

    @Override
    public int getItemCount() {
        return mAssessments != null ? mAssessments.size() : 0;
    }

    public void setAssessments(List<Assessments> assessments) {
        mAssessments.clear();
        if (assessments != null) {
            mAssessments.addAll(assessments);
        }
        notifyDataSetChanged();
    }

    //for AssessmentList activity
    public Assessments getAssessmentAtPosition(int position) {
        return mAssessments.get(position);
    }

    static class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView typeTextView;
        private final TextView startDateTextView;
        private final TextView endDateTextView;
        Button editButton, deleteButton, viewButton;

        // Constructor for ViewHolder
        AssessmentViewHolder(View itemView, onAssessmentListener listener, boolean showButtons) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title);
            typeTextView = itemView.findViewById(R.id.Type);
            startDateTextView = itemView.findViewById(R.id.startDate);
            endDateTextView = itemView.findViewById(R.id.endDate);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            viewButton = itemView.findViewById(R.id.viewButton);

            if (!showButtons) {
                editButton.setVisibility(View.GONE);
                deleteButton.setVisibility(View.GONE);
                viewButton.setVisibility(View.GONE);
            } else {
                setupClickListeners(listener);
            }
        }

        private void setupClickListeners(onAssessmentListener listener) {
            editButton.setOnClickListener(v -> listener.onEditClicked(getAdapterPosition()));
            deleteButton.setOnClickListener(v -> listener.onDeleteClicked(getAdapterPosition()));
            viewButton.setOnClickListener(v -> listener.onViewClicked(getAdapterPosition()));
        }

        void bind(final Assessments current) {
            titleTextView.setText(current.getTitle());
            typeTextView.setText(current.getType());
            startDateTextView.setText(current.getStartDate());
            endDateTextView.setText(current.getEndDate());

        }
    }
}
