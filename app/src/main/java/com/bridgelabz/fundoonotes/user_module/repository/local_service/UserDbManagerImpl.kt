/**
 * Fundoo Notes
 * @description UserDbManagerImpl that implements
 * UserDatabaseManager interface
 * @file UserDbManagerImpl.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 30/01/2020
 */
package com.bridgelabz.fundoonotes.user_module.repository.local_service

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.bridgelabz.fundoonotes.user_module.login.model.AuthState
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

    private lateinit var database: SQLiteDatabase

    /**
     * To insert a row into User Entry table
     *
     * @param user to be inserted
     */
    override fun insert(user: User): Long {

        database = databaseHelper.open()

        val values = ContentValues().apply {
            put(KEY_FIRSTNAME, user.firstName)
            put(KEY_LASTNAME, user.lastName)
            put(KEY_DOB, user.dateOfBirth)
            put(KEY_EMAIL, user.email)
            put(KEY_PASSWORD, user.password)
            put(KEY_PHONE_NUMBER, user.phoneNumber)
        }
        val rowId = database.insert(TABLE_NAME, null, values)
        databaseHelper.close()
        return rowId
    }

    /**
     * To fetch User Entry
     *
     * @return Cursor object
     */
    @SuppressLint("Recycle")
    override fun fetch(): Cursor {
        database = databaseHelper.readableDatabase

        val columns =
            arrayOf(
                BaseColumns._ID,
                KEY_FIRSTNAME,
                KEY_LASTNAME,
                KEY_DOB,
                KEY_EMAIL,
                KEY_PASSWORD,
                KEY_PHONE_NUMBER
            )
        //val selection = "$KEY_EMAIL = ?"
        // val selectionArgs = arrayOf(KEY_FIRSTNAME)
        // val sortOrder = "$KEY_FIRSTNAME DESC"

        return database.query(
            TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null
        ).apply {
            moveToFirst()
        }
    }

    /**
     * To update a row in User Entry Table
     * @param _id of row to be updates
     * @param user details to be inserted.
     * @return positive integer if row successfully updated.
     * */
    override fun update(_id: Long, user: User): Int {
        database = databaseHelper.open()

        val values = ContentValues().apply {
            put(KEY_FIRSTNAME, user.firstName)
            put(KEY_LASTNAME, user.lastName)
            put(KEY_DOB, user.dateOfBirth)
            put(KEY_EMAIL, user.email)
            put(KEY_PASSWORD, user.password)
            put(KEY_PHONE_NUMBER, user.phoneNumber)
        }
        val status = database.update(
            TABLE_NAME,
            values,
            BaseColumns._ID + "=" + _id,
            null
        )
        databaseHelper.close()
        return status
    }

    /**To delete a row in User Entry table
     *
     * @param _id of row to be deleted.
     */
    override fun delete(_id: Long) {
        database = databaseHelper.open()
        database.delete(TABLE_NAME, BaseColumns._ID + "=" + _id, null)
        database.close()
    }

    /**To delete all Entries of User Entry Table*/
    override fun deleteAll() {
        database = databaseHelper.open()
        database.delete(TABLE_NAME, null, null)
        database.close()
    }

    /**
     * Function To verify whether the user is present
     * in User Entry.
     *
     * @param user to be authenticated
     * @return live data of AuthState
     */
    @SuppressLint("Recycle")
    override fun isUserRegistered(user: User): Boolean {
        database = databaseHelper.readableDatabase

        val columns =
            arrayOf(
                BaseColumns._ID,
                KEY_FIRSTNAME,
                KEY_LASTNAME,
                KEY_DOB,
                KEY_EMAIL,
                KEY_PASSWORD,
                KEY_PHONE_NUMBER
            )
        val selection = "$KEY_EMAIL=?"

        val selectionArgs = arrayOf(user.email)

        val cursor = database.query(
            TABLE_NAME,
            columns,
            selection,
            selectionArgs,      //Where clause
            null,
            null,
            null
        )

        //if cursor has value then in user database there is user associated with this given email
        if (cursor != null && cursor.moveToFirst() && cursor.count > 0) {
            return true
        }
        //if user is not inserted into database.
        return false
    }


    /**
     * Function to authenticate if email is and password
     * entered matches the actual email and password present
     * in User Entry.
     *
     * @param email to be searched.
     * @return live data of AuthState
     */
    @SuppressLint("Recycle")
    override fun authenticate(email: String, password: String): AuthState {
        database = databaseHelper.readableDatabase

        val columns = arrayOf(
            BaseColumns._ID,
            KEY_FIRSTNAME,
            KEY_LASTNAME,
            KEY_DOB,
            KEY_EMAIL,
            KEY_PASSWORD,
            KEY_PHONE_NUMBER
        )
        val selection = "$KEY_EMAIL =?"

        val selectionArgs = arrayOf(email)

        val cursor = database.query(
            TABLE_NAME,// Selecting Table
            columns,//Selecting columns want to query
            selection,
            selectionArgs,//Where clause
            null,
            null,
            null
        )
        //if cursor has value then in user database there is user associated with this given email so return true
        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            val userEmail = cursor.getString(4)
            val userPassword = cursor.getString(5)
            if (userEmail == email && userPassword == password) {
                return AuthState.AUTH
            }
            return AuthState.AUTH_FAILED
        }
        //if email does not exist return false
        return AuthState.NOT_AUTH
    }
}