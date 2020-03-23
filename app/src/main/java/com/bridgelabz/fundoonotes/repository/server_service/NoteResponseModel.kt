package com.bridgelabz.fundoonotes.repository.server_service

import com.google.gson.annotations.SerializedName

class NoteResponseModel {
    @SerializedName("data")
    private val listOfNotes: ArrayList<NoteModel>? = null
}