/**
 * Fundoo Notes
 * @description LabelTableManagerTest class to test functions of
 * LabelTableManagerImpl class.
 * @file LabelTableManagerTest.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 07/02/2020
 */
package com.bridgelabz.fundoonotes

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bridgelabz.fundoonotes.label_module.model.Label
import com.bridgelabz.fundoonotes.repository.common.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.local_service.lable_module.LabelTableManager
import com.bridgelabz.fundoonotes.repository.local_service.lable_module.LabelTableManagerImpl
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LabelTableManagerTest {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var labelTableManager: LabelTableManager

    @Before
    fun onCreate() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dbHelper =
            DatabaseHelper(context)
        labelTableManager = LabelTableManagerImpl(dbHelper)
    }

    @Test
    fun createLabel_testInsetLabelFunction() {
        val label = Label("Sound Notes")
        labelTableManager.insertLabel(label)
    }

    @Test
    fun createLabel_testFetchLabelsFunction() {
        val label = Label("First Label")
        labelTableManager.insertLabel(label)
        val labels = labelTableManager.fetchLabels()

        assertEquals(2, labels.size)
    }

    @Test
    fun fetchLabels_testUpdateLAbelFunction() {
        var labels = labelTableManager.fetchLabels()
        val label = labels[0]
        label.label = "Sound Updated Notes"
        labelTableManager.updateLabel(label)

        labels = labelTableManager.fetchLabels()
        assertEquals("Sound Updated Notes", labels[0].label)

    }

    @Test
    fun testDeleteLabelFunction() {
        val labelsBeforeDeleteFunctionExecuted = labelTableManager.fetchLabels()
        labelTableManager.deleteLabel(labelsBeforeDeleteFunctionExecuted[2].id!!.toLong())
        val labelsAfterDeleteFunctionExecuted = labelTableManager.fetchLabels()

        assertEquals(labelsBeforeDeleteFunctionExecuted.size - 1, labelsAfterDeleteFunctionExecuted.size)
    }
}