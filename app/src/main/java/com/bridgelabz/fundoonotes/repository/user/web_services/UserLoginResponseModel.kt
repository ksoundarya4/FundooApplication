/**
 * Fundoo Notes
 * @description UserLoginResponseModel is data class
 * that holds user information of logged user.
 * @file UserLoginResponseModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 29/03/2020
 */
package com.bridgelabz.fundoonotes.repository.user.web_services

data class UserLoginResponseModel(
    var id: String? = null,
    var ttl: Int = 0,
    var created: String? = null,
    var userId: String? = null,
    var role: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var imageUrl: String? = null
)
