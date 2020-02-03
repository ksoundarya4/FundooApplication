/**
 * Fundoo Notes
 * @description RegistrationListener interface to checkout
 * user registration was successful or not
 * @file RegisterActivity.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 02/02/2020
 */
package com.bridgelabz.fundoonotes.user_module.registration.view

import androidx.lifecycle.LiveData
import com.bridgelabz.fundoonotes.user_module.registration.model.RegistrationStatus

interface RegistrationListener {
    fun onSuccess(liveDate: LiveData<RegistrationStatus>)
    fun onFailure(message: String)
    fun validateUserInput()
}
