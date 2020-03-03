/**
 * Fundoo Notes
 * @description NoteTableManagerTest class to test functions of
 * NoteTableManagerImpl class.
 * @file NoteTableManagerTest.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 07/02/2020
 */
package com.bridgelabz.fundoonotes

import android.content.Context
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteTableManager
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteTableManagerImpl
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteTableManagerTest {

    private lateinit var noteDbHelper: DatabaseHelper
    private lateinit var noteDbManager: NoteTableManager
    private var rowId: Long = 0L

    @Before
    fun oncreate() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        noteDbHelper = DatabaseHelper(context)
        noteDbManager =
            NoteTableManagerImpl(
                noteDbHelper
            )
    }

    @Test
    fun createNote_testAddingIntoDatabase_usingInsertFunction() {
        val note = Note("FirstNote", "Hi,How are you")
        rowId = noteDbManager.insert(note)

        assertEquals(1, rowId)
    }

    @Test
    fun createNote_testRowId_isBeingIncremented() {
        val note = Note("SecondNote", "To test row is is incrementing")
        val nextRowId = noteDbManager.insert(note)

        assertEquals(2, nextRowId)
    }

    @Test
    fun toTestDeleteFunction() {
        noteDbManager.delete(2L)
    }

    @Test
    fun toTestForInvalidIDInput_toDeleteFunction_TestShouldNotDeleteAnyRows() {
        noteDbManager.delete(2L)
    }

    @Test
    fun toTestFetchFunction_countNumberOfNotesPresentInDatabase() {
        val notes = noteDbManager.fetchNotes()
        val numberOfNotes = notes.size
        print(notes)
        assertEquals(2, numberOfNotes)
    }

    @Test
    fun addArchiveNote_testFetchArchiveNoteFunction() {
        val note = Note("Archive", "archive")
        note.isArchived = 1
        noteDbManager.insert(note)
        val archiveNote = noteDbManager.fetchArchiveNote()
        Log.d("archive", archiveNote.toString())
        assertEquals(1, archiveNote.size)
    }

    @Test
    fun test_fetchSimpleNoteFunction() {
        val simpleNote = noteDbManager.fetchSimpleNote()
        Log.d("simpleNote", simpleNote.toString())
        assertEquals(3, simpleNote.size)
    }

    @Test
    fun test_fetchReminderNotesFunction() {
        val reminderNOtes = noteDbManager.fetchReminderNOtes()
        Log.d("ReminderNote", reminderNOtes.toString())
        assertEquals(2, reminderNOtes.size)
    }
}