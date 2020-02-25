/**
 * Fundoo Notes
 * @description SharedViewModel extends ViewModel
 * @file SharedViewModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 12/02/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.recycler_view_strategy.RecyclerViewType
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteTableManager

class SharedViewModel(private val noteTableManager: NoteTableManager) : ViewModel() {

    private val notesLiveData = MutableLiveData<ArrayList<Note>>()
    private val recyclerViewTypeLiveData = MutableLiveData<RecyclerViewType>()

    /**Function to insert Note into Notes table*/
    fun insertNoteOnCLick(note: Note) {
        noteTableManager.insert(note)
    }

    /**Function to fetchSimpleNotes from Notes table*/
    private fun fetchSimpleNotes() {
        notesLiveData.value = noteTableManager.fetchSimpleNote()
    }

    /**Function to return liveData of SimpleNote*/
    fun getSimpleNoteLiveData(): LiveData<ArrayList<Note>> {
        fetchSimpleNotes()
        return notesLiveData
    }

    /**Function to update note in Note table*/
    fun updateNoteOnClick(note: Note) {
        noteTableManager.updateNote(note)
    }

    fun getRecyclerViewType(): LiveData<RecyclerViewType> {
        return recyclerViewTypeLiveData
    }

    fun setRecyclerViewType(recyclerViewType: RecyclerViewType) {
        recyclerViewTypeLiveData.value = recyclerViewType
    }

    /**Function to fetchSArchiveNotes from Notes table*/
    private fun fetchArchiveNote() {
        notesLiveData.value = noteTableManager.fetchArchiveNote()
    }

    /**Function to return liveData of ArchiveNote*/
    fun getArchiveNoteLiveData(): LiveData<ArrayList<Note>> {
        fetchArchiveNote()
        return notesLiveData
    }

    private fun fetchDeletedNotes() {
        notesLiveData.value = noteTableManager.fetchDeletedNote()
    }

    fun getDeletedNoteLiveData(): LiveData<ArrayList<Note>> {
        fetchDeletedNotes()
        return notesLiveData
    }
}