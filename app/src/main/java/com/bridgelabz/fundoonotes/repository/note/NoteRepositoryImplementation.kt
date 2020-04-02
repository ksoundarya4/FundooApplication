package com.bridgelabz.fundoonotes.repository.note

import android.util.Log
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.repository.note.web_service.NoteApi
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteTableManager
import com.bridgelabz.fundoonotes.repository.note.web_service.GetNoteResponseModel
import com.bridgelabz.fundoonotes.repository.note.web_service.NoteResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoteRepositoryImplementation(
    private val noteApi: NoteApi,
    private val noteTableManager: NoteTableManager
) : NoteRepository {
    private val tag = "NoteRepository"
    override fun insertNote(note: Note) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateNote(note: Note) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteNote(note: Note) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchNotes(accessToken: String): ArrayList<Note> {
        val notes = ArrayList<Note>()

        val call =
            noteApi.getNotesFromServer(accessToken = accessToken)
        call.enqueue(object : Callback<GetNoteResponseModel> {

            override fun onFailure(call: Call<GetNoteResponseModel>, t: Throwable) {
                Log.i(tag, t.message!!)
            }

            override fun onResponse(
                call: Call<GetNoteResponseModel>,
                response: Response<GetNoteResponseModel>
            ) {
                if (!response.isSuccessful) {
                    Log.i(tag, response.code().toString())
                    return
                }

                val dataResponse: GetNoteResponseModel = response.body()!!
                Log.i(tag, dataResponse.toString())
                val listOfNoteResponseModel = dataResponse.data.listOfNoteResponses
                for (noteResponseModel in listOfNoteResponseModel!!) {
                    Log.i(tag, noteResponseModel.toString())
//                    val note = noteResponseModel.getNote()
                    updateNoteTable(noteResponseModel)
                }
            }
        }
        )
        return notes
    }

    private fun updateNoteTable(noteResponseModel: NoteResponseModel) {
        val noteId = noteResponseModel.id
        val note = noteTableManager.fetchNoteByNoteId(noteId!!)
        if (note != null)
            updateNoteInNoteTable(note, noteResponseModel)
        else
            insertNoteInNoteTable(noteResponseModel)
    }

    private fun updateNoteInNoteTable(note: Note, noteResponseModel: NoteResponseModel) {
        val noteTobeUpdated = noteResponseModel.getNote()
        noteTobeUpdated.id = note.id
        noteTableManager.updateNote(noteTobeUpdated)
    }

    private fun insertNoteInNoteTable(noteResponseModel: NoteResponseModel) {
        val noteTobeInserted = noteResponseModel.getNote()
        noteTableManager.insert(noteTobeInserted)
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
    note.noteId = this.id

    return note
}