package com.bridgelabz.fundoonotes.repository.note

import androidx.lifecycle.LiveData
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.NoteServerResponse

interface NoteRepository {
    fun insertNote(note: Note, accessToken: String): LiveData<NoteServerResponse>
    fun updateNote(note: Note)
    fun deleteNote(note: Note)
    fun fetchNotes(accessToken: String, userId: String): ArrayList<Note>
}