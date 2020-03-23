/**
 * Fundoo Notes
 * @description NoteResponseModel is a model class to post and get
 * notes from sever.
 * @file NoteResponseModel
 * @author ksoundarya4
 * @version 1.0
 * @since 23/03/2020
 */
package com.bridgelabz.fundoonotes.repository.server_service

import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note

class NoteResponseModel {
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


    fun NoteResponseModel.getNote(): Note {
        val note = Note()
        note.title = this.title!!
        note.description = this.description!!
        note.isPinned = convertBooleanToInt(this.isPinned)
        note.isArchived = convertBooleanToInt(this.isArchived)
        note.isDeleted = convertBooleanToInt(this.isDeleted)
        note.label = this.label.toString()
        note.reminder = this.reminder.toString()
        note.colour = this.colour!!.toInt()
        note.userId = this.userId

        return note
    }

    private fun convertBooleanToInt(property: Boolean?): Int {
        if (property!!)
            return 1
        return 0
    }
}