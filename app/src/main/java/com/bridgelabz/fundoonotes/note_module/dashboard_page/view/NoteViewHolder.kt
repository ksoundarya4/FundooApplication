/**
 * Fundoo Notes
 * @description NoteViewHolder class extends RecyclerView.ViewHolder
 * @file NoteViewAdapter.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 17/02/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note

class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val cardView: CardView = itemView.findViewById(R.id.note_card_view)
    private val title: TextView = itemView.findViewById(R.id.text_view_note_title)
    private val description: TextView = itemView.findViewById(R.id.text_view_note_description)

    fun bindNote(
        note: Note
    ) {
        title.text = note.title
        description.text = note.description
    }

    fun onNoteCLickListener(onNoteClick: OnNoteClickListener, adapterPosition: Int) {
        itemView.setOnClickListener {
            onNoteClick.onClick(adapterPosition)
        }
        itemView.setOnLongClickListener {
            onLongClick(onNoteClick, adapterPosition)
        }
    }

    private fun onLongClick(onNoteClick: OnNoteClickListener, adapterPosition: Int): Boolean {
        onNoteClick.onLongClick(adapterPosition)
        return true
    }
}