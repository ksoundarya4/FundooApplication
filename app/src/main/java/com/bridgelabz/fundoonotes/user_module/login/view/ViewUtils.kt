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
import android.provider.Settings.Global.getString
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.bridgelabz.fundoonotes.R

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