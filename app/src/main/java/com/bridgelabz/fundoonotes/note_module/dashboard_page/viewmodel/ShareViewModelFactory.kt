/**
 * Fundoo Notes
 * @description RepositoryFactory that implements
 * ViewModelProvider.factory to instantiate constructor
 * parameter of ViewModel.
 * @file NoteTableManagerFactory.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 13/02/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteTableManagerImpl
import com.bridgelabz.fundoonotes.repository.note.NoteRepository
import com.bridgelabz.fundoonotes.repository.note.NoteRepositoryImplementation
import com.bridgelabz.fundoonotes.repository.note.web_service.NoteApi
import com.bridgelabz.fundoonotes.repository.user.web_services.RetrofitClient

class ShareViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val retrofit = RetrofitClient.getRetrofitClient()
    private val noteApi = retrofit.create(NoteApi::class.java)
    private val noteTableManager = NoteTableManagerImpl(DatabaseHelper(context))
    private val noteRepository = NoteRepositoryImplementation(noteApi, noteTableManager)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(NoteRepository::class.java)
            .newInstance(noteRepository)
    }
}