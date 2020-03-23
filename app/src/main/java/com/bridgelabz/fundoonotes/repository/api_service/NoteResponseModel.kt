/**
 * Fundoo Notes
 * @description NoteResponseModel is a model class to post and get
 * notes from sever.
 * @file NoteResponseModel
 * @author ksoundarya4
 * @version 1.0
 * @since 23/03/2020
 */
package com.bridgelabz.fundoonotes.repository.api_service

data class NoteResponseModel(
    var title: String? = null,
    var description: String? = null,
    var isPinned: Boolean = false,
    var isArchived: Boolean = false,
    var isDeleted: Boolean = false,
    var reminder: ArrayList<String> = ArrayList(),
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var colour: String? = null,
    var label: ArrayList<String> = ArrayList(),
    var imageUrl: String? = null,
    var linkUrl: String? = null,
    var collaborators: ArrayList<String> = ArrayList(),
    var id: String? = null,
    var userId: String? = null,
    var collaborator: ArrayList<String> = ArrayList(),
    var noteCheckLists: ArrayList<String> = ArrayList(),
    var noteLabels: ArrayList<String> = ArrayList(),
    var questionAndAnswerNotes: ArrayList<String> = ArrayList(),
    var user: UserResponseModel? = null
) {
    fun convertBooleanToInt(property: Boolean?): Int {
        if (property!!)
            return 1
        return 0
    }
}