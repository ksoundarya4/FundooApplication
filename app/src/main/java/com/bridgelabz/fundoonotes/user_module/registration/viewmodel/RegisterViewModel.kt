/**
 * Fundoo Notes
 * @description RegistrationViewModel class that extends the ViewModel
 * @file AuthViewModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 02/02/2020
 */
package com.bridgelabz.fundoonotes.user_module.registration.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bridgelabz.fundoonotes.user_module.registration.model.RegistrationStatus
import com.bridgelabz.fundoonotes.user_module.registration.model.User
import com.bridgelabz.fundoonotes.user_module.registration.model.validateUser
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserDatabaseManager
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserDbHelper
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserDbManagerImpl

class RegisterViewModel : ViewModel() {

    private lateinit var dbManager: UserDatabaseManager
    private val registrationResponse = MutableLiveData<RegistrationStatus>()

    fun onSingUpButtonClick(view: View, user: User) {
        if (validateUser(user)) {
            dbManager = UserDbManagerImpl(UserDbHelper(view.context))
            handelRegistration(user)
        } else {
            registrationResponse.value = RegistrationStatus.Failed
        }
    }

    /**
     * Function that assign the Registration status
     * to registrationResponse if the user is
     * inserted into USerDatabase.db
     */
    fun handelRegistration(user: User) {
        registrationResponse.value = dbManager.insert(user)
    }

    /**
     * Function that returns RegistrationStatus live data
     */
    fun getRegistrationStatus(): LiveData<RegistrationStatus> {
        return registrationResponse
    }
}