/**
 * Fundoo Notes
 * @description UserViewModel Class extends ViewModel
 * which interacts with the repository.
 * @file UserViewModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 01.04/2020
 */
package com.bridgelabz.fundoonotes.user_module.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bridgelabz.fundoonotes.repository.common.UserRepository
import com.bridgelabz.fundoonotes.user_module.model.AuthState
import com.bridgelabz.fundoonotes.user_module.model.RegistrationStatus
import com.bridgelabz.fundoonotes.user_module.model.User

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private var registrationResponse =
        MutableLiveData<RegistrationStatus>(RegistrationStatus.Loading)
    private var loginResponse: LiveData<AuthState> = MutableLiveData<AuthState>()
    private var updatePasswordStatus: LiveData<Boolean> = MutableLiveData<Boolean>()

    fun signUpUser(user: User) {
        registrationResponse.value = repository.insertUser(user).value
    }

    fun getRegistrationStatus(): LiveData<RegistrationStatus> {
        return registrationResponse
    }

    fun userLogin(email: String, password: String, preferences: SharedPreferences) {
        loginResponse = repository.fetchUser(email, password, preferences)
    }

    fun getLoginResponse(): LiveData<AuthState> {
        return loginResponse
    }

    fun updateNewPassword(email: String, password: String, accessToken: String) {
        val user = repository.fetchUserFromLocalDb(email)
        if (user != null) {
            updatePasswordStatus = repository.updatePassword(password, accessToken = accessToken)
        }
    }

    fun getUpdatePasswordStatus(): LiveData<Boolean> {
        return updatePasswordStatus
    }
}