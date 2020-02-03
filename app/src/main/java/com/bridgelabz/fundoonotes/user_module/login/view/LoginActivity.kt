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
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.user_module.login.model.AuthState
import com.bridgelabz.fundoonotes.user_module.login.viewmodel.AuthViewModel
import com.bridgelabz.fundoonotes.user_module.registration.view.RegisterActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity(), AuthListener {

    lateinit var email: TextInputEditText
    lateinit var password: TextInputEditText
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
        viewModel.authListener = this
        onClickListenere()
    }

    private fun onViews() {
        email = findViewById(R.id.login_email)
        password = findViewById(R.id.login_password)
        loginButton = findViewById(R.id.button_login)
        forgotPasswordButton = findViewById(R.id.button_forgot_password)
        registerButton = findViewById(R.id.button_register)
    }

    private fun onClickListenere() {

        loginButton.setOnClickListener {
            val inputEmail = email.editableText.toString()
            val inputPassword = password.editableText.toString()

            viewModel.onLoginButtonClick(
                View(this),
                inputEmail,
                inputPassword
            )
        }
        registerButton.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }
    }

    override fun onStarted() {
        toast("Login Started")
    }

    override fun onSuccess(liveData: LiveData<AuthState>) {
        liveData.observe(this, Observer { toast(it.toString()) })
    }

    override fun onFailure(message: String) {
        toast(message)
    }
}
