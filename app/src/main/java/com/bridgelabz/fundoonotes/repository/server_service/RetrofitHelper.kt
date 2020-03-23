package com.bridgelabz.fundoonotes.repository.server_service

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

    fun getNotesFromServer(): ArrayList<Note> {

        val jsonPlaceHolderApi: JsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi::class.java)

        val call = jsonPlaceHolderApi.getNotesFromServer()
        call.enqueue(notesCallback)
        return notes
    }

    private val notesCallback = object : Callback<NoteResponseModel> {

        override fun onFailure(call: Call<NoteResponseModel>, t: Throwable) {
            Log.i(tag, t.message!!)
        }

        override fun onResponse(
            call: Call<NoteResponseModel>,
            response: Response<NoteResponseModel>
        ) {
            if (!response.isSuccessful) {
                Log.i(tag, response.code().toString())
                return
            }

            val listOfNotesResponseModel: NoteResponseModel = response.body()!!
            Log.i(tag, listOfNotesResponseModel.toString())
//            for (noteResponseModel in listOfNotesResponseModel) {
//                Log.i(tag, noteResponseModel.toString())
//                val note = noteResponseModel.getNote()
//                notes.add(note)
//            }
        }
    }
}

private fun NoteModel.getNote(): Note {
    val note = Note()
    note.title = this.title!!
    note.description = this.description!!
    note.isPinned = convertBooleanToInt(this.isPinned)
    note.isArchived = convertBooleanToInt(this.isArchived)
    note.isDeleted = convertBooleanToInt(this.isDeleted)
    note.label = this.label.toString()
    note.reminder = this.reminder.toString()
    note.colour = this.colour!!.toInt()
    note.userId = this.userId

    return note
}

