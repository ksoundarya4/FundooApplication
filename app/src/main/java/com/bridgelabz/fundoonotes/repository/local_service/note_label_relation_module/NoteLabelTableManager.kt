/**
 * Fundoo Notes
 * @description NoteLabelTableManager Interface to perform crud
 * operation on Notes and labels relation.
 * @file NoteLabelTableManager.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 04/03.2020
 */
package com.bridgelabz.fundoonotes.repository.local_service.note_label_relation_module

interface NoteLabelTableManager {
    fun insertNoteLabel(noteId: Long, labelId: Long): Long
    fun updateNoteLabel(noteId: Long, labelId: Long)
    fun deleteNoteLabel(rowId: Long)
}