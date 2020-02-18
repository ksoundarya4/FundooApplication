package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.NoteDbManagerFactory
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.SharedViewModel
import com.bridgelabz.fundoonotes.repository.local_service.DatabaseHelper
import com.bridgelabz.fundoonotes.repository.local_service.note_module.NoteDatabaseManagerImpl

class NoteFragment : Fragment(), RecyclerClickListener {

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

//    private lateinit var actionMode: ActionMode
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

    //    private fun implementRecyclerViewClickListener() {
//        recyclerView.addOnItemTouchListener(
//            RecyclerTouchListener(
//                context = requireActivity(),
//                recyclerView = recyclerView,
//                clickListener = object : RecyclerClickListener {
//                    override fun onClick(view: View, position: Int) {
//                    }
//
//                    override fun onLongClick(view: View, position: Int) {
//                        onItemViewSelected(position)
//                    }
//                }
//            )
//        )
//    }
//
//    private fun onItemViewSelected(position: Int) {
//        noteAdapter.toggleSelection(position)
//        val hasCheckedItem: Boolean = noteAdapter.getSelectedItemCount() > 0
//
//        if (hasCheckedItem) {
//            actionMode = (requireActivity() as AppCompatActivity).startSupportActionMode(
//                ToolbarActionModeCallBack(
//                    requireActivity(),
//                    noteAdapter,
//                    notes
//                )
//            )!!
//        } else if (!hasCheckedItem) {
//            actionMode.finish()
//        }
//        if (actionMode != null) {
//            actionMode.setTitle(noteAdapter.getSelectedItemCount())
//        }
//    }
//fun setActionModeTonull(){
//    if(actionMode != null){
//        actionMode = null
//    }
//}
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

    override fun onClick(note: Note) {
     replaceWithAddNoteFragment(note)
        }

    private fun replaceWithAddNoteFragment(note: Note) {

    }

    override fun onLongClick(note: Note) {
//        Toast.makeText(requireActivity(), "NoteCLicked", Toast.LENGTH_SHORT).show()
    }
}