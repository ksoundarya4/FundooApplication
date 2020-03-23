/**
 * Fundoo Notes
 * @description NoteModel is a model class to post and get
 * notes from sever.
 * @file NoteModel
 * @author ksoundarya4
 * @version 1.0
 * @since 23/03/2020
 */
package com.bridgelabz.fundoonotes.repository.server_service

import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note

class NoteModel {
    var title: String? = null
    var description: String? = null
    var isPinned = false
    var isArchived = false
    var isDeleted = false
    var reminder = ArrayList<Any>()
    var createdDate: String? = null
    var modifiedDate: String? = null
    var colour: String? = null
    var label = ArrayList<Any>()
    var imageUrl: String? = null
    var linkUrl: String? = null
    var collaborators = ArrayList<Any>()
    var id: String? = null
    var userId: String? = null
    var collaborator = ArrayList<Any>()
    var noteCheckLists = ArrayList<Any>()
    var noteLabels = ArrayList<Any>()
    var questionAndAnswerNotes = ArrayList<Any>()
    var user: UserResponseModel? = null

    fun convertBooleanToInt(property: Boolean?): Int {
        if (property!!)
            return 1
        return 0
    }

    override fun toString(): String {
        return "NoteResponseModel(title=$title, description=$description, isPinned=$isPinned, isArchived=$isArchived, isDeleted=$isDeleted, reminder=$reminder, createdDate=$createdDate, modifiedDate=$modifiedDate, colour=$colour, label=$label, imageUrl=$imageUrl, linkUrl=$linkUrl, collaborators=$collaborators, id=$id, userId=$userId, collaborator=$collaborator, noteCheckLists=$noteCheckLists, noteLabels=$noteLabels, questionAndAnswerNotes=$questionAndAnswerNotes, user=$user)"
    }
}