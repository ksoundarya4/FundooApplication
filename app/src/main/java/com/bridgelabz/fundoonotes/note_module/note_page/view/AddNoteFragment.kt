package com.bridgelabz.fundoonotes.note_module.note_page.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.SharedViewModel

class AddNoteFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel
    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var saveNoteButton: Button
    private lateinit var note: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_note, container, false)
        view.findView()

        saveNoteButton.setOnClickListener {
            val noteTitle = title.editableText.toString()
            val noteDescription = description.editableText.toString()
            note = Note(noteTitle, noteDescription)
            setClickListener(view, note)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)
    }

    private fun View.findView() {
        title = this.findViewById(R.id.edit_text_title)
        description = this.findViewById(R.id.edit_text_description)
        saveNoteButton = this.findViewById(R.id.button_save_note)
    }

    private fun setClickListener(view: View, note: Note) {
        viewModel.onSaveButtonClick(view, note)
    }
}
