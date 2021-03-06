/**
 * Fundoo Notes
 * @description RegistrationActivity to get user information
 * and store them into UserRegistration datbase
 * @file RegisterActivity.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 02/02/2020
 */
package com.bridgelabz.fundoonotes.user_module.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.user_module.viewModel.UserViewModel
import com.bridgelabz.fundoonotes.user_module.viewModel.UserViewModelFactory
import com.bridgelabz.fundoonotes.user_module.model.*
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {

    private val firstName by lazy {
        findViewById<TextInputEditText>(R.id.first_name)
    }
    private val lastName by lazy {
        findViewById<TextInputEditText>(R.id.last_name_)
    }
    private val dateOfBirth by lazy {
        findViewById<TextInputEditText>(R.id.date_of_birth)
    }
    private val email by lazy {
        findViewById<TextInputEditText>(R.id.user_email)
    }
    private val password by lazy {
        findViewById<TextInputEditText>(R.id.password)
    }
    private val confirmPassword by lazy {
        findViewById<TextInputEditText>(R.id.confirm_password)
    }
    private val phoneNumber by lazy {
        findViewById<TextInputEditText>(R.id.phoneNumber)
    }
    private val signUPButton by lazy {
        findViewById<Button>(R.id.button_sign_up)
    }
    private val registerActivity by lazy {
        findViewById<ConstraintLayout>(R.id.register_constraint_layout)
    }
    private lateinit var user: User

    private val userViewModelFactory =
        UserViewModelFactory(
            this
        )

    private val userViewModel by lazy {
        ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setSignUpClickListener()
        setHideKeyboardOnTouch(
            this,
            registerActivity
        )
    }

    private fun setSignUpClickListener() {
        signUPButton.setOnClickListener {

            val fName = firstName.editableText.toString()
            val lName = lastName.editableText.toString()
            val dob = dateOfBirth.editableText.toString()
            val userMail = email.editableText.toString()
            val userPass = password.editableText.toString()
            val userNumber = phoneNumber.editableText.toString()
            user = User(
                fName,
                lName,
                dob,
                userMail,
                userPass,
                userNumber
            )

            val cPassword = confirmPassword.editableText.toString()
            validateUserInput()
            if (validateConfirmPassword(
                    userPass,
                    cPassword
                )
            ) {
                userViewModel.signUpUser(user)
                userViewModel.getRegistrationStatus().observe(this, Observer {
                    handleRegistrationStatus(it)
                })
            } else {
                confirmPassword.error = "Did not match password"
            }
        }
    }

    private fun handleRegistrationStatus(registrationStatus: RegistrationStatus) {
        when (registrationStatus) {
            RegistrationStatus.Successful -> {
                toast("Registration Successful")
                Intent(this, LoginActivity::class.java).apply {
                    startActivity(this)
                }
                finish()
            }
            RegistrationStatus.Failed -> {
                toast("Registration Failed")
            }
            RegistrationStatus.Loading -> {
                toast("Signing Up")
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
        if (!validatePhone(user.phoneNumber)) {
            phoneNumber.error = "Invalid phone number"
        }
    }
}