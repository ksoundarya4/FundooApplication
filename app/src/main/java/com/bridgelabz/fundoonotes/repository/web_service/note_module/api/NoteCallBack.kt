package com.bridgelabz.fundoonotes.repository.web_service.note_module.api

import com.bridgelabz.fundoonotes.repository.web_service.note_module.models.NoteResponseModel

interface NoteCallBack {
    fun onNoteReceivedSuccess(noteResponseModel: NoteResponseModel)
    fun onNoteReceivedFailure(exception: Throwable)
}