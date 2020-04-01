/**
 * Fundoo Notes
 * @description UserViewModelFactory class initializes the constructor
 * of UserViewModel.
 * @file UserViewModelFactory.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 01/04/2020
 */
package com.bridgelabz.fundoonotes.user_module

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.local_service.user_module.UserDbManagerImpl
import com.bridgelabz.fundoonotes.repository.user.UserRepository
import com.bridgelabz.fundoonotes.repository.user.UserRepositoryImplementation
import com.bridgelabz.fundoonotes.repository.user.web_services.RetrofitClient
import com.bridgelabz.fundoonotes.repository.user.web_services.UserApi

class UserViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val retrofit = RetrofitClient.getRetrofitClient()
    private val userApi = retrofit.create(UserApi::class.java)
    private val userTableManger = UserDbManagerImpl(DatabaseHelper(context))
    private val repository = UserRepositoryImplementation(userApi, userTableManger)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(UserRepository::class.java).newInstance(repository)
    }
}