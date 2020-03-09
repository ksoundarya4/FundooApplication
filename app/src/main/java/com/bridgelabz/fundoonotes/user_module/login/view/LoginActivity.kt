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

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.HomeDashBoardActivity
import com.bridgelabz.fundoonotes.user_module.login.model.AuthState
import com.bridgelabz.fundoonotes.user_module.login.viewmodel.AuthViewModel
import com.bridgelabz.fundoonotes.user_module.registration.view.RegisterActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.SignInButton

class LoginActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 0
    private var signInCLient: GoogleSignInClient? = null
    private val EMAIL = "email"
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
    private val forgotPasswordButton by lazy {
        findViewById<Button>(R.id.button_forgot_password)
    }
    private val registerButton by lazy {
        findViewById<Button>(R.id.button_register)
    }
    private val viewModel by lazy {
        ViewModelProvider(this).get(AuthViewModel::class.java)
    }
    private val loginActivity by lazy {
        findViewById<ConstraintLayout>(R.id.login_constaint_layout)
    }
    private val preferences: SharedPreferences by lazy {
        this.getSharedPreferences(
            "LaunchScreen",
            Context.MODE_PRIVATE
        )
    }
    private val googleSignInButton by lazy {
        findViewById<SignInButton>(R.id.google_sign_in_button)
    }
    private val facebookSignInButton by lazy {
        findViewById<LoginButton>(R.id.facebook_sign_in_button)
    }
    private val callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        emailEditText.addTextChangedListener(emailWatcher)
        passwordEditText.addTextChangedListener(passwordWatcher)
        setButtonClickListeners()
        setHideKeyboardOnTouch(context = this, view = loginActivity)
        setGoogleSignInOption()
    }

    /**
     * To set GoogleSignIn Option
     */
    private fun setGoogleSignInOption() {
        val googleSignInOption =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        signInCLient = GoogleSignIn.getClient(this, googleSignInOption)
    }

    private fun setButtonClickListeners() {
        setClickOnLoginButton()
        setClickOnRegisterButton()
        setClickOnForgotPasswordButton()
        setClickToGoogleButton()
        setUpFacebookButton()
    }

    private fun setClickOnLoginButton() {
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
    }

    private fun setClickOnRegisterButton() {
        registerButton.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }
    }

    private fun setClickOnForgotPasswordButton() {
        forgotPasswordButton.setOnClickListener {
            val email = emailEditText.editableText.toString()
            Intent(this, ForgotPasswordActivity::class.java).apply {
                putExtra("email", email)
                startActivity(this)
            }
        }
    }

    private fun setUpFacebookButton() {
        facebookSignInButton.setPermissions(listOf(EMAIL))
        facebookSignInButton.registerCallback(callbackManager, facebookCallback)
    }

    private fun setClickToGoogleButton() {
        googleSignInButton.setSize(SignInButton.SIZE_WIDE)
        googleSignInButton.setOnClickListener {
            onGoogleSignInButtonClicked()
        }
    }

    private val facebookCallback = object : FacebookCallback<LoginResult> {
        override fun onSuccess(result: LoginResult?) {
            navigateToHomeDashBoard()
        }

        override fun onCancel() {
            Log.d("LoginActivity", "cancelled")
        }

        override fun onError(error: FacebookException?) {
            Log.d("LoginActivity", "Faied")
            toast("Connect to network")
        }

    }

    private fun onGoogleSignInButtonClicked() {
        val signIntent = signInCLient!!.signInIntent
        startActivityForResult(signIntent, RC_SIGN_IN)
    }

    /**Function to notify user based on Login status
     *
     * @param loginStatus contain the authentication state
     * of email and password.
     */
    private fun handelLoginStatus(loginStatus: AuthState) {
        when (loginStatus) {
            AuthState.NOT_AUTH -> {
                toast(getString(R.string.toast_email_not_registered))
            }
            AuthState.AUTH_FAILED -> {
                toast(getString(R.string.toast_login_unsuccessful))
                passwordEditText.error = getString(R.string.invalidPassword)
            }
            AuthState.AUTH -> {
                val inputEmail = emailEditText.editableText.toString()
                getSharedPreference(inputEmail)
                toast(getString(R.string.toast_login_successful))
                navigateToHomeDashBoard()
            }
        }
    }

    private fun getSharedPreference(email: String) {
        val editor = preferences.edit()
        editor.putString("email", email)
        editor.apply()
    }

    /**Email text watcher*/
    private val emailWatcher = object : TextWatcher {

        override fun afterTextChanged(editableEmail: Editable?) {
            if (editableEmail.isNullOrEmpty())
                emailErrorText.text = getString(R.string.emptyEmail)
            if (editableEmail!!.length >= 30)
                emailErrorText.text = getString(R.string.emailTooLong)
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
                passwordErrorText.text = getString(R.string.emptyPassword)
            else {
                if (editablePassword.length < 5)
                    passwordErrorText.text = getString(R.string.shortPasswordLength)
                else
                    passwordErrorText.text = getString(R.string.validPasswordLength)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleGoogleSignInResult(result)
        }
    }

    private fun handleGoogleSignInResult(result: GoogleSignInResult?) {
        if (result!!.isSuccess) {
            val account = result.signInAccount
            val userEmail = account!!.email
            getSharedPreference(userEmail!!)
            navigateToHomeDashBoard()
        } else
            toast("Sign in Failed")
    }

    private fun navigateToHomeDashBoard() {
        val intent = Intent(this, HomeDashBoardActivity::class.java)
        finish()
        startActivity(intent)
    }
}