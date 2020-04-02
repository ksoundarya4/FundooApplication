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

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.NoteFragment
import com.bridgelabz.fundoonotes.repository.user.UserRepository
import com.bridgelabz.fundoonotes.user_module.model.User

class DashBoardViewModel(private val repository: UserRepository) : ViewModel() {

    private val userLiverData = MutableLiveData<User>()
    private val fragmentLiveData = MutableLiveData<Fragment>(NoteFragment())

    fun authenticatedUser(email: String) {
        userLiverData.value = repository.fetchUserFromLocalDb(email)
    }

    fun getUser(): LiveData<User> {
        return userLiverData
    }

    fun setFragmentLiveData(fragment: Fragment) {
        fragmentLiveData.value = fragment
    }

    fun getFragmentLiveData(): LiveData<Fragment> {
        return fragmentLiveData
    }
}