package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.notesapp.adapter.NotesAdapter
import com.example.notesapp.database.MyDatabase
import com.example.notesapp.database.NotesDao
import com.example.notesapp.databinding.ActivityMainBinding
import com.example.notesapp.noteModel.Note
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    private lateinit var roomDao : NotesDao
    private var database =  MyDatabase
    var notesList = ArrayList<Note>()
    lateinit var notesAdapter : NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        roomDao = database.getNoteDaoInstance(applicationContext)!!
        setAdapter()
        getSavedNotes()
        manageUi()
    }

    private fun manageUi() {
        binding.addImage.setOnClickListener {
            addNewNote()
        }
    }

    private fun setAdapter() {
        notesAdapter = NotesAdapter(this)
        binding.recyclerView.adapter = notesAdapter
    }

    private fun getSavedNotes() {
        CoroutineScope(Dispatchers.IO).launch{
            notesList = roomDao.getNotes() as ArrayList<Note>
            notesAdapter.differ.submitList(notesList)
            runOnUiThread{handleList()}
            Log.d("listinroom","${notesList.toString()}")
        }
    }

    private fun handleList() {
        if(notesList.size == 0){
            binding.noNotes.visibility = View.VISIBLE
        }else{
            binding.noNotes.visibility = View.GONE
        }
    }

    private fun addNewNote(){
        val intent = Intent(this,MainActivity2::class.java).putExtra("type","new")
        startActivity(intent)
    }

    fun editNote(note : Note){
        val gson = Gson()
        val selectedNote : String = gson.toJson(note)
        val intent = Intent(this,MainActivity2::class.java).putExtra("note",selectedNote).putExtra("type","edit")
        startActivity(intent)
    }

    fun deleteNote(note : Note){
        CoroutineScope(Dispatchers.IO).launch{
            roomDao.delete(note)
        }
            getSavedNotes()
    }

    override fun onResume() {
        super.onResume()
        getSavedNotes()
    }
}