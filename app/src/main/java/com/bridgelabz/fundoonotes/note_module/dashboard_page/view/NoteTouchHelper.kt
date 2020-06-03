/**
 * Fundoo Notes
 * @description Interface called when an item has been dragged far
 * enough to trigger a move. This is called every time
 * an item is shifted.
 * @file NoteTouchHelper.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 13/04/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

interface NoteTouchHelper {
    fun onNoteMove(fromPosition: Int, toPosition: Int): Boolean
    fun onNoteSwipe(position: Int)
}