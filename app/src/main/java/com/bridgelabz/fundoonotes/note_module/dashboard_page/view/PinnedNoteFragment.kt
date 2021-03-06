package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.view_utils.ViewUtils
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.ShareViewModelFactory
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.SharedViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PinnedNoteFragment : Fragment(), OnNoteClickListener {

    private val noteFactory: ShareViewModelFactory by lazy {
        ShareViewModelFactory(requireContext())
    }
    private val viewModel by lazy {
        ViewModelProvider(this, noteFactory).get(SharedViewModel::class.java)
    }
    private val recyclerView by lazy {
        requireView().findViewById<RecyclerView>(R.id.notes_recycler_view)
    }
    private val floatingActionButton by lazy {
        requireActivity().findViewById<FloatingActionButton>(R.id.fab)
    }
    private val noteAdapter = NoteViewAdapter(ArrayList(), this)
    private lateinit var pinnedNotes: ArrayList<Note>
    private var note = Note()
    private var accessToken: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        ViewUtils.setActionBarTitle(
            (requireActivity() as AppCompatActivity),
            getString(R.string.important)
        )
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getNoteArguments()
        viewModel.getNoteLiveData(userId = note.userId!!)
            .observe(requireActivity(), Observer { observeArchiveNotes(it) })
        initRecyclerView()
    }

    private fun getNoteArguments() {
        if (arguments != null) {
            note = arguments!!.get(getString(R.string.note)) as Note
            accessToken = arguments!!.getString("access_token")!!
            Log.d("noteBundle", note.toString())
        }
    }

    private fun initRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = noteAdapter
    }

    private fun observeArchiveNotes(noteList: ArrayList<Note>) {
        pinnedNotes = getPinnedNotes(noteList)
        noteAdapter.setListOfNotes(pinnedNotes)
        noteAdapter.notifyDataSetChanged()
    }

    private fun getPinnedNotes(noteList: ArrayList<Note>): ArrayList<Note> {
        val notes = ArrayList<Note>()
        for (note in noteList) {
            if (note.isPinned == 1)
                notes.add(note)
        }
        return notes
    }

    override fun onClick(adapterPosition: Int) {
    }

    override fun onLongClick(adapterPosition: Int) {

    }

    override fun onResume() {
        super.onResume()
        hideBottomAppbar()
    }

    override fun onStop() {
        super.onStop()
        showBottomAppBar()
    }

    private fun hideBottomAppbar() {
        floatingActionButton.hide()
//        bottomAppBar.performHide()
    }

    private fun showBottomAppBar() {
        floatingActionButton.show()
//        bottomAppBar.performShow()
    }
}