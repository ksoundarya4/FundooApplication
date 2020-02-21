package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.NoteDbManagerFactory
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.SharedViewModel
import com.bridgelabz.fundoonotes.note_module.note_page.view.AddNoteFragment
import com.bridgelabz.fundoonotes.note_module.note_page.view.DetailNoteFragment
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteDatabaseManagerImpl

class NoteFragment : Fragment(), OnNoteClickListener {

    private val noteFactory by lazy {
        NoteDbManagerFactory(NoteDatabaseManagerImpl(DatabaseHelper(requireContext())))
    }
    private val sharedViewModel: SharedViewModel by lazy {
        ViewModelProvider(this, noteFactory).get(SharedViewModel::class.java)
    }
    private val recyclerView: RecyclerView by lazy {
        requireView().findViewById<RecyclerView>(R.id.notes_recycler_view)
    }

    private val noteAdapter = NoteViewAdapter(ArrayList<Note>(), this)

    private lateinit var notes: ArrayList<Note>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
        sharedViewModel.getNoteLiveData().observe(requireActivity(), Observer { observeNotes(it) })
    }

    private fun initRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = noteAdapter
    }

    private fun observeNotes(noteList: ArrayList<Note>) {
        Log.d("noteList", noteList.toString())
        notes = noteList
        noteAdapter.setListOfNotes(noteList)
        noteAdapter.notifyDataSetChanged()
    }

    override fun onClick(adapterPosition: Int) {
        val note = notes[adapterPosition]
        val bundle = Bundle()
        bundle.putSerializable(getString(R.string.note), note)
        replaceWithAddNoteFragment(bundle)
    }

    override fun onLongClick(adapterPosition: Int) {
        Toast.makeText(requireActivity(), "onLongClick", Toast.LENGTH_SHORT).show()
    }

    private fun replaceWithAddNoteFragment(bundle: Bundle) {
        val addNoteFragment = AddNoteFragment()
        addNoteFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, addNoteFragment).addToBackStack(null).commit()
    }
}