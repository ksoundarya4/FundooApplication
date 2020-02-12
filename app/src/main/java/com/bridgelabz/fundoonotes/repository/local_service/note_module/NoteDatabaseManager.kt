/**
 * Fundoo Notes
 * @description NoteDatabaseManager Interface
 * to perform operations on notes.
 * @file NoteDatabaseManager.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 07/02/2020
 */
package com.bridgelabz.fundoonotes.repository.local_service.note_module

import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note

interface NoteDatabaseManager {
    fun insert(note: Note): Long
    fun fetch(): List<Note>
    fun delete(_id : Long)
}