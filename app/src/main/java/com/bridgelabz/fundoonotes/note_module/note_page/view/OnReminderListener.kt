/**
 * Fundoo Notes
 * @description OnReminderListener interface is used
 * extract the date and time set using ReminderDialogFragment.
 * @file OnReminderListener.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 28/02/2020
 */
package com.bridgelabz.fundoonotes.note_module.note_page.view

interface OnReminderListener {
    fun onReminderSubmit(date: String, time: String)
}