/**
 * Fundoo Notes
 * @Description UpdateNoteResponseData is used to receive response
 * from server after updating the note in server.
 * @file UpdateNoteResponseData.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 03/04/2020
 */
package com.bridgelabz.fundoonotes.repository.web_service.note_module.models

data class UpdateNoteData(
    var success: Boolean? = false,
    var message: String? = null
)
