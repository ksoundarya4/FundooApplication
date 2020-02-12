package com.bridgelabz.fundoonotes.note_module.note_page.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.note_module.NoteDatabaseManager
import com.bridgelabz.fundoonotes.repository.note_module.NoteDatabaseManagerImpl

class NoteViewModel : ViewModel() {

    private lateinit var noteDbManager: NoteDatabaseManager
    private val noteLiveData = MutableLiveData<ArrayList<Note>>()

    fun onSaveButtonClick(view: View, note: Note) {
        noteDbManager =
            NoteDatabaseManagerImpl(
                DatabaseHelper(view.context)
            )
        val rowId = noteDbManager.insert(note)
        if (rowId > 0)
            fetchNotes()
    }
    private fun fetchNotes(){
        noteLiveData.value = noteDbManager.fetch() as ArrayList<Note>
    }

    fun getNoteLiveData() : LiveData<ArrayList<Note>>{
        return noteLiveData
    }
}