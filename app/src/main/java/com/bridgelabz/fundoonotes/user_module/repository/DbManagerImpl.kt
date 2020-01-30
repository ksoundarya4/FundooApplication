package com.bridgelabz.fundoonotes.user_module.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.bridgelabz.fundoonotes.user_module.registration.model.User
import com.bridgelabz.fundoonotes.user_module.repository.DataBaseHelper.Companion.DATABASE_NAME
import com.bridgelabz.fundoonotes.user_module.repository.DataBaseHelper.Companion.KEY_DOB
import com.bridgelabz.fundoonotes.user_module.repository.DataBaseHelper.Companion.KEY_EMAIL
import com.bridgelabz.fundoonotes.user_module.repository.DataBaseHelper.Companion.KEY_FIRSTNAME
import com.bridgelabz.fundoonotes.user_module.repository.DataBaseHelper.Companion.KEY_ID
import com.bridgelabz.fundoonotes.user_module.repository.DataBaseHelper.Companion.KEY_LASTNAME
import com.bridgelabz.fundoonotes.user_module.repository.DataBaseHelper.Companion.KEY_PASSWORD
import com.bridgelabz.fundoonotes.user_module.repository.DataBaseHelper.Companion.KEY_PHONE_NUMBER
import com.bridgelabz.fundoonotes.user_module.repository.DataBaseHelper.Companion.TABLE_NAME

class DbManagerImpl(
    private val context: Context,
    private val databaseHelper: DataBaseHelper,
    private var database: SQLiteDatabase
) : DatabaseManager {

    override fun open(): DatabaseManager {
        database = databaseHelper.writableDatabase
        return this
    }

    override fun close() {
        databaseHelper.close()
    }

    override fun insert(user: User, description: String) {
        val value = ContentValues()
        value.put(KEY_FIRSTNAME, user.firstName)
        value.put(KEY_LASTNAME, user.lastName)
        value.put(KEY_DOB, user.dateOfBirth.toString())
        value.put(KEY_EMAIL, user.email)
        value.put(KEY_PASSWORD, user.password)
        value.put(KEY_PHONE_NUMBER, user.phoneNumber)
        database.insert(DATABASE_NAME, null, value)
    }

    override fun fetch(): Cursor {
        val columns =
            arrayOf<String>(
                KEY_ID, KEY_FIRSTNAME,
                KEY_LASTNAME, KEY_DOB,
                KEY_EMAIL, KEY_EMAIL,
                KEY_PASSWORD, KEY_PHONE_NUMBER
            )
        val cursor: Cursor = database.query(
            TABLE_NAME, columns,
            null, null, null,
            null, null
        )
        cursor.moveToFirst()
        return cursor
    }

    override fun update(KEY_ID: Long, userProperty : String , description: String): Int {
       return 0
    }

    override fun delete() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}