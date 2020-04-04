package com.bridgelabz.fundoonotes.repository.common

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.NoteServerResponse
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteTableManager
import com.bridgelabz.fundoonotes.repository.web_service.note_module.api.NoteApi
import com.bridgelabz.fundoonotes.repository.web_service.note_module.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoteRepositoryImplementation(
    private val noteApi: NoteApi,
    private val noteTableManager: NoteTableManager
) : NoteRepository {

    private val tag = "NoteRepository"
    private val noteUpdateCallback = object : Callback<UpdateNoteResponseModel> {
        override fun onFailure(call: Call<UpdateNoteResponseModel>, t: Throwable) {
            Log.i(tag, t.message!!)
        }

        override fun onResponse(
            call: Call<UpdateNoteResponseModel>,
            response: Response<UpdateNoteResponseModel>
        ) {

            if (!response.isSuccessful) {
                Log.i(tag, response.code().toString())
                return
            }

            val updateNoteResponseModel = response.body()
            Log.d(tag, updateNoteResponseModel!!.data.message!!)
        }
    }

    override fun insertNote(note: Note, accessToken: String): LiveData<NoteServerResponse> {
        val noteInsertionStatus = MutableLiveData<NoteServerResponse>()
        val postParametersToInsertNote: Map<String, Any> = getPostParameters(note)

        val call = noteApi.addNoteToServer(
            addNoteModel = postParametersToInsertNote,
            accessToken = accessToken
        )

        call.enqueue(object : Callback<AddNoteResponseModel> {
            override fun onFailure(call: Call<AddNoteResponseModel>, t: Throwable) {
                Log.i(tag, t.message!!)
                noteInsertionStatus.value = NoteServerResponse.Failure
            }

            override fun onResponse(
                call: Call<AddNoteResponseModel>,
                response: Response<AddNoteResponseModel>
            ) {

                if (!response.isSuccessful) {
                    Log.i(tag, response.code().toString())
                    return
                }

                val addNoteResponseModel = response.body()
                Log.i(tag, addNoteResponseModel!!.status.toString())
                noteInsertionStatus.value = NoteServerResponse.Success
                fetchNotesFromServer(accessToken, addNoteResponseModel.status.details.userId!!)
            }
        })
        return noteInsertionStatus
    }

    override fun updateNote(note: Note, accessToken: String) {
        val noteIdList = ArrayList<String>()
        noteIdList.add(note.noteId!!)

        noteTableManager.updateNote(note)
        updateTitleAndDescription(note, accessToken)

        if (note.isArchived == 1) {
            val archiveNoteModel = ArchiveNoteModel(isArchived = true, noteIdList = noteIdList)
            updateNotesInServer(archiveNoteModel, accessToken)
        }
        if (note.isPinned == 1) {
            val pinNoteModel = PinNoteModel(true, noteIdList)
            updateNotesInServer(pinNoteModel, accessToken)
        }
        if (note.isDeleted == 1) {
            val trashNoteModel = TrashNoteModel(true, noteIdList)
            updateNotesInServer(trashNoteModel, accessToken)
        }
        if (note.colour != null) {
            val colourNoteModel = ColourNoteModel(note.colour.toString(), noteIdList)
            updateNotesInServer(colourNoteModel, accessToken)
        }
        if (note.reminder != null) {
            val reminderNoteModel = ReminderNoteModel(note.reminder, noteIdList)
            updateNotesInServer(reminderNoteModel, accessToken)
        }
    }

    override fun deleteNote(note: Note) {

    }

    override fun fetchNotesFromServer(accessToken: String, userId: String) {

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
                noteTableManager.deleteNotesByUserId(userId)
                val dataResponse: GetNoteResponseModel = response.body()!!
                val listOfNoteResponseModel = dataResponse.data.listOfNoteResponses
                for (noteResponseModel in listOfNoteResponseModel!!) {
                    Log.i(tag, noteResponseModel.toString())
                    updateNoteTable(noteResponseModel)
                }
            }
        })
    }

    override fun fetchNotesFromLocalDb(userId: String): LiveData<ArrayList<Note>> {
        val noteLiveData = MutableLiveData<ArrayList<Note>>()
        val notes = noteTableManager.fetchUserNotes(userId)
        noteLiveData.value = notes
        return noteLiveData
    }

    private fun updateTitleAndDescription(note: Note, accessToken: String) {
        val updateNoteMap = HashMap<String, String>()
        updateNoteMap["noteId"] = note.noteId!!
        updateNoteMap["title"] = note.title
        updateNoteMap["description"] = note.description

        val call = noteApi.updateNoteInServer(updateNoteMap, accessToken)
        call.enqueue(noteUpdateCallback)
    }

    private fun updateNotesInServer(
        updateNoteModel: UpdateNoteModel,
        accessToken: String
    ) {
        val call: Call<UpdateNoteResponseModel>
        when (updateNoteModel) {
            is ArchiveNoteModel -> {
                call = noteApi.archiveOrUnarchiveNotes(accessToken, updateNoteModel)
                call.enqueue(noteUpdateCallback)
            }
            is PinNoteModel -> {
                call =
                    noteApi.pinUnpinNotes(accessToken, updateNoteModel)
                call.enqueue(noteUpdateCallback)
            }
            is TrashNoteModel -> {
                call = noteApi.trashNotes(accessToken, updateNoteModel)
                call.enqueue(noteUpdateCallback)
            }
            is ReminderNoteModel -> {
                call =
                    noteApi.updateRemindersForNotes(accessToken, updateNoteModel)
                call.enqueue(noteUpdateCallback)
            }
            is ColourNoteModel -> {
                call = noteApi.changeColourOfNotes(accessToken, updateNoteModel)
                call.enqueue(noteUpdateCallback)
            }
        }
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
        if (note.title.isNotEmpty())
            noteParameters["title"] = note.title
        if (note.description.isNotEmpty())
            noteParameters["description"] = note.description
        if (note.colour != null)
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
    if (this.reminder.isNotEmpty())
        note.reminder = reminder.toString()
    if (this.colour != null)
        note.colour = Color.parseColor(this.colour)
    note.userId = this.userId
    note.noteId = this.id

    return note
}