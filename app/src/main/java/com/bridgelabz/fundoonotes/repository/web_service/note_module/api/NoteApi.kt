package com.bridgelabz.fundoonotes.repository.web_service.note_module.api

import com.bridgelabz.fundoonotes.repository.web_service.note_module.models.*
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

    @FormUrlEncoded
    @POST("notes/updateNotes")
    fun updateNoteInServer(
        @FieldMap updateNote: Map<String, String>,
        @Query("access_token") accessToken: String
    ): Call<UpdateNoteResponseModel>

    @POST("notes/archiveNotes")
    fun archiveOrUnarchiveNotes(
        @Query("access_token") accessToken: String,
        @Body archiveNoteModel: ReminderNoteModel
    )

    @POST("notes/pinUnpinNotes")
    fun pinUnpinNotes(
        @Query("access_token") accessToken: String,
        @Body pinNoteModel: ReminderNoteModel
    )

    @POST("notes/trashNotes")
    fun trashNotes(
        @Query("access_token") accessToken: String,
        @Body trashNoteModel: ReminderNoteModel
    )

    @POST("notes/changesColorNotes")
    fun changeColourOfNotes(
        @Query("access_token") accessToken: String,
        @Body colourNoteModel: ReminderNoteModel
    )

    @POST("notes/addUpdateReminderNotes")
    fun updateRemindersForNotes(
        @Query("access_token") accessToken: String,
        @Body colourNoteModel: ReminderNoteModel
    )
}