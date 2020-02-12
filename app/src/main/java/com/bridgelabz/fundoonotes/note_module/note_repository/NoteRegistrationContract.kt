/**
 * Fundoo Notes
 * @description NoteRegistrationContract contain
 * NoteEntry which extends BaseColumns and has
 * TABLE_NAME,KEY_TITLE and KEY_DESCRIPTION.
 * @file NoteRegistrationContract.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 07/02/2020
 */
package com.bridgelabz.fundoonotes.note_module.note_repository

import android.provider.BaseColumns

object NoteRegistrationContract {
    object NoteEntry : BaseColumns {
        const val TABLE_NAME = "Note"
        const val KEY_TITLE = "Title"
        const val KEY_DESCRIPTION = "Description"
    }
}