/**
 * Fundoo Notes
 * @description UserViewModelFactory class initializes the constructor
 * of UserViewModel.
 * @file UserViewModelFactory.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 01/04/2020
 */
package com.bridgelabz.fundoonotes.user_module.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bridgelabz.fundoonotes.launch_module.FundooNotesPreference
import com.bridgelabz.fundoonotes.repository.common.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.local_service.user_module.UserDbManagerImpl
import com.bridgelabz.fundoonotes.repository.common.UserRepository
import com.bridgelabz.fundoonotes.repository.common.UserRepositoryImplementation
import com.bridgelabz.fundoonotes.repository.common.RetrofitClient
import com.bridgelabz.fundoonotes.repository.web_service.user_module.api.UserApi

class UserViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val retrofit = RetrofitClient.getRetrofitClient()
    private val userApi = retrofit.create(UserApi::class.java)
    private val userTableManger = UserDbManagerImpl(
        DatabaseHelper(
            context
        )
    )
//    private val preferences = FundooNotesPreference.getPreference(context)
    private val repository =
        UserRepositoryImplementation(
            userApi,
            userTableManger
        )

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UserRepository::class.java).newInstance(repository)
    }
}