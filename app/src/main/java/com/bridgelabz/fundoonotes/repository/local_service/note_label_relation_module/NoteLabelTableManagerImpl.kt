/**
 * Fundoo Notes
 * @description NoteLabelTableManagerImpl implements
 * NoteLabelTableManager.
 * @file NoteLabelTableManagerImpl.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 04/03.2020
 */
package com.bridgelabz.fundoonotes.repository.local_service.note_label_relation_module

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.bridgelabz.fundoonotes.repository.common.DatabaseHelper

class NoteLabelTableManagerImpl(private val dbHelper: DatabaseHelper) : NoteLabelTableManager {

    lateinit var database: SQLiteDatabase

    companion object {
        private const val TABLE_NOTE_LABEL = "Note_Label"
        private const val KEY_ID = "ID"
        private const val KEY_NOTE_ID = "NoteId"
        private const val KEY_LABEL_ID = "LabelId"

        const val CREATE_TABLE_NOTE_LABEL =
            " Create Table $TABLE_NOTE_LABEL ( " +
                    "$KEY_ID INTEGER PRIMARY KEY, " +
                    "$KEY_NOTE_ID INTEGER, " +
                    "$KEY_LABEL_ID INTEGER)"
    }

    override fun insertNoteLabel(noteId: Long, labelId: Long): Long {
        database = dbHelper.open()

        val values = ContentValues().apply {
            put(KEY_NOTE_ID, noteId)
            put(KEY_LABEL_ID, labelId)
        }

        val rowId = database.insert(TABLE_NOTE_LABEL, null, values)
        database.close()
        return rowId
    }

    override fun updateNoteLabel(rowId: Long, noteId: Long, labelId: Long) {
        database = dbHelper.open()

        val values = ContentValues().apply {
            put(KEY_NOTE_ID, noteId)
            put(KEY_LABEL_ID, labelId)
        }
        val whereClause = "$KEY_ID = $rowId"
        database.update(TABLE_NOTE_LABEL, values, whereClause, null)
        database.close()
    }

    override fun deleteNoteLabel(rowId: Long) {
        database = dbHelper.open()
        val whereClause = "$KEY_ID = $rowId"
        database.delete(TABLE_NOTE_LABEL, whereClause, null)
    }
}