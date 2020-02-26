/**
 * Fundoo Notes
 * @description LabelViewModel extends VieModel
 * @file LabelViewModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 26/02/2020
 */
package com.bridgelabz.fundoonotes.label_module.viewmodel

import androidx.lifecycle.ViewModel
import com.bridgelabz.fundoonotes.repository.local_service.lable_module.LabelTableManager

class LabelViewModel(private val labelTableManager : LabelTableManager) : ViewModel() {
}