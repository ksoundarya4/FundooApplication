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
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
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
        dbHelper = DatabaseHelper(context)
        tableManager = NoteLabelTableManagerImpl(dbHelper)
    }

    @Test
    fun test_inserFunction_ofNoteLabelTabelManager() {
        val noteId = 1L
        val labelId = 3L
        val rowId = tableManager.insertNoteLabel(noteId = noteId, labelId = labelId)

        assertEquals(1, rowId)
    }
}