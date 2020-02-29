package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.recycler_view_strategy.RecyclerViewType
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.recycler_view_strategy.LinearRecyclerViewManager
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.recycler_view_strategy.RecyclerViewLayoutManager
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.recycler_view_strategy.StaggeredRecyclerViewtManager
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.NoteTableManagerFactory
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.SharedViewModel
import com.bridgelabz.fundoonotes.note_module.note_page.view.AddNoteFragment
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteTableManagerImpl

class NoteFragment : Fragment(), OnNoteClickListener {

    private val noteFactory by lazy {
        NoteTableManagerFactory(NoteTableManagerImpl(DatabaseHelper(requireContext())))
    }
    private val sharedViewModel: SharedViewModel by lazy {
        requireActivity().run { ViewModelProvider(this, noteFactory).get(SharedViewModel::class.java) }
    }
    private val recyclerView: RecyclerView by lazy {
        requireView().findViewById<RecyclerView>(R.id.notes_recycler_view)
    }

    private val noteAdapter = NoteViewAdapter(ArrayList(), this)

    private lateinit var notes: ArrayList<Note>

    private var recyclerViewType = RecyclerViewType.ListView

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
        sharedViewModel.getSimpleNoteLiveData().observe(requireActivity(), Observer { observeNotes(it) })
        sharedViewModel.getRecyclerViewType()
            .observe(requireActivity(), Observer { recyclerViewType = it })
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = setRecyclerViewType(recyclerViewType)
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

    private val actionModeCallBack: ActionMode.Callback = object : ActionMode.Callback {
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
        return when (item.itemId) {
            R.id.app_bar_recycler_view -> {
                switchRecyclerViewType()
                switchIcon(item)
                true
            }
            else -> false
        }
    }

    private fun switchIcon(item: MenuItem) {

        if (recyclerViewType == RecyclerViewType.ListView)
            item.setIcon(R.drawable.ic_grid_view_white)
        else {
            item.setIcon(R.drawable.ic_list_view_white)
        }
    }

    private fun switchRecyclerViewType() {

        when (recyclerViewType) {
            RecyclerViewType.GridView -> {
                sharedViewModel.setRecyclerViewType(RecyclerViewType.ListView)
                recyclerView.layoutManager = setRecyclerViewType(recyclerViewType)
            }
            RecyclerViewType.ListView -> {
                sharedViewModel.setRecyclerViewType(RecyclerViewType.GridView)
                recyclerView.layoutManager = setRecyclerViewType(recyclerViewType)
            }
        }
    }

    private fun setRecyclerViewType(viewType: RecyclerViewType): RecyclerView.LayoutManager {
        val layoutManager = RecyclerViewLayoutManager()
        layoutManager.addRecyclerView(recyclerView)

        return when (viewType) {
            RecyclerViewType.ListView -> {
                layoutManager.setRecyclerView(LinearRecyclerViewManager(requireContext()))
            }
            RecyclerViewType.GridView -> {
                layoutManager.setRecyclerView(
                    StaggeredRecyclerViewtManager(
                        numberOfRows = 2,
                        orientation = 1
                    )
                )
            }
        }
    }
}