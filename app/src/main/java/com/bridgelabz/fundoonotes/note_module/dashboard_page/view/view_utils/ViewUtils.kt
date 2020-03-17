/**
 * Fundoo Notes
 * @description ViewUtils Object contains functions related to View
 * @file ViewUtils.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 17/03/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.view.view_utils

import androidx.appcompat.app.AppCompatActivity

object ViewUtils {
    fun setActionBarTitle(activity: AppCompatActivity, title: String) {
        activity.supportActionBar?.let {
            it.title = title
        }
    }
}