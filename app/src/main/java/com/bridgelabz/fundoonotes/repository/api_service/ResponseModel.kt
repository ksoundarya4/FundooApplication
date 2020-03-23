/**
 *Fundoo Notes
 *@description ResponseModel is a is a data class
 * that holds the properties of HTTP response body.
 * @file ResponseModel.kt
 * @author ksoundarya4
 * @version 1.0
 * @since 23/03/2020
 */
package com.bridgelabz.fundoonotes.repository.api_service

import com.google.gson.annotations.SerializedName

data class ResponseModel(
    var success: Boolean? = null,
    var message: String? = null,
    @SerializedName("data")
    var listOfNoteResponses: ArrayList<NoteResponseModel>? = null
)