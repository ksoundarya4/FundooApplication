/**
 * Fundoo Notes
 * @description UserSignUpModel is data class that
 * contains user information for sign up.
 * @file UserSignUpModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 29/03/2020
 */
package com.bridgelabz.fundoonotes.repository.user.web_services

data class UserSignUpModel(
    var firstName: String? = null,
    var lastName: String? = null,
    var phoneNumber: String? = null,
    var imageUrl: String? = null,
    var role: String? = null,
    var service: String? = null,
    var createdDate: String? = null,
    var modifiedDate: String? = null,
    var addresses: List<Addresses>? = null,
    var realm: String? = null,
    var username: String? = null,
    var email: String? = null,
    var emailVerified: Boolean = false,
    var id: String? = null
)