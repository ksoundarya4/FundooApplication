package com.bridgelabz.fundoonotes.repository.web_service

import retrofit2.Call
import retrofit2.http.*

interface NoteApi {
    @GET("getNotesList")
    fun getNotesFromServer(@Query("access_token") accessToken: String): Call<getNoteResponselModel>

    @FormUrlEncoded
    @POST("addNotes")
    fun addNoteToServer(
        @Query("access_token") accessToken: String,
        @FieldMap addNoteModel: Map<String, String>
    ): Call<getNoteResponselModel>
}