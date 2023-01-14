package com.example.todolist;

import static androidx.recyclerview.widget.ItemTouchHelper.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

//первое действие в активити
    private RecyclerView recyclerViewNote;
    private FloatingActionButton actionAddNote;
    private NoteAdapter notesAdapter;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inirViews(); //третье действие в активити

        viewModel = new MainViewModel(getApplication());

        notesAdapter = new NoteAdapter();
        notesAdapter.setOnNoteClick(new NoteAdapter.OnNoteClickLisner() {
            @Override
            public void onNoteClick(Note note) {
                viewModel.showCount();
                int count = viewModel.getCount();
                Toast.makeText(
                        MainActivity.this,
                        String.valueOf(count),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        recyclerViewNote.setAdapter(notesAdapter);

        viewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                notesAdapter.setNotes(notes);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new SimpleCallback(
                        0,
                        RIGHT | LEFT) {
                    @Override
                    public boolean onMove(
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(
                            @NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Note note = notesAdapter.getNotes().get(position);
                        viewModel.remove(note);

                    }
                });
        itemTouchHelper.attachToRecyclerView(recyclerViewNote);

        actionAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddNoteActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void inirViews()
    {
        recyclerViewNote = findViewById(R.id.recyclerViewNote);
        actionAddNote = findViewById(R.id.actionAddNote);

    }
}