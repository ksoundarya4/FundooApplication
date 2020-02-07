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
package com.bridgelabz.fundoonotes.note_module.note_repository

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.note_repository.NoteRegistrationContract.NoteEntry.KEY_DESCRIPTION
import com.bridgelabz.fundoonotes.note_module.note_repository.NoteRegistrationContract.NoteEntry.KEY_TITLE
import com.bridgelabz.fundoonotes.note_module.note_repository.NoteRegistrationContract.NoteEntry.TABLE_NAME

class NoteDatabaseManagerImpl(
    private val noteDbHelper: NoteDatabaseHelper
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
            put(KEY_TITLE, note.tile)
            put(KEY_DESCRIPTION, note.description)
        }

        val rowID = database.insert(TABLE_NAME, null, values)
        database.close()
        return rowID
    }

    /**
     * Function to retrieve list of notes from FundooNotes.db
     *
     * @return List of Notes.
     */
    override fun fetch(): List<Note> {
        val notes = ArrayList<Note>()
        database = noteDbHelper.readableDatabase

        val columns = arrayOf(
            BaseColumns._ID,
            KEY_TITLE,
            KEY_DESCRIPTION
        )
        val cursor = database.query(
            TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null
        )
        cursor.moveToFirst()
        do {

            notes.add(Note(cursor.getString(1), cursor.getString(2)))
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
        database.delete(TABLE_NAME, whereClause, null)
    }
}