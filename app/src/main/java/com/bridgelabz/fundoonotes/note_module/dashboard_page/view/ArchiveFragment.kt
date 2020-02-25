package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.NoteTableManagerFactory
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.SharedViewModel
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteTableManagerImpl

class ArchiveFragment : Fragment(), OnNoteClickListener {

    private val noteFactory: NoteTableManagerFactory by lazy {
        NoteTableManagerFactory(NoteTableManagerImpl(DatabaseHelper(requireContext())))
    }
    private val viewModel by lazy {
        ViewModelProvider(this, noteFactory).get(SharedViewModel::class.java)
    }

    private val recyclerView: RecyclerView by lazy {
        requireView().findViewById<RecyclerView>(R.id.notes_recycler_view)
    }

    private val noteAdapter = NoteViewAdapter(ArrayList(), this)

    private lateinit var archiveNotes: ArrayList<Note>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getArchiveNoteLiveData()
            .observe(requireActivity(), Observer { observeArchiveNotes(it) })
        initRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        (requireActivity() as AppCompatActivity).supportActionBar!!.title =
            getString(R.string.app_bar_title_archive_notes)
    }

    private fun initRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = noteAdapter
    }

    private fun observeArchiveNotes(archiveNoteList: ArrayList<Note>) {
        archiveNotes = archiveNoteList
        noteAdapter.setListOfNotes(archiveNoteList)
        noteAdapter.notifyDataSetChanged()
    }

    override fun onClick(adapterPosition: Int) {
    }

    override fun onLongClick(adapterPosition: Int) {

    }

}