package com.example.todolist;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")

public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;         //поля заметок
    private String text;
    private int priory;

    public Note(int id, String text, int priory) {
        this.id = id;
        this.text = text;
        this.priory = priory;
    }

    @Ignore
    public Note(String text, int priory) {
        this.text = text;
        this.priory = priory;
    }
    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getPriory() {
        return priory;
    }
}
