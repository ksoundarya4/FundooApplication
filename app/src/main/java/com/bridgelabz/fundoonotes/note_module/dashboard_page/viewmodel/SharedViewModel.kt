package com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteDatabaseManager

class SharedViewModel(private val noteDbManager: NoteDatabaseManager) : ViewModel() {

    private val noteLiveData = MutableLiveData<ArrayList<Note>>()

    fun onSaveButtonClick(note: Note) {
        noteDbManager.insert(note)
    }

    private fun fetchNotes() {
        noteLiveData.value = noteDbManager.fetch()
    }

    fun getNoteLiveData(): LiveData<ArrayList<Note>> {
        fetchNotes()
        return noteLiveData
    }
}