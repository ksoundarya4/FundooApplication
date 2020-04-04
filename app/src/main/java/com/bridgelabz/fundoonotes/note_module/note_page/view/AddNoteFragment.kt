package com.bridgelabz.fundoonotes.note_module.note_page.view

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.launch_module.FundooNotesPreference
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.NoteServerResponse
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.OnBackPressed
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.ShareViewModelFactory
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.SharedViewModel
import com.bridgelabz.fundoonotes.user_module.view.hideKeyboard
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform

const val REMINDER_REQUEST_CODE = 0
const val COLOUR_REQUEST_CODE = 1

class AddNoteFragment : Fragment(), OnBackPressed, OnReminderListener, OnColourListener {

    private lateinit var title: EditText
    private lateinit var description: EditText
    private var note = Note()
    private val noteFactory by lazy {
        ShareViewModelFactory(requireContext())
    }
    private val viewModel by lazy {
        ViewModelProvider(this, noteFactory).get(SharedViewModel::class.java)
    }
    private val floatingActionButton by lazy {
        requireActivity().findViewById<FloatingActionButton>(R.id.fab)
    }
    private val toolbar by lazy {
        requireView().findViewById<Toolbar>(R.id.fragment_add_note_toolbar)
    }
    private val addNoteFragment by lazy {
        requireView().findViewById<ConstraintLayout>(R.id.add_note_constraint_layout)
    }
    private val fragmentContainerLayout by lazy {
        requireActivity().findViewById<ConstraintLayout>(R.id.fragment_constraint_layout)
    }
    private val preference by lazy {
        FundooNotesPreference.getPreference(requireContext())
    }
    private val accessToken by lazy {
        preference.getString("access_token", "")
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
        observeNoteServerResponse()
        setUpFragmentToolbar()
        setToolBarOnCLickListener()
    }

    private fun observeNoteServerResponse() {
        viewModel.getNoteSererResponse()
            .observe(viewLifecycleOwner, Observer { handleServerResponse(it) })
    }

    private fun handleServerResponse(serverResponse: NoteServerResponse) {
        when (serverResponse) {
            NoteServerResponse.Failure -> Toast.makeText(
                requireContext(),
                "Note not saved",
                Toast.LENGTH_LONG
            ).show()
            NoteServerResponse.Success -> {
                Toast.makeText(
                    requireContext(),
                    "Note saved",
                    Toast.LENGTH_LONG
                ).show()
                viewModel.fetchNoteFromServer(accessToken!!, note.userId!!)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform(requireContext())
    }

    private fun setLayoutBackground() {
        if (note.colour != null) {
            addNoteFragment.setBackgroundColor(note.colour!!)
            fragmentContainerLayout.setBackgroundColor(note.colour!!)
        }
    }

    private fun setToolBarOnCLickListener() {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_note_menu_archive_note -> {
                    makeNoteArchive()
                }
                R.id.add_note_menu_reminder -> {
                    startReminderFragment()
                }
                R.id.add_note_menu_pin_button -> {
                    it.isChecked = true
                    makeNotePinned()
                }
                R.id.add_note_menu_delete_note -> {
                    deleteNote()
                }
                R.id.add_note_menu__copy_note -> {
                    makeCopyOfNote()
                }
                R.id.add_note_menu_change_colour -> {
                    startColourFragment()
                }
                else -> return@setOnMenuItemClickListener false
            }
        }
    }

    private fun startColourFragment(): Boolean {
        val fragmentManager = requireActivity().supportFragmentManager
        val colourFragment = ColourDialogFragment()
        colourFragment.setTargetFragment(this, COLOUR_REQUEST_CODE)
        colourFragment.show(fragmentManager, getString(R.string.note_colour))
        return true
    }

    private fun makeCopyOfNote(): Boolean {
        if (note.isArchived == 1)
            note.isArchived = 0
        if (note.isPinned == 1)
            note.isPinned = 0
        insertNote()
        requireActivity().onBackPressed()
        return true
    }

    private fun deleteNote(): Boolean {
        note.isDeleted = 1
        viewModel.markNoteAsTrash(note, accessToken!!)
        snackBar(requireView(), getString(R.string.delete_note_snackbar_message))
        requireActivity().onBackPressed()
        return true
    }

    private fun makeNotePinned(): Boolean {
        note.isPinned = 1
        if (note.isArchived == 1)
            note.isArchived = 0
        viewModel.markNoteAsPinOrUnpin(note, accessToken!!)
        snackBar(requireView(), getString(R.string.pin_note_snackbar_message))
        return true
    }

    private fun makeNoteArchive(): Boolean {
        note.isArchived = 1
        if (note.isPinned == 1)
            note.isPinned = 0
        viewModel.markNoteAsArchiveOrUnarchive(note, accessToken!!)
        snackBar(
            requireView(),
            getString(R.string.archive_note_snackbar_message)
        )
        requireActivity().onBackPressed()
        return true
    }

    private fun startReminderFragment(): Boolean {
        val fragmentManager = requireActivity().supportFragmentManager
        val reminderDialog = ReminderDialogFragment()
        reminderDialog.setTargetFragment(this, REMINDER_REQUEST_CODE)
        reminderDialog.show(fragmentManager, getString(R.string.dialog_reminder_title))
        return true
    }

    private fun getNoteArgument() {
        if (arguments != null) {
            note = arguments!!.get(getString(R.string.note)) as Note
            Log.d("noteBundle", note.toString())
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
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        showBottomAppBar()
        view?.hideKeyboard()
        fragmentContainerLayout.setBackgroundColor(
            resources.getColor(
                R.color.colorPrimaryWhite,
                resources.newTheme()
            )
        )
    }

    private fun showBottomAppBar() {
        floatingActionButton.show()
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
            val createdNote = createNote(noteTitle, noteDescription)
            viewModel.insertNoteOnCLick(accessToken = accessToken!!, note = createdNote)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.toast_discard_empty_note),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun createNote(noteTitle: String, noteDescription: String): Note {
        val newNote = Note(noteTitle, noteDescription)
        newNote.isArchived = note.isArchived
        newNote.isDeleted = note.isDeleted
        newNote.isPinned = note.isPinned
        newNote.label = note.label
        newNote.reminder = note.reminder
        newNote.position = note.position
        newNote.colour = note.colour
        newNote.userId = note.userId
        return newNote
    }

    private fun updateNote() {
        val noteTitle = title.editableText.toString()
        val noteDescription = description.editableText.toString()
        if (noteTitle.isNotEmpty() || noteDescription.isNotEmpty()) {
            val noteToUpdate = createUpdateNote(noteTitle, noteDescription)
            Log.d("note", noteToUpdate.toString())
            viewModel.updateNoteOnClick(noteToUpdate, accessToken!!)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.toast_discard_empty_note),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun createUpdateNote(noteTitle: String, noteDescription: String): Note {
        val noteTobeUpdated = Note(noteTitle, noteDescription)
        noteTobeUpdated.id = note.id
        noteTobeUpdated.isArchived = note.isArchived
        noteTobeUpdated.isDeleted = note.isDeleted
        noteTobeUpdated.isPinned = note.isPinned
        noteTobeUpdated.label = note.label
        noteTobeUpdated.reminder = note.reminder
        noteTobeUpdated.position = note.position
        noteTobeUpdated.colour = note.colour
        noteTobeUpdated.noteId = note.noteId
        noteTobeUpdated.userId = note.userId
        return noteTobeUpdated
    }

    override fun onReminderSubmit(date: String, time: String) {
        note.reminder = "$date,$time"
        viewModel.updateReminderOfNote(note, accessToken!!)
    }

    private fun snackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        hideActivityToolbar()
        hideBottomAppbar()
        setLayoutBackground()
    }

    override fun onColourSubmit(colour: Int) {
        note.colour = colour
        viewModel.updateColourOfNote(note, accessToken!!)
        addNoteFragment.setBackgroundColor(colour)
        fragmentContainerLayout.setBackgroundColor(colour)
    }
}