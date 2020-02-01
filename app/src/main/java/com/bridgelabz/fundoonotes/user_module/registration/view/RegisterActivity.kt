package com.bridgelabz.fundoonotes.user_module.registration.view

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.user_module.registration.model.User
import com.bridgelabz.fundoonotes.user_module.registration.viewmodel.RegisterViewModel
import com.bridgelabz.fundoonotes.user_module.repository.local_service.UserDbHelper
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {

    lateinit var registerViewModel: RegisterViewModel

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
    private val phoneNumber by lazy {
        findViewById<TextInputEditText>(R.id.phoneNumber)
    }
    lateinit var user: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        registerViewModel = RegisterViewModel(UserDbHelper(this))

        user = User(
            firstName.editableText.toString(),
            lastName.editableText.toString(),
            dateOfBirth.editableText.toString(),
            email.editableText.toString(),
            password.editableText.toString(),
            phoneNumber.editableText.toString()
        )

        Log.d("UserInfo","$user")

        val signUPButton = findViewById<Button>(R.id.button_sign_up)
        signUPButton.setOnClickListener { registerViewModel.validateUser(user) }
    }
}
