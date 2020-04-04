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
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.NoteServerResponse
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.recycler_view_strategy.RecyclerViewType
import com.bridgelabz.fundoonotes.repository.common.NoteRepository

class SharedViewModel(private val repository: NoteRepository) : ViewModel() {

    private var notesLiveData: LiveData<ArrayList<Note>> = MutableLiveData<ArrayList<Note>>()
    private val recyclerViewTypeLiveData = MutableLiveData<RecyclerViewType>()
    private var noteServerResponse: LiveData<NoteServerResponse> =
        MutableLiveData<NoteServerResponse>()

    /**Function to insert Note into Server*/
    fun insertNoteOnCLick(accessToken: String, note: Note) {
        noteServerResponse = repository.insertNote(note, accessToken)
    }

    /**Function to return liveData of Notes*/
    fun getNoteLiveData(userId: String): LiveData<ArrayList<Note>> {
        notesLiveData = repository.fetchNotesFromLocalDb(userId)
        return notesLiveData
    }

    fun getNoteSererResponse(): LiveData<NoteServerResponse> {
        return noteServerResponse
    }

    fun fetchNoteFromServer(accessToken: String, userId: String) {
        repository.fetchNotesFromServer(accessToken, userId)
    }

    /**Function to update note in Note table*/
    fun updateNoteOnClick(note: Note) {
//        noteTableManager.updateNote(note)
    }

    fun getRecyclerViewType(): LiveData<RecyclerViewType> {
        return recyclerViewTypeLiveData
    }

    fun setRecyclerViewType(recyclerViewType: RecyclerViewType) {
        recyclerViewTypeLiveData.value = recyclerViewType
    }

    /**Function to fetch ArchiveNotes from Notes table*/
    private fun fetchArchiveNote() {
//        notesLiveData.value = noteTableManager.fetchArchiveNote()
    }

    /**Function to return liveData of ArchiveNote*/
    fun getArchiveNoteLiveData(): LiveData<ArrayList<Note>> {
        fetchArchiveNote()
        return notesLiveData
    }

    /**Function to return live data of Deleted Note*/
    private fun fetchDeletedNotes() {
//        notesLiveData.value = noteTableManager.fetchDeletedNote()
    }

    fun getDeletedNoteLiveData(): LiveData<ArrayList<Note>> {
        fetchDeletedNotes()
        return notesLiveData
    }

    /**Function to return Array of Pinned Note*/
    private fun fetchPinnedNotes() {
//        notesLiveData.value = noteTableManager.fetchPinnedNote()
    }

    fun getPinnedNoteLiveData(): LiveData<ArrayList<Note>> {
        fetchPinnedNotes()
        return notesLiveData
    }
}