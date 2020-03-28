package com.bridgelabz.fundoonotes.repository.user

import com.bridgelabz.fundoonotes.user_module.registration.model.User

interface UserRepository {
    fun insertUser(user: User)
    fun updateUser(user: User)
    fun deleteUser(user: User)
    fun fetchUser(email: String): User
}