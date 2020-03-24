package com.bridgelabz.fundoonotes.repository.api_service

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NoteApi {
    @GET("getNotesList")
    fun getNotesFromServer(@Query("access_token") accessToken: String): Call<DataModel>

    @POST("addNotes")
    fun addNoteToServer(
        @Query("access_token") accessToken: String,
        @Body noteResponseModel: NoteResponseModel
    ): Call<DataModel>
}