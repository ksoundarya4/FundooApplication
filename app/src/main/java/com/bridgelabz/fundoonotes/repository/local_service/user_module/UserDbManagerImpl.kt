/**
 * Fundoo Notes
 * @description UserDbManagerImpl that implements
 * UserDatabaseManager interface
 * @file UserDbManagerImpl.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 30/01/2020
 */
package com.bridgelabz.fundoonotes.repository.local_service.user_module

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.user_module.login.model.AuthState
import com.bridgelabz.fundoonotes.user_module.registration.model.User

class UserDbManagerImpl(
    private val databaseHelper: DatabaseHelper
) : UserDatabaseManager {

    companion object UserEntry {
        const val TABLE_USER = "UserEntry"
        const val USER_ID = "ID"
        const val KEY_FIRSTNAME = "FirstName"
        const val KEY_LASTNAME = "LastName"
        const val KEY_DOB = "DateOfBirth"
        const val KEY_EMAIL = "Email"
        const val KEY_PASSWORD = "Password"
        const val KEY_PHONE_NUMBER = "PhoneNumber"

        const val CREATE_USER_TABLE =
            " Create Table $TABLE_USER (" +
                    "$USER_ID INTEGER PRIMARY KEY," +
                    "$KEY_FIRSTNAME CHAR(15)," +
                    "$KEY_LASTNAME CHAR(15)," +
                    "$KEY_DOB CHAR(10)," +
                    "$KEY_EMAIL VARCHAR(200)," +
                    "$KEY_PASSWORD VARCHAR(20)," +

                    "$KEY_PHONE_NUMBER CHAR(20))"
    }

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
        val rowId = database.insert(TABLE_USER, null, values)
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
                USER_ID,
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
            TABLE_USER,
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
            TABLE_USER,
            values,
            "$USER_ID=$_id",
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
        database.delete(TABLE_USER, "$USER_ID=$_id", null)
        database.close()
    }

    /**To delete all Entries of User Entry Table*/
    override fun deleteAll() {
        database = databaseHelper.open()
        database.delete(TABLE_USER, null, null)
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
                USER_ID,
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
            TABLE_USER,
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
            USER_ID,
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
            TABLE_USER,// Selecting Table
            columns,//Selecting columns want to query
            selection,
            selectionArgs,//Where clause
            null,
            null,
            null
        )
        //if cursor has value then in user database there is user associated with this given email so return true
        if (cursor != null && cursor.moveToFirst() && cursor.count > 0) {
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

    /**Function to update password in user table
     *
     * @param email
     * @param password to be updated
     * @return true if password is updated
     */
    @SuppressLint("Recycle")
    override fun updatePassword(email: String, password: String): Boolean {
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
            TABLE_USER,// Selecting Table
            columns,//Selecting columns want to query
            selection,
            selectionArgs,//Where clause
            null,
            null,
            null
        )
        //if cursor has value then in user database there is user associated with this given email so return true
        if (cursor != null && cursor.moveToFirst() && cursor.count > 0) {
            val rowId = cursor.getLong(cursor.getColumnIndex(USER_ID))
            val firstName = cursor.getString(cursor.getColumnIndex(KEY_FIRSTNAME))
            val lastName = cursor.getString(cursor.getColumnIndex(KEY_LASTNAME))
            val dateOfBirth = cursor.getString(cursor.getColumnIndex(KEY_DOB))
            val userEmail = cursor.getString(cursor.getColumnIndex(KEY_EMAIL))
            val phoneNumber = cursor.getString(cursor.getColumnIndex(KEY_PHONE_NUMBER))
            val user = User(firstName, lastName, dateOfBirth, userEmail, password, phoneNumber)
            Log.d("userInfo", user.toString())
            update(rowId, user)
            return true
        }
        return false
    }
}