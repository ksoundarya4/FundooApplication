/**
 * Fundoo Notes
 * @description LabelTableManagerImpl Interface implements
 * LabelTableManager.kt
 * @file LabelTableManagerImpl.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 25/02/2020
 */
package com.bridgelabz.fundoonotes.repository.local_service.lable_module

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.bridgelabz.fundoonotes.label_module.model.Label
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper

class LabelTableManagerImpl(private val dbHelper: DatabaseHelper) : LabelTableManager {

    companion object {
        private const val TABLE_LABEL = "Labels"
        private const val LABEL_ID = "ID"
        private const val KEY_LABEL = "Label"
        const val CREATE_TABLE_LABEL =
            " Create Table $TABLE_LABEL ( " +
                    "$LABEL_ID INTEGER PRIMARY KEY, " +
                    "$KEY_LABEL TEXT NOT NULL)"
    }

    private lateinit var database: SQLiteDatabase

    override fun insertLabel(label: Label): Long {
        database = dbHelper.open()

        val values = ContentValues().apply {
            put(KEY_LABEL, label.label)
        }
        val rowId = database.insert(TABLE_LABEL, null, values)
        database.close()
        return rowId
    }

    override fun updateLabel(label: Label) {
        database = dbHelper.open()

        val values = ContentValues()
        values.put(KEY_LABEL, label.label)
        val where = "$LABEL_ID = ${label.id}"

        database.update(TABLE_LABEL, values, where, null)
        database.close()
    }

    override fun fetchLabels(): ArrayList<Label> {
        val labels = ArrayList<Label>()
        database = dbHelper.readableDatabase

        val columns = arrayOf(
            LABEL_ID,
            KEY_LABEL
        )

        val cursor = database.query(
            TABLE_LABEL,
            columns,
            null,
            null,
            null,
            null,
            null
        )

        cursor.moveToFirst()
        do {
            val id = cursor.getLong(cursor.getColumnIndex(LABEL_ID))
            val labelValue = cursor.getString(cursor.getColumnIndex(KEY_LABEL))

            val label = Label(labelValue)
            label.id = id.toInt()

            labels.add(label)
        } while (cursor.moveToNext())
        cursor.close()
        database.close()
        return labels
    }

    override fun deleteLabel(id: Long) {
        database = dbHelper.open()
        val whereClause = "$LABEL_ID = $id"
        database.delete(TABLE_LABEL, whereClause, null)
        database.close()
    }
}