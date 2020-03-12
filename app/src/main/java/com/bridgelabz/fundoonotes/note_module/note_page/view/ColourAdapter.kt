package com.bridgelabz.fundoonotes.note_module.note_page.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.fundoonotes.R

class ColourAdapter(
    private val colours: ArrayList<Int>,
    private val onColourClickListener: OnColourClickListener
) :
    RecyclerView.Adapter<ColourViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColourViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.colour_list, parent, false)
        return ColourViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return colours.size
    }

    override fun onBindViewHolder(holder: ColourViewHolder, position: Int) {
        val colour = colours[position]
        holder.bindColour(colour)
        holder.onColourButtonClick(onColourClickListener, position)
    }
}