package com.bridgelabz.fundoonotes.user_module.registration.viewmodel

import androidx.lifecycle.ViewModel
import com.bridgelabz.fundoonotes.user_module.regex_util.RegexUtil
import com.bridgelabz.fundoonotes.user_module.registration.model.User
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserDbHelper
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserDbManagerImpl

class RegisterViewModel(private val dbHelper: UserDbHelper) : ViewModel() {

    private val dbManager = UserDbManagerImpl(dbHelper)
    private val regexUtil = RegexUtil()

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

    fun validateUser(user: User) {
        if (validateFirstName(user.firstName)
            && validateLastName(user.lastName)
            && validateDOB(user.dateOfBirth)
            && validateEmail(user.email)
            && validatePassword(user.password)
            && validatePhone(user.phoneNumber)
        ) {
            dbManager.insert(user)
        }
    }
}