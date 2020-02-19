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

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper.NoteRegistrationContract.NoteEntry.KEY_DESCRIPTION
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper.NoteRegistrationContract.NoteEntry.KEY_TITLE
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper.NoteRegistrationContract.NoteEntry.TABLE_NOTE

class NoteDatabaseManagerImpl(
    private val noteDbHelper: DatabaseHelper
) : NoteDatabaseManager {

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
            BaseColumns._ID,
            KEY_TITLE,
            KEY_DESCRIPTION
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
            val title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
            val description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
            notes.add(Note(title, description))
        } while (cursor.moveToNext())
        cursor.close()
        database.close()
        return notes
    }

    /**
     * Function to delete Note with particular id.
     *
     * @param  _id of row from where note has to be deleted from.
     */
    override fun delete(_id: Long) {
        database = noteDbHelper.open()
        val whereClause = "${BaseColumns._ID} = $_id"
        database.delete(TABLE_NOTE, whereClause, null)
        database.close()
    }

    /**Function to fetch row id of note
     *
     * @param note
     * @return row id
     */
    override fun fetchNoteId(note: Note): Long {
        var rowId = 0L
        database = noteDbHelper.readableDatabase

        val columns = arrayOf(
            BaseColumns._ID,
            KEY_TITLE,
            KEY_DESCRIPTION
        )

        val selection = "$KEY_TITLE=?"
        val selectionArgs = arrayOf(note.title)
        val cursor = database.query(
            TABLE_NOTE,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        if (cursor != null && cursor.moveToNext() && cursor.count > 0) {
            if (note.title == cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                && note.description == cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
            )
                rowId = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID)).toLong()
        }
        return rowId
    }
}