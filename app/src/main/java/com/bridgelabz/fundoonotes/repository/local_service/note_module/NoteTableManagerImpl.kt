/**
 * Fundoo Notes
 * @description NoteTableManagerImpl class implements
 * NoteTableManager interface which can perform insert,
 * fetch and delete on notes.
 * @file NoteTableManagerImpl.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 07/02/2020
 */
package com.bridgelabz.fundoonotes.repository.local_service.note_module

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.repository.common.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.local_service.user_module.UserDbManagerImpl
import com.bridgelabz.fundoonotes.repository.local_service.user_module.UserDbManagerImpl.UserEntry.TABLE_USER

class NoteTableManagerImpl(
    private val noteDbHelper: DatabaseHelper
) : NoteTableManager {

    companion object NoteEntry {
        private const val TABLE_NOTE = "Notes"
        private const val ID = "ID"
        private const val KEY_TITLE = "Title"
        private const val KEY_DESCRIPTION = "Description"
        private const val KEY_ARCHIVE = "Archive"
        private const val KEY_TRASH = "Trash"
        private const val KEY_PINNED = "Pinned"
        private const val KEY_LABEL = "Label"
        private const val KEY_REMINDER = "Reminder"
        private const val KEY_POSITION = "Position"
        private const val KEY_COLOUR = "Colour"
        private const val KEY_USER_ID = "UserId"
        private const val KEY_NOTE_ID = "NoteId"

        const val CREATE_NOTE_TABLE =
            " Create Table $TABLE_NOTE ( " +
                    "$ID INTEGER PRIMARY KEY, " +
                    "$KEY_TITLE TEXT NOT NULL, " +
                    "$KEY_DESCRIPTION TEXT NOT NULL, " +
                    "$KEY_ARCHIVE INTEGER, " +
                    "$KEY_TRASH INTEGER, " +
                    "$KEY_PINNED INTEGER, " +
                    "$KEY_LABEL VARCHAR(20), " +
                    "$KEY_REMINDER VARCHAR(20), " +
                    "$KEY_POSITION INTEGER, " +
                    "$KEY_COLOUR INTEGER, " +
                    "$KEY_NOTE_ID VARCHAR(25), " +
                    "$KEY_USER_ID VARCHAR(25), " +
                    "FOREIGN KEY($KEY_USER_ID) REFERENCES $TABLE_USER(${UserDbManagerImpl.KEY_USER_ID}) )"
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
            put(KEY_TRASH, note.isDeleted)
            put(KEY_PINNED, note.isPinned)
            put(KEY_LABEL, note.label)
            put(KEY_REMINDER, note.reminder)
            put(KEY_POSITION, note.position)
            put(KEY_COLOUR, note.colour)
            put(KEY_NOTE_ID, note.noteId)
            put(KEY_USER_ID, note.userId)
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
    override fun fetchUserNotes(userId: String): ArrayList<Note> {
        val notes = ArrayList<Note>()
        database = noteDbHelper.readableDatabase

        val columns = arrayOf(
            ID,
            KEY_TITLE,
            KEY_DESCRIPTION,
            KEY_ARCHIVE,
            KEY_TRASH,
            KEY_PINNED,
            KEY_LABEL,
            KEY_REMINDER,
            KEY_POSITION,
            KEY_COLOUR,
            KEY_NOTE_ID,
            KEY_USER_ID
        )
        val selection = "$KEY_USER_ID =?"
        val selectionArgs = arrayOf(userId)
        val cursor = database.query(
            TABLE_NOTE,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            do {
                val id = cursor.getString(cursor.getColumnIndex(ID))
                val title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                val description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
                val isArchived = cursor.getInt(cursor.getColumnIndex(KEY_ARCHIVE))
                val isDeleted = cursor.getInt(cursor.getColumnIndex(KEY_TRASH))
                val isPinned = cursor.getInt(cursor.getColumnIndex(KEY_PINNED))
                val label = cursor.getString(cursor.getColumnIndex(KEY_LABEL))
                val reminder = cursor.getString(cursor.getColumnIndex(KEY_REMINDER))
                val position = cursor.getInt(cursor.getColumnIndex(KEY_POSITION))
                val colour = cursor.getInt(cursor.getColumnIndex(KEY_COLOUR))
                val noteId = cursor.getString(cursor.getColumnIndex(KEY_NOTE_ID))
                val userIdFromNoteTable = cursor.getString(cursor.getColumnIndex(KEY_USER_ID))

                val note = Note(title, description)
                note.id = id
                note.isArchived = isArchived
                note.isDeleted = isDeleted
                note.isPinned = isPinned
                note.label = label
                note.reminder = reminder
                note.position = position
                note.colour = colour
                note.userId = userIdFromNoteTable
                note.noteId = noteId

                notes.add(note)
            } while (cursor.moveToNext())
        }
        cursor.close()
        database.close()
        return notes
    }

    /**
     * Function to retrieve a note based on its note_ID from FundooNotes.db
     *
     * @return Note.
     */
    override fun fetchNoteByNoteId(noteId: String): Note? {
        var note: Note? = null
        database = noteDbHelper.readableDatabase

        val columns = arrayOf(
            ID,
            KEY_TITLE,
            KEY_DESCRIPTION,
            KEY_ARCHIVE,
            KEY_TRASH,
            KEY_PINNED,
            KEY_LABEL,
            KEY_REMINDER,
            KEY_POSITION,
            KEY_COLOUR,
            KEY_NOTE_ID,
            KEY_USER_ID
        )
        val selection = "$KEY_NOTE_ID =?"
        val selectionArgs = arrayOf(noteId)
        val cursor = database.query(
            TABLE_NOTE,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            do {
                val id = cursor.getString(cursor.getColumnIndex(ID))
                val title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                val description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
                val isArchived = cursor.getInt(cursor.getColumnIndex(KEY_ARCHIVE))
                val isDeleted = cursor.getInt(cursor.getColumnIndex(KEY_TRASH))
                val isPinned = cursor.getInt(cursor.getColumnIndex(KEY_PINNED))
                val label = cursor.getString(cursor.getColumnIndex(KEY_LABEL))
                val reminder = cursor.getString(cursor.getColumnIndex(KEY_REMINDER))
                val position = cursor.getInt(cursor.getColumnIndex(KEY_POSITION))
                val colour = cursor.getInt(cursor.getColumnIndex(KEY_COLOUR))
                val noteIdFromNoteTable = cursor.getString(cursor.getColumnIndex(KEY_NOTE_ID))
                val userIdFromNoteTable = cursor.getString(cursor.getColumnIndex(KEY_USER_ID))

                note = Note(title, description)
                note.id = id
                note.isArchived = isArchived
                note.isDeleted = isDeleted
                note.isPinned = isPinned
                note.label = label
                note.reminder = reminder
                note.position = position
                note.colour = colour
                note.userId = userIdFromNoteTable
                note.noteId = noteIdFromNoteTable

            } while (cursor.moveToNext())
        }
        cursor.close()
        database.close()
        return note
    }

    /**
     * Function to delete Note with particular id.
     *
     * @param  _id of row from where note has to be deleted from.
     */
    override fun delete(_id: Long) {
        database = noteDbHelper.open()
        val whereClause = "ID = $_id"
        database.delete(TABLE_NOTE, whereClause, null)
        database.close()
    }

    override fun deleteNotesByUserId(userId: String) {
        database = noteDbHelper.open()
        val whereClause = "$KEY_NOTE_ID =?"
        val whereArgs = arrayOf(userId)
        database.delete(TABLE_NOTE, whereClause, whereArgs)
    }

    override fun updateNote(note: Note) {
        database = noteDbHelper.open()

        val values = ContentValues().apply {
            put(KEY_TITLE, note.title)
            put(KEY_DESCRIPTION, note.description)
            put(KEY_ARCHIVE, note.isArchived)
            put(KEY_TRASH, note.isDeleted)
            put(KEY_PINNED, note.isPinned)
            put(KEY_LABEL, note.label)
            put(KEY_REMINDER, note.reminder)
            put(KEY_POSITION, note.position)
            put(KEY_COLOUR, note.colour)
            put(KEY_NOTE_ID, note.noteId)
            put(KEY_USER_ID, note.userId)
        }
        database.update(
            TABLE_NOTE,
            values,
            "$ID = ${note.id}",
            null
        )
        database.close()
    }

    /**Function to fetch Archive Note
     *
     * @return ArrayList of Archive notes
     */
    override fun fetchArchiveNote(): ArrayList<Note> {

        val archiveNotes = ArrayList<Note>()
        database = noteDbHelper.open()

        val columns = arrayOf(
            ID,
            KEY_TITLE,
            KEY_DESCRIPTION,
            KEY_ARCHIVE,
            KEY_TRASH,
            KEY_PINNED,
            KEY_LABEL,
            KEY_REMINDER,
            KEY_POSITION,
            KEY_COLOUR,
            KEY_NOTE_ID,
            KEY_USER_ID
        )

        val selection = "$KEY_ARCHIVE =?"
        val selectionArgs = arrayOf("1")

        val cursor = database.query(
            TABLE_NOTE,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            do {
                val id = cursor.getString(cursor.getColumnIndex(ID))
                val title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                val description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
                val isArchived = cursor.getInt(cursor.getColumnIndex(KEY_ARCHIVE))
                val isDeleted = cursor.getInt(cursor.getColumnIndex(KEY_TRASH))
                val isPinned = cursor.getInt(cursor.getColumnIndex(KEY_PINNED))
                val label = cursor.getString(cursor.getColumnIndex(KEY_LABEL))
                val reminder = cursor.getString(cursor.getColumnIndex(KEY_REMINDER))
                val position = cursor.getInt(cursor.getColumnIndex(KEY_POSITION))
                val colour = cursor.getInt(cursor.getColumnIndex(KEY_COLOUR))
                val noteId = cursor.getString(cursor.getColumnIndex(KEY_NOTE_ID))
                val userId = cursor.getString(cursor.getColumnIndex(KEY_USER_ID))

                val note = Note(title, description)
                note.id = id
                note.isArchived = isArchived
                note.isDeleted = isDeleted
                note.isPinned = isPinned
                note.label = label
                note.reminder = reminder
                note.position = position
                note.colour = colour
                note.userId = userId
                note.noteId = noteId

                archiveNotes.add(note)
            } while (cursor.moveToNext())
        }
        cursor.close()
        database.close()
        return archiveNotes
    }

    /**Function to fetch Deleted Note
     *
     * @return ArrayList of Deleted notes
     */
    override fun fetchDeletedNote(): ArrayList<Note> {
        val deletedNotes = ArrayList<Note>()
        database = noteDbHelper.open()

        val columns = arrayOf(
            ID,
            KEY_TITLE,
            KEY_DESCRIPTION,
            KEY_ARCHIVE,
            KEY_TRASH,
            KEY_PINNED,
            KEY_LABEL,
            KEY_REMINDER,
            KEY_POSITION,
            KEY_COLOUR,
            KEY_USER_ID
        )

        val selection = "$KEY_TRASH =?"
        val selectionArgs = arrayOf("1")

        val cursor = database.query(
            TABLE_NOTE,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            do {
                val id = cursor.getString(cursor.getColumnIndex(ID))
                val title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                val description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
                val isArchived = cursor.getInt(cursor.getColumnIndex(KEY_ARCHIVE))
                val isDeleted = cursor.getInt(cursor.getColumnIndex(KEY_TRASH))
                val isPinned = cursor.getInt(cursor.getColumnIndex(KEY_PINNED))
                val label = cursor.getString(cursor.getColumnIndex(KEY_LABEL))
                val reminder = cursor.getString(cursor.getColumnIndex(KEY_REMINDER))
                val position = cursor.getInt(cursor.getColumnIndex(KEY_POSITION))
                val colour = cursor.getInt(cursor.getColumnIndex(KEY_COLOUR))
                val userId = cursor.getString(cursor.getColumnIndex(KEY_USER_ID))

                val note = Note(title, description)
                note.id = id
                note.isArchived = isArchived
                note.isDeleted = isDeleted
                note.isPinned = isPinned
                note.label = label
                note.reminder = reminder
                note.position = position
                note.colour = colour
                note.userId = userId

                deletedNotes.add(note)
            } while (cursor.moveToNext())
        }
        cursor.close()
        database.close()
        return deletedNotes
    }

    /**Function to fetch Pined Note
     *
     * @return ArrayList of Pined notes
     */
    override fun fetchPinnedNote(): ArrayList<Note> {
        val pinnedNotes = ArrayList<Note>()
        database = noteDbHelper.open()

        val columns = arrayOf(
            ID,
            KEY_TITLE,
            KEY_DESCRIPTION,
            KEY_ARCHIVE,
            KEY_TRASH,
            KEY_PINNED,
            KEY_LABEL,
            KEY_REMINDER,
            KEY_POSITION,
            KEY_COLOUR,
            KEY_USER_ID
        )

        val selection = "$KEY_PINNED =?"
        val selectionArgs = arrayOf("1")

        val cursor = database.query(
            TABLE_NOTE,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            do {
                val id = cursor.getString(cursor.getColumnIndex(ID))
                val title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                val description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
                val isArchived = cursor.getInt(cursor.getColumnIndex(KEY_ARCHIVE))
                val isDeleted = cursor.getInt(cursor.getColumnIndex(KEY_TRASH))
                val isPinned = cursor.getInt(cursor.getColumnIndex(KEY_PINNED))
                val label = cursor.getString(cursor.getColumnIndex(KEY_LABEL))
                val reminder = cursor.getString(cursor.getColumnIndex(KEY_REMINDER))
                val position = cursor.getInt(cursor.getColumnIndex(KEY_POSITION))
                val colour = cursor.getInt(cursor.getColumnIndex(KEY_COLOUR))
                val userId = cursor.getString(cursor.getColumnIndex(KEY_USER_ID))

                val note = Note(title, description)
                note.id = id
                note.isArchived = isArchived
                note.isDeleted = isDeleted
                note.isPinned = isPinned
                note.label = label
                note.reminder = reminder
                note.position = position
                note.colour = colour
                note.userId = userId

                pinnedNotes.add(note)
            } while (cursor.moveToNext())
        }
        cursor.close()
        database.close()
        return pinnedNotes
    }

    /**Function to fetch simple Note
     *
     * @return ArrayList of simple notes
     */
    override fun fetchSimpleNote(): ArrayList<Note> {
        val simpleNotes = ArrayList<Note>()
        database = noteDbHelper.open()

        val columns = arrayOf(
            ID,
            KEY_TITLE,
            KEY_DESCRIPTION,
            KEY_ARCHIVE,
            KEY_TRASH,
            KEY_PINNED,
            KEY_LABEL,
            KEY_REMINDER,
            KEY_POSITION,
            KEY_COLOUR,
            KEY_USER_ID
        )

        val selection = "$KEY_ARCHIVE =? AND $KEY_TRASH =? AND $KEY_PINNED =?"
        val selectionArgs = arrayOf("0", "0", "0")

        val cursor = database.query(
            TABLE_NOTE,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            do {
                val id = cursor.getString(cursor.getColumnIndex(ID))
                val title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                val description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
                val isArchived = cursor.getInt(cursor.getColumnIndex(KEY_ARCHIVE))
                val isDeleted = cursor.getInt(cursor.getColumnIndex(KEY_TRASH))
                val isPinned = cursor.getInt(cursor.getColumnIndex(KEY_PINNED))
                val label = cursor.getString(cursor.getColumnIndex(KEY_LABEL))
                val reminder = cursor.getString(cursor.getColumnIndex(KEY_REMINDER))
                val position = cursor.getInt(cursor.getColumnIndex(KEY_POSITION))
                val colour = cursor.getInt(cursor.getColumnIndex(KEY_COLOUR))
                val userId = cursor.getString(cursor.getColumnIndex(KEY_USER_ID))

                val note = Note(title, description)
                note.id = id
                note.isArchived = isArchived
                note.isDeleted = isDeleted
                note.isPinned = isPinned
                note.label = label
                note.reminder = reminder
                note.position = position
                note.colour = colour
                note.userId = userId

                simpleNotes.add(note)
            } while (cursor.moveToNext())
        }
        cursor.close()
        database.close()
        return simpleNotes
    }

    override fun fetchReminderNOtes(): ArrayList<Note> {
        val reminderNotes = ArrayList<Note>()
        database = noteDbHelper.open()

        val columns = arrayOf(
            ID,
            KEY_TITLE,
            KEY_DESCRIPTION,
            KEY_ARCHIVE,
            KEY_TRASH,
            KEY_PINNED,
            KEY_LABEL,
            KEY_REMINDER,
            KEY_POSITION,
            KEY_COLOUR,
            KEY_USER_ID
        )

        val selection = "$KEY_REMINDER =?"
        val selectionArgs = arrayOf("NOTNULL")

        val cursor = database.query(
            TABLE_NOTE,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            do {
                val id = cursor.getString(cursor.getColumnIndex(ID))
                val title = cursor.getString(cursor.getColumnIndex(KEY_TITLE))
                val description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION))
                val isArchived = cursor.getInt(cursor.getColumnIndex(KEY_ARCHIVE))
                val isDeleted = cursor.getInt(cursor.getColumnIndex(KEY_TRASH))
                val isPinned = cursor.getInt(cursor.getColumnIndex(KEY_PINNED))
                val label = cursor.getString(cursor.getColumnIndex(KEY_LABEL))
                val reminder = cursor.getString(cursor.getColumnIndex(KEY_REMINDER))
                val position = cursor.getInt(cursor.getColumnIndex(KEY_POSITION))
                val colour = cursor.getInt(cursor.getColumnIndex(KEY_COLOUR))
                val userId = cursor.getString(cursor.getColumnIndex(KEY_USER_ID))

                val note = Note(title, description)
                note.id = id
                note.isArchived = isArchived
                note.isDeleted = isDeleted
                note.isPinned = isPinned
                note.label = label
                note.reminder = reminder
                note.position = position
                note.colour = colour
                note.userId = userId

                reminderNotes.add(note)
            } while (cursor.moveToNext())
        }
        cursor.close()
        database.close()
        return reminderNotes
    }
}