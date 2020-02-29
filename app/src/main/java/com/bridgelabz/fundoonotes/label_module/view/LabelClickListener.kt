/**
 * Fundoo Notes
 * @description ListViewClickListener Interface to perform
 * onClick on label itemView.
 * @file LabelClickListener.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 17/02/2020
 */
package com.bridgelabz.fundoonotes.label_module.view

interface LabelClickListener {
    fun onClick(adapterPosition: Int)
    fun onUpdateClick(adapterPosition: Int)
    fun onDeleteClick(adapterPosition: Int)
}