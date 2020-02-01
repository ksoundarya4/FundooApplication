/**
 * Fundoo Notes
 * @description DatabaseManager Interface to perform
 * actions on user database.
 * @file DatabaseManager.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 30/01/2020
 */
package com.bridgelabz.fundoonotes.user_module.repository.local_service

import android.database.Cursor
import com.bridgelabz.fundoonotes.user_module.registration.model.User

interface UserDatabaseManager {
    fun insert(user: User): Long
    fun fetch(): Cursor
    fun update(_id: Long, user: User): Int
    fun delete(_id: Long)
    fun deleteAll()
}