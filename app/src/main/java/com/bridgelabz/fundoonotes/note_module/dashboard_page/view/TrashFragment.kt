package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.view_utils.ViewUtils
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.NoteTableManagerFactory
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.SharedViewModel
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteTableManagerImpl
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TrashFragment : Fragment(), OnNoteClickListener {

    private val noteFactory: NoteTableManagerFactory by lazy {
        NoteTableManagerFactory(NoteTableManagerImpl(DatabaseHelper(requireContext())))
    }

    private val viewModel by lazy {
        ViewModelProvider(this, noteFactory).get(SharedViewModel::class.java)
    }

    private val recyclerView  by lazy {
        requireActivity().findViewById<RecyclerView>(R.id.notes_recycler_view)
    }

//    private val bottomAppBar by lazy {
//        requireActivity().findViewById<BottomAppBar>(R.id.bottom_app_bar)
//    }

    private val floatingActionButton by lazy {
        requireActivity().findViewById<FloatingActionButton>(R.id.fab)
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
        ViewUtils.setActionBarTitle(
            (requireActivity() as AppCompatActivity),
            "Trash"
        )
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