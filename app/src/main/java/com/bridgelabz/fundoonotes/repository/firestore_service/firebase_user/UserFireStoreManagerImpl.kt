package com.bridgelabz.fundoonotes.repository.firestore_service.firebase_user

import android.util.Log
import com.bridgelabz.fundoonotes.user_module.registration.model.RegistrationStatus
import com.bridgelabz.fundoonotes.user_module.registration.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore


class UserFireStoreManagerImpl : UserFireStoreManager {


    private val TAG = "FireStore Manager"
    private val completeListener = OnCompleteListener<Void> { task ->
        if (task.isSuccessful)
            Log.d(TAG, "User Saved")
        else
            Log.d(TAG, "Failed to save USer")
    }
    private val db = FirebaseFirestore.getInstance()
    private val userReference = db.collection("Users")


    override fun insertUser(user: User) {
        val userDocumentReference = userReference.document()

        if (user.id == null) {
            user.id = userDocumentReference.id
            userDocumentReference.set(user).addOnCompleteListener(completeListener)
        }
    }

    override fun updateUser(user: User) {
        val userDocumentReference = userReference.document(user.id!!)
        userDocumentReference.update("id", user.id, user).addOnCompleteListener(completeListener)
    }

    override fun deleteUser(user: User) {
        val userDocumentReference = userReference.document(user.id!!)
        userDocumentReference.delete()
    }

    override fun fetchUser(email: String): User? {
        var user: User? = null
        val userDocumentReference =
            userReference.document(email).get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    user = documentSnapshot.toObject(User::class.java)
                } else
                    Log.d(TAG, "User does not exit")
            }
        return user!!
    }
}