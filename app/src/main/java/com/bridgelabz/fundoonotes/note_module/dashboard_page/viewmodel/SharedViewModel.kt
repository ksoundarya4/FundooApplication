package com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteDatabaseManager

class SharedViewModel(private val noteDbManager: NoteDatabaseManager) : ViewModel() {

    private val notesLiveData = MutableLiveData<ArrayList<Note>>()
    private val noteLiveData = MutableLiveData<Note>()
    private var rowId: Long? = null

    fun onSaveButtonClick(note: Note) {
        noteDbManager.insert(note)
    }

    private fun fetchNotes() {
        notesLiveData.value = noteDbManager.fetchNotes()
    }

    fun getNoteLiveData(): LiveData<ArrayList<Note>> {
        fetchNotes()
        return notesLiveData
    }

    fun handleNoteAt(note: Note) {
        noteLiveData.value = note
        rowId = noteDbManager.fetchNoteId(note)
        Log.d("note", note.toString())
    }

    fun getLiveNote(): LiveData<Note> {
        return noteLiveData
    }

    fun updateNoteOnClick(note: Note) {
        noteDbManager.updateNote(rowId!!, note)
    }
}