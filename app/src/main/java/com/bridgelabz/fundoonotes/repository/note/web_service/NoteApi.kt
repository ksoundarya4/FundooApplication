package com.bridgelabz.fundoonotes.repository.note.web_service

import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface NoteApi {
    @GET("getNotesList")
    fun getNotesFromServer(@Query("access_token") accessToken: String): Call<GetNoteResponseModel>

    @FormUrlEncoded
    @POST("addNotes")
    fun addNoteToServer(
        @FieldMap addNoteModel: Map<String, Any>,
        @Query("access_token") accessToken: String
    ): Call<AddNoteResponseModel>
}