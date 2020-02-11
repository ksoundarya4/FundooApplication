/**
 * Fundoo Notes
 * @description NoteDatabaseHelper class extends SQLiteOpenHelper
 * contains context and used to create FundooNotes table inside
 * FundooNotes.db database.
 * @file ksoundarya4
 * @author ksoundarya4
 * @version 1.0
 * @since 07/02/2020
 */
package com.bridgelabz.fundoonotes.note_module.note_repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

/**Constant that create table inside database*/
const val CREATE_TABLE = "Create table ${NoteRegistrationContract.NoteEntry.TABLE_NAME} " +
        "(" +
        " ${BaseColumns._ID} INTEGER PRIMARY KEY ," +
        "${NoteRegistrationContract.NoteEntry.KEY_TITLE} TEXT , " +
        "${NoteRegistrationContract.NoteEntry.KEY_DESCRIPTION} TEXT" +
        ")"

/**Constant that drops the table*/
const val DROP_ENTRIES = "DROP TABLE IF EXISTS ${NoteRegistrationContract.NoteEntry.TABLE_NAME}"

class NoteDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null,
    DATABASE_VERSION
) {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "App.db"
    }

    /**Function to create FundooNotes.db database*/
    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(CREATE_TABLE)
    }

    /**Function to up grade existing database*/
    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //use migration to perform onUpgrade
    }

    /**Function to downgrade existing database*/
    override fun onDowngrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        database.execSQL(DROP_ENTRIES)
        onCreate(database)
    }

    /**Function to return return writable database*/
    fun open() : SQLiteDatabase{
        return this.writableDatabase
    }
}