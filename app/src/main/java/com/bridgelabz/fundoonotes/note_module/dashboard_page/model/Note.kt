/**
 * Fundoo Notes
 * @description Note data class which contains
 * Note title and description.
 * @file Note.kt
 * @author ksoundarya4
 * @version 1.0
 *@since 07/02/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.model

import java.io.Serializable

data class Note(val title: String, val description: String) : Serializable