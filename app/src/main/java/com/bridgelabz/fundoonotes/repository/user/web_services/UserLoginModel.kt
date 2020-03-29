/**
 * Fundoo Notes
 * @description UserLoginModel is data class that
 * is used to login in user using email and password.
 * @file UserLoginModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 29/03/2020
 */
package com.bridgelabz.fundoonotes.repository.user.web_services

data class UserLoginModel(
    var email: String? = null,
    var password: String? = null
)
