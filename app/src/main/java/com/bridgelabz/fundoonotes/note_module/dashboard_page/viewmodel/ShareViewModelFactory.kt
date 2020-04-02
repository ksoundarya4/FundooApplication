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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteTableManager

class ShareViewModelFactory(private val noteTableManager: NoteTableManager) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(NoteTableManager::class.java)
            .newInstance(noteTableManager)
    }

}