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
import com.bridgelabz.fundoonotes.repository.firestore_service.firebase_user.UserFireStoreManager
import com.bridgelabz.fundoonotes.repository.firestore_service.firebase_user.UserFireStoreManagerImpl
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.local_service.user_module.UserDatabaseManager
import com.bridgelabz.fundoonotes.repository.local_service.user_module.UserDbManagerImpl
import com.bridgelabz.fundoonotes.user_module.registration.model.RegistrationStatus
import com.bridgelabz.fundoonotes.user_module.registration.model.User
import com.bridgelabz.fundoonotes.user_module.registration.model.validateUser

class RegisterViewModel : ViewModel() {

    private lateinit var dbManager: UserDatabaseManager
    private val registrationResponse = MutableLiveData<RegistrationStatus>()
    private lateinit var userFireStoreManager: UserFireStoreManager

    fun onSignUpButtonClick(view: View, user: User) {
        if (validateUser(user)) {
            dbManager =
                UserDbManagerImpl(
                    DatabaseHelper(view.context)
                )
            userFireStoreManager = UserFireStoreManagerImpl()
            handelRegistration(user)
        }
    }

    /**
     * Function that assign the Registration status
     * to registrationResponse if the user is
     * inserted into USerDatabase.db
     */
    private fun handelRegistration(user: User) {
        if (dbManager.isUserRegistered(user))
            registrationResponse.value = RegistrationStatus.Failed
        else {
            dbManager.insert(user)
            userFireStoreManager.insertUser(user)
            registrationResponse.value = RegistrationStatus.Successful
        }
    }

    /**
     * Function that returns RegistrationStatus live data
     */
    fun getRegistrationStatus(): LiveData<RegistrationStatus> {
        return registrationResponse
    }
}