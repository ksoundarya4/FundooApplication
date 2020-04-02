/**
 * Fundoo Notes
 * @description Note data class which contains
 * Note title and description.
 * @file Note.kt
 * @author ksoundarya4
 * @version 1.0
 *@since 07/02/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.model

import java.io.Serializable

data class Note(var title: String, var description: String) : Serializable {
    var id: String? = null
    var isArchived: Int = 0
    var isDeleted: Int = 0
    var isPinned: Int = 0
    var reminder: String? = null
    var label: String? = null
    var position: Int = 0
    var colour: String? = null
    var userId: String? = null
    var noteId: String? = null

    constructor() : this(title = "", description = "")
}