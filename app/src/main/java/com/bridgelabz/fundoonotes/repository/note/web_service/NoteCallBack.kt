package com.bridgelabz.fundoonotes.repository.note.web_service

interface NoteCallBack {
    fun onNoteReceivedSuccess(noteResponseModel: NoteResponseModel)
    fun onNoteReceivedFailure(exception: Throwable)
}