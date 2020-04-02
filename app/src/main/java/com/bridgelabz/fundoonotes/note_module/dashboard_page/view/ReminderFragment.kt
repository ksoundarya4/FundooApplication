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
import com.bridgelabz.fundoonotes.launch_module.FundooNotesPreference
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note
import com.bridgelabz.fundoonotes.note_module.dashboard_page.view.view_utils.ViewUtils
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.ShareViewModelFactory
import com.bridgelabz.fundoonotes.note_module.dashboard_page.viewmodel.SharedViewModel

class ReminderFragment : Fragment(), OnNoteClickListener {

    private val noteFactory: ShareViewModelFactory by lazy {
        ShareViewModelFactory(requireContext())
    }
    private val sharedViewModel by lazy {
        ViewModelProvider(this, noteFactory).get(SharedViewModel::class.java)
    }
    private val recyclerView: RecyclerView by lazy {
        requireView().findViewById<RecyclerView>(R.id.notes_recycler_view)
    }
    private val preference by lazy {
        FundooNotesPreference.getPreference(requireContext())
    }
    private val accessToken by lazy {
        preference.getString("access_token", "")
    }
    private lateinit var note: Note
    private val noteAdapter = NoteViewAdapter(ArrayList(), this)
    private val reminderNotes = ArrayList<Note>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        ViewUtils.setActionBarTitle(
            (requireActivity() as AppCompatActivity),
            getString(R.string.app_bar_title_reminder_notes)
        )
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getNoteArguments()
        sharedViewModel.getNoteLiveData(userId = note.userId!!)
            .observe(requireActivity(), Observer { observeArchiveNotes(it) })
        initRecyclerView()
    }

    private fun getNoteArguments() {
        if (arguments != null) {
            note = arguments!!.get(getString(R.string.note)) as Note
            Log.d("noteBundle", note.toString())
        }
    }

    private fun initRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = noteAdapter
    }

    private fun observeArchiveNotes(noteList: ArrayList<Note>) {
        val notes = noteList
        for (note in notes) {
            if (!note.reminder.isNullOrEmpty())
                reminderNotes.add(note)
        }
        noteAdapter.setListOfNotes(reminderNotes)
        noteAdapter.notifyDataSetChanged()
    }

    override fun onClick(adapterPosition: Int) {
    }

    override fun onLongClick(adapterPosition: Int) {

    }
}