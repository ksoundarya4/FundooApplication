/**
 * Fundoo Notes
 * @Description UpdateNoteResponseModel is used to receive response
 * from server after updating the note in server.
 * @file UpdateNoteResponseModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 03/04/2020
 */
package com.bridgelabz.fundoonotes.repository.web_service.note_module.models

data class UpdateNoteResponseModel(
    var title: String? = null,
    var description: String? = null,
    var isPined: Boolean = false,
    var isArchived: Boolean = false,
    var isDeleted: Boolean = false,
    var reminder: ArrayList<String>? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var color: String? = null,
    var label: ArrayList<String>? = null,
    var typeOfNote: String? = null,
    var checkList: ArrayList<String>? = null,
    var imageUrl: String? = null,
    var linkUrl: String? = null,
    var collaborators: ArrayList<Any>? = null,
    var id: String? = null,
    var userId: String? = null,
    var collaberator: ArrayList<Any>? = null
)