/**
 * Fundoo Notes
 * @description RegistrationActivity to get user information
 * and store them into UserRegistration datbase
 * @file RegisterActivity.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 02/02/2020
 */
package com.bridgelabz.fundoonotes.user_module.registration.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.user_module.login.view.LoginActivity
import com.bridgelabz.fundoonotes.user_module.login.view.toast
import com.bridgelabz.fundoonotes.user_module.registration.model.*
import com.bridgelabz.fundoonotes.user_module.registration.viewmodel.RegisterViewModel
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {

    private val registerViewModel by lazy {
        ViewModelProviders.of(this).get(RegisterViewModel::class.java)
    }

    lateinit var firstName: TextInputEditText
    lateinit var lastName: TextInputEditText
    lateinit var dateOfBirth: TextInputEditText
    lateinit var email: TextInputEditText
    lateinit var password: TextInputEditText
    lateinit var confirmPassword: TextInputEditText
    lateinit var phoneNumber: TextInputEditText
    lateinit var signUPButton: Button
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        onViews()
        onClickListener()
    }

    private fun onViews() {
        firstName = findViewById(R.id.first_name)
        lastName = findViewById(R.id.last_name_)
        dateOfBirth = findViewById(R.id.date_of_birth)
        email = findViewById(R.id.user_email)
        password = findViewById(R.id.password)
        confirmPassword = findViewById(R.id.confirm_password)
        phoneNumber = findViewById(R.id.phoneNumber)
        signUPButton = findViewById(R.id.button_sign_up)
    }

    private fun onClickListener() {
        signUPButton.setOnClickListener {

            val fName = firstName.editableText.toString()
            val lName = lastName.editableText.toString()
            val dob = dateOfBirth.editableText.toString()
            val userMail = email.editableText.toString()
            val userPass = password.editableText.toString()
            val userNumber = phoneNumber.editableText.toString()
            val cPassword = confirmPassword.editableText.toString()
            user = User(fName, lName, dob, userMail, userPass, userNumber)
                registerViewModel.onSingUpButtonClick((View(this)), user)
            registerViewModel.getRegistrationStatus().observe(
                this,
                Observer { handleRegistrationStatus(it) })
        }
    }

    private fun handleRegistrationStatus(registrationStatus: RegistrationStatus) {
        when (registrationStatus) {
            RegistrationStatus.Successful -> {
                toast("Registration Succesfull")
                Intent(this, LoginActivity::class.java).apply {
                    startActivity(this)
                }
            }
            RegistrationStatus.Failed -> {
                toast("Registration Failed")
                validateUserInput()
            }
        }
    }


    private fun validateUserInput() {
        if (!validateFirstName(user.firstName)) {
            firstName.error = "Enter valid name"
        }
        if (!validateLastName(user.lastName)) {
            lastName.error = "Enter valid name"
        }
        if (!validateDOB(user.dateOfBirth)) {
            dateOfBirth.error = "Invalid date"
        }
        if (!validateEmail(user.email)) {
            email.error = "Invalid email"
        }
        if (!validatePassword(user.password)) {
            password.error = "Invalid password"
        }
        if (password != confirmPassword) {
            confirmPassword.error = "Did not match password"
        }
        if (!validatePhone(user.phoneNumber)) {
            phoneNumber.error = "Invalid phone number"
        }
    }
}