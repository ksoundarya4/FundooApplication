/**
 * Fundoo Notes
 * @description LoginActivity class that contain
 * user email and password to login.
 * @file LoginActivity.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 02/02/2020
 */
package com.bridgelabz.fundoonotes.user_module.login.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.user_module.login.model.AuthState
import com.bridgelabz.fundoonotes.user_module.login.viewmodel.AuthViewModel
import com.bridgelabz.fundoonotes.user_module.registration.view.RegisterActivity
import com.google.android.material.button.MaterialButton

class LoginActivity : AppCompatActivity() {

    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var loginButton: MaterialButton
    lateinit var forgotPasswordButton: MaterialButton
    lateinit var registerButton: MaterialButton

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(AuthViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        onViews()
        onClickListener()
    }

    private fun onViews() {
        emailEditText = findViewById(R.id.login_email)
        passwordEditText = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.button_login)
        forgotPasswordButton = findViewById(R.id.button_forgot_password)
        registerButton = findViewById(R.id.button_register)
    }

    private fun onClickListener() {

        loginButton.setOnClickListener {

            val inputEmail = emailEditText.editableText.toString()

            val inputPassword = passwordEditText.editableText.toString()
            viewModel.onLoginButtonClick(
                View(this),
                inputEmail,
                inputPassword
            )
            viewModel.getLoginStatus().observe(this, Observer { handelLoginStatus(it) })
        }
        registerButton.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }
    }

    private fun handelLoginStatus(loginStatus: AuthState) {
        when (loginStatus) {
            AuthState.NOT_AUTH -> {
                toast("Not Registered. First Register and then try to login")
            }
            AuthState.AUTH_FAILED -> {
                toast("Login Unsuccessful")
                emailEditText.error = "Invalid Email"
                passwordEditText.error = "Invalid Password"
            }
            AuthState.AUTH -> {
                toast("Login Successful")
            }
        }
    }
}
