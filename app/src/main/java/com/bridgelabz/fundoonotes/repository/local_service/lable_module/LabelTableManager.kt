/**
 * Fundoo Notes
 * @description LabelTableManager Interface to perform
 * CRUD operation on Label Table in App.db
 * @file LabelTableManager.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 25/02/2020
 */
package com.bridgelabz.fundoonotes.repository.local_service.lable_module

import com.bridgelabz.fundoonotes.label_module.model.Label

interface LabelTableManager {
    fun insertLabel(label: Label): Long
    fun updateLabel(label: Label)
    fun fetchLabels(): ArrayList<Label>
    fun deleteLabel(id: Long)
}