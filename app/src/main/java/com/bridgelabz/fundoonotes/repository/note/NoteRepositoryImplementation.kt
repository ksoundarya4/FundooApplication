package com.bridgelabz.fundoonotes.repository.note

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.NoteInsertionStatus
import com.bridgelabz.fundoonotes.repository.note.web_service.NoteApi
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteTableManager
import com.bridgelabz.fundoonotes.repository.note.web_service.AddNoteResponseModel
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

    override fun insertNote(note: Note, accessToken: String): LiveData<NoteInsertionStatus> {
        val noteInsertionStatus = MutableLiveData<NoteInsertionStatus>()
        val postParametersToInsertNote: Map<String, Any> = getPostParameters(note)

        val call = noteApi.addNoteToServer(
            addNoteModel = postParametersToInsertNote,
            accessToken = accessToken
        )

        call.enqueue(object : Callback<AddNoteResponseModel> {
            override fun onFailure(call: Call<AddNoteResponseModel>, t: Throwable) {
                Log.i(tag, t.message!!)
                noteInsertionStatus.value = NoteInsertionStatus.Failure
            }

            override fun onResponse(
                call: Call<AddNoteResponseModel>,
                response: Response<AddNoteResponseModel>
            ) {

                if (!response.isSuccessful) {
                    Log.i(tag, response.code().toString())
                    noteInsertionStatus.value = NoteInsertionStatus.Failure
                    return
                }

                val addNoteResponseModel = response.body()
                Log.i(tag, response.message())
                noteInsertionStatus.value = NoteInsertionStatus.Success
            }
        })
        return noteInsertionStatus
    }

    override fun updateNote(note: Note) {

    }

    override fun deleteNote(note: Note) {

    }

    override fun fetchNotes(accessToken: String, userId: String): ArrayList<Note> {

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
                    updateNoteTable(noteResponseModel)
                }
            }
        }
        )
        return noteTableManager.fetchUserNotes(userId)
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

    private fun getPostParameters(note: Note): Map<String, Any> {
        val noteParameters = HashMap<String, Any>()
        noteParameters["title"] = note.title
        noteParameters["description"] = note.description
        noteParameters["color"] = note.colour.toString()
        if (note.reminder != null)
            noteParameters["reminder"] = note.reminder.toString()
        noteParameters["isPined"] = note.isPinned == 1
        noteParameters["isArchived"] = note.isArchived == 1

        return noteParameters
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