/**
 * Fundoo Notes
 * @description AddNoteResponseModel is a data class
 * that contains the response status received from server
 * after successfully adding note to server.
 * @file AddNoteResponseModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 02/04/2020
 */
package com.bridgelabz.fundoonotes.repository.web_service.note_module.models

data class AddNoteResponseModel(var status: StatusResponseModel)