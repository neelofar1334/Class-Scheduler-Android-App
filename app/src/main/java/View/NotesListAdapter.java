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
import com.example.c196.entities.Notes;
import java.util.ArrayList;
import java.util.List;


public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteViewHolder> {
    private List<Notes> mNotes;
    private LayoutInflater mInflater;
    private Context mContext;
    private NoteActionsListener mListener;
    private boolean showButtons;

    public interface NoteActionsListener {
        void onDeleteClicked(int position);
        void onViewClicked(int position);
        void onShareClicked(int position);
    }

    public NotesListAdapter(Context context, List<Notes> notes, NoteActionsListener listener, boolean showButtons) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mNotes = notes;
        mListener = listener;
        this.showButtons = showButtons;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.notes_recycler_view_row, parent, false);
        return new NoteViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Notes note = mNotes.get(position);
        holder.noteTitle.setText(note.getNoteTitle());
        holder.bind(note, mContext);
    }


    @Override
    public int getItemCount() {
        if (mNotes != null)
            return mNotes.size();
        else
            return 0;
    }

    public Notes getNoteAt(int position) {
        if (position >= 0 && position < mNotes.size()) {
            return mNotes.get(position);
        }
        return null;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteTitle;
        private Button shareButton, deleteButton, viewButton;

        NoteViewHolder(View itemView, NoteActionsListener listener) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.noteTitle);
            shareButton = itemView.findViewById(R.id.shareButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            viewButton = itemView.findViewById(R.id.viewButton);

            setupListeners(listener);
        }

        private void setupListeners(NoteActionsListener listener) {
            shareButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onShareClicked(getAdapterPosition());
                }
            });
            deleteButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDeleteClicked(getAdapterPosition());
                }
            });
            viewButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onViewClicked(getAdapterPosition());
                }
            });
        }

        void bind(Notes note, Context context) {
            noteTitle.setText(note.getNoteTitle());
        }
    }

    public void setNotes(List<Notes> notes) {
        mNotes = notes;
        notifyDataSetChanged();
    }
}

