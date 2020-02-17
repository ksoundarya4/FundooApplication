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
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ScrollView
import android.widget.Toast

/**Function to display toast message*/
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

/** Extension Function to hide keyBoard*/
fun View.hideKeyboard() {
    val inputMethodManager: InputMethodManager =
        this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

/**Extension Function to set HideBoard on touch*/
fun setHideKeyboardOnTouch(context: Context, view: View) {
    try {
        if (!(view is EditText || view is ScrollView)) {
            view.setOnTouchListener { view: View, motionEvent: MotionEvent ->
                onTouch(view)
            }
        }

        if (view is ViewGroup) {
            for (index in 0.until(view.childCount)) {
                val innerView = view.getChildAt(index)
                setHideKeyboardOnTouch(context, innerView)
            }
        }
    } catch (exception: Exception) {
        exception.printStackTrace()

    }
}

private fun onTouch(view: View): Boolean {
    view.hideKeyboard()
    return false
}

