/**
 * Fundoo Notes
 * @description ViewUtil Class
 * @file ViewUtil.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 02/02/2020
 */
package com.bridgelabz.fundoonotes.user_module.login.view

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

/**DisplayErrorMessage to display error messages
 * when user enter bad inputs*/
object DisplayErrorMessage {
    const val emptyEmail = "email cannot be empty"
    const val emailTooLong = "email length is too long"
    const val emptyPassword = "password cannot be empty"
    const val shortPasswordLength = "password length too short"
    const val validPasswordLength = "valid password length"
    const val longPasswordLength = "password length is too long"
}

/**Function to display toast message*/
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

/** Extension Function to hide keyBoard*/
fun View.hideKeyboard() {
    val inputMethodManager: InputMethodManager =
        this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}