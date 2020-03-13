/**
 * Fundoo Notes
 * @description NoteFireStoreManager interface manages the
 * CRUD operations related to cloud fire store.
 * @file NoteFireStoreManager.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 13/03/2020
 */
package com.bridgelabz.fundoonotes.repository.firestore_service.firebase_note

import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note

interface NoteFireStoreManager {
    fun insertNote(note: Note)
    fun updateNote(note: Note)
    fun deleteNote()
    fun fetchNote(): Note
}