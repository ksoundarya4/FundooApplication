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

    private var notesLiveData = MutableLiveData<ArrayList<Note>>()
    private val recyclerViewTypeLiveData = MutableLiveData<RecyclerViewType>()
    private var noteServerResponse: LiveData<NoteServerResponse> =
        MutableLiveData<NoteServerResponse>()

    /**Function to insert Note into Server*/
    fun insertNoteOnCLick(accessToken: String, note: Note) {
        noteServerResponse = repository.insertNote(note, accessToken)
    }

    /**Function to return liveData of Notes*/
    fun getNoteLiveData(userId: String): LiveData<ArrayList<Note>> {
        notesLiveData.value = repository.fetchNotesFromLocalDb(userId).value
        return notesLiveData
    }

    fun getNoteSererResponse(): LiveData<NoteServerResponse> {
        return noteServerResponse
    }

    fun fetchNoteFromServer(accessToken: String, userId: String) {
        repository.fetchNotesFromServer(accessToken, userId)
        notesLiveData.value = repository.fetchNotesFromLocalDb(userId).value
    }

    /**Function to update note in Note table*/
    fun updateNoteOnClick(note: Note, accessToken: String) {
        noteServerResponse = repository.updateNote(note, accessToken)
    }

    fun markNoteAsArchiveOrUnarchive(note: Note, accessToken: String) {
        noteServerResponse = repository.markNoteAsArchiveOrUnarchive(note, accessToken)
    }

    fun markNoteAsPinOrUnpin(note: Note, accessToken: String) {
        noteServerResponse = repository.markNoteAsPinOrUnpin(note, accessToken)
    }

    fun markNoteAsTrash(note: Note, accessToken: String) {
        noteServerResponse = repository.markNoteAsTrash(note, accessToken)
    }

    fun updateColourOfNote(note: Note, accessToken: String) {
        noteServerResponse = repository.updateColourOfNote(note, accessToken)
    }

    fun updateReminderOfNote(note: Note, accessToken: String) {
        noteServerResponse = repository.updateReminderOfNote(note, accessToken)
    }

    fun getRecyclerViewType(): LiveData<RecyclerViewType> {
        return recyclerViewTypeLiveData
    }

    fun setRecyclerViewType(recyclerViewType: RecyclerViewType) {
        recyclerViewTypeLiveData.value = recyclerViewType
    }
}