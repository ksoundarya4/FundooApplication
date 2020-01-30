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
package com.bridgelabz.fundoonotes.user_module.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "UserDatabase"
        private val TABLE_NAME = "UserTable"
        private val KEY_ID = "id"
        private val KEY_FIRSTNAME = "firstName"
        private val KEY_LASTNAME = "lastName"
        private val KEY_DOB = "dateOfBirth"
        private val KEY_EMAIL = "email"
        private val KEY_PASSWORD = "password"
        private val KEY_PHONE_NUMBER = "phoneNumber"
    }

    /**Function to create new User database*/
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FIRSTNAME + " VARCHAR(15),"
                + KEY_LASTNAME + "VARCHAR(15)," + KEY_DOB + "DATE," + KEY_EMAIL + " VARCHAR(200),"
                + KEY_PASSWORD + "TEXT," + KEY_PHONE_NUMBER + "VARCHAR(10)" + ")")
        db?.execSQL(CREATE_USER_TABLE)
    }

    /**Function to upgade the existing user database*/
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME)
        onCreate(db)
    }
}