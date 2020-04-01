/**
 * Fundoo Notes
 * @description UserDatabaseManager Interface to perform
 * actions on user database.
 * @file UserDatabaseManager.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 30/01/2020
 */
package com.bridgelabz.fundoonotes.repository.local_service.user_module

import com.bridgelabz.fundoonotes.user_module.model.AuthState
import com.bridgelabz.fundoonotes.user_module.model.User

interface UserDatabaseManager {
    fun insert(user: User): Long
    fun fetchUser(email : String): User?
    fun update(_id: Long, user: User): Int
    fun delete(_id: Long)
    fun deleteAll()
    fun authenticate(email: String, password: String): AuthState
    fun isUserRegistered(user: User): Boolean
    fun updatePassword(email: String, password: String): Boolean
}