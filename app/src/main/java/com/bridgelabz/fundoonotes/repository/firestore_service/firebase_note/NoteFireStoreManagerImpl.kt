package com.bridgelabz.fundoonotes.repository.firestore_service.firebase_note

import android.util.Log
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore

class NoteFireStoreManagerImpl : NoteFireStoreManager {

    private val TAG = "FireStore Manager"
    private val completeListener = OnCompleteListener<Void> { task ->
        if (task.isSuccessful)
            Log.d(TAG, "Note Saved")
        else
            Log.d(TAG, "Failed to save Note")
    }
    private val db = FirebaseFirestore.getInstance()
    private val notesReference = db.collection("Notes")

    override fun insertNote(note: Note) {
        val noteDocumentReference = notesReference.document()
        if (note.id == null) {
            note.id = noteDocumentReference.id
            noteDocumentReference.set(note).addOnCompleteListener(completeListener)
        }
    }

    override fun updateNote(note: Note) {
        val noteDocumentReference = notesReference.document(note.id!!)
        noteDocumentReference.set(note).addOnCompleteListener(completeListener)

}

override fun deleteNote(id: String) {
    val noteDocumentReference = notesReference.document(id)
    noteDocumentReference.delete()
}

override fun fetchNote(userId: String): Note? {
    val noteDocumentReference = notesReference.document(userId)
    var note: Note? = null

    noteDocumentReference.get().addOnSuccessListener { documentSnapshot ->
        if (documentSnapshot.exists())
            note = documentSnapshot.toObject(Note::class.java)
        else
            Log.d(TAG, "Note does note exist")
    }
    return note
}

override fun fetchNotes(): ArrayList<Note> {
    val noteDocumentReference = notesReference.document()
    val notes = ArrayList<Note>()

    noteDocumentReference.get().addOnSuccessListener { documentSnapshot ->
        if (documentSnapshot.exists()) {
            val note = documentSnapshot.toObject(Note::class.java)
            if (note != null)
                notes.add(note)
        } else
            Log.d(TAG, "Note does note exist")
    }
    return notes
}
}