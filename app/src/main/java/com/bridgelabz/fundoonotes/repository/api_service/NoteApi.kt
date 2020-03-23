package com.bridgelabz.fundoonotes.repository.api_service

import retrofit2.Call
import retrofit2.http.GET

interface NoteApi {
    @GET("getNotesList?access_token=Ntr4sdRxow4lKOTdciFT63cue4ejHDWSpgx9JBKFNsJBdQ0BGALGnbHZucKHewPM")
    fun getNotesFromServer(): Call<DataModel>
}