package com.bridgelabz.fundoonotes.repository.common

import androidx.lifecycle.LiveData
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.NoteServerResponse

interface NoteRepository {
    fun insertNote(note: Note, accessToken: String): LiveData<NoteServerResponse>
    fun updateNote(note: Note, accessToken: String): LiveData<NoteServerResponse>
    fun markNoteAsArchiveOrUnarchive(note: Note, accessToken: String): LiveData<NoteServerResponse>
    fun markNoteAsPinOrUnpin(note: Note, accessToken: String): LiveData<NoteServerResponse>
    fun markNoteAsTrash(note: Note, accessToken: String): LiveData<NoteServerResponse>
    fun updateColourOfNote(note: Note, accessToken: String): LiveData<NoteServerResponse>
    fun updateReminderOfNote(note: Note, accessToken: String): LiveData<NoteServerResponse>
    fun deleteNote(note: Note)
    fun fetchNotesFromServer(accessToken: String, userId: String)
    fun fetchNotesFromLocalDb(userId: String): LiveData<ArrayList<Note>>
}