package com.example.todolist;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private NoteDatabase noteDatabase;
    private int count;

    public MainViewModel(@NonNull Application application) {
        super(application);
        noteDatabase= NoteDatabase.getInstance(application);
    }

    public void showCount(){
        count++;
    }

    public int getCount() {
        return count;
    }

    public LiveData<List<Note>> getNotes(){
        return noteDatabase.noteDao().getNotes();
    }

    public void remove(Note note){
     Thread thread = new Thread(new Runnable() {
         @Override
         public void run() {
             noteDatabase.noteDao().remove(note.getId());
         }
     });    thread.start();
    }
}
