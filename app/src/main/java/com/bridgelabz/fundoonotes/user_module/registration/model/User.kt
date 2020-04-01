/**
 * Fundoo Notes
 * @description User Class that contains first name,
 * last name, date of birth, email address, fundoo password,
 * and phone number.
 * @file User.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 30/01/2020
 */
package com.bridgelabz.fundoonotes.user_module.registration.model

import java.io.Serializable

data class User(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val email: String,
    val password: String,
    val phoneNumber: String
) : Serializable {
    var id: String? = null
    var image: String? = null
    var userId: String? = null

    constructor(firstName: String, lastName: String, email: String) : this(
        firstName = firstName,
        lastName = lastName,
        dateOfBirth = "",
        email = email,
        password = "",
        phoneNumber = ""
    )
}