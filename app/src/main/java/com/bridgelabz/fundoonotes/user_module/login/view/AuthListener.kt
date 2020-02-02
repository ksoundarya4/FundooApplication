package com.bridgelabz.fundoonotes.user_module.login.view

import androidx.lifecycle.LiveData
import com.bridgelabz.fundoonotes.user_module.login.model.AuthState

interface AuthListener {
    fun onStarted()
    fun onFailure(message: String)
    fun onSuccess(liveData: LiveData<AuthState>)
}