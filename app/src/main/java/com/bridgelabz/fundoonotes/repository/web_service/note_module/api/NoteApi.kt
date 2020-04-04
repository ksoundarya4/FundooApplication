package com.bridgelabz.fundoonotes.repository.web_service.note_module.api

import com.bridgelabz.fundoonotes.repository.web_service.note_module.models.AddNoteResponseModel
import com.bridgelabz.fundoonotes.repository.web_service.note_module.models.GetNoteResponseModel
import com.bridgelabz.fundoonotes.repository.web_service.note_module.models.NoteModel
import com.bridgelabz.fundoonotes.repository.web_service.note_module.models.UpdateNoteResponseModel
import retrofit2.Call
import retrofit2.http.*

interface NoteApi {
    @GET("notes/getNotesList")
    fun getNotesFromServer(@Query("access_token") accessToken: String): Call<GetNoteResponseModel>

    @FormUrlEncoded
    @POST("notes/addNotes")
    @JvmSuppressWildcards
    fun addNoteToServer(
        @FieldMap addNoteModel: Map<String, Any>,
        @Query("access_token") accessToken: String
    ): Call<AddNoteResponseModel>

    @PATCH("notes/{id}")
    fun updateNoteInServer(
        @Path("id") noteId: String,
        @Query("access_token") accessToken: String,
        @Body noteModel: NoteModel
    ): Call<UpdateNoteResponseModel>
}