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
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import java.util.*
import kotlin.collections.ArrayList

class NoteViewAdapter(
    private var notes: ArrayList<Note>,
    private val onItemClickListener: OnNoteClickListener
) :
    RecyclerView.Adapter<NoteViewHolder>(), Filterable, NoteTouchHelper {

    //Copy of note List
    private val noteListFull = ArrayList<Note>(notes)

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
        holder.onNoteCLickListener(onItemClickListener, position)
    }

    /**Set array of notes*/
    fun setListOfNotes(notes: ArrayList<Note>) {
        this.notes = notes
    }

    override fun getFilter(): Filter {
        return noteFilter
    }

    private val noteFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredNotes = ArrayList<Note>()
            if (constraint == null) {
                filteredNotes.addAll(noteListFull)
            } else {
                val filterPattern = constraint.toString().toLowerCase(Locale.ENGLISH).trim()

                for (note in noteListFull) {
                    if (note.title.toLowerCase(Locale.ENGLISH).contains(filterPattern))
                        filteredNotes.add(note)
                }
            }

            val result = FilterResults()
            result.values = filteredNotes
            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            notes.clear()
            notes.addAll(results!!.values as ArrayList<Note>)
            notifyDataSetChanged()
        }
    }

    override fun onNoteMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(notes, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onNoteSwipe(position: Int) {
        val note = notes[position]
        notes.remove(note)
        notifyItemRemoved(position)
    }
}