/**
 * Fundoo Notes
 * @description NoteDatabaseManagerImpl class implements
 * NoteDatabaseManager interface which can perform insert,
 * fetch and delete on notes.
 * @file NoteDatabaseManagerImpl.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 07/02/2020
 */
package com.bridgelabz.fundoonotes.repository.local_service.note_module

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper

class NoteDatabaseManagerImpl(
    private val noteDbHelper: DatabaseHelper
) : NoteDatabaseManager {

    companion object NoteEntry {
        private const val TABLE_NOTE = "Notes"
        private const val NOTE_ID = "ID"
        private const val KEY_TITLE = "Title"
        private const val KEY_DESCRIPTION = "Description"
        private const val KEY_ARCHIVE = "Archive"
        private const val KEY_DELETE = "Delete"
        private const val KEY_PINNED = "Pinned"
        private const val KEY_LABEL = "Label"
        private const val KEY_REMINDER = "Reminder"
        private const val KEY_POSITION = "Position"
        private const val KEY_COLOUR = "Colour"
        const val CREATE_NOTE_TABLE =
            " Create Table $TABLE_NOTE ( " +
                    "$NOTE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$KEY_TITLE TEXT NOT NULL, " +
                    "$KEY_DESCRIPTION TEXT NOT NULL" +
                    " $KEY_ARCHIVE INTEGER" +
                    " $KEY_DELETE INTEGER" +
                    " $KEY_PINNED INTEGER " +
                    "$KEY_LABEL VARCHAR(20)" +
                    " $KEY_REMINDER VARCHAR(20) " +
                    "$KEY_POSITION INTEGER" +
                    " $KEY_COLOUR INTEGER)"

    }

    private lateinit var database: SQLiteDatabase

    /**Function to insert Note into FundooNotes.db
     *
     * @param note to be stored.
     * @return rowId of the table in which
     * note is stored.
     */
    override fun insert(note: Note): Long {
        database = noteDbHelper.open()

        val values = ContentValues().apply {
            put(KEY_TITLE, note.title)
            put(KEY_DESCRIPTION, note.description)
            put(KEY_ARCHIVE, note.isArchived)
            put(KEY_DELETE, note.isDeleted)
            put(KEY_PINNED, note.isPinned)
            put(KEY_LABEL, note.label)
            put(KEY_REMINDER, note.reminder)
            put(KEY_POSITION, note.position)
            put(KEY_COLOUR, note.colour)
        }

        val rowID = database.insert(TABLE_NOTE, null, values)
        database.close()
        return rowID
    }

    /**
     * Function to retrieve list of notes from FundooNotes.db
     *
     * @return List of Notes.
     */
    override fun fetchNotes(): ArrayList<Note> {
        val notes = ArrayList<Note>()
        database = noteDbHelper.readableDatabase

        val columns = arrayOf(
            NOTE_ID,
            KEY_TITLE,
            KEY_DESCRIPTION,
            KEY_ARCHIVE,
            KEY_DELETE,
            KEY_PINNED,
            KEY_LABEL,
            KEY_REMINDER,
            KEY_POSITION,
            KEY_COLOUR
        )
        val cursor = database.query(
            TABLE_NOTE,
            columns,
            null,
            null,
            null,
            null,
            null
        )
        cursor.moveToFirst()
        do {
            val id = cursor.getInt(cursor.getColumnIndex(NOTE_ID))
            val title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
            val description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
            val isArchived = cursor.getInt(cursor.getColumnIndex(KEY_ARCHIVE))
            val isDeleted = cursor.getInt(cursor.getColumnIndex(KEY_DELETE))
            val isPinned = cursor.getInt(cursor.getColumnIndex(KEY_POSITION))
            val label = cursor.getString(cursor.getColumnIndex(KEY_LABEL))
            val reminder = cursor.getString(cursor.getColumnIndex(KEY_REMINDER))
            val position = cursor.getInt(cursor.getColumnIndex(KEY_POSITION))
            val colour = cursor.getInt(cursor.getColumnIndex(KEY_COLOUR))

            val note = Note(title, description)
            note.id = id
            note.isArchived = isArchived
            note.isDeleted = isDeleted
            note.isPinned = isPinned
            note.label = label
            note.reminder = reminder
            note.position = position
            note.colour = colour

            notes.add(note)
        } while (cursor.moveToNext())
        cursor.close()
        database.close()
        return notes
    }

    /**
     * Function to delete Note with particular id.
     *
     * @param  id of row from where note has to be deleted from.
     */
    override fun delete(id: Long) {
        database = noteDbHelper.open()
        val whereClause = "ID = $id"
        database.delete(TABLE_NOTE, whereClause, null)
        database.close()
    }

    override fun updateNote(rowId: Long, note: Note) {
        database = noteDbHelper.open()

        val values = ContentValues().apply {
            put(KEY_TITLE, note.title)
            put(KEY_DESCRIPTION, note.description)
        }
        database.update(
            TABLE_NOTE,
            values,
            BaseColumns._ID + "=" + rowId,
            null
        )
        database.close()
    }
}