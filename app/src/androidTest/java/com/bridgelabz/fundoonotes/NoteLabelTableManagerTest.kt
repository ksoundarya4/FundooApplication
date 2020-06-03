/**
 * Fundoo Notes
 * @description NoteLabelTableManagerTest class to test functions of
 * NoteTableManagerImpl class.
 * @file NoteLabelTableManagerTest.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 07/02/2020
 */
package com.bridgelabz.fundoonotes

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.bridgelabz.fundoonotes.repository.common.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.local_service.note_label_relation_module.NoteLabelTableManager
import com.bridgelabz.fundoonotes.repository.local_service.note_label_relation_module.NoteLabelTableManagerImpl
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NoteLabelTableManagerTest {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var tableManager: NoteLabelTableManager

    @Before
    fun onCreate() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dbHelper =
            DatabaseHelper(context)
        tableManager = NoteLabelTableManagerImpl(dbHelper)
    }

    @Test
    fun test_insertFunction_ofNoteLabelTableManager() {
        val noteId = 1L
        val labelId = 3L
        val rowId = tableManager.insertNoteLabel(noteId = noteId, labelId = labelId)

        assertEquals(1, rowId)
    }

    @Test
    fun test_updateFunction_ofNoteLabelTableManager() {
        val noteId = 2L
        val labelId = 3L
        val rowId = 1L
        tableManager.updateNoteLabel(rowId = rowId, noteId = noteId, labelId = labelId)

        assertEquals(1, rowId)
    }

    @Test
    fun test_deleteFunction_ofNoteLabelTableManager() {
        val rowId = 1L
        tableManager.deleteNoteLabel(rowId)

        val noteId = 1L
        val labelId = 3L
        val newRowId = tableManager.insertNoteLabel(noteId, labelId)
        assertEquals(1, newRowId)
    }
}