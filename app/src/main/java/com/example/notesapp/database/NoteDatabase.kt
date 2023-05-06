package com.example.notesapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notesapp.noteModel.Note

@Database(entities = [Note::class], version = 2, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun NotesDao() : NotesDao
}