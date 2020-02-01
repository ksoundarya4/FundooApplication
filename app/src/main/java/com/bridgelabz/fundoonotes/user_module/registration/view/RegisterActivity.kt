package com.bridgelabz.fundoonotes.user_module.registration.view

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.user_module.registration.model.User
import com.bridgelabz.fundoonotes.user_module.registration.viewmodel.RegisterViewModel
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserDbHelper
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {

    private val registerViewModel by lazy { RegisterViewModel(UserDbHelper(this)) }

    lateinit var firstName: TextInputEditText
    lateinit var lastName: TextInputEditText
    lateinit var dateOfBirth: TextInputEditText
    lateinit var email: TextInputEditText
    lateinit var password: TextInputEditText
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
            registerViewModel.validateUser(user)
        }
    }
}
