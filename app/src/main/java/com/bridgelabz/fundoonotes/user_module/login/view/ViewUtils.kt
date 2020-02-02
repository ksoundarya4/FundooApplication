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
import android.widget.Toast

/**Function to display toast message*/
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}