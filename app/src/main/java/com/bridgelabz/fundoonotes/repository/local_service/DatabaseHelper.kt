package com.bridgelabz.fundoonotes.repository.local_service

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper.UserNoteRegistrationContract.UserNote.TABLE_USER_NOTE
import com.bridgelabz.fundoonotes.repository.local_service.lable_module.LabelTableManagerImpl.Companion.CREATE_TABLE_LABEL
import com.bridgelabz.fundoonotes.repository.local_service.note_label_relation_module.NoteLabelTableManagerImpl.Companion.CREATE_TABLE_NOTE_LABEL
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteTableManagerImpl.NoteEntry.CREATE_NOTE_TABLE
import com.bridgelabz.fundoonotes.repository.local_service.user_module.UserDbManagerImpl.UserEntry.CREATE_USER_TABLE

private const val CREATE_USER_NOTE_TABLE =
    "Create Table If Not Exists $TABLE_USER_NOTE (" +
            " ${BaseColumns._ID} INTEGER PRIMARY KEY, " +
            "${DatabaseHelper.UserNoteRegistrationContract.UserNote.KEY_USER_ID} INTEGER, " +
            "${DatabaseHelper.UserNoteRegistrationContract.UserNote.KEY_NOTE_ID} INTEGER)"

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME, null,
        DATABASE_VERSION
    ) {

    /**Function to create new User database*/
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
        db.execSQL(CREATE_NOTE_TABLE)
        db.execSQL(CREATE_TABLE_LABEL)
    }

    /**Function to upgrade the existing user database*/
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (DATABASE_VERSION < VERSION_TWO) {
            db.execSQL(CREATE_NOTE_TABLE)
        }
        if (DATABASE_VERSION < VERSION_THREE) {
            db.execSQL(CREATE_TABLE_LABEL)
        }
        if (DATABASE_VERSION < VERSION_FOUR)
            db.execSQL(CREATE_TABLE_NOTE_LABEL)
    }

    /**Function to Downgrade*/
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    /**Function to open database to write*/
    fun open(): SQLiteDatabase {
        return this.writableDatabase
    }

    companion object {
        const val VERSION_ONE = 1
        const val VERSION_TWO = 2
        const val VERSION_THREE = 3
        const val VERSION_FOUR = 4
        const val DATABASE_VERSION = VERSION_THREE
        const val DATABASE_NAME = "App.db"
    }

    object UserNoteRegistrationContract {
        object UserNote : BaseColumns {
            const val TABLE_USER_NOTE = "User Notes"
            const val KEY_USER_ID = "User_Id"
            const val KEY_NOTE_ID = "Note_Id"
        }
    }
}