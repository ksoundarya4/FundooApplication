/**
 * Fundoo Notes
 * @description DatabaseManager Interface to perform
 * actions on user database.
 * @file DatabaseManager.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 30/01/2020
 */
package com.bridgelabz.fundoonotes.user_module.repository

import android.database.Cursor
import com.bridgelabz.fundoonotes.user_module.registration.model.User

interface DatabaseManager {
    fun open(): DatabaseManager
    fun close()
    fun insert(user : User,description : String)
    fun fetch(): Cursor
    fun update(KEY_ID : Long, userPropert : String , description : String): Int
    fun delete()
}