package View;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.R;
import com.example.c196.controllers.AssessmentDetail;
import com.example.c196.entities.Assessments;

import java.util.ArrayList;
import java.util.List;

public class AssessmentListAdapter extends RecyclerView.Adapter<AssessmentListAdapter.AssessmentViewHolder> {
    private List<Assessments> mAssessments = new ArrayList<>();
    private final LayoutInflater mInflater;
    private final Context context;

    // Constructor
    public AssessmentListAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        setHasStableIds(true); //because each assessment has a unique id, needed for recyclerView
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_recycler_view_row, parent, false);
        return new AssessmentViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentListAdapter.AssessmentViewHolder holder, int position) {
        Log.d("AssessmentListAdapter", "Binding position: " + position);
        if (!mAssessments.isEmpty()) {
            Assessments current = mAssessments.get(position);
            holder.bind(current);
        } else {
            Log.d("AssessmentListAdapter", "No data available to bind.");
        }
    }

    @Override
    public long getItemId(int position) {
        return mAssessments.get(position).getAssessmentId();
    }

    @Override
    public int getItemCount() {
        int count = mAssessments != null ? mAssessments.size() : 0;
        Log.d("AssessmentListAdapter", "Item count: " + count);
        return count;
    }

    public void setAssessments(List<Assessments> assessments) {
        this.mAssessments = assessments;
        notifyDataSetChanged();
    }

    static class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView typeTextView;
        private final TextView startDateTextView;
        private final TextView endDateTextView;
        private final Context context;

        // Constructor for ViewHolder
        AssessmentViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context; // Set context
            titleTextView = itemView.findViewById(R.id.title);
            typeTextView = itemView.findViewById(R.id.Type);
            startDateTextView = itemView.findViewById(R.id.startDate);
            endDateTextView = itemView.findViewById(R.id.endDate);
        }

        void bind(final Assessments current) {
            titleTextView.setText(current.getTitle());
            typeTextView.setText(current.getType());
            startDateTextView.setText(current.getStartDate());
            endDateTextView.setText(current.getEndDate());

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(context, AssessmentDetail.class);
                    intent.putExtra("assessmentId", current.getAssessmentId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
