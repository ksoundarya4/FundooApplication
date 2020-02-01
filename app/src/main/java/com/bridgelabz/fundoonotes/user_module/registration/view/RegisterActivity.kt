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

    lateinit var registerViewModel: RegisterViewModel
    lateinit var firstName: TextInputEditText
    lateinit var lastName: TextInputEditText
    lateinit var dateOfBirth: TextInputEditText
    lateinit var email: TextInputEditText
    lateinit var password: TextInputEditText
    lateinit var phoneNumber: TextInputEditText
    lateinit var signUPButton :Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        onViews()
        registerViewModel = RegisterViewModel(UserDbHelper(this))



        onClickListener()






    }

    private fun onViews() {
        firstName = findViewById(R.id.first_name)
        lastName = findViewById(R.id.last_name_)
        dateOfBirth = findViewById(R.id.date_of_birth)
        email = findViewById(R.id.user_email)
        password = findViewById(R.id.password)
        phoneNumber = findViewById(R.id.phoneNumber)
        signUPButton =findViewById<Button>(R.id.button_sign_up)
    }

    private fun onClickListener(){
        signUPButton.setOnClickListener {

            val fname =firstName.editableText.toString()
            val lname =lastName.editableText.toString()
            val dob =dateOfBirth.editableText.toString()
            email.editableText.toString()
            password.editableText.toString()
            phoneNumber.editableText.toString()
            registerViewModel.validateUser(User(
                "fj", "foas", "22/10/9182", "foiej@gmail","773ffee2", "423423423523"))

        }
    }
}
