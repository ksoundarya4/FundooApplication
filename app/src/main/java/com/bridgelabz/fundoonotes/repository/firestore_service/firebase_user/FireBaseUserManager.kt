package com.bridgelabz.fundoonotes.repository.firestore_service.firebase_user

interface FireBaseUserManager {
    fun insertUser(fireBaseUser : FireBaseUser)
    fun updateUser(fireBaseUser: FireBaseUser)
    fun deleteUser(fireBaseUser: FireBaseUser)
}