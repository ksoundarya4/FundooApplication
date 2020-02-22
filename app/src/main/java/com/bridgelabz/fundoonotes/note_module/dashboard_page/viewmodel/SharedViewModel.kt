/**
 * Fundoo Notes
 * @description SharedViewModel extends ViewModel
 * @file SharedViewModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 12/02/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.recycler_view_strategy.RecyclerViewType
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteDatabaseManager

class SharedViewModel(private val noteDbManager: NoteDatabaseManager) : ViewModel() {

    private val notesLiveData = MutableLiveData<ArrayList<Note>>()
    private val recyclerViewTypeLiveData = MutableLiveData<RecyclerViewType>()
    private var rowId: Long? = null

    fun insertNoteOnCLick(note: Note) {
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
        rowId = noteDbManager.fetchNoteId(note)
        Log.d("note", note.toString())
    }

    fun updateNoteOnClick(note: Note) {
        noteDbManager.updateNote(rowId!!, note)
    }

    fun getRecyclerViewType(): LiveData<RecyclerViewType> {
        return recyclerViewTypeLiveData
    }

    fun setRecyclerViewType(recyclerViewType: RecyclerViewType) {
        recyclerViewTypeLiveData.value = recyclerViewType
    }
}