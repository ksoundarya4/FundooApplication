package com.bridgelabz.fundoonotes.user_module.repository.local_service

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.bridgelabz.fundoonotes.user_module.registration.model.User
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserRegistrationContract.UserEntry.KEY_DOB
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserRegistrationContract.UserEntry.KEY_EMAIL
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserRegistrationContract.UserEntry.KEY_FIRSTNAME
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserRegistrationContract.UserEntry.KEY_LASTNAME
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserRegistrationContract.UserEntry.KEY_PASSWORD
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserRegistrationContract.UserEntry.KEY_PHONE_NUMBER
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserRegistrationContract.UserEntry.TABLE_NAME

class UserDbManagerImpl(
    private val databaseHelper: UserDbHelper
) : UserDatabaseManager {

    lateinit var database: SQLiteDatabase

    override fun open(): SQLiteDatabase {
        return databaseHelper.writableDatabase
    }

    override fun close() {
        databaseHelper.close()
    }

    override fun insert(user: User, description: String): Long {

        val values = ContentValues().apply {
            put(KEY_FIRSTNAME, user.firstName)
            put(KEY_LASTNAME, user.lastName)
            put(KEY_DOB, user.dateOfBirth)
            put(KEY_EMAIL, user.email)
            put(KEY_PASSWORD, user.password)
            put(KEY_PHONE_NUMBER, user.phoneNumber)
        }
        return database.insert(TABLE_NAME, null, values)
    }

    override fun fetch(): Cursor {
        database = databaseHelper.readableDatabase

        val columns =
            arrayOf<String>(
                BaseColumns._ID, KEY_FIRSTNAME,
                KEY_LASTNAME, KEY_DOB,
                KEY_EMAIL, KEY_EMAIL,
                KEY_PASSWORD, KEY_PHONE_NUMBER
            )
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(KEY_FIRSTNAME)
        val sortOrder = "$KEY_FIRSTNAME DESC"

        val cursor: Cursor = database.query(
            TABLE_NAME,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )

        cursor.moveToFirst()
        return cursor
    }

    override fun update(_id: Long, user: User, description: String): Int {
        database = open()

        val values = ContentValues().apply {
            put(KEY_FIRSTNAME, user.firstName)
            put(KEY_LASTNAME, user.lastName)
            put(KEY_DOB, user.dateOfBirth)
            put(KEY_EMAIL, user.email)
            put(KEY_PASSWORD, user.password)
            put(KEY_PHONE_NUMBER, user.phoneNumber)
        }
        val selection = "{${BaseColumns._ID} LIKE $_id"
        return database.update(
            TABLE_NAME,
            values,
            selection,
            null
        )
    }

    override fun delete(_id: Long) {
        database.delete(TABLE_NAME, BaseColumns._ID + "=" + _id, null)
    }
}