package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.NoteDbManagerFactory
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.SharedViewModel
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteDatabaseManagerImpl

class TrashFragment : Fragment(), OnNoteClickListener {

    private val noteFactory: NoteDbManagerFactory by lazy {
        NoteDbManagerFactory(NoteDatabaseManagerImpl(DatabaseHelper(requireContext())))
    }

    private val viewModel by lazy {
        ViewModelProvider(this, noteFactory).get(SharedViewModel::class.java)
    }

    private val recyclerView: RecyclerView by lazy {
        requireActivity().findViewById<RecyclerView>(R.id.notes_recycler_view)
    }
    private val adapter = NoteViewAdapter(ArrayList(), this)
    private var deletedNotes = ArrayList<Note>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getDeletedNoteLiveData()
            .observe(requireActivity(), Observer { observeDeletedNote(it) })
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, 1)
        recyclerView.adapter = adapter
    }

    private fun observeDeletedNote(deletedNoteList: ArrayList<Note>) {
        deletedNotes = deletedNoteList
        adapter.setListOfNotes(deletedNoteList)
        adapter.notifyDataSetChanged()
    }

    override fun onClick(adapterPosition: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLongClick(adapterPosition: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}