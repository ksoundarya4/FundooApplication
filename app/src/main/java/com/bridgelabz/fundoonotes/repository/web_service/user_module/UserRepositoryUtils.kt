package com.bridgelabz.fundoonotes.repository.web_service.user_module

import com.bridgelabz.fundoonotes.user_module.model.User


object UserRepositoryUtils {

    fun isUserAuthenticated(user: User, email: String, password: String): Boolean {
        val userEmail = user.email
        val userPassword = user.password
        if (userEmail == email && userPassword == password) return true
        return false
    }
}