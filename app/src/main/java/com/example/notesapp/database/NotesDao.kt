package com.example.notesapp.database

import androidx.room.*
import com.example.notesapp.noteModel.Note

@Dao
interface NotesDao {

    @Query("SELECT * FROM Note")
    fun getNotes() : List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(data: Note)

    @Query("SELECT * FROM Note WHERE primaryKey = :noteId")
    fun getNoteById(noteId: Int): Note?

    @Query("SELECT * FROM Note WHERE title = :title AND message = :message")
    fun findNoteByTitle(title : String, message : String) : Note?

    @Update
    fun updateNote(user: Note)

    @Delete
    fun delete(article:Note)

    @Query("UPDATE Note SET title = :newTitle, message = :newContent WHERE primaryKey = :noteId")
    fun updateNoteById(noteId: Int, newTitle: String, newContent: String)
}