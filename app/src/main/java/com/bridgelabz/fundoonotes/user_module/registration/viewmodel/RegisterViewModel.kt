package com.bridgelabz.fundoonotes.user_module.registration.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import com.bridgelabz.fundoonotes.user_module.login.model.AuthState
import com.bridgelabz.fundoonotes.user_module.login.view.AuthListener
import com.bridgelabz.fundoonotes.user_module.regex_util.RegexUtil
import com.bridgelabz.fundoonotes.user_module.registration.model.User
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserDatabaseManager
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserDbHelper
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserDbManagerImpl

class RegisterViewModel : ViewModel() {

    lateinit var dbManager: UserDatabaseManager
    private val regexUtil = RegexUtil()
    var authListener: AuthListener? = null

    fun validateFirstName(firstName: String): Boolean {
        if (regexUtil.validateName(firstName)
            && firstName.isNotEmpty()
        ) return true
        return false
    }

    fun validateLastName(lastName: String): Boolean {
        if (regexUtil.validateName(lastName)
            && lastName.isNotEmpty()
        ) return true
        return false
    }

    fun validateDOB(dateOfBirth: String): Boolean {
        if (regexUtil.validateDOB(dateOfBirth)
            && dateOfBirth.isNotEmpty()
        ) return true
        return false
    }

    fun validateEmail(email: String): Boolean {
        if (regexUtil.validateEmail(email) &&
            email.isNotEmpty()
        ) return true
        return false
    }

    fun validatePassword(password: String): Boolean {
        if (regexUtil.validatePassword(password)
            && password.isNotEmpty()
        ) return true
        return false
    }

    fun validatePhone(phoneNumber: String): Boolean {
        if (regexUtil.validatePhone(phoneNumber)
            && phoneNumber.isNotEmpty()
        ) return true
        return false
    }

    fun validateUser(view: View, user: User) {
        if (validateFirstName(user.firstName)
            && validateLastName(user.lastName)
            && validateDOB(user.dateOfBirth)
            && validateEmail(user.email)
            && validatePassword(user.password)
            && validatePhone(user.phoneNumber)
        ) {
            dbManager = UserDbManagerImpl(UserDbHelper(view.context))
            dbManager.insert(user)
            val registrationResponse = dbManager.authenticate(user)
            if (registrationResponse.value == AuthState.AUTH)
                authListener?.onSuccess(registrationResponse)
            if (registrationResponse.value == AuthState.AUTH_FAILED)
                authListener?.onFailure("Enter valid inputs")
        }
    }
}