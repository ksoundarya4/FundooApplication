package com.bridgelabz.fundoonotes.note_module.note_page.view

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.OnBackPressed
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.NoteTableManagerFactory
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.SharedViewModel
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteTableManagerImpl
import com.bridgelabz.fundoonotes.user_module.login.view.hideKeyboard
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

const val REMINDER_REQUEST_CODE = 0

class AddNoteFragment : Fragment(), OnBackPressed, OnReminderListener {

    private lateinit var title: EditText
    private lateinit var description: EditText
    private var note = Note()
    private val noteFactory by lazy {
        NoteTableManagerFactory(NoteTableManagerImpl(DatabaseHelper(requireContext())))
    }
    private val viewModel by lazy {
        ViewModelProvider(this, noteFactory).get(SharedViewModel::class.java)
    }
    private val bottomAppBar by lazy {
        requireActivity().findViewById<BottomAppBar>(R.id.bottom_app_bar)
    }
    private val floatingActionButton by lazy {
        requireActivity().findViewById<FloatingActionButton>(R.id.fab)
    }
    private val toolbar by lazy {
        requireView().findViewById<Toolbar>(R.id.fragment_add_note_toolbar)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_note, container, false)
        findViews(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getNoteArgument()
        hideActivityToolbar()
        setUpFragmentToolbar()
        hideBottomAppbar()
        setToolBarOnCLickListener()
    }

    private fun setToolBarOnCLickListener() {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_note_menu_archive_note -> {
                    note.isArchived = 1
                    Toast.makeText(requireActivity(), "clicked Archive", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }
                R.id.add_note_menu_reminder -> {
                    startReminderFragment()
                    return@setOnMenuItemClickListener true
                }
                else -> return@setOnMenuItemClickListener false
            }
        }
    }

    private fun startReminderFragment() {
        val fragmentManager = requireActivity().supportFragmentManager
        val reminderDialog = ReminderDialogFragment()
        reminderDialog.setTargetFragment(this, REMINDER_REQUEST_CODE)
        reminderDialog.show(fragmentManager, getString(R.string.dialog_reminder_title))
    }

    private fun getNoteArgument() {
        if (arguments != null) {
            note = arguments!!.get(getString(R.string.note)) as Note
            title.setText(note.title)
            description.setText(note.description)
        }
    }

    private fun setUpFragmentToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun hideActivityToolbar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    private fun hideBottomAppbar() {
        floatingActionButton.hide()
        bottomAppBar.performHide()
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        showBottomAppBar()
        view?.hideKeyboard()
    }

    private fun showBottomAppBar() {
        floatingActionButton.show()
        bottomAppBar.performShow()
    }

    private fun findViews(view: View) {
        title = view.findViewById(R.id.edit_text_title)
        description = view.findViewById(R.id.edit_text_description)
    }

    override fun onBackPressed() {
        if (note.title.isEmpty() && note.description.isEmpty()) {
            insertNote()
        } else {
            updateNote()
        }
    }

    private fun insertNote() {
        val noteTitle = title.editableText.toString()
        val noteDescription = description.editableText.toString()
        if (noteTitle.isNotEmpty() || noteDescription.isNotEmpty()) {
            val createdNote = createNewNote(noteTitle, noteDescription)
            viewModel.insertNoteOnCLick(createdNote)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.toast_discard_empty_note),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun createNewNote(noteTitle: String, noteDescription: String): Note {
        val newNote = Note(noteTitle, noteDescription)
        newNote.isArchived = note.isArchived
        newNote.isDeleted = note.isDeleted
        newNote.isPinned = note.isPinned
        newNote.label = note.label
        newNote.reminder = note.reminder
        newNote.position = note.position
        newNote.colour = note.colour
        return newNote
    }

    private fun updateNote() {
        val noteTitle = title.editableText.toString()
        val noteDescription = description.editableText.toString()
        if (noteTitle.isNotEmpty() || noteDescription.isNotEmpty()) {
            val noteToUpdate = noteToBeUpdated(noteTitle, noteDescription)
            Log.d("note", noteToUpdate.toString())
            viewModel.updateNoteOnClick(noteToUpdate)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.toast_discard_empty_note),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun noteToBeUpdated(noteTitle: String, noteDescription: String): Note {
        val noteToUpdate = Note(noteTitle, noteDescription)
        noteToUpdate.id = note.id
        noteToUpdate.isArchived = note.isArchived
        noteToUpdate.isDeleted = note.isDeleted
        noteToUpdate.isPinned = note.isPinned
        noteToUpdate.label = note.label
        noteToUpdate.reminder = note.reminder
        noteToUpdate.position = note.position
        noteToUpdate.colour = note.colour
        return noteToUpdate
    }

    override fun onReminderSubmit(date: String, time: String) {
        note.reminder = "$date,$time"
    }
}