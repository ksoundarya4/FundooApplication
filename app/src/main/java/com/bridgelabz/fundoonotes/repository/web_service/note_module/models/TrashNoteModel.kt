/**
 * Fundoo Notes
 * @description TrashNoteModel is used to add note to trash
 * Note in Server.
 * @file TrashNoteModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 03/04/2020
 */
package com.bridgelabz.fundoonotes.repository.web_service.note_module.models

data class TrashNoteModel(
    var isDeleted: Boolean = false,
    var noteIdList: ArrayList<String>? = null
) : UpdateNoteModel