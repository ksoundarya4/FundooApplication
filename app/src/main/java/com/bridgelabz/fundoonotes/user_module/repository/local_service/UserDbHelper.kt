/**
 * Fundoo Notes
 * @description DatabaseHandler class that extends
 * SQLiteOpenHelper used to store User Registration
 * information.
 * @file DatabaseHandler.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 30/01/2020
 */
package com.bridgelabz.fundoonotes.user_module.repository.local_service

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserRegistrationContract.UserEntry.KEY_DOB
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserRegistrationContract.UserEntry.KEY_EMAIL
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserRegistrationContract.UserEntry.KEY_FIRSTNAME
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserRegistrationContract.UserEntry.KEY_LASTNAME
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserRegistrationContract.UserEntry.KEY_PASSWORD
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserRegistrationContract.UserEntry.KEY_PHONE_NUMBER
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserRegistrationContract.UserEntry.TABLE_NAME

private const val CREATE_TABLE =
    " Create Table $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$KEY_FIRSTNAME CHAR(15)," +
            "$KEY_LASTNAME CHAR(15)," +
            "$KEY_DOB CHAR(10)," +
            "$KEY_EMAIL VARCHAR(200)," +
            "$KEY_PASSWORD VARCHAR(20)," +
            "$KEY_PHONE_NUMBER CHAR(20))"

private const val DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

class UserDbHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME, null,
        DATABASE_VERSION
    ) {

    /**Function to create new User database*/
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    /**Function to upgade the existing user database*/
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DELETE_ENTRIES)
        onCreate(db)
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
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "UserRegistration.db"
    }
}