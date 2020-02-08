package com.bridgelabz.fundoonotes.note_module.note_page.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.note_repository.NoteDatabaseHelper
import com.bridgelabz.fundoonotes.note_module.note_repository.NoteDatabaseManager
import com.bridgelabz.fundoonotes.note_module.note_repository.NoteDatabaseManagerImpl

class NoteViewModel : ViewModel() {

    private lateinit var noteDbManager: NoteDatabaseManager
    private val noteLiveData = MutableLiveData<ArrayList<Note>>()

    fun onSaveButtonClick(view: View, note: Note) {
        noteDbManager = NoteDatabaseManagerImpl(NoteDatabaseHelper(view.context))
        val rowId = noteDbManager.insert(note)
        if (rowId > 0)
            fetchNotes()
    }
    fun fetchNotes(){
        noteLiveData.value = noteDbManager.fetch() as ArrayList<Note>
    }

    fun getNoteLiveData() : LiveData<ArrayList<Note>>{
        return noteLiveData
    }
}