/**
 * Fundoo Notes
 * @description NoteResponseModel is a model class to post and get
 * notes from sever.
 * @file NoteResponseModel
 * @author ksoundarya4
 * @version 1.0
 * @since 23/03/2020
 */
package com.bridgelabz.fundoonotes.repository.server_service

import com.google.gson.annotations.SerializedName

class NoteResponseModel {
    @SerializedName("data")
    private val listOfNotes: ArrayList<NoteModel>? = null
}