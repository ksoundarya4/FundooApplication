/**
 * Fundoo Notes
 * @description VerifyUser class that contains
 * validations for user inputs from registration activity.
 * @file VerifyUser.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 03/02/2020
 */
package com.bridgelabz.fundoonotes.user_module.registration.model

import com.bridgelabz.fundoonotes.user_module.regex_util.RegexUtil

val regexUtil = RegexUtil()

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

fun validateUser(user: User): Boolean {
    if (validateFirstName(user.firstName)
        && validateLastName(user.lastName)
        && validateDOB(user.dateOfBirth)
        && validateEmail(user.email)
        && validatePassword(user.password)
        && validatePhone(user.phoneNumber)
    ) return true
    return false
}