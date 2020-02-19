package com.bridgelabz.fundoonotes.note_module.note_page.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.OnBackPressed
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.NoteDbManagerFactory
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.SharedViewModel
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteDatabaseManagerImpl
import com.bridgelabz.fundoonotes.user_module.login.view.hideKeyboard
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton


class DetailNoteFragment : Fragment(), OnBackPressed {

    private val noteFactory by lazy {
        NoteDbManagerFactory(NoteDatabaseManagerImpl(DatabaseHelper(requireContext())))
    }
    private val sharedViewModel: SharedViewModel by lazy {
        ViewModelProvider(this, noteFactory).get(SharedViewModel::class.java)
    }
    private val bottomAppBar by lazy {
        requireActivity().findViewById<BottomAppBar>(R.id.bottom_app_bar)
    }
    private val floatingActionButton by lazy {
        requireActivity().findViewById<FloatingActionButton>(R.id.fab)
    }
    private val toolbar by lazy {
        requireView().findViewById<Toolbar>(R.id.fragment_detail_note_toolbar)
    }
    private lateinit var title: EditText
    private lateinit var description: EditText
    private lateinit var note: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_note, container, false)
        findViews(view)
        return view
    }

    private fun findViews(view: View) {
        title = view.findViewById(R.id.detail_edit_text_title)
        description = view.findViewById(R.id.detail_edit_text_description)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedViewModel.getLiveNote()
            .observe(requireActivity(), Observer { initDetailFragment(it) })
        hideActivityToolbar()
        setUpFragmentToolbar()
        hideBottomAppbar()
    }

    private fun initDetailFragment(note: Note) {
        Log.d("notedetails", note.title)
        title.setText(note.title)
        Log.d("notetitle", title.toString())
        description.setText(note.description)
        Log.d("notedescription", description.toString())
    }

    private fun setUpFragmentToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
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

    override fun onBackPressed() {
        val noteTitle = title.editableText.toString()
        val noteDescription = description.editableText.toString()
        if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
            note = Note(noteTitle, noteDescription)
            setClickListener(note)
        } else {
            Toast.makeText(requireContext(), "Empty note discarded", Toast.LENGTH_LONG).show()
        }
    }

    private fun setClickListener(note: Note) {
        sharedViewModel.updateNoteOnClick(note)
    }
}
