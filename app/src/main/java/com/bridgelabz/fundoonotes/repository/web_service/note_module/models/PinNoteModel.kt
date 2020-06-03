/**
 * Fundoo Notes
 * @description PinNoteModel is used to Pin or un pin
 * Note in Server.
 * @file PinNoteModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 03/04/2020
 */
package com.bridgelabz.fundoonotes.repository.web_service.note_module.models

data class PinNoteModel(
    var isPined: Boolean = false,
    var noteIdList: ArrayList<String>? = null
) : UpdateNoteModel
