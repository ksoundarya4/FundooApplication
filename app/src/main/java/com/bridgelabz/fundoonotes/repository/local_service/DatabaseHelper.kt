package com.bridgelabz.fundoonotes.repository.local_service

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper.NoteRegistrationContract.NoteEntry.TABLE_NOTE
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper.UserRegistrationContract.UserEntry.TABLE_USER

private const val CREATE_USER_TABLE =
    " Create Table $TABLE_USER (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${DatabaseHelper.UserRegistrationContract.UserEntry.KEY_FIRSTNAME} CHAR(15)," +
            "${DatabaseHelper.UserRegistrationContract.UserEntry.KEY_LASTNAME} CHAR(15)," +
            "${DatabaseHelper.UserRegistrationContract.UserEntry.KEY_DOB} CHAR(10)," +
            "${DatabaseHelper.UserRegistrationContract.UserEntry.KEY_EMAIL} VARCHAR(200)," +
            "${DatabaseHelper.UserRegistrationContract.UserEntry.KEY_PASSWORD} VARCHAR(20)," +
            "${DatabaseHelper.UserRegistrationContract.UserEntry.KEY_PHONE_NUMBER} CHAR(20))"

private const val CREATE_NOTE_TABLE =
    " Create Table $TABLE_NOTE ( " +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
            "${DatabaseHelper.NoteRegistrationContract.NoteEntry.KEY_TITLE} TEXT NOT NULL, " +
            "${DatabaseHelper.NoteRegistrationContract.NoteEntry.KEY_DESCRIPTION} TEXT NOT NULL)"

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME, null,
        DATABASE_VERSION
    ) {

    /**Function to create new User database*/
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
    }

    /**Function to upgade the existing user database*/
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (DATABASE_VERSION > 1) {
            db.execSQL(CREATE_NOTE_TABLE)
        }
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
        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "App.db"
    }

    object UserRegistrationContract {
        object UserEntry : BaseColumns {
            const val TABLE_USER = "UserEntry"
            const val KEY_FIRSTNAME = "FirstName"
            const val KEY_LASTNAME = "LastName"
            const val KEY_DOB = "DateOfBirth"
            const val KEY_EMAIL = "Email"
            const val KEY_PASSWORD = "Password"
            const val KEY_PHONE_NUMBER = "PhoneNumber"
        }
    }

    object NoteRegistrationContract {
        object NoteEntry : BaseColumns {
            const val TABLE_NOTE = "Notes"
            const val KEY_TITLE = "Title"
            const val KEY_DESCRIPTION = "Description"
        }
    }
}
