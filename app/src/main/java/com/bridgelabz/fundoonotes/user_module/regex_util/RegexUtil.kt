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
    fun validateName(name: String): Boolean {
        val nameExpression = "^[a-zA-Z\\s]*$"
        val namePattern = Pattern.compile(nameExpression)
        return namePattern.matcher(name).matches()
    }

    /** Function to validate user email address*/
    fun validateEmail(email: String): Boolean {
        val emailExpression = "^[A-Za-z0-9+_.-]+@(.+)$"
        val emailPattern = Pattern.compile(emailExpression)
        return emailPattern.matcher(email).matches()
    }

    /** Function to validate user email password*/
    fun validatePassword(password: String): Boolean {
        val passwordExpression = "(?=.*[a-zA-Z])" + ".{5,}" //at least 5 character
        val passwordPattern = Pattern.compile(passwordExpression)
        return passwordPattern.matcher(password).matches()
    }

    /**Function to validate user phone number*/
    fun validatePhone(phoneNumber: String): Boolean {
        val phoneNumberExpression = "^\\d{10}$"
        val phonePattern = Pattern.compile(phoneNumberExpression)
        return phonePattern.matcher(phoneNumber).matches()
    }

    /**Function to validate date of birth*/
    fun validateDOB(dateOfBirth: String): Boolean {
        val dateExpression =
            "([0]?[1-9]|[1|2][0-9]|[3][0|1])[./-]([0]?[1-9]|[1][0-2])[./-]([0-9]{4}|[0-9]{2})\$"
        val datePattern = Pattern.compile(dateExpression)
        return datePattern.matcher(dateOfBirth).matches()
    }
}