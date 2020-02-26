/**
 * Fundoo Notes
 * @description LabelViewAdapter class extends RecyclerView.Adapter
 * which holds functions that deals with implementation of RecyclerView.
 * @file LabelViewAdapter.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 15/02/2020
 */
package com.bridgelabz.fundoonotes.label_module.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.label_module.model.Label

class LabelViewAdapter(private var labels: ArrayList<Label>) :
    RecyclerView.Adapter<LabelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.label_list, parent, false)
        return LabelViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return labels.size
    }

    override fun onBindViewHolder(holder: LabelViewHolder, position: Int) {
        val label = labels[position]
        holder.bindLabel(label)
    }

    fun setLabelList(labels: ArrayList<Label>) {
        this.labels = labels
    }
}