/**
 * Fundoo Notes
 * @description RecyclerClickListener Interface to perform
 * onClick and onLongClick on itemView.
 * @file RecyclerClickListener.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 17/02/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

interface OnNoteClickListener {
    fun onClick(adapterPosition: Int)
    fun onLongClick(adapterPosition: Int)
}