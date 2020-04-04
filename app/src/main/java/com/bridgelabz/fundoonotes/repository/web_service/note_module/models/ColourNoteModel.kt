/**
 * Fundoo Notes
 * @description ColourNoteModel is used to add colour to
 * Note in Server.
 * @file ColourNoteModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 03/04/2020
 */
package com.bridgelabz.fundoonotes.repository.web_service.note_module.models

data class ColourNoteModel(
    var color: String? = null,
    var noteIdList: ArrayList<String>? = null
)