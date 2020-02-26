/**
 * Fundoo Notes
 * @description LabelViewHolder class extends RecyclerView.ViewHolder
 * @file LabelViewAdapter.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 17/02/2020
 */
package com.bridgelabz.fundoonotes.label_module.view

import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.label_module.model.Label

class LabelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val labelEditText: EditText = itemView.findViewById(R.id.text_label)

    fun bindLabel(label: Label) {
        labelEditText.setText(label.label)
    }

    fun onLabelClickListener(adapterPosition: Int, labelClickListener: LabelClickListener) {
        itemView.setOnClickListener {
            labelClickListener.onClick(adapterPosition)
        }
    }
}