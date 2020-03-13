package com.bridgelabz.fundoonotes.repository.firestore_service.firebase_user

import com.bridgelabz.fundoonotes.user_module.registration.model.User
import com.google.firebase.firestore.FirebaseFirestore

class UserFireStoreManagerImpl : UserFireStoreManager {

    private val db = FirebaseFirestore.getInstance()
    private val userReference = db.collection("Users")
    private val userDocumentReference = userReference.document()

    override fun insertUser(user: User) {
        userReference.add(user)
    }

    override fun updateUser(user: User) {

    }

    override fun deleteUser() {
        TODO("Not yet implemented")
    }

    override fun fetchUser(): User {
        TODO("Not yet implemented")
    }
}