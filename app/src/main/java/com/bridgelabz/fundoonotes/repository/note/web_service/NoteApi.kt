package com.bridgelabz.fundoonotes.repository.note.web_service

import retrofit2.Call
import retrofit2.http.*

interface NoteApi {
    @GET("getNotesList")
    fun getNotesFromServer(@Query("access_token") accessToken: String): Call<GetNoteResponseModel>

    @FormUrlEncoded
    @POST("addNotes")
    fun addNoteToServer(
        @Query("access_token") accessToken: String,
        @FieldMap addNoteModel: Map<String, String>
    ): Call<GetNoteResponseModel>
}