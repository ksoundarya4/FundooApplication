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
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.user_module.login.model.AuthState
import com.bridgelabz.fundoonotes.user_module.login.viewmodel.AuthViewModel
import com.bridgelabz.fundoonotes.user_module.registration.view.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private val emailEditText by lazy {
        findViewById<EditText>(R.id.login_email)
    }
    private val passwordEditText by lazy {
        findViewById<EditText>(R.id.login_password)
    }
    private val emailErrorText by lazy {
        findViewById<TextView>(R.id.email_error_text)
    }
    private val passwordErrorText by lazy {
        findViewById<TextView>(R.id.passwor_error_text)
    }
    private val loginButton by lazy {
        findViewById<Button>(R.id.button_login)
    }
//    private val forgotPasswordButton by lazy {
//        findViewById<Button>(R.id.button_forgot_password)
//    }
    private val registerButton by lazy {
        findViewById<Button>(R.id.button_register)
    }
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(AuthViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        emailEditText.addTextChangedListener(emailWatcher)
        passwordEditText.addTextChangedListener(passwordWatcher)
        setButtonClickListeners()
    }

    private fun setButtonClickListeners() {
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