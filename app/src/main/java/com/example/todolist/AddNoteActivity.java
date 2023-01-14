package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextNote;
    private RadioButton r_b_Low;
    private RadioButton r_b_Medium;
    private Button buttonSave;

    private NoteDatabase noteDatabase;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        initViews();
        noteDatabase = NoteDatabase.getInstance(getApplication());

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }

    private void saveNote(){
        String text = editTextNote.getText().toString().trim();
        int priotity = getPriority();
        Note note = new Note( text,priotity);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                noteDatabase.noteDao().add(note);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
            }
        });
        thread.start();
    }

    private int getPriority(){
        int priority;
        if (r_b_Low.isChecked()){
            priority = 0;
        } else if (r_b_Medium.isChecked()){
            priority = 1;
        }else {
            priority = 2;
        }return priority;
    }

    public static Intent newIntent(Context context){
        return new Intent(context, AddNoteActivity.class);
    }

    protected void initViews(){
        editTextNote = findViewById(R.id.editTextNote);
        r_b_Low = findViewById(R.id.r_b_Low);
        r_b_Medium= findViewById(R.id.r_b_Medium);
        buttonSave= findViewById(R.id.buttonSave);
    }
}