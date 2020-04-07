package com.bridgelabz.fundoonotes.repository.common

import androidx.lifecycle.LiveData
import com.bridgelabz.fundoonotes.user_module.model.AuthState
import com.bridgelabz.fundoonotes.user_module.model.RegistrationStatus
import com.bridgelabz.fundoonotes.user_module.model.User

interface UserRepository {
    fun insertUser(user: User): LiveData<RegistrationStatus>
    fun updateUser(user: User)
    fun updatePassword(password: String, accessToken: String): Boolean
    fun deleteUser(user: User)
    fun fetchUser(email: String, password: String): LiveData<AuthState>
    fun fetchUserFromLocalDb(email: String): User?
}