package com.example.notesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.MainActivity
import com.example.notesapp.R
import com.example.notesapp.databinding.NotesLayoutBinding
import com.example.notesapp.noteModel.Note

class NotesAdapter(val activity: MainActivity) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: NotesLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun setData(note : Note){
            binding.title.text = note.title
            binding.message.text = note.message
        }
    }

    private val diffUtilCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.primaryKey == newItem.primaryKey
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return false
        }
    }

    val differ = AsyncListDiffer(this, diffUtilCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : NotesLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.notes_layout,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])

        holder.binding.title.setOnClickListener {
            activity.editNote(differ.currentList[position])
        }
        holder.binding.message.setOnClickListener {
            activity.editNote(differ.currentList[position])
        }

        holder.binding.delete.setOnClickListener {
            activity.deleteNote(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}