package com.example.todolist;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes = new ArrayList<>();

    private OnNoteClickLisner onNoteClickLisner;

    public void setOnNoteClick(OnNoteClickLisner onNoteClickLisner) {
        this.onNoteClickLisner = onNoteClickLisner;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public List<Note> getNotes() {
        return new ArrayList<>( notes);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.note_item,
                parent,
                false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder viewHolder, int position) {
        Note note = notes.get(position);
        viewHolder.textViewNote.setText(note.getText());

        int colorRsId;
        switch (note.getPriory()){
            case 0:
                colorRsId = android.R.color.holo_green_light;
                break;
            case 1:
                colorRsId = android.R.color.holo_orange_light;
                break;
            default:
                colorRsId = android.R.color.holo_red_light;
        }
        int color = ContextCompat.getColor(viewHolder.itemView.getContext(),colorRsId);
        viewHolder.textViewNote.setBackgroundColor(color);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNoteClickLisner != null) {
                    onNoteClickLisner.onNoteClick(note);
                }
            }
        });
    }

    @Override
    public int getItemCount() {   return notes.size();}

        class NoteViewHolder extends RecyclerView.ViewHolder{
            private TextView textViewNote;
            public NoteViewHolder(@NonNull View itemView) {
                super(itemView);
                textViewNote = itemView.findViewById(R.id.textViewNote);
            }
        }
        interface OnNoteClickLisner{
        void onNoteClick(Note note);
        }
}

