package com.bridgelabz.fundoonotes.repository.user

import com.bridgelabz.fundoonotes.user_module.registration.model.User


object UserRepositoryUtils {

    fun isUserAuthenticated(user: User, email: String, password: String): Boolean {
        val userEmail = user.email
        val userPassword = user.password
        if (userEmail == email && userPassword == password) return true
        return false
    }
}