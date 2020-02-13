package com.bridgelabz.fundoonotes.note_module.note_page.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.NoteDbManagerFactory
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.SharedViewModel
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteDatabaseManagerImpl
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton


class AddNoteFragment : Fragment() {

    private val noteFactory by lazy {
        NoteDbManagerFactory(NoteDatabaseManagerImpl(DatabaseHelper(requireContext())))
    }
    private val viewModel by lazy {
        ViewModelProviders.of(this, noteFactory).get(SharedViewModel::class.java)
    }
    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var note: Note
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
        hideActivityToolbar()
        setUpFragmentToolbar()
        hideBottomAppbar()
    }

    private fun setUpFragmentToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener {
            val noteTitle = title.editableText.toString()
            val noteDescription = description.editableText.toString()
            if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                note = Note(noteTitle, noteDescription)
                setClickListener(note)
                requireActivity().supportFragmentManager.popBackStack()
            } else {
                Toast.makeText(requireContext(), "Empty note discarded", Toast.LENGTH_LONG).show()
                requireActivity().supportFragmentManager.popBackStack()
            }
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
    }

    private fun showBottomAppBar() {
        floatingActionButton.show()
        bottomAppBar.performShow()
    }

    private fun findViews(view: View) {
        title = view.findViewById(R.id.edit_text_title)
        description = view.findViewById(R.id.edit_text_description)
//        saveNoteButton = view.findViewById(R.id.button_save_note)
    }

    private fun setClickListener(note: Note) {
        viewModel.onSaveButtonClick(note)
    }
}
