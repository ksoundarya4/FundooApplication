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
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.user_module.login.view.LoginActivity
import com.bridgelabz.fundoonotes.user_module.login.view.toast
import com.bridgelabz.fundoonotes.user_module.regex_util.RegexUtil
import com.bridgelabz.fundoonotes.user_module.registration.model.RegistrationStatus
import com.bridgelabz.fundoonotes.user_module.registration.model.User
import com.bridgelabz.fundoonotes.user_module.registration.viewmodel.RegisterViewModel
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity(), RegistrationListener {

    private val registerViewModel by lazy {
        ViewModelProviders.of(this).get(RegisterViewModel::class.java)
    }

    lateinit var firstName: TextInputEditText
    lateinit var lastName: TextInputEditText
    lateinit var dateOfBirth: TextInputEditText
    lateinit var email: TextInputEditText
    lateinit var password: TextInputEditText
    lateinit var phoneNumber: TextInputEditText
    lateinit var signUPButton: Button
    lateinit var user: User

    val regexUtil = RegexUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        onViews()
        registerViewModel.registrationListener = this
        onClickListener()
    }

    private fun onViews() {
        firstName = findViewById(R.id.first_name)
        lastName = findViewById(R.id.last_name_)
        dateOfBirth = findViewById(R.id.date_of_birth)
        email = findViewById(R.id.user_email)
        password = findViewById(R.id.password)
        phoneNumber = findViewById(R.id.phoneNumber)
        signUPButton = findViewById<Button>(R.id.button_sign_up)
    }

    private fun onClickListener() {
        signUPButton.setOnClickListener {

            val fName = firstName.editableText.toString()
            val lName = lastName.editableText.toString()
            val dob = dateOfBirth.editableText.toString()
            val userMail = email.editableText.toString()
            val userPass = password.editableText.toString()
            val userNumber = phoneNumber.editableText.toString()
            user = User(fName, lName, dob, userMail, userPass, userNumber)
            registerViewModel.validateUser((View(this)), user)
        }
    }

    override fun onSuccess(liveDate: LiveData<RegistrationStatus>) {
        liveDate.observe(this, Observer { toast("Registration $it") })
        Intent(this, LoginActivity::class.java).apply {
            startActivity(this)
        }
    }

    override fun onFailure(message: String) {
        toast(message)
    }

    override fun validateUserInput() {
        if (!regexUtil.validateName(firstName.toString()))
            firstName.error = "Enter valid name"

        if (!regexUtil.validateName(lastName.toString()))
            lastName.error = "Enter valid name"

        if (!regexUtil.validateDOB(dateOfBirth.toString()))
            dateOfBirth.error = "Enter date of birth in dd/MM/yyyy format"

        if (!regexUtil.validateEmail(email.toString()))
            email.error = "Enter valid email address"

        if (!regexUtil.validatePassword(password.toString()))
            password.error = "Enter valid password"

        if (!regexUtil.validatePhone(phoneNumber.toString()))
            phoneNumber.error = "Enter valid phone number"
    }
}