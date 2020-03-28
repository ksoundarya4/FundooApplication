package com.bridgelabz.fundoonotes.repository.web_service

import retrofit2.Call
import retrofit2.http.*

interface NoteApi {
    @GET("getNotesList")
    fun getNotesFromServer(@Query("access_token") accessToken: String): Call<DataModel>

    @POST("addNotes")
    @FormUrlEncoded
    fun addNoteToServer(
        @Query("access_token") accessToken: String,
        @Body noteResponseModel: NoteResponseModel
    ): Call<DataModel>
}