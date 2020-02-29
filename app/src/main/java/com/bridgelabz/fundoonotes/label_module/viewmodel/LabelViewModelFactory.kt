/**
 * Fundoo Notes
 * @description RepositoryFactory that implements
 * ViewModelProvider.factory to instantiate constructor
 * parameter of LabelViewModel.
 * @file LabelViewModelFactory.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 13/02/2020
 */
package com.bridgelabz.fundoonotes.label_module.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bridgelabz.fundoonotes.repository.local_service.lable_module.LabelTableManager

class LabelViewModelFactory(val labelTableManager: LabelTableManager) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(LabelTableManager::class.java)
            .newInstance(labelTableManager)
    }
}