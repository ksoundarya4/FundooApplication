package com.bridgelabz.fundoonotes.note_module.dashboard_page.view.viewutil

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

object ViewUtil {

    fun setUpActionBarTitle(activity: AppCompatActivity, title: String) {
        activity.supportActionBar?.let {
            it.title = title
        }
    }
}