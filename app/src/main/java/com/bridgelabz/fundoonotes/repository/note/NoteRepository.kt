package com.bridgelabz.fundoonotes.repository.note

import androidx.lifecycle.LiveData
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.NoteInsertionStatus

interface NoteRepository {
    fun insertNote(note: Note, accessToken: String): LiveData<NoteInsertionStatus>
    fun updateNote(note: Note)
    fun deleteNote(note: Note)
    fun fetchNotes(accessToken: String, userId: String): ArrayList<Note>
}