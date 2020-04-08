/**
 * Fundoo Notes
 * @description OnUserClickListener is used to get user details
 * from UserProfileDialogFragment
 * @file OnUserClickListener.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 08/04/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

import com.bridgelabz.fundoonotes.user_module.model.User


interface OnUserClickListener {
    fun onUserSubmit(user: User)
}