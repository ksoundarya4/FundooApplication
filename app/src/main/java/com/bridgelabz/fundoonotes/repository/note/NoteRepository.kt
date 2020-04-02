package com.bridgelabz.fundoonotes.repository.note

import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note

interface NoteRepository {
    fun insertNote(note: Note)
    fun updateNote(note: Note)
    fun deleteNote(note: Note)
    fun fetchNotes(accessToken: String): ArrayList<Note>
}