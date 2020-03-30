package com.bridgelabz.fundoonotes.repository.user

import androidx.lifecycle.LiveData
import com.bridgelabz.fundoonotes.user_module.login.model.AuthState
import com.bridgelabz.fundoonotes.user_module.registration.model.RegistrationStatus
import com.bridgelabz.fundoonotes.user_module.registration.model.User
import com.google.android.gms.auth.api.Auth

interface UserRepository {
    fun insertUser(user: User): LiveData<RegistrationStatus>
    fun updateUser(user: User)
    fun deleteUser(user: User)
    fun fetchUser(email: String, password: String): LiveData<AuthState>
}