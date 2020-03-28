package com.bridgelabz.fundoonotes.repository.web_service

interface NoteCallBack {
    fun onNoteReceivedSuccess(noteResponseModel: NoteResponseModel)
    fun onNoteReceivedFailure(exception: Throwable)
}