package com.bridgelabz.fundoonotes.user_module.regex_util

import java.util.regex.Pattern

class RegexUtil {

    fun validateFirstName(firstName: String) {

    }

    fun validateEmail(email: String): Boolean {
        val emailExpression = "^[A-Za-z0-9+_.-]+@(.+)$"
        val emailPattern = Pattern.compile(emailExpression)
        return emailPattern.matcher(email).matches()
    }

    fun validatePassword(password: String): Boolean {
        val passwordExpression = "(?=.*[a-zA-Z])" + ".{5,})" //at least 5 character
        val passwordPattern = Pattern.compile(passwordExpression)
        return passwordPattern.matcher(password).matches()
    }
}