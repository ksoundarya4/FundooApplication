/**
 * Fundoo Notes
 * @description UserResponseModel is a model class to post and get
 * user from sever.
 * @file UserResponseModel
 * @author ksoundarya4
 * @version 1.0
 * @since 23/03/2020
 */
package com.bridgelabz.fundoonotes.repository.api_service

import com.bridgelabz.fundoonotes.user_module.registration.model.User

class UserResponseModel {
    var firstName: String? = null
    var lastName: String? = null
    var phoneNumber: String? = null
    var imageUrl: String? = null
    var role: String? = null
    var service: String? = null
    var createdDate: String? = null
    var modifiedDate: String? = null
    var username: String? = null
    var email: String? = null
    var emailVerified = false
    var id: String? = null

    private fun UserResponseModel.convertToUser(): User {
        val firstName = this.firstName
        val lastName = this.lastName
        val email = this.email
        val phoneNumber = this.phoneNumber
        val id = this.id
        val user = User(
            firstName = firstName!!, lastName = lastName!!, dateOfBirth = "", email = email!!,
            password = "",
            phoneNumber = phoneNumber!!
        )
        user.id = id
        return  user
    }
}