package com.bridgelabz.fundoonotes.note_module.note_page.view

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bridgelabz.fundoonotes.R

class ColourDialogFragment : DialogFragment(), OnColourClickListener {

    private val recyclerView by lazy {
        requireView().findViewById<RecyclerView>(R.id.colour_recycler_view)
    }
    private val colourArray by lazy {
        resources.getStringArray(R.array.array_of_colour)
    }
    private val colours = ArrayList<Int>()

    private val onColourlistener by lazy {
        targetFragment as OnColourListener
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
        getColours()
        initRecyclerView()
    }

    private fun getColours() {
        for (colour in colourArray) {
            Log.d("colour", colour.toString())
            val color = Color.parseColor(colour)
            colours.add(color)
        }
    }

    private fun initRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = ColourAdapter(colours, this)
        recyclerView.layoutManager = StaggeredGridLayoutManager(4, 1)
    }

    override fun onClick(position: Int) {
        Log.d("ColourFragmentClick", position.toString())
        val colour = colours[position]
        onColourlistener.onColourSubmit(colour)
        dialog!!.cancel()
    }
}