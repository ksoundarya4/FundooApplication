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
    private val onItemClickListener: RecyclerClickListener
) :
    RecyclerView.Adapter<NoteViewHolder>() {

//    private var selectedItemsIds = SparseBooleanArray()

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
        holder.bindNote(note, onItemClickListener)
    }

    /**Set array of notes*/
    fun setListOfNotes(notes: ArrayList<Note>) {
        this.notes = notes
    }
//
//    /**Toggle selection function*/
//    fun toggleSelection(position: Int) {
//        selectView(position = position, value = !selectedItemsIds.get(position))
//    }
//
//    /**Remove selected selection */
//    fun removeSelection() {
//        selectedItemsIds = SparseBooleanArray()
//        notifyDataSetChanged()
//    }
//
//    /**Put or delete selected item view position
//     * into SparseBooleanArray*/
//    private fun selectView(position: Int, value: Boolean) {
//        if (value)
//            selectedItemsIds.put(position, value)
//        else
//            selectedItemsIds.delete(position)
//        notifyDataSetChanged()
//    }
//
//    /**Get total selected item view count*/
//    fun getSelectedItemCount(): Int {
//        return selectedItemsIds.size()
//    }
//
//    /**Return all selected item view ids*/
//    fun getSelectedItem(): SparseBooleanArray {
//        return selectedItemsIds
//    }
}