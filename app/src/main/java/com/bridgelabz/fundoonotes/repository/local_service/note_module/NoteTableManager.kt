/**
 * Fundoo Notes
 * @description NoteTableManager Interface
 * to perform operations on notes.
 * @file NoteTableManager.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 07/02/2020
 */
package com.bridgelabz.fundoonotes.repository.local_service.note_module

import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note

interface NoteTableManager {
    fun insert(note: Note): Long
    fun fetchNotes(): ArrayList<Note>
    fun delete(_id: Long)
    fun updateNote(note: Note)
    fun fetchArchiveNote(): ArrayList<Note>
    fun fetchDeletedNote(): ArrayList<Note>
    fun fetchPinnedNote(): ArrayList<Note>
    fun fetchSimpleNote(): ArrayList<Note>
    fun fetchReminderNOtes(): ArrayList<Note>
}