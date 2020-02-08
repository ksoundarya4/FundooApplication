package com.bridgelabz.fundoonotes.note_module.note_page.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.bridgelabz.fundoonotes.R

class AddNoteActivity : AppCompatActivity() {

    private val title by lazy { findViewById<EditText>(R.id.edit_text_title) }
    private val description by lazy { findViewById<EditText>(R.id.edit_text_description) }
    private val saveButton by lazy { findViewById<Button>(R.id.button_save_note) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        setOnSaveNoteButton()
    }

    private fun setOnSaveNoteButton() {
        val noteTitle = title.editableText.toString()
        val noteDescription = description.editableText.toString()
    }
}
