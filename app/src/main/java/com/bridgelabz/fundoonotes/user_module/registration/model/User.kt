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

import java.time.LocalDate

data class User(
    private val firstName: String,
    private val lastName: String,
    private val dateOfBirth: LocalDate,
    private val email: String,
    private val password: String,
    private val phoneNumber: String
)