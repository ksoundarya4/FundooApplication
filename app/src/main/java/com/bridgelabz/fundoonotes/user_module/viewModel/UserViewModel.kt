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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bridgelabz.fundoonotes.repository.common.UserRepository
import com.bridgelabz.fundoonotes.user_module.model.AuthState
import com.bridgelabz.fundoonotes.user_module.model.RegistrationStatus
import com.bridgelabz.fundoonotes.user_module.model.User
import com.facebook.AccessToken

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private var registrationResponse: LiveData<RegistrationStatus> =
        MutableLiveData<RegistrationStatus>()
    private var loginResponse: LiveData<AuthState> = MutableLiveData<AuthState>()
    private val updatePasswordStatus = MutableLiveData<Boolean>()

    fun signUpUser(user: User) {
        registrationResponse = repository.insertUser(user)
    }

    fun getRegistrationStatus(): LiveData<RegistrationStatus> {
        return registrationResponse
    }

    fun userLogin(email: String, password: String) {
        loginResponse = repository.fetchUser(email, password)
    }

    fun getLoginResponse(): LiveData<AuthState> {
        return loginResponse
    }

    fun updateNewPassword(email: String, password: String, accessToken: String) {
        val user = repository.fetchUserFromLocalDb(email)
        if (user != null) {
            updatePasswordStatus.value =
                repository.updatePassword(password, accessToken = accessToken)
        } else
            updatePasswordStatus.value = false
    }

    fun getUpdatePasswordStatus(): LiveData<Boolean> {
        return updatePasswordStatus
    }
}