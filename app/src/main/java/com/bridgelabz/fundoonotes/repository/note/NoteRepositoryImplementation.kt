package com.bridgelabz.fundoonotes.repository.note

import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.repository.api_service.NoteApi
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteTableManager

class NoteRepositoryImplementation(
    private val noteApi: NoteApi,
    private val noteTableManager: NoteTableManager
) : NoteRepository{
    override fun insertNote(note: Note) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateNote(note: Note) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteNote(note: Note) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fetchNotes(): ArrayList<Note> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}