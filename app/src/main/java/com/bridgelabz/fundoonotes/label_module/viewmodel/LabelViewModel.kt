/**
 * Fundoo Notes
 * @description LabelViewModel extends VieModel
 * @file LabelViewModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 26/02/2020
 */
package com.bridgelabz.fundoonotes.label_module.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bridgelabz.fundoonotes.label_module.model.Label
import com.bridgelabz.fundoonotes.repository.local_service.lable_module.LabelTableManager

class LabelViewModel(private val labelTableManager: LabelTableManager) : ViewModel() {

    private val labelLiveData = MutableLiveData<ArrayList<Label>>()

    fun saveLabel(label: Label) {
        val labelId = labelTableManager.insertLabel(label)
        if(labelId > 0){
            fetchLabels()
        }
    }

    private fun fetchLabels() {
        labelLiveData.value = labelTableManager.fetchLabels()
    }

    fun getLabelLiveData(): LiveData<ArrayList<Label>> {
        fetchLabels()
        return labelLiveData
    }
}