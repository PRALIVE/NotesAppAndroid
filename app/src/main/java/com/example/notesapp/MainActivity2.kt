package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import com.example.notesapp.database.MyDatabase
import com.example.notesapp.database.NotesDao
import com.example.notesapp.databinding.ActivityMain2Binding
import com.example.notesapp.noteModel.Note
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity2 : AppCompatActivity() {

    lateinit var binding : ActivityMain2Binding
    private lateinit var roomDao : NotesDao
    private var database =  MyDatabase
    private var selectedNote : Note? = null
    private var title : String = ""
    private var message : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main2)
        roomDao = database.getNoteDaoInstance(applicationContext)!!
        manageUi()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        handleNote()
    }

    private fun manageUi() {
        binding.messageField.requestFocus()
        val gson = Gson()
        selectedNote  = gson.fromJson(intent.getStringExtra("note"),Note::class.java)
        if(selectedNote != null){
            binding.titleField.setText(
                selectedNote!!.title
            )
            binding.messageField.setText(
                selectedNote!!.message
            )
            title = selectedNote!!.title
            message = selectedNote!!.message
        }
        registerListener()
        textFieldListener()
    }

    private fun registerListener() {
        binding.back.setOnClickListener {
            handleNote()
        }
    }

    private fun handleNote() {
        val type = intent.getStringExtra("type")
        if(type == "new"){
            if(title!="" || message!="") {
                val note = Note(title = title, message = message)
                CoroutineScope(Dispatchers.IO).launch{
                    val findNote = roomDao.findNoteByTitle(title,message)
                    if(findNote == null){
                        roomDao.insertNote(note)
                        finish()
                    }
                }
            }else{
                finish()
            }
        }else{
            CoroutineScope(Dispatchers.IO).launch{
                roomDao.updateNoteById(selectedNote!!.primaryKey,title,message)
                finish()
            }
        }
    }

    private fun textFieldListener() {
        binding.titleField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
                //before text change
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                 title = s.toString()
            }

            override fun afterTextChanged(s: Editable) {
                //nothing
            }
        })

        binding.messageField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
                //before text change
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                message = s.toString()
            }

            override fun afterTextChanged(s: Editable) {
                //nothing
            }
        })
    }
}