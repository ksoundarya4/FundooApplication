/**
 * Fundoo Notes
 * @description RegexUtil Class to validate User
 * properties.
 * @file RegexUtil.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 30/01/2020
 */
package com.bridgelabz.fundoonotes.user_module.regex_util

import java.util.regex.Pattern

class RegexUtil {

    /**Function to validate User first name*/
    fun validateFirstName(firstName: String) {

    }

    /** Function to validate user email address*/
    fun validateEmail(email: String): Boolean {
        val emailExpression = "^[A-Za-z0-9+_.-]+@(.+)$"
        val emailPattern = Pattern.compile(emailExpression)
        return emailPattern.matcher(email).matches()
    }

    /** Function to validate user email password*/
    fun validatePassword(password: String): Boolean {
        val passwordExpression = "(?=.*[a-zA-Z])" + ".{5,})" //at least 5 character
        val passwordPattern = Pattern.compile(passwordExpression)
        return passwordPattern.matcher(password).matches()
    }
}