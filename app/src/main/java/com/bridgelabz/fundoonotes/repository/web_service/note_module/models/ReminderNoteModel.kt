/**
 * Fundoo Notes
 * @description ReminderNoteModel is used to add reminder to
 * Note in Server.
 * @file ReminderNoteModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 03/04/2020
 */
package com.bridgelabz.fundoonotes.repository.web_service.note_module.models

data class ReminderNoteModel(
    var color: String? = null,
    var noteIdList: ArrayList<String>? = null
) : UpdateNoteModel