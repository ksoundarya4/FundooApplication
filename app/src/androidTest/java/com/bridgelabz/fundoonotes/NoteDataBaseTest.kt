/**
 * Fundoo Notes
 * @description NoteDataBaseTest class to test functions of
 * NoteDatabaseManagerImpl class.
 * @file NoteDatabaseTest.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 07/02/2020
 */
package com.bridgelabz.fundoonotes

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.note_module.NoteDatabaseManager
import com.bridgelabz.fundoonotes.repository.note_module.NoteDatabaseManagerImpl
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteDataBaseTest {

    private lateinit var noteDbHelper: DatabaseHelper
    private lateinit var noteDbManager: NoteDatabaseManager
    private var rowId: Long = 0L

    @Before
    fun oncreate() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        noteDbHelper = DatabaseHelper(context)
        noteDbManager =
            NoteDatabaseManagerImpl(
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
        val notes = noteDbManager.fetch()
        val numberOfNotes = notes.size
        print(notes)
        assertEquals(2, numberOfNotes)
    }
}