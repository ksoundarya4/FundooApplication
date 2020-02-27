/**
 * Fundoo Notes
 * @description LabelViewHolder class extends RecyclerView.ViewHolder
 * @file LabelViewAdapter.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 17/02/2020
 */
package com.bridgelabz.fundoonotes.label_module.view

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.label_module.model.Label

class LabelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val cardView: CardView = itemView.findViewById(R.id.label_card_view)
    private val labelEditText: EditText = itemView.findViewById(R.id.text_label)
    private val label: ImageButton = itemView.findViewById(R.id.label)
    private val checkLabel: ImageButton = itemView.findViewById(R.id.check_label)

    fun bindLabel(label: Label) {
        labelEditText.setText(label.label)
    }

    fun onLabelClickListener(adapterPosition: Int, labelClickListener: LabelClickListener) {
        cardView.setOnClickListener {
            label.setImageResource(R.drawable.ic_delete)
            checkLabel.setImageResource(R.drawable.ic_check_grey)
            labelClickListener.onClick(adapterPosition)
        }

        label.setOnClickListener {
            labelClickListener.onDeleteClick(adapterPosition)
        }

        checkLabel.setOnClickListener {
            labelClickListener.onUpdateClick(adapterPosition)
        }
    }

    fun onTextChanged(label: Label) {
        labelEditText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
               label.label = labelEditText.editableText.toString()
            }

        })
    }
}