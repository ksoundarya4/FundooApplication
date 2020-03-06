/**
 * Fundoo Notes
 * @description DashBoardViewModel extends ViewModel
 * to provide user live data
 * @file DashBoardViewModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 06/03/2020
 */
package com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.local_service.user_module.UserDbManagerImpl
import com.bridgelabz.fundoonotes.user_module.registration.model.User

class DashBoardViewModel(dbHelper: DatabaseHelper) : ViewModel() {

    private val userTableManager = UserDbManagerImpl(dbHelper)
    private val userLiverData = MutableLiveData<User>()

    fun authenticatedUser(email: String) {
        userLiverData.value = userTableManager.fetchUser(email)
    }

    fun getUser(): LiveData<User> {
        return userLiverData
    }
}