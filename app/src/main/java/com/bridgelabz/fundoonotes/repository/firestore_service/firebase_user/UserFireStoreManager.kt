/**
 * Fundoo Notes
 * @description UserFireStoreManager interface manages the
 * CRUD operations related to cloud fire store.
 * @file UserFireStoreManager.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 13/03/2020
 */
package com.bridgelabz.fundoonotes.repository.firestore_service.firebase_user

import com.bridgelabz.fundoonotes.user_module.registration.model.User

interface UserFireStoreManager {
    fun insertUser(user: User)
    fun updateUser(user: User)
    fun deleteUser()
    fun fetchUser(): User
}