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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note

class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val cardView: CardView = itemView.findViewById(R.id.note_card_view)
    private val title: TextView = itemView.findViewById(R.id.text_view_note_title)
    private val description: TextView = itemView.findViewById(R.id.text_view_note_description)
    private val constraintLayout: ConstraintLayout =
        itemView.findViewById(R.id.note_list_constraint_layout)

    fun bindNote(
        note: Note
    ) {
        title.text = note.title
        description.text = note.description
        setViewBackgroundColour(note.colour)

        if (note.reminder != null) {
            val reminder = note.reminder
            createCardView(reminder!!)
        }
    }

    private fun setViewBackgroundColour(colour: Int) {
        cardView.setCardBackgroundColor(colour)
    }

    private fun createCardView(reminder: String) {
        val reminderCardView = CardView(itemView.context)
        reminderCardView.id = R.id.reminderCardView
        reminderCardView.maxCardElevation = 4F

        val reminderTextView = TextView(itemView.context)
        reminderTextView.id = R.id.reminderTextView
        reminderTextView.text = reminder

        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        constraintSet.connect(
            reminderCardView.id,
            ConstraintSet.START,
            constraintLayout.id,
            ConstraintSet.START,
            R.dimen.margin_16dp
        )
        constraintSet.connect(
            reminderCardView.id,
            ConstraintSet.END,
            constraintLayout.id,
            ConstraintSet.END,
            R.dimen.margin_16dp
        )
        constraintSet.connect(
            reminderCardView.id,
            ConstraintSet.TOP,
            description.id,
            ConstraintSet.BOTTOM,
            R.dimen.margin_8dp
        )
        constraintSet.connect(
            reminderCardView.id,
            ConstraintSet.BOTTOM,
            constraintLayout.id,
            ConstraintSet.BOTTOM,
            R.dimen.margin_8dp
        )

        reminderCardView.addView(reminderTextView)
        constraintLayout.addView(reminderCardView)
        constraintSet.constrainWidth(constraintLayout.id, ConstraintSet.WRAP_CONTENT)
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