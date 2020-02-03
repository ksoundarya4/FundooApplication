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
import com.bridgelabz.fundoonotes.user_module.login.view.AuthListener
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserDatabaseManager
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserDbHelper
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserDbManagerImpl

class AuthViewModel : ViewModel() {

    lateinit var dbManager: UserDatabaseManager
    lateinit var loginResponse: LiveData<AuthState>
    var authListener: AuthListener? = null

    fun onLoginButtonClick(view: View, email: String, password: String) {
        authListener?.onStarted()

        dbManager = UserDbManagerImpl(UserDbHelper(view.context))
        loginResponse = dbManager.authenticate(email, password)

        when (loginResponse.value) {
            AuthState.AUTH -> authListener?.onSuccess(loginResponse)
            AuthState.AUTH_FAILED -> authListener?.onFailure("Enter Valid Email and Password")
            else -> authListener?.onFailure("First Register and then try to login")
        }
    }
}