/**
 * Fundoo Notes
 * @description FundooNotesPreference object is used to set
 * Shared Preference for Fundoo Notes app.
 * @file FundooNotesPreference.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 01/04/2020
 */
package com.bridgelabz.fundoonotes.launch_module

import android.content.Context
import android.content.SharedPreferences
import com.bridgelabz.fundoonotes.R

object FundooNotesPreference {

    fun getPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            context.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
    }

    fun editPreference(preference: SharedPreferences, key: String, value: String) {
        val editor = preference.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun removePreference(preference: SharedPreferences, key: String) {
        preference.contains(key)
        val editor = preference.edit()
        editor.clear().apply()
    }
}