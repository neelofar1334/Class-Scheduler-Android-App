package View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.c196.R;
import com.example.c196.controllers.TermDetail;
import com.example.c196.entities.Terms;
import com.example.c196.viewmodel.TermViewModel;

import java.util.List;

public class TermListAdapter extends RecyclerView.Adapter<TermListAdapter.TermViewHolder> {
    private List<Terms> mTerms;
    private LayoutInflater mInflater;
    private Context context;

    // Constructor
    public TermListAdapter(Context context, List<Terms> terms) {
        this.context = context;
        this.mTerms = terms;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_recycler_view_row, parent, false);
        return new TermViewHolder(itemView, context);
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

    static class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView startDateTextView;
        private final TextView endDateTextView;
        private final Context context;

        //View Holder for Term List
        TermViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            titleTextView = itemView.findViewById(R.id.title);
            startDateTextView = itemView.findViewById(R.id.startDate);
            endDateTextView = itemView.findViewById(R.id.endDate);
        }

        void bind(final Terms current) {
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

