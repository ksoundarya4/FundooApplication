package com.bridgelabz.fundoonotes.repository.common

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.bridgelabz.fundoonotes.user_module.model.AuthState
import com.bridgelabz.fundoonotes.user_module.model.RegistrationStatus
import com.bridgelabz.fundoonotes.user_module.model.User

interface UserRepository {
    fun insertUser(user: User): LiveData<RegistrationStatus>
    fun updateUser(user: User)
    fun updatePassword(password: String, accessToken: String): LiveData<Boolean>
    fun updateUserInDb(user: User)
    fun deleteUser(user: User)
    fun fetchUser(
        email: String,
        password: String,
        preferences: SharedPreferences
    ): LiveData<AuthState>

    fun fetchUserFromLocalDb(email: String): User?
}