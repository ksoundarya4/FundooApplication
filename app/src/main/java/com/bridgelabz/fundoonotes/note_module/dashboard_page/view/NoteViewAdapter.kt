/**
 * Fundoo Notes
 * @description NoteViewAdapter class extends RecyclerView.Adapter
 * which holds functions that deals with implementation of RecyclerView.
 * @file NoteViewAdapter.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 15/02/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note

class NoteViewAdapter(
    private var notes: ArrayList<Note>,
    private val onItemClickListener: OnNoteClickListener
) :
    RecyclerView.Adapter<NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.note_list, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bindNote(note)
        holder.onNoteCLickListener(onItemClickListener,position)
    }

    /**Set array of notes*/
    fun setListOfNotes(notes: ArrayList<Note>) {
        this.notes = notes
    }
}