package com.bridgelabz.fundoonotes.repository.server_service

import retrofit2.Call
import retrofit2.http.GET

interface JsonPlaceHolderApi {
    @GET("getNotesList")
    fun getNotesFromServer(): Call<List<NoteResponseModel>>
}