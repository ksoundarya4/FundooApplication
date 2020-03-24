package com.bridgelabz.fundoonotes.repository.api_service

import android.util.Log
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    private val tag = "RetrofitHelper"
    private val retrofit =
        Retrofit.Builder().baseUrl("http://fundoonotes.incubation.bridgelabz.com/api/notes/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    private val notes = ArrayList<Note>()

    fun getNotesFromServer(noteCallBack: NoteCallBack) {

        val noteApi: NoteApi = retrofit.create(NoteApi::class.java)

        val call =
            noteApi.getNotesFromServer(accessToken = "Ntr4sdRxow4lKOTdciFT63cue4ejHDWSpgx9JBKFNsJBdQ0BGALGnbHZucKHewPM")
        call.enqueue(object : Callback<DataModel> {

            override fun onFailure(call: Call<DataModel>, t: Throwable) {
                Log.i(tag, t.message!!)
                noteCallBack.onNoteReceivedFailure(t)
            }

            override fun onResponse(
                call: Call<DataModel>,
                response: Response<DataModel>
            ) {
                if (!response.isSuccessful) {
                    Log.i(tag, response.code().toString())
                    return
                }

                val dataResponse: DataModel = response.body()!!
                Log.i(tag, dataResponse.toString())
                for (noteResponseModel in dataResponse.data.listOfNoteResponses!!) {
                    Log.i(tag, noteResponseModel.toString())
                    noteCallBack.onNoteReceivedSuccess(noteResponseModel)
                }
            }
        }
        )
    }

    private fun setNotes(noteResponseModel: NoteResponseModel) {
        val note = noteResponseModel.getNote()
        notes.add(note)
    }

    fun getNotes(): ArrayList<Note> {
        return notes
    }
}

 fun NoteResponseModel.getNote(): Note {
    val note = Note()
    note.title = this.title!!
    note.description = this.description!!
    note.isPinned = convertBooleanToInt(this.isPinned)
    note.isArchived = convertBooleanToInt(this.isArchived)
    note.isDeleted = convertBooleanToInt(this.isDeleted)
    note.label = this.label.toString()
    note.reminder = this.reminder.toString()
    note.colour = this.colour?.toInt()
    note.userId = this.userId

    return note
}