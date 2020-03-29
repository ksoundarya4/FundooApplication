/**
 * Fundoo Notes
 * @description UserSignUpResponseModel is data class
 * that holds user information of logged user.
 * @file UserSignUpResponseModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 29/03/2020
 */
package com.bridgelabz.fundoonotes.repository.user.web_services

data class UserSignUpResponseModel(
    var success: Boolean? = null,
    var message: String? = null
)