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
import com.bridgelabz.fundoonotes.repository.user.UserRepository
import com.bridgelabz.fundoonotes.user_module.model.AuthState
import com.bridgelabz.fundoonotes.user_module.model.RegistrationStatus
import com.bridgelabz.fundoonotes.user_module.model.User

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private var registrationResponse: LiveData<RegistrationStatus> =
        MutableLiveData<RegistrationStatus>()
    private var loginResponse: LiveData<AuthState> = MutableLiveData<AuthState>()

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
}