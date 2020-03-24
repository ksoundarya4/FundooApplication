package com.bridgelabz.fundoonotes.repository.api_service

interface NoteCallBack {
    fun onNoteReceivedSuccess(noteResponseModel: NoteResponseModel)
    fun onNoteReceivedFailure(exception: Throwable)
}