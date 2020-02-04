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
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
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
    lateinit var emailErrorText: TextView
    lateinit var passwordErrorText: TextView
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
        emailEditText.addTextChangedListener(emailWatcher)
        passwordEditText = findViewById(R.id.login_password)
        passwordEditText.addTextChangedListener(passwordWatcher)
        emailErrorText = findViewById(R.id.email_error_text)
        passwordErrorText = findViewById(R.id.passwor_error_text)
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

    /**Function to notify user based on Login status
     *
     * @param loginStatus contain the authentication state
     * of email and password.
     */
    private fun handelLoginStatus(loginStatus: AuthState) {
        when (loginStatus) {
            AuthState.NOT_AUTH -> {
                toast("Not Registered. First Register and then try to login")
            }
            AuthState.AUTH_FAILED -> {
                toast("Login Unsuccessful")
                passwordEditText.error = "Invalid Password"
            }
            AuthState.AUTH -> {
                toast("Login Successful")
            }
        }
    }

    /**Email text watcher*/
    private val emailWatcher = object : TextWatcher {

        override fun afterTextChanged(editableEmail: Editable?) {
            if (editableEmail.isNullOrEmpty())
                emailErrorText.text = DisplayErrorMessage.emptyEmail
            if (editableEmail!!.length >= 30)
                emailErrorText.text = DisplayErrorMessage.emailTooLong
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    /**Password text watcher*/
    private val passwordWatcher = object : TextWatcher {
        override fun afterTextChanged(editablePassword: Editable?) {
            if (editablePassword.isNullOrEmpty())
                passwordErrorText.text = DisplayErrorMessage.emptyPassword
            else {
                if (editablePassword.length < 5)
                    passwordErrorText.text = DisplayErrorMessage.shortPasswordLength
                else
                    passwordErrorText.text = DisplayErrorMessage.validPasswordLength
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }
}