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
import com.example.c196.controllers.TermDetail;
import com.example.c196.entities.Courses;
import com.example.c196.entities.Terms;
import com.example.c196.viewmodel.TermViewModel;

import java.util.List;

public class TermListAdapter extends RecyclerView.Adapter<TermListAdapter.TermViewHolder> {
    private List<Terms> mTerms;
    private LayoutInflater mInflater;
    private Context context;
    private OnTermListener listener;

    //for edit and delete buttons
    public interface OnTermListener {
        void onEditClicked(int position);
        void onDeleteClicked(int position);
    }

    // Constructor
    public TermListAdapter(Context context, List<Terms> terms, OnTermListener listener) {
        this.context = context;
        this.mTerms = terms;
        this.mInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_recycler_view_row, parent, false);
        return new TermViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        Terms current = mTerms.get(position);
        holder.bind(current);
    }

    @Override
    public int getItemCount() {
        return mTerms != null ? mTerms.size() : 0;
    }

    public void setTerms(List<Terms> terms) {
        mTerms.clear();
        mTerms.addAll(terms);
        notifyDataSetChanged();
    }

    //for TermList activity
    public Terms getTermAtPosition(int position) {
        return mTerms.get(position);
    }

    static class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView startDateTextView;
        private final TextView endDateTextView;
        Button editButton, deleteButton;

        //View Holder for Term List
        TermViewHolder(View itemView, OnTermListener listener) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title);
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

        void bind(final Terms current) {
            Log.d("TermListAdapter", "Binding term: " + current.getTitle());

            titleTextView.setText(current.getTitle());
            startDateTextView.setText(current.getStartDate());
            endDateTextView.setText(current.getEndDate());

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(v.getContext(), TermDetail.class);
                    intent.putExtra("termId", current.getTermID());
                    intent.putExtra("title", current.getTitle());
                    intent.putExtra("start date", current.getStartDate());
                    intent.putExtra("end date", current.getEndDate());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}

