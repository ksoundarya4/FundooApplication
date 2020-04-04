/**
 * Fundoo Notes
 * @description ArchiveNoteModel is used to archive or un archive
 * Note in Server.
 * @file ArchiveNoteModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 03/04/2020
 */
package com.bridgelabz.fundoonotes.repository.web_service.note_module.models

data class ArchiveNoteModel(
    var isArchived: Boolean = false,
    var noteIdList: ArrayList<String>? = null
)
