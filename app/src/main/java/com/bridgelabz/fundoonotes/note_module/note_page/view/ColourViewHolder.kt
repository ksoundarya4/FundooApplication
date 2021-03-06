package com.bridgelabz.fundoonotes.note_module.note_page.view

import android.view.View
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.fundoonotes.R

class ColourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val cardView = itemView.findViewById<CardView>(R.id.colour_card_view)

    fun bindColour(colour: Int) {
        cardView.setCardBackgroundColor(colour)
    }

    fun onColourButtonClick(onColourClickListener: OnColourClickListener, position: Int) {
        cardView.setOnClickListener {
            onColourClickListener.onClick(position)
        }
    }
}