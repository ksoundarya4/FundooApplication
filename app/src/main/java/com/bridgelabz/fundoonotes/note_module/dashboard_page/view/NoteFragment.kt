package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.recycler_view_strategy.LinearRecyclerViewManager
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.recycler_view_strategy.RecyclerViewLayoutManager
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.recycler_view_strategy.StaggeredRecyclerViewtManager
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.NoteDbManagerFactory
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.SharedViewModel
import com.bridgelabz.fundoonotes.note_module.note_page.view.AddNoteFragment
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
    private var recycleViewIsLinearLayout = false
    private var viewChanged = true

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
        (requireActivity() as AppCompatActivity).startSupportActionMode(actionModeCallBack)
    }

    private fun replaceWithAddNoteFragment(bundle: Bundle) {
        val addNoteFragment = AddNoteFragment()
        addNoteFragment.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, addNoteFragment).addToBackStack(null).commit()

    }

    val actionModeCallBack: ActionMode.Callback = object : ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return false
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode!!.menuInflater.inflate(R.menu.long_click_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onDestroyActionMode(mode: ActionMode?) {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (requireActivity() as AppCompatActivity).supportActionBar!!.title =
            getString(R.string.menu_notes)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_recycler_view -> {
                switchIcon(item)
                switchRecyclerViewLayout()
                return true
            }
            else -> return false
        }
    }

    private fun switchRecyclerViewLayout() {
        val recyclerViewLayout = RecyclerViewLayoutManager()
        recyclerViewLayout.addRecyclerView(recyclerView)
        val numberOfRows = 2
        val orientation = 1
        if (viewChanged) {
            recyclerView.layoutManager = recyclerViewLayout.setRecyclerView(
                StaggeredRecyclerViewtManager(
                    numberOfRows,
                    orientation
                )
            )
        } else {
            recyclerView.layoutManager = recyclerViewLayout.setRecyclerView(
                LinearRecyclerViewManager(requireContext())
            )
        }
        viewChanged = !viewChanged
    }

    private fun switchIcon(item: MenuItem) {
        if (recycleViewIsLinearLayout) {
            item.setIcon(R.drawable.ic_view_module_black_24dp)
        } else {
            item.setIcon(R.drawable.ic_view_stream_black_24dp)
        }
        recycleViewIsLinearLayout = !recycleViewIsLinearLayout
    }
}