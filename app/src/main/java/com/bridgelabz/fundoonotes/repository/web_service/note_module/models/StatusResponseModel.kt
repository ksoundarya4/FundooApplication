/**
 * Fundoo Notes
 * @description StatusResponseModel is a data class
 * that contains the response status received from server
 * after successfully adding note to server.
 * @file StatusResponseModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 02/04/2020
 */
package com.bridgelabz.fundoonotes.repository.web_service.note_module.models

data class StatusResponseModel(
    var success: Boolean,
    var message: String,
    var details: NoteResponseModel
)