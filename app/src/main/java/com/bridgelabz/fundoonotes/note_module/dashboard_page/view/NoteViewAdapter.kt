package com.bridgelabz.fundoonotes.note_module.dashboard_page.view

import android.view.*
import android.widget.TextView
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.fundoonotes.R
import com.bridgelabz.fundoonotes.note_module.dashboard_page.model.Note


class NoteViewAdapter(private var notes: List<Note>) :
    RecyclerView.Adapter<NoteViewAdapter.NoteViewHolder>() {

    private var multiSelect = false
    private val selectedItems = ArrayList<Int>()

    val actionModeCallBacks: ActionMode.Callback =
        object : ActionMode.Callback {
            override fun onCreateActionMode(
                mode: ActionMode?,
                menu: Menu?
            ): Boolean {
                multiSelect = true
                menu?.add("Delete")
                return true
            }

            override fun onPrepareActionMode(
                mode: ActionMode?,
                menu: Menu?
            ): Boolean {
                return false
            }

            override fun onActionItemClicked(
                mode: ActionMode?,
                item: MenuItem?
            ): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode) {}
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.note_list, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.title.text = note.tile
        holder.description.text = note.description
    }

    fun setListOfNotes(notes: List<Note>) {
        this.notes = notes
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.text_view_note_title)
        val description: TextView = itemView.findViewById(R.id.text_view_note_description)
    }
}