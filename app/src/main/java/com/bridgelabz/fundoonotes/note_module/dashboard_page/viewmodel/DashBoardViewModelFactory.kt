/**
 * Fundoo Notes
 * @description RepositoryFactory that implements
 * ViewModelProvider.factory to instantiate constructor
 * DashBoardViewModel.
 * @file DashBoardViewModelFactory.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 06/03/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper

class DashBoardViewModelFactory(private val dbHelper: DatabaseHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(DatabaseHelper::class.java)
            .newInstance(dbHelper)
    }
}