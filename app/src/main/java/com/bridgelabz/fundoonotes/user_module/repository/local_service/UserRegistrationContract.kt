/**
 * Fundoo Notes
 * @description UsrRegistrationContract Object that contain
 * UserEntry Object which Implements BaseColumns contain
 * UserRegistration database column names.
 * @file UserRegistrationContract.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 31/02/2020
 */
package com.bridgelabz.fundoonotes.user_module.repository.local_service

import android.provider.BaseColumns

object UserRegistrationContract {
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "UserEntry"
        const val KEY_FIRSTNAME = "FirstName"
        const val KEY_LASTNAME = "LastName"
        const val KEY_DOB = "DateOfBirth"
        const val KEY_EMAIL = "Email"
        const val KEY_PASSWORD = "Password"
        const val KEY_PHONE_NUMBER = "PhoneNumber"
    }
}