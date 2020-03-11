package com.bridgelabz.fundoonotes.note_module.note_page.view

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bridgelabz.fundoonotes.R

class ColourDialogFragment : DialogFragment() {

    private val recyclerView by lazy {
        requireView().findViewById<RecyclerView>(R.id.colour_recycler_view)
    }
    val colours = ArrayList<Int>().apply {
        add(Color.WHITE)
        add(Color.CYAN)
        add(Color.BLACK)
        add(Color.BLUE)
        add(Color.GRAY)
        add(Color.GREEN)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_colours, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setTitle(getString(R.string.note_colour))
        return dialog
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = ColourAdapter(colours)
        recyclerView.layoutManager = StaggeredGridLayoutManager(3, 1)
    }

}