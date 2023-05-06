package com.example.notesapp.noteModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) var primaryKey : Int = 0,
    @ColumnInfo(name = "title") var title : String = "",
    @ColumnInfo(name = "message") var message : String = ""
)
