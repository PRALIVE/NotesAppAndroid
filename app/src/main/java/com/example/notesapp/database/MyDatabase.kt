package com.example.notesapp.database

import android.content.Context
import androidx.room.Room


object MyDatabase {

    var noteDao : NotesDao? = null
    var count = 0;

    fun getNoteDaoInstance(context : Context) : NotesDao? {
        var dao : NotesDao?
        if(noteDao == null){
            //create the instance
            val room  = Room.databaseBuilder(context,NoteDatabase::class.java,"SavedNotes").fallbackToDestructiveMigration().build()
            noteDao = room.NotesDao()
            dao = noteDao
        }
        else{
            dao = noteDao
        }
        return dao
    }
}