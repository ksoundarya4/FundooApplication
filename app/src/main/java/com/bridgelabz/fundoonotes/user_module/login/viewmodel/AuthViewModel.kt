/**
 * Fundoo Notes
 * @description AuthViewModel class that extends the ViewModel
 * @file AuthViewModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 02/02/2020
 */
package com.bridgelabz.fundoonotes.user_module.login.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bridgelabz.fundoonotes.user_module.login.model.AuthState
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserDatabaseManager
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserDbHelper
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserDbManagerImpl

class AuthViewModel : ViewModel() {

    lateinit var dbManager: UserDatabaseManager
    lateinit var loginResponse: LiveData<AuthState>

    fun onLoginButtonClick(view: View, email: String, password: String) {

        dbManager = UserDbManagerImpl(UserDbHelper(view.context))
        handleLogin(email, password)
    }

    fun handleLogin(email: String, password: String) {
        loginResponse = dbManager.authenticate(email, password)
    }

    fun getLoginStatus(): LiveData<AuthState> {
        return loginResponse
    }
}